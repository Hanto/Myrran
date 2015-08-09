package Model.Classes.Mobiles;// Created by Hanto on 07/04/2014.

import DB.DAO;
import DTO.DTOsSkillPersonalizado;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Model.AbstractModel;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Cuerpos.BodyFactory;
import Model.Cuerpos.Cuerpo;
import Model.GameState.Mundo;
import Model.Settings;
import Model.Skills.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class PC extends AbstractModel implements PropertyChangeListener, PCI, Debuffeable
{
    protected int connectionID;
    protected int iDProyectiles = 0;

    protected int ultimoMapTileX = 0;
    protected int ultimoMapTileY = 0;
    protected float x;
    protected float y;
    protected int numAnimacion = 5;
    protected float velocidadMax = 80.0f;
    protected float velocidadMod = 1.0f;
    protected String nombre = "Player";
    protected int nivel = 1;
    protected float actualHPs=1;
    protected float maxHPs=2000;

    protected String spellIDSeleccionado = null;
    protected Object parametrosSpell;
    protected float actualCastingTime = 0.0f;
    protected float totalCastingTime = 0.0f;
    protected List<AuraI>listaDeAuras = new ArrayList<>();
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected Cuerpo cuerpo;
    protected PCNotificador notificador;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //Atributos exclusivos del Servidor:
    protected int targetX = 0;
    protected int targetY = 0;
    protected boolean castear = false;


    //GET:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return connectionID; }
    @Override public float getX()                                       { return x; }
    @Override public float getY()                                       { return y; }
    @Override public int getUltimoMapTileX()                            { return ultimoMapTileX; }
    @Override public int getUltimoMapTileY()                            { return ultimoMapTileY; }
    @Override public int getMapTileX()                                  { return (int)(x / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                                  { return (int)(y / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getNumAnimacion()                              { return numAnimacion; }
    @Override public float getVelocidadMod()                            { return velocidadMod; }
    @Override public float getVelocidadMax()                            { return velocidadMax; }
    @Override public String getNombre()                                 { return nombre; }
    @Override public int getNivel()                                     { return nivel; }
    @Override public float getActualHPs()                               { return actualHPs; }
    @Override public float getMaxHPs()                                  { return maxHPs; }
    @Override public boolean isCasteando()                              { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                       { return actualCastingTime; }
    @Override public float getTotalCastingTime()                        { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                    { return spellIDSeleccionado; }
    @Override public Object getParametrosSpell()                        { return parametrosSpell; }
    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }

    //SET:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setUltimoMapTile (int x, int y)               { ultimoMapTileX = x; ultimoMapTileY = y; }
    @Override public void setConnectionID (int connectionID)            { this.connectionID = connectionID; }
    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }
    @Override public void setVectorDireccion(float x, float y)          { cuerpo.setVectorDireccion(x, y); }
    @Override public void setTotalCastingTime(float castingTime)        { actualCastingTime = 0.01f; totalCastingTime = castingTime;}
    @Override public void setVelocidaMod(float velocidadMod)            { this.velocidadMod = velocidadMod; }
    @Override public void setVelocidadMax(float velocidadMax)           { this.velocidadMax = velocidadMax; }
    @Override public void setSpellIDSeleccionado(String spellID)        { spellIDSeleccionado = spellID; }
    @Override public void setParametrosSpell(Object parametros)         { parametrosSpell = parametros; }
    @Override public void setNombre(String nombre)                      { this.nombre = nombre; }
    @Override public void setNivel(int nivel)                           { this.nivel = nivel; }
    @Override public void añadirAura(AuraI aura)                        { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                      { listaDeAuras.remove(aura); }
    @Override public Iterator<AuraI> getAuras()                         { return listaDeAuras.iterator(); }
    @Override public void setMaxHPs(float HPs)                          { maxHPs = HPs; }
    @Override public void setActualHPs(float HPs)                       { modificarHPs(HPs - actualHPs); }
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID) { return listaSpellsPersonalizados.get(spellID); }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado(){ return listaSpellsPersonalizados.values().iterator(); }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado(){ return listaSkillsPersonalizados.values().iterator(); }

    //Constructor:
    public PC(int connectionID, Mundo mundo)
    {
        this.connectionID = connectionID;
        this.notificador = new PCNotificador(this);

        cuerpo = new Cuerpo(mundo.getWorld(), 48, 48);
        BodyFactory.darCuerpo.RECTANGULAR.nuevo(cuerpo);
    }

    //NOTIFICACIONES CAMPO VISION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void dispose()
    {
        //Dejamos de observar a cada uno de los Spells Personalizados:
        Iterator<SpellPersonalizadoI> iSpell = getIteratorSpellPersonalizado();
        while (iSpell.hasNext()) { iSpell.next().eliminarObservador(this); }
        //le decimos a la vista que desaparezca:
        notificador.setDispose();
        this.eliminarObservadores();
    }

    @Override public void setPosition(float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.x = x;
        this.y = y;
        notificador.setPosition();
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificador.setNumAnimacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificador.addModificarHPs(HPs);
    }

    @Override public void añadirSkillsPersonalizados(String spellID)
    {
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(spellID);
        if (spell == null) { logger.error("ERROR: añadirSkillsPersonalizados: spellID no encontrado: {}", spellID); return; }

        SpellPersonalizadoI spellPersonalizado = new SpellPersonalizado(spell);
        listaSpellsPersonalizados.put(spellPersonalizado.getID(), spellPersonalizado);

        listaSkillsPersonalizados.put(spellPersonalizado.getCustomSpell().getID(), spellPersonalizado.getCustomSpell());
        Iterator<SkillPersonalizadoI> iterator = spellPersonalizado.getIteratorCustomDebuffs();
        while(iterator.hasNext())
        {
            SkillPersonalizadoI skillPersonalizado = iterator.next();
            listaSkillsPersonalizados.put(skillPersonalizado.getID(), skillPersonalizado);
        }

        spellPersonalizado.añadirObservador(this);
        notificador.addSpellPersonalizado(spell.getID());
    }

    //Este metodo no es el que notifica de la modificacion de los talentos del skill personalizado, ya que hay mas modos de modificarlos
    //como pidiendo directamente un skillPersonalizado al HashMap y modificandolo. Por tanto el player observa directamente la fuente
    //(el propio Skill Personalizado), y si se modifica se le hace saber al player y es entonces cuando este manda la notifcacion al cliente

    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {
        SkillPersonalizadoI skillPersonalizado = listaSkillsPersonalizados.get(skillID);
        if (skillPersonalizado == null) { logger.error("ERROR: setNumTalentosSkillPersonalizado, spellID no existe: {}", skillID); return; }
        else if (valor >= 0 && valor <= skillPersonalizado.getTalentoMaximo(statID))
            skillPersonalizado.setNumTalentos(statID, valor);
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getIDProyectiles()
    {
        if (iDProyectiles++ >= (Math.pow(2, 20))) { iDProyectiles = 0; }
        return iDProyectiles;
    }

    // METODOS ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    public void setCastear (boolean castear, int targetX, int targetY)
    {
        this.castear = castear;
        this.targetX = targetX;
        this.targetY = targetY;
        if (castear) this.castear = true;
        else this.castear = false;
    }

    private void castear(MundoI mundo)
    {
        if (!isCasteando())
        {
            SpellI spell = DB.DAO.spellDAOFactory.getSpellDAO().getSpell(spellIDSeleccionado);
            if (spell != null)
            {
                spell.castear(this, targetX, targetY, mundo);
                castear = false;
                //actualCastingTime += 0.01f;
            }
        }
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
        }
    }

    public void actualizarAuras (float delta)
    {
        AuraI aura;
        Iterator<AuraI> aurasIteator = getAuras();
        while (aurasIteator.hasNext())
        {
            aura = aurasIteator.next();
            aura.actualizarAura(delta);
            if (aura.getDuracion() >= aura.getDuracionMax())
                aurasIteator.remove();
        }
    }

    @Override public void actualizar(float delta, MundoI mundo)
    {
        actualizarCastingTime(delta);
        actualizarAuras(delta);
        if (castear) castear(mundo);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkillPersonalizado.SetNumTalentos)
        {
            //Si alguien cambia mis Skills Personalizados de cualquier manera (hay varios metodos de hacerlo, se notifica
            //a la vista para que envie el mensaje al cliente:
            notificador.addNumTalentosSkillPersonalizado(
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).skillID,
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).statID,
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).valor);
        }

    }
}
