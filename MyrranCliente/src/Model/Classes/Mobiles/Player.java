package Model.Classes.Mobiles;// Created by Hanto on 10/04/2014.

import Core.Cuerpos.BodyFactory;
import Core.Cuerpos.Cuerpo;
import Core.FSM.IO.PlayerIO;
import Core.FSM.MaquinaEstados;
import Core.FSM.MaquinaEstadosFactory;
import Core.Skills.SpellPersonalizado;
import DB.DAO;
import DTO.NetDTO;
import DTO.NetPlayerCliente;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Maquinable;
import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.Geo.MapaI;
import Interfaces.Input.PlayerIOI;
import Interfaces.Model.AbstractModel;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.DTO.PlayerDTO;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Player extends AbstractModel implements MobPlayer, CasterPersonalizable, Debuffeable, Maquinable
{
    protected Mundo mundo;
    protected int connectionID;
    protected MapaI mapaI;

    protected float x = 0.0f;
    protected float y = 0.0f;
    protected int numAnimacion = 5;

    protected float velocidadMax = 80.0f;
    protected float velocidadMod = 1.0f;

    protected String nombre;
    protected int nivel;

    protected float actualHPs;
    protected float maxHPs;

    protected boolean castearInterrumpible = false;
    protected String spellIDSeleccionado;
    protected Object parametrosSpell;
    protected float actualCastingTime = 0.0f;
    protected float totalCastingTime = 0.0f;

    protected Array<AuraI> listaDeAuras = new Array<>();
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected Cuerpo cuerpo;
    protected MaquinaEstados fsm;
    protected PlayerIO input = new PlayerIO();
    protected PlayerIO output = new PlayerIO();

    protected NetPlayerCliente netPlayer = new NetPlayerCliente();

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Player(Mundo mundo)
    {
        this.mundo = mundo;

        fsm = MaquinaEstadosFactory.PLAYER.nuevo(this);
        cuerpo = new Cuerpo(mundo.getWorld(), 48, 48);
        BodyFactory.darCuerpo.RECTANGULAR.nuevo(cuerpo);
        cuerpo.setPosition(x, y);
    }

    //GET:
    public Body getBody()                                               { return cuerpo.getBody(); }
    public NetPlayerCliente getNetPlayer()                                     { return netPlayer; }
    @Override public int getConnectionID()                              { return connectionID; }
    @Override public float getX()                                       { return x; }
    @Override public float getY()                                       { return y; }
    @Override public int getNumAnimacion()                              { return numAnimacion; }
    @Override public float getVelocidadMod()                            { return velocidadMod; }
    @Override public float getVelocidadMax()                            { return velocidadMax; }
    @Override public String getNombre()                                 { return nombre; }
    @Override public int getNivel()                                     { return nivel; }
    @Override public float getActualHPs()                               { return actualHPs; }
    @Override public float getMaxHPs()                                  { return maxHPs; }
    @Override public MapaI getMapa()                                    { return mapaI; }
    @Override public boolean isCasteando()                              { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                       { return actualCastingTime; }
    @Override public float getTotalCastingTime()                        { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                    { return spellIDSeleccionado; }
    @Override public Object getParametrosSpell()                        { return parametrosSpell; }
    @Override public PlayerIOI getInput()                               { return input; }
    @Override public PlayerIOI getOutput()                              { return output; }

    //SET:
    @Override public void setConnectionID (int connectionID)            { this.connectionID = connectionID; }
    @Override public void setNivel (int nivel)                          { this.nivel = nivel; }
    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }
    @Override public void setVectorDireccion(float x, float y)          { cuerpo.setVectorDireccion(x, y); }
    @Override public void setTotalCastingTime(float castingTime)        { this.actualCastingTime = 0.01f; totalCastingTime = castingTime;}
    @Override public void setVelocidaMod(float velocidadMod)            { this.velocidadMod = velocidadMod; }
    @Override public void setVelocidadMax(float velocidadMax)           { this.velocidadMax = velocidadMax; }              { }
    @Override public void añadirAura(AuraI aura)                        { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                      { listaDeAuras.removeValue(aura, true); }
    @Override public Iterator<AuraI> getAuras()                         { return listaDeAuras.iterator(); }
    @Override public void setActualHPs (float hps)                      { modificarHPs(hps - actualHPs); }
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID){ return listaSpellsPersonalizados.get(spellID); }
    public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado(){ return listaSpellsPersonalizados.values().iterator(); }

    //RECEPCION DATOS:
    //-------------------------------------------------------------------------------------------------------------------

    @Override public void añadirSkillsPersonalizados(String spellID)
    {
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(spellID);
        if (spell == null) { logger.error("ERROR: añadirSkillsPersonalizados: spellID no encontrado: {}", spellID ); return; }

        SpellPersonalizado spellPersonalizado = new SpellPersonalizado(spell);
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

    //NOTITIFACION VISTA:
    //-------------------------------------------------------------------------------------------------------------------

    @Override public void modificarHPs(float HPs)
    {   //Vista:
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0f;
        Object modificarHPs = new NetDTO.ModificarHPsPPC(this, HPs);
        notificarActualizacion("RECEPCION: modificarHPs", null, modificarHPs);
    }

    @Override public void setNombre (String nombre)
    {   //Vista:
        this.nombre = nombre;
        notificarActualizacion("RECEPCION: setNombre", null, new NetPlayerCliente.Nombre(nombre));
    }

    @Override public void setMaxHPs (float mHps)
    {   //Vista:
        maxHPs = mHps;
        Object mHPsDTO = new PlayerDTO.MaxHPs(mHps);
        notificarActualizacion("RECEPCION: setMaxHPs", null, mHPsDTO);
    }

    private void actualizarCastingTime(float delta)
    {   //Vista:
        if (isCasteando())
        {
            actualCastingTime += delta;
            if (actualCastingTime >= totalCastingTime)
            {
                actualCastingTime = 0f;
                totalCastingTime = 0f;
            }
            Object castingTimePercent = new NetDTO.CastingTimePercent(this);
            notificarActualizacion("actualizarCastingTime", null, castingTimePercent);
        }
    }

    //ENVIO DATOS:
    //-------------------------------------------------------------------------------------------------------------------


    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   //Servidor:
        netPlayer.setNumTalentosSkillPersonalizado(skillID, statID, valor);
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        //Servidor:
        netPlayer.setNumAnimacion(numAnimacion);
        //Vista:
        notificarActualizacion("ENVIO: setTipoAnimacion", null, netPlayer.animacion);
    }

    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.x = x;
        this.y = y;
        //Servidor:
        netPlayer.setPosition(getX(), getY());
        //Vista:
        notificarActualizacion("ENVIO: setPosition", null, netPlayer.posicion);
    }

    private void getPosicionInterpoladaCuerpo()
    {
        this.x = cuerpo.getXinterpolada();
        this.y = cuerpo.getYinterpolada();
        //Servidor:
        netPlayer.setPosition(getX(), getY());
        //Vista:
        notificarActualizacion("ENVIO: setPosition", null, netPlayer.posicion);
    }

    @Override public void setParametrosSpell(Object parametros)
    {
        parametrosSpell = parametros;
        //Servidor:
        netPlayer.setParametrosSpell(parametros);
    }

    @Override public void setSpellIDSeleccionado(String spellID)
    {
        spellIDSeleccionado = spellID;
        //Servidor:
        netPlayer.setSpellIDSeleccionado(spellID, getParametrosSpell());
    }

    private void stopCastear()
    {
        if (castearInterrumpible)
        {
            castearInterrumpible = false;
            //Servidor:
            netPlayer.setStopCastear(output.getScreenX(), output.getScreenY());
        }
    }

    private void startCastear()
    {
        if (!isCasteando())
        {
            SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(output.getSpellID());
            if (spell != null)
            {
                castearInterrumpible = true;
                //Servidor:
                netPlayer.setStartCastear(output.getScreenX(), output.getScreenY());
                //actualCastingTime += 0.01f;
            }
        }
    }

    private void moverse ()
    {
        cuerpo.setLinearVelocity(velocidadMax);
        if      (output.irAbajo && !output.irDerecha && !output.irIzquierda)    { cuerpo.setVectorDireccion(  0,      -1); }      //Sur
        else if (output.irArriba && !output.irDerecha && !output.irIzquierda)   { cuerpo.setVectorDireccion(  0,      +1); }      //Norte
        else if (output.irDerecha && !output.irArriba && !output.irAbajo)       { cuerpo.setVectorDireccion( +1,       0); }      //Este
        else if (output.irIzquierda && !output.irArriba && !output.irAbajo)     { cuerpo.setVectorDireccion( -1,       0); }      //Oeste
        else if (output.irAbajo&& output.irIzquierda)                           { cuerpo.setVectorDireccion( -0.707f, -0.707f); } //SurOeste
        else if (output.irAbajo && output.irDerecha)                            { cuerpo.setVectorDireccion( +0.707f, -0.707f); } //SurEste
        else if (output.irArriba && output.irIzquierda)                         { cuerpo.setVectorDireccion( -0.707f, +0.707f); } //NorOeste
        else if (output.irArriba && output.irDerecha)                           { cuerpo.setVectorDireccion( +0.707f, +0.707f); } //NorEste
        else if (!output.irAbajo && !output.irArriba && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.setLinearVelocity(0f); }
    }

    public void actualizar (float delta)
    {
        fsm.actualizar(delta);
        actualizarCastingTime(delta);
        moverse();
        setNumAnimacion(output.getNumAnimacion());
        setSpellIDSeleccionado(output.getSpellID());
        if (output.getStartCastear()) startCastear();
        else if (output.getStopCastear()) stopCastear();
    }

    public void copiarUltimaPosicion()
    {   cuerpo.copiarUltimaPosicion(); }

    public void interpolarPosicion(float alpha)
    {
        cuerpo.interpolarPosicion(alpha);
        getPosicionInterpoladaCuerpo();
    }
}
