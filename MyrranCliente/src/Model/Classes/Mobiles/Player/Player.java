package Model.Classes.Mobiles.Player;// Created by Hanto on 10/04/2014.

import DB.DAO;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.GameState.MundoI;
import Interfaces.Input.PlayerIOI;
import Interfaces.Model.AbstractModel;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Cuerpos.Cuerpo;
import Model.FSM.IO.PlayerIO;
import Model.FSM.MaquinaEstados;
import Model.FSM.MaquinaEstadosFactory;
import Model.Settings;
import Model.Skills.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Player extends AbstractModel implements PlayerI, Debuffeable, MaquinablePlayer
{
    protected int connectionID;
    protected int iDProyectiles = 0;

    protected int ultimoMapTileX;
    protected int ultimoMapTileY;
    //protected float x = 0.0f;
    //protected float y = 0.0f;
    protected Vector2 velocidad = new Vector2();
    protected int numAnimacion = 5;
    protected float velocidadMax = 80.0f;
    protected float velocidadMod = 1.0f;
    protected String nombre;
    protected int nivel;
    protected float actualHPs;
    protected float maxHPs;

    protected String spellIDSeleccionado;
    protected Object parametrosSpell;
    protected float actualCastingTime = 0.0f;
    protected float totalCastingTime = 0.0f;
    protected Array<AuraI> listaDeAuras = new Array<>();
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected Cuerpo cuerpo;
    protected PlayerNotificador notificador;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //Atributos exclusivos del Cliente:
    protected boolean castearInterrumpible = false;
    protected MaquinaEstados fsm;
    protected PlayerIO input = new PlayerIO();
    protected PlayerIO output = new PlayerIO();

    //
    //------------------------------------------------------------------------------------------------------------------

    //GET:
    @Override public int getID()                                        { return connectionID; }
    @Override public void setID(int iD)                                 { this.connectionID = iD; }

    @Override public float getX()                                       { return cuerpo.getXinterpolada(); }
    @Override public float getY()                                       { return cuerpo.getYinterpolada(); }
    @Override public int getUltimoMapTileX()                            { return ultimoMapTileX; }
    @Override public int getUltimoMapTileY()                            { return ultimoMapTileY; }
    @Override public Vector2 getVelocidad()                             { return velocidad; }
    @Override public int getMapTileX()                                  { return (int)(cuerpo.getXinterpolada() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                                  { return (int)(cuerpo.getYinterpolada() / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getNumAnimacion()                              { return numAnimacion; }
    @Override public float getVelocidadMod()                            { return velocidadMod; }

    @Override
    public float getVelocidadAngular()
    {
        return 0;
    }

    @Override
    public float getVelocidadAngularMax()
    {
        return 0;
    }

    @Override
    public float getAceleracionAngularMax()
    {
        return 0;
    }

    @Override public float getVelocidadMax()                            { return velocidadMax; }

    @Override
    public float getAceleracionMax()
    {
        return 0;
    }

    @Override public String getNombre()                                 { return nombre; }
    @Override public int getNivel()                                     { return nivel; }
    @Override public float getActualHPs()                               { return actualHPs; }
    @Override public float getMaxHPs()                                  { return maxHPs; }
    @Override public boolean isCasteando()                              { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                       { return actualCastingTime; }
    @Override public float getTotalCastingTime()                        { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                    { return spellIDSeleccionado; }
    @Override public Object getParametrosSpell()                        { return parametrosSpell; }
    @Override public PlayerIOI getInput()                               { return input; }
    @Override public PlayerIOI getOutput()                              { return output; }
    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }
    public PlayerNotificador getNotificador()                           { return notificador; }

    //SET:
    @Override public void setUltimoMapTile (int x, int y)               { ultimoMapTileX = x; ultimoMapTileY = y; }
    @Override public void setNivel (int nivel)                          { this.nivel = nivel; }
    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }
    @Override public void setTotalCastingTime(float castingTime)        { this.actualCastingTime = 0.01f; totalCastingTime = castingTime;}
    @Override public void setVelocidaMod(float velocidadMod)            { this.velocidadMod = velocidadMod; }

    @Override
    public void setVelocidadAngular(float velocidadAngular)
    {

    }

    @Override
    public void setVelocidadAngularMax(float velocidadAngularMax)
    {

    }

    @Override
    public void setAceleracionAngularMax(float aceleracionAngularMax)
    {

    }

    @Override public void setVelocidadMax(float velocidadMax)           { this.velocidadMax = velocidadMax; }

    @Override
    public void setAceleracionMax(float aceleracionMax)
    {

    }

    { }
    @Override public void añadirAura(AuraI aura)                        { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                      { listaDeAuras.removeValue(aura, true); }
    @Override public Iterator<AuraI> getAuras()                         { return listaDeAuras.iterator(); }
    @Override public void setActualHPs (float hps)                      { modificarHPs(hps - actualHPs); }
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID){ return listaSpellsPersonalizados.get(spellID); }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado(){ return listaSpellsPersonalizados.values().iterator(); }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado(){ return listaSkillsPersonalizados.values().iterator(); }

    //Constructor:
    public Player(Cuerpo cuerpo)
    {
        this.cuerpo = cuerpo;
        this.notificador = new PlayerNotificador(this);
        this.fsm = MaquinaEstadosFactory.PLAYER.nuevo(this);
        cuerpo.setPosition(0, 0);
    }

    @Override public void dispose()
    {   cuerpo.dispose();   }

    //RECEPCION DATOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirSkillsPersonalizados(String spellID)
    {
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(spellID);
        if (spell == null) { logger.error("ERROR: añadirSkillsPersonalizados: spellID no encontrado: {}", spellID ); return; }
        else logger.debug("Añadido Spell: {}", spell.getID());

        SpellPersonalizadoI spellPersonalizado = new SpellPersonalizado(spell);
        listaSpellsPersonalizados.put(spellPersonalizado.getID(), spellPersonalizado);

        listaSkillsPersonalizados.put(spellPersonalizado.getCustomSpell().getID(), spellPersonalizado.getCustomSpell());
        Iterator<SkillPersonalizadoI> iterator = spellPersonalizado.getIteratorCustomDebuffs();
        while(iterator.hasNext())
        {
            SkillPersonalizadoI skillPersonalizado = iterator.next();
            listaSkillsPersonalizados.put(skillPersonalizado.getID(), skillPersonalizado);
        }
    }

    public void setNumTalentosSkillPersonalizadoFromServer(String skillID, int statID, int valor)
    {
        SkillPersonalizadoI skillPersonalizado = listaSkillsPersonalizados.get(skillID);
        if (skillPersonalizado == null) { logger.error("ERROR: setNumTalentosSkillPersonalizado, spellID no existe: {}", skillID); return; }
        skillPersonalizado.setNumTalentos(statID, valor);
    }

    //  NOTITIFACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    //Vista:
    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0f;
        notificador.setModificarHPs(HPs);
    }

    //Vista:
    @Override public void setNombre (String nombre)
    {
        this.nombre = nombre;
        notificador.setNombre(nombre);
    }

    //Vista:
    @Override public void setMaxHPs (float mHps)
    {
        maxHPs = mHps;
        notificador.setMaxHPs(maxHPs);
    }

    //Vista:
    private void actualizarCastingTime(float delta)
    {
        if (isCasteando())
        {
            actualCastingTime += delta;
            if (actualCastingTime >= totalCastingTime)
            {
                actualCastingTime = 0f;
                totalCastingTime = 0f;
            }
            notificador.setCastingTime(this);
        }
    }

    //  ENVIO DATOS: (y en algunos casos notificacion a la vista)
    //------------------------------------------------------------------------------------------------------------------

    //Vista - Servidor:
    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificador.setNumAnimacion(numAnimacion);
    }

    //Vista - Servidor:
    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);
        //this.x = x;
        //this.y = y;
        notificador.setPosition(x, y);
    }

    //Vista - Servidor:
    private void getPosicionInterpoladaCuerpo()
    {
        //this.x = cuerpo.getXinterpolada();
        //this.y = cuerpo.getYinterpolada();
        notificador.setPosition(cuerpo.getXinterpolada(), cuerpo.getYinterpolada());
    }

    //Servidor:
    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   notificador.setNumTalentosSkillPersonalizado(skillID, statID, valor); }

    //Servidor:
    @Override public void setParametrosSpell(Object parametros)
    {
        parametrosSpell = parametros;
        notificador.setParametrosSpell(parametros);
    }

    //Servidor:
    @Override public void setSpellIDSeleccionado(String spellID)
    {
        spellIDSeleccionado = spellID;
        notificador.setSpellIDSeleccionado(spellID, getParametrosSpell());
    }

    //Servidor:
    private void stopCastear()
    {
        if (castearInterrumpible)
        {
            castearInterrumpible = false;

            //TODO PRUEBA
            notificador.setStopCastear(output.mundoX, output.mundoY);
            //notificador.setStopCastear(output.getScreenX(), output.getScreenY());
        }
    }

    //Servidor:
    private void startCastear(MundoI mundo)
    {
        if (!isCasteando())
        {
            SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(output.getSpellID());
            if (spell != null)
            {
                //TODO PRUEBA
                spell.castear(this, output.mundoX, output.mundoY, mundo);
                //spell.castear(this, output.getScreenX(), output.getScreenY(), mundo);
                castearInterrumpible = true;
                notificador.setStartCastear(output.mundoX, output.mundoY);
                //notificador.setStartCastear(output.getScreenX(), output.getScreenY());
            }
        }
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getIDProyectiles()
    {
        if (iDProyectiles++ >= (Math.pow(2, 20))) { iDProyectiles = 0; }
        return iDProyectiles;
    }

    // METODOS DE ACTUALIZACION
    //------------------------------------------------------------------------------------------------------------------

    private void moverse ()
    {
        if      (output.irAbajo && !output.irDerecha && !output.irIzquierda)    { cuerpo.setDireccionNorVelocidad(0, -1, velocidadMax * velocidadMod); }            //Sur
        else if (output.irArriba && !output.irDerecha && !output.irIzquierda)   { cuerpo.setDireccionNorVelocidad(0, +1, velocidadMax * velocidadMod); }               //Norte
        else if (output.irDerecha && !output.irArriba && !output.irAbajo)       { cuerpo.setDireccionNorVelocidad(+1, 0, velocidadMax * velocidadMod); }               //Este
        else if (output.irIzquierda && !output.irArriba && !output.irAbajo)     { cuerpo.setDireccionNorVelocidad(-1, 0, velocidadMax * velocidadMod); }               //Oeste
        else if (output.irAbajo&& output.irIzquierda)                           { cuerpo.setDireccionNorVelocidad(-0.707f, -0.707f, velocidadMax * velocidadMod); }    //SurOeste
        else if (output.irAbajo && output.irDerecha)                            { cuerpo.setDireccionNorVelocidad(+0.707f, -0.707f, velocidadMax * velocidadMod); }    //SurEste
        else if (output.irArriba && output.irIzquierda)                         { cuerpo.setDireccionNorVelocidad(-0.707f, +0.707f, velocidadMax * velocidadMod); }    //NorOeste
        else if (output.irArriba && output.irDerecha)                           { cuerpo.setDireccionNorVelocidad(+0.707f, +0.707f, velocidadMax * velocidadMod); }    //NorEste
        else if (!output.irAbajo && !output.irArriba && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.setVelocidad(0f); }
    }

    public void actualizar (float delta, MundoI mundo)
    {
        fsm.actualizar(delta);
        actualizarCastingTime(delta);
        moverse();
        setNumAnimacion(output.getNumAnimacion());
        setSpellIDSeleccionado(output.getSpellID());
        if (output.getStartCastear()) startCastear(mundo);
        else if (output.getStopCastear()) stopCastear();
    }

    @Override public void copiarUltimaPosicion()
    {   cuerpo.copiarUltimaPosicion(); }

    @Override public void interpolarPosicion(float alpha)
    {
        cuerpo.interpolarPosicion(alpha);
        getPosicionInterpoladaCuerpo();
    }

    //
    //-----------------------------------------------------------------------------------------------------------------

    @Override public void setCastear(boolean castear, int screenX, int screenY)
    {
        output.setScreenX(screenX);
        output.setScreenY(screenY);
        if (castear) { output.setStartCastear(true); output.setStopCastear(false); }
        else { output.setStopCastear(true); output.setStartCastear(false); }
    }

    @Override
    public Vector2 getPosition()
    {
        return null;
    }

    @Override
    public float getOrientation()
    {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return null;
    }

    @Override
    public float getAngularVelocity()
    {
        return 0;
    }

    @Override
    public float getBoundingRadius()
    {
        return 0;
    }

    @Override
    public boolean isTagged()
    {
        return false;
    }

    @Override
    public void setTagged(boolean tagged)
    {

    }

    @Override
    public Vector2 newVector()
    {
        return null;
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        return null;
    }

    @Override
    public float getMaxLinearSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {

    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {

    }

    @Override
    public float getMaxAngularSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {

    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {

    }
}
