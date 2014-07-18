package Model.Classes.Mobiles;// Created by Hanto on 10/04/2014.

import Core.Cuerpos.BodyFactory;
import Core.Cuerpos.ObjetoDinamico;
import Core.FSM.IO.PlayerIO;
import Core.FSM.MaquinaEstados;
import Core.FSM.MaquinaEstadosFactory;
import Core.Skills.SpellPersonalizado;
import DB.DAO;
import DTO.NetDTO;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.CasterConTalentos;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Maquinable;
import Interfaces.EntidadesPropiedades.Vulnerable;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Data.Settings.PIXEL_METROS;

public class Player extends AbstractModel implements MobPlayer, CasterConTalentos, Vulnerable, Debuffeable, Maquinable
{
    protected Mundo mundo;

    protected int connectionID;
    protected MapaI mapaI;                         //mapaI al que pertecene el Player

    protected float x = 0.0f;                      //Coordenadas X:
    protected float y = 0.0f;                      //Coordenadas Y:

    protected int numAnimacion = 5;

    protected float velocidadMax = 80.0f;
    protected float velocidadMod = 1.0f;
    protected double direccion;

    protected String nombre;
    protected int nivel;

    protected float actualHPs;
    protected float maxHPs;

    protected boolean castear = false;
    protected boolean castearInterrumpible = false;

    protected float actualCastingTime = 0.0f;
    protected float totalCastingTime = 0.0f;
    protected Object parametrosSpell;

    protected Array<AuraI> listaDeAuras = new Array<>();
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected ObjetoDinamico cuerpo;
    protected MaquinaEstados fsm;
    protected PlayerIO input = new PlayerIO();
    protected PlayerIO output = new PlayerIO();

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Player(Mundo mundo)
    {
        this.mundo = mundo;
        fsm = MaquinaEstadosFactory.PLAYER.nuevo(this);
        cuerpo = new ObjetoDinamico(mundo.getWorld(), 48, 48);
        BodyFactory.darCuerpo.RECTANGULAR.nuevo(cuerpo);
        cuerpo.setPosition(x, y);
    }

