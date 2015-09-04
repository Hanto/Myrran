package Model.Classes.Mobiles.Modulares.PC;// Created by Hanto on 03/09/2015.

import DTO.DTOsSkillPersonalizado;
import Interfaces.BDebuff.AuraI;
import InterfacesEntidades.EntidadesPropiedades.*;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.*;
import InterfacesEntidades.EntidadesPropiedades.Steerable.Seguible;
import InterfacesEntidades.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.AI.Huellas.Huellas;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class PC extends PCNotificador implements CasterPersonalizable, Caster, Vulnerable, Debuffeable, PCStats,
        IDentificable, Animable, Espacial, Colisionable, Dinamico, Solido, Orientable, Steerable<Vector2>, Seguible,
        Disposable, PropertyChangeListener, PCI
{
    protected IDentificable identificable;
    protected Caster caster;
    protected CasterPersonalizable casterPersonalizado;
    protected Vulnerable vulnerable;
    protected Debuffeable debuffeable;
    protected PCStats pcStats;
    protected Animable animable;

    protected int targetX = 0;
    protected int targetY = 0;
    protected boolean castear = false;

    public PC(int connectionID, int ancho, int alto,
              IDentificable identificable, Caster caster, CasterPersonalizable casterPersonalizado,
              Vulnerable vulnerable, Debuffeable debuffeable, PCStats pcStats,
              Animable animable)
    {
        this.identificable = identificable;
        this.caster = caster;
        this.casterPersonalizado = casterPersonalizado;
        this.vulnerable = vulnerable;
        this.debuffeable = debuffeable;
        this.pcStats = pcStats;
        this.animable = animable;


        this.identificable.setID(connectionID);
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setHuellas(new Huellas());
        this.setSeguible(true);
        this.setTiempoDecayHuellas(20f);
    }

    @Override public void dispose()
    {
        //Dejamos de observar a cada uno de los Spells Personalizados:
        Iterator<SpellPersonalizadoI> iSpell = getIteratorSpellPersonalizado();
        while (iSpell.hasNext())
            iSpell.next().eliminarObservador(this);

        notificarSetDispose();
        this.eliminarObservadores();
    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    public int getID()                                              {   return identificable.getID(); }
    public void setID(int iD)                                       {   identificable.setID(iD); }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setDireccion(float x, float y)            {}
    @Override public void setDireccion(float grados)                {}
    @Override public void actualizarFisica(float delta,MundoI mundo){}

    // ANIMABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getNumAnimacion()                          {   return animable.getNumAnimacion(); }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public float getActualHPs()                           {   return vulnerable.getActualHPs(); }
    @Override public float getMaxHPs()                              {   return vulnerable.getMaxHPs(); }
    @Override public void setActualHPs(float HPs)                   {   vulnerable.setActualHPs(HPs); }
    @Override public void setMaxHPs(float HPs)                      {   vulnerable.setMaxHPs(HPs); }

    // DEBUFFEABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void añadirAura(AuraI aura)                    {   debuffeable.añadirAura(aura); }
    @Override public void eliminarAura(AuraI aura)                  {   debuffeable.eliminarAura(aura); }
    @Override public Iterator<AuraI> getAuras()                     {   return debuffeable.getAuras(); }
    @Override public void actualizarAuras(float delta)              {   debuffeable.actualizarAuras(delta); }

    // PCSTATS:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getIDProyectiles()                         {   return pcStats.getIDProyectiles(); }
    @Override public String getNombre()                             {   return pcStats.getNombre(); }
    @Override public int getNivel()                                 {   return pcStats.getNivel(); }
    @Override public void setNombre(String nombre)                  {   pcStats.setNombre(nombre); }
    @Override public void setNivel(int nivel)                       {   pcStats.setNivel(nivel); }


    // CASTER PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID)
    {   return casterPersonalizado.getSkillPersonalizado(skillID); }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID)
    {   return casterPersonalizado.getSpellPersonalizado(spellID); }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado()
    {   return casterPersonalizado.getIteratorSpellPersonalizado(); }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado()
    {   return casterPersonalizado.getIteratorSkillPersonalizado(); }
    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento)
    {   casterPersonalizado.setNumTalentosSkillPersonalizado(skillID, statID, talento); }

    // CASTER:
    //------------------------------------------------------------------------------------------------------------------
    @Override public boolean isCasteando()                          {   return caster.isCasteando(); }
    @Override public float getActualCastingTime()                   {   return caster.getActualCastingTime(); }
    @Override public float getTotalCastingTime()                    {   return caster.getTotalCastingTime(); }
    @Override public String getSpellIDSeleccionado()                {   return caster.getSpellIDSeleccionado(); }
    @Override public Object getParametrosSpell()                    {   return caster.getParametrosSpell(); }
    @Override public void setActualCastingTime(float castingTime)   {   caster.setActualCastingTime(castingTime); }
    @Override public void setTotalCastingTime(float castingTime)    {   caster.setTotalCastingTime(castingTime); }
    @Override public void setSpellIDSeleccionado(String spellID)    {   caster.setSpellIDSeleccionado(spellID); }
    @Override public void setParametrosSpell(Object parametrosDTO)  {   caster.setParametrosSpell(parametrosDTO); }
    @Override public void actualizarCastingTime(float delta)        {   caster.actualizarCastingTime(delta); }

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setCastear(boolean castear, int screenX, int screenY)
    {
        this.castear = castear;
        this.targetX = screenX;
        this.targetY = screenY;
        this.castear = castear;
    }

    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        animable.setNumAnimacion(numAnimacion);
        notificarSetNumAnimacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        vulnerable.modificarHPs(HPs);
        notificarAddModificarHPs(HPs);
    }

    @Override public void añadirSkillsPersonalizados(String spellID)
    {
        casterPersonalizado.añadirSkillsPersonalizados(spellID);
        casterPersonalizado.getSpellPersonalizado(spellID).añadirObservador(this);
        notificarAddSpellPersonalizado(spellID);
    }

    // ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta)
    {
        actualizarCastingTime(delta);
        actualizarAuras(delta);
        actualizarHuellas(delta);
    }

    @Override public void actualizarIA(float delta, MundoI mundo)
    {   if (castear) castear(mundo); }

    // METODOS PROPIOS:
    //------------------------------------------------------------------------------------------------------------------

    private void castear(MundoI mundo)
    {
        if (!isCasteando())
        {
            SpellI spell = DB.DAO.spellDAOFactory.getSpellDAO().getSpell(caster.getSpellIDSeleccionado());
            if (spell != null)
            {
                spell.castear(this, targetX, targetY, mundo);
                castear = false;
            }
        }
    }

    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable)  {}
    @Override public void checkColisionesConMuro() {}

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









    //TODO Modificar los interfaces:
    @Override public Cuerpo getCuerpo() { return null; }
}
