package Model.Classes.Mobiles.PC;// Created by Hanto on 07/04/2014.

import DB.DAO;
import DTO.DTOsSkillPersonalizado;
import Interfaces.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Mobiles.Cuerpos.Cuerpo;
import Model.Skills.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class PC extends PCNotificador implements PropertyChangeListener, PCI, Debuffeable
{
    protected int iD;                                                                       // Identificable:
    protected int numAnimacion = 5;                                                         // Animable:
    protected float actualHPs=1;                                                            // Vulnerable:
    protected float maxHPs=2000;
    protected Cuerpo cuerpo;                                                                // Corporeo:
    protected int iDProyectiles = 0;                                                        // PCStats:
    protected String nombre = "Player";
    protected int nivel = 1;
    protected float actualCastingTime = 0.0f;                                               // Caster:
    protected float totalCastingTime = 0.0f;
    protected String spellIDSeleccionado = null;
    protected Object parametrosSpell;
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>(); // CasterPersonalizable:
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();
    protected List<AuraI>listaDeAuras = new ArrayList<>();                                  // Debuffeable:
    protected int targetX = 0;                                                              // Atributos Servidor:
    protected int targetY = 0;
    protected boolean castear = false;
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());            // Logger:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                    { }
    @Override public void setDireccion(float grados)                        { }


    // ANIMABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getNumAnimacion()                                  { return numAnimacion; }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                                   { return actualHPs; }
    @Override public float getMaxHPs()                                      { return maxHPs; }
    @Override public void setActualHPs(float HPs)                           { modificarHPs(HPs - actualHPs); }
    @Override public void setMaxHPs(float HPs)                              { maxHPs = HPs; }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Cuerpo getCuerpo()                                     { return cuerpo; }

    // PCSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public String getNombre()                                     { return nombre; }
    @Override public int getNivel()                                         { return nivel; }
    @Override public void setNombre(String nombre)                          { this.nombre = nombre; }
    @Override public void setNivel(int nivel)                               { this.nivel = nivel; }

    // CASTER:
    //------------------------------------------------------------------------------------------------------------------

    @Override public boolean isCasteando()                                  { if (actualCastingTime >0) return true; else return false; }
    @Override public float getActualCastingTime()                           { return actualCastingTime; }
    @Override public float getTotalCastingTime()                            { return totalCastingTime; }
    @Override public String getSpellIDSeleccionado()                        { return spellIDSeleccionado; }
    @Override public Object getParametrosSpell()                            { return parametrosSpell; }
    @Override public void setTotalCastingTime(float castingTime)            { actualCastingTime = 0.01f; totalCastingTime = castingTime; }
    @Override public void setSpellIDSeleccionado(String spellID)            { spellIDSeleccionado = spellID; }
    @Override public void setParametrosSpell(Object parametros)             { parametrosSpell = parametros; }

    // CASTERPERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID){ return listaSkillsPersonalizados.get(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID) { return listaSpellsPersonalizados.get(spellID); }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado(){ return listaSpellsPersonalizados.values().iterator(); }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado(){ return listaSkillsPersonalizados.values().iterator(); }

    // DEBUFFEABLE
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirAura(AuraI aura)                            { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                          { listaDeAuras.remove(aura); }
    @Override public Iterator<AuraI> getAuras()                             { return listaDeAuras.iterator(); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public PC( int connectionID, Cuerpo cuerpo )
    {
        this.iD = connectionID;
        this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
    }

    @Override public void dispose()
    {
        //Dejamos de observar a cada uno de los Spells Personalizados:
        Iterator<SpellPersonalizadoI> iSpell = getIteratorSpellPersonalizado();
        while (iSpell.hasNext()) { iSpell.next().eliminarObservador(this); }
        //le decimos a la vista que desaparezca:
        notificarSetDispose();
        this.eliminarObservadores();
    }

    //NOTIFICACIONES CAMPO VISION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        cuerpo.setPosition(x, y);
        huellas.añadirHuella(this);
        notificarSetPosition();
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificarSetNumAnimacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificarAddModificarHPs(HPs);
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
        notificarAddSpellPersonalizado(spell.getID());
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

    // METODOS ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

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
        }
    }

    @Override public void actualizarAuras (float delta)
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

    @Override public void actualizarTimers(float delta)
    {
        actualizarHuellas(delta);
        actualizarCastingTime(delta);
        actualizarAuras(delta);
    }

    @Override public void actualizarFisica(float delta, MundoI mundo)   {}

    @Override public void actualizarIA (float delta, MundoI mundo)
    {   if (castear) castear(mundo); }

    // EVENTOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkillPersonalizado.SetNumTalentos)
        {
            //Si alguien cambia mis Skills Personalizados de cualquier manera (hay varios metodos de hacerlo, se notifica
            //a la vista para que envie el mensaje al cliente:
            notificarAddNumTalentosSkillPersonalizado(
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).skillID,
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).statID,
                    ((DTOsSkillPersonalizado.SetNumTalentos) evt.getNewValue()).valor);
        }
    }
}