    //GET:
    public int getTimestamp()                                           { return cuerpo.getTimeStamp(); }
    public ObjetoDinamico getObjetoDinamico()                           { return cuerpo; }
    @Override public int getConnectionID()                              { return connectionID; }
    @Override public float getX()                                       { return x; }
    @Override public float getY()                                       { return y; }
    @Override public int getNumAnimacion()                              { return numAnimacion; }
    @Override public float getVelocidadMod()                            { return velocidadMod; }
    @Override public float getVelocidadMax()                            { return velocidadMax; }
    @Override public double getDireccion()                              { return direccion; }
    @Override public String getNombre()                                 { return nombre; }
    @Override public int getNivel()                                     { return nivel; }
    @Override public float getActualHPs()                               { return actualHPs; }
    @Override public float getMaxHPs()                                  { return maxHPs; }
    @Override public MapaI getMapa()                                    { return mapaI; }
    @Override public boolean isCasteando()                              { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                       { return actualCastingTime; }
    @Override public float getTotalCastingTime()                        { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                    { return output.getSpellID(); }
    @Override public Object getParametrosSpell()                        { return parametrosSpell; }
    @Override public PlayerIOI getInput()                               { return input; }
    @Override public PlayerIOI getOutput()                              { return output; }

    //SET:
    public void setTimestamp(int timestamp)                             { cuerpo.setTimeStamp(timestamp); }
    @Override public void setConnectionID (int connectionID)            { this.connectionID = connectionID; }
    @Override public void setTotalCastingTime(float castingTime)        { this.actualCastingTime = 0.01f; totalCastingTime = castingTime;}
    @Override public void setVelocidaMod(float velocidadMod)            { this.velocidadMod = velocidadMod; }
    @Override public void setVelocidadMax(float velocidadMax)           { this.velocidadMax = velocidadMax; }
    @Override public void setDireccion(double direccion)                { }
    @Override public void añadirAura(AuraI aura)                        { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                      { listaDeAuras.removeValue(aura, true); }
    @Override public Iterator<AuraI> getAuras()                         { return listaDeAuras.iterator(); }
    @Override public void setActualHPs (float hps)                      { modificarHPs(hps - actualHPs); }
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID){ return listaSpellsPersonalizados.get(spellID); }

    //RECEPCION DATOS:
    @Override public void añadirSkillsPersonalizados(String spellID)
    {
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(spellID);
        if (spell == null) { System.out.println("ERROR: añadirSkillsPersonalizados: spellID no encontrado: " + spellID ); return; }

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

    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {
        SkillPersonalizadoI skillPersonalizado = listaSkillsPersonalizados.get(skillID);
        if (skillPersonalizado == null) { System.out.println("ERROR: setNumTalentosSkillPersonalizado, spellID no existe: " + skillID); return; }
        skillPersonalizado.setNumTalentos(statID, valor);
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0f;
        Object modificarHPs = new NetDTO.ModificarHPsPPC(this, HPs);
        notificarActualizacion("RECEPCION: modificarHPs", null, modificarHPs);
    }

    @Override public void setNombre (String nombre)
    {
        this.nombre = nombre;
        Object nombreDTO = new PlayerDTO.NombrePlayer(nombre);
        notificarActualizacion("RECEPCION: setNombre", null, nombreDTO);
    }

    @Override public void setNivel (int nivel)
    {
        this.nivel = nivel;
        Object nivelDTO = new PlayerDTO.NivelPlayer(nivel);
        notificarActualizacion("RECEPCION: setNivel", null, nivelDTO);
    }

    @Override public void setMaxHPs (float mHps)
    {
        maxHPs = mHps;
        Object mHPsDTO = new PlayerDTO.MaxHPs(mHps);
        notificarActualizacion("RECEPCION: setMaxHPs", null, mHPsDTO);
    }








    //ENVIO DATOS:
    @Override public void setNumAnimacion(int numAnimacion)
    {
        if (this.numAnimacion != numAnimacion)
        {
            this.numAnimacion = numAnimacion;
            Object AnimacionDTO = new NetDTO.AnimacionPPC(this);
            notificarActualizacion("ENVIO: setTipoAnimacion", null, AnimacionDTO);
        }
    }

    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);

        if (Math.abs(this.x-x) >= 1 || Math.abs(this.y-y) >= 1)
        {
            this.x = x;
            this.y = y;
            Object posicionDTO = new NetDTO.PosicionPPC(this);
            notificarActualizacion("ENVIO: setPosition", null, posicionDTO);
        }
    }

    private void setBodyPosition()
    {
        if (Math.abs(this.cuerpo.getXinterpolada()-x) >= 1 || Math.abs(this.cuerpo.getYinterpolada()-y) >= 1)
        {
            this.x = cuerpo.getXinterpolada();
            this.y = cuerpo.getYinterpolada();
            Object posicionDTO = new NetDTO.PosicionPPC(this);
            notificarActualizacion("ENVIO: setPosition", null, posicionDTO);
        }
    }

    @Override public void setParametrosSpell(Object parametros)
    {
        parametrosSpell = parametros;
        Object setParametrosSpell = new PlayerDTO.SetParametrosSpell();
        notificarActualizacion("ENVIO: setParametrosSpell", null, setParametrosSpell);
    }

    @Override public void setSpellIDSeleccionado(String spellID)
    {
        Object spellIDSeleccionadoDTO = new PlayerDTO.SetSpellIDSeleccionado(spellID);
        notificarActualizacion("ENVIO: setSpellIDSeleccionado", null, spellIDSeleccionadoDTO);
    }

    @Override public void setCastear(boolean intentaCastear, int clickX, int clickY)
    {
        castear = intentaCastear;

        if (castear) startCastear();
        else stopCastear();
    }

    private void stopCastear()
    {
        if (castearInterrumpible)
        {
            Object castearDTO = new NetDTO.CastearPPC(false, output.getScreenX(), output.getScreenY());
            notificarActualizacion("ENVIO: setCastear", null, castearDTO);
            castearInterrumpible = false;
        }
    }

    private void startCastear()
    {
        if (!isCasteando())
        {
            SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(output.getSpellID());
            if (spell != null)
            {
                spell.castear(this, output.getScreenX(), output.getScreenY());

                Object castearDTO = new NetDTO.CastearPPC(true, output.getScreenX(), output.getScreenY());
                notificarActualizacion("ENVIO: setCastear", null, castearDTO);
                //actualCastingTime += 0.01f;
                castearInterrumpible = true;
            }
        }
    }

/*
    public void setInput (PlayerIOI playerInput)
    {
        irArriba = playerInput.getIrArriba();
        irAbajo = playerInput.getIrAbajo();d
        irDerecha = playerInput.getIrDerecha();
        irIzquierda = playerInput.getirIzquierda();

        disparar = playerInput.getDisparar();

        moverse();
        setNumAnimacion(playerInput.getNumAnimacion());

        if (playerInput.getStopCastear()) setCastear(false, playerInput.getScreenX(), playerInput.getScreenY());
        else if (playerInput.getStartCastear()) setCastear (true, playerInput.getScreenX(), playerInput.getScreenY());
    }*/

    public void comprobarSnapshop(NetDTO.PlayerSnapshot snapshot)
    {
        System.out.println("CLIENTE: "+cuerpo.getTimeStamp() +" "+ "SERVIDOR: "+snapshot.timeStamp);

        try
        {
            int pos;
            int posS = snapshot.x;
            Vector2 vector = cuerpo.listaPosiciones.get(snapshot.timeStamp);
            if (vector != null) {
                pos = (int)vector.x;
                if (pos != posS)
                    logger.trace("{} - {}", pos, posS);
            }
            else logger.info("NULL???");



        }
        catch (Exception e)
        {   logger.error("", e);}


    }

    public void setInput (PlayerIOI p) {}

    private void moverse ()
    {
        //Sur
        if (output.irAbajo && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.getBody().setLinearVelocity(0, -velocidadMax * PIXEL_METROS); }
        //Norte
        else if (output.irArriba && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.getBody().setLinearVelocity(0, velocidadMax * PIXEL_METROS);}
        //Este
        else if (output.irDerecha && !output.irArriba && !output.irAbajo)
        {   cuerpo.getBody().setLinearVelocity(velocidadMax * PIXEL_METROS, 0);}
        //Oeste
        else if (output.irIzquierda && !output.irArriba && !output.irAbajo)
        {   cuerpo.getBody().setLinearVelocity(-velocidadMax * PIXEL_METROS, 0);}
        //SurOeste
        else if (output.irAbajo&& output.irIzquierda)
        {   cuerpo.getBody().setLinearVelocity(-velocidadMax * 0.707f * PIXEL_METROS, -velocidadMax * 0.707f * PIXEL_METROS); }
        //SurEste
        else if (output.irAbajo && output.irDerecha)
        {   cuerpo.getBody().setLinearVelocity(velocidadMax * 0.707f * PIXEL_METROS, -velocidadMax * 0.707f * PIXEL_METROS); }
        //NorOeste
        else if (output.irArriba && output.irIzquierda)
        {   cuerpo.getBody().setLinearVelocity(-velocidadMax * 0.707f * PIXEL_METROS, velocidadMax * 0.707f * PIXEL_METROS); }
        //NorEste
        else if (output.irArriba && output.irDerecha)
        {   cuerpo.getBody().setLinearVelocity(velocidadMax * 0.707f * PIXEL_METROS, velocidadMax * 0.707f * PIXEL_METROS); }
        else if (!output.irAbajo && !output.irArriba && !output.irDerecha && !output.irIzquierda)
        {   cuerpo.getBody().setLinearVelocity(0, 0); }
    }

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

            Object castingTimePercent = new NetDTO.CastingTimePercent(this);
            notificarActualizacion("actualizarCastingTime", null, castingTimePercent);
        }
    }

    public void actualizar (float delta)
    {
        fsm.actualizar(delta);
        actualizarCastingTime(delta);
        moverse();
        setNumAnimacion(output.getNumAnimacion());
        if (output.getStartCastear()) startCastear();
        else if (output.getStopCastear()) stopCastear();
    }

    public void enviarComandosAServidor()
    {
        input.setTimeStamp(cuerpo.getTimeStamp());
        if (mundo.getCliente() != null)
            mundo.getCliente().enviarAServidor(input);
    }

    public void interpolacionEspacial(float alpha)
    {
        cuerpo.interpolar(alpha);
        setBodyPosition();
    }
}
