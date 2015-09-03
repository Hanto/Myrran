package Model.Classes.Mobiles.Player;// Created by Hanto on 10/04/2014.

import DB.DAO;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.GameState.MundoI;
import Interfaces.Input.PlayerIOI;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.AI.FSM.IO.PlayerIO;
import Model.AI.FSM.MaquinaEstados;
import Model.AI.FSM.MaquinaEstadosFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import Model.Skills.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.utils.Array;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Player extends PlayerNotificador implements PlayerI, Debuffeable, MaquinablePlayer
{
    protected int iD;                                                                       // Identificable:
    protected int numAnimacion = 5;                                                         // Animable:
    protected float actualHPs;                                                              // Vulnerable:
    protected float maxHPs;
    protected Cuerpo cuerpo;                                                                // Corporeo:
    protected int iDProyectiles = 0;                                                        // PCStats:
    protected String nombre;
    protected int nivel;
    protected float actualCastingTime = 0.0f;                                               // Caster:
    protected float totalCastingTime = 0.0f;
    protected String spellIDSeleccionado;
    protected Object parametrosSpell;
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>(); // CasterPersonalizado:
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();
    protected Array<AuraI> listaDeAuras = new Array<>();                                    // Debuffeable:
    protected boolean castearInterrumpible = false;                                         // Atributos Cliente:
    protected MaquinaEstados fsm;
    protected PlayerIO input = new PlayerIO();
    protected PlayerIO output = new PlayerIO();
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());            // Logger:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return iD; }
    @Override public void setID(int iD)                                 { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }

    // COLISION CALLBACKS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable) {}
    @Override  public void checkColisionesConMuro() {}

    // ANIMABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getNumAnimacion()                              { return numAnimacion; }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                               { return actualHPs; }
    @Override public float getMaxHPs()                                  { return maxHPs; }
    @Override public void setActualHPs (float hps)                      { modificarHPs(hps - actualHPs); }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }

    // PCSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public String getNombre()                                 { return nombre; }
    @Override public int getNivel()                                     { return nivel; }
    @Override public void setNivel (int nivel)                          { this.nivel = nivel; }

    // CASTER:
    //------------------------------------------------------------------------------------------------------------------

    @Override public boolean isCasteando()                              { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                       { return actualCastingTime; }
    @Override public float getTotalCastingTime()                        { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                    { return spellIDSeleccionado; }
    @Override public Object getParametrosSpell()                        { return parametrosSpell; }
    @Override public void setTotalCastingTime(float castingTime)        { this.actualCastingTime = 0.01f; totalCastingTime = castingTime;}

    // CASTERPERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID){ return listaSpellsPersonalizados.get(spellID); }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado(){ return listaSpellsPersonalizados.values().iterator(); }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado(){ return listaSkillsPersonalizados.values().iterator(); }

    // DEBUFFEABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirAura(AuraI aura)                        { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                      { listaDeAuras.removeValue(aura, true); }
    @Override public Iterator<AuraI> getAuras()                         { return listaDeAuras.iterator(); }

    // INPUT:
    //------------------------------------------------------------------------------------------------------------------

    @Override public PlayerIOI getInput()                               { return input; }
    @Override public PlayerIOI getOutput()                              { return output; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Player(Cuerpo cuerpo)
    {
        velocidadMax = 80f;
        this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
        this.fsm = MaquinaEstadosFactory.PLAYER.nuevo(this);
        cuerpo.setPosition(0, 0);
        cuerpo.setCalculosInterpolados(true);
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
        notificarSetModificarHPs(HPs);
    }

    //Vista:
    @Override public void setNombre (String nombre)
    {
        this.nombre = nombre;
        notificarSetNombre();
    }

    //Vista:
    @Override public void setMaxHPs (float mHps)
    {
        maxHPs = mHps;
        notificarSetMaxHPs();
    }

    //Vista:
    @Override public void actualizarCastingTime(float delta)
    {
        if (isCasteando())
        {
            actualCastingTime += delta;
            if (actualCastingTime >= totalCastingTime)
            {
                actualCastingTime = 0f;
                totalCastingTime = 0f;
            }
            notificarSetCastingTime();
        }
    }

    //  ENVIO DATOS: (y en algunos casos notificacion a la vista)
    //------------------------------------------------------------------------------------------------------------------

    //Vista - Servidor:
    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificarSetNumAnimacion();
    }

    //Vista - Servidor:
    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);
        posicion.set(x, y);
        notificarSetPosition();
    }

    //Vista - Servidor:
    private void getPosicionInterpoladaCuerpo()
    {
        posicion.set(cuerpo.getX(), cuerpo.getY());
        notificarSetPosition();
    }

    //Servidor:
    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   notificarSetNumTalentosSkillPersonalizado(skillID, statID, valor); }

    //Servidor:
    @Override public void setParametrosSpell(Object parametros)
    {
        parametrosSpell = parametros;
        notificarSetParametrosSpell();
    }

    //Servidor:
    @Override public void setSpellIDSeleccionado(String spellID)
    {
        spellIDSeleccionado = spellID;
        notificarSetSpellIDSeleccionado();
    }

    //Servidor:
    private void stopCastear()
    {
        if (castearInterrumpible)
        {
            castearInterrumpible = false;

            //TODO PRUEBA
            notificarSetStopCastear(output.mundoX, output.mundoY);
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
                notificarSetStartCastear(output.mundoX, output.mundoY);
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

    private void setRumbo()
    {
        if      (output.irAbajo && !output.irDerecha && !output.irIzquierda)    { cuerpo.setDireccionNorVelocidad(0, -1, velocidadMax * velocidadMod); }                //Sur
        else if (output.irArriba && !output.irDerecha && !output.irIzquierda)   { cuerpo.setDireccionNorVelocidad(0, +1, velocidadMax * velocidadMod); }               //Norte
        else if (output.irDerecha && !output.irArriba && !output.irAbajo)       { cuerpo.setDireccionNorVelocidad(+1, 0, velocidadMax * velocidadMod); }               //Este
        else if (output.irIzquierda && !output.irArriba && !output.irAbajo)     { cuerpo.setDireccionNorVelocidad(-1, 0, velocidadMax * velocidadMod); }               //Oeste
        else if (output.irAbajo && output.irIzquierda)                          { cuerpo.setDireccionNorVelocidad(-0.707f, -0.707f, velocidadMax * velocidadMod); }    //SurOeste
        else if (output.irAbajo && output.irDerecha)                            { cuerpo.setDireccionNorVelocidad(+0.707f, -0.707f, velocidadMax * velocidadMod); }    //SurEste
        else if (output.irArriba && output.irIzquierda)                         { cuerpo.setDireccionNorVelocidad(-0.707f, +0.707f, velocidadMax * velocidadMod); }    //NorOeste
        else if (output.irArriba && output.irDerecha)                           { cuerpo.setDireccionNorVelocidad(+0.707f, +0.707f, velocidadMax * velocidadMod); }    //NorEste
        else if (!output.irAbajo && !output.irArriba && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.setVelocidad(0f); }
    }

    // METODOS DE ACTUALIZACION
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarAuras(float delta)  {}
    @Override public void actualizarTimers(float delta)
    {   actualizarCastingTime(delta); }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {    }

    @Override public void actualizarIA (float delta, MundoI mundo)
    {
        fsm.actualizar(delta);
        setNumAnimacion(output.getNumAnimacion());

        setRumbo();

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
}
