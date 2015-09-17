package Model.Classes.Mobiles.PC;// Created by Hanto on 03/09/2015.

import DB.DAO;
import DTOs.DTOsSkillPersonalizado;
import Interfaces.Misc.Spell.*;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Propiedades.*;
import Interfaces.EntidadesPropiedades.TipoMobile.PCStats;
import Model.AI.Huellas.Huellas;
import Model.Classes.Mobiles.PC.SpellsACastear.SpellACastear;
import Model.Classes.Mobiles.PC.SpellsACastear.SpellsACastear;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class PC extends PCNotificador implements PropertyChangeListener
{
    protected IDentificable identificable;
    protected Caster caster;
    protected CasterPersonalizable casterPersonalizado;
    protected Vulnerable vulnerable;
    protected Debuffeable debuffeable;
    protected PCStats pcStats;
    protected Animable animable;

    protected SpellsACastear spellsACastear = new SpellsACastear();
    protected SpellACastear spellACastear;

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

        this.debuffeable.setNotificador(this);

        this.identificable.setID(connectionID);
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setHuellas(new Huellas());
        this.setSeguible(true);
        this.setTiempoDecayHuellas(20f);
        this.setVelocidadMax(80);
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
    @Override public AuraI getAura(int auraID)
    {   return debuffeable.getAura(auraID); }
    @Override public Iterator<AuraI> getAuras()
    {   return debuffeable.getAuras(); }
    @Override public void añadirAura(BDebuffI debuff, Caster caster, Debuffeable target)
    {   debuffeable.añadirAura(debuff, caster, target);}
    @Override public void añadirAura(int iDAura, BDebuffI debuff, Caster caster, Debuffeable target)
    {   debuffeable.añadirAura(iDAura, debuff, caster, target); }
    @Override public void eliminarAura(int iDAura)
    {   debuffeable.eliminarAura(iDAura); }
    @Override public void actualizarAuras(float delta)
    {   debuffeable.actualizarAuras(delta); }
    @Override public void setNotificador(DeBuffeableNotificadorI notificador)
    {   debuffeable.setNotificador(notificador); }

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

    // CASTER:
    //------------------------------------------------------------------------------------------------------------------
    @Override public boolean isCasteando()                          {   return caster.isCasteando(); }
    @Override public float getActualCastingTime()                   {   return caster.getActualCastingTime(); }
    @Override public float getTotalCastingTime()                    {   return caster.getTotalCastingTime(); }
    @Override public String getSpellIDSeleccionado()                {   return caster.getSpellIDSeleccionado(); }
    @Override public Object getParametrosSpell()                    {   return caster.getParametrosSpell(); }
    @Override public void setActualCastingTime(float castingTime)   {   caster.setActualCastingTime(castingTime); }
    @Override public void setTotalCastingTime(float castingTime)    {   caster.setTotalCastingTime(castingTime); }
    @Override public void actualizarCastingTime(float delta)        {   caster.actualizarCastingTime(delta); }
    @Override public void setSpellIDSeleccionado(String spellID)    {   caster.setSpellIDSeleccionado(spellID); }
    @Override public void setParametrosSpell(Object parametrosDTO)  {   caster.setParametrosSpell(parametrosDTO); }

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setCastear(String spellID, Object parametrosSpell, int screenX, int screenY)
    {   spellsACastear.add(spellID, parametrosSpell, screenX, screenY); }

    @Override public void setCastear(boolean castear, int screenX, int screenY)
    {  }

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

    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento)
    {
        casterPersonalizado.setNumTalentosSkillPersonalizado(skillID, statID, talento);

        //TODO Provisional salvando datos en el propio Spell:
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(skillID);
        if (spell != null) spell.getStats().getStat(statID).setNumTalentos(talento);
        else DAO.debuffDAOFactory.getBDebuffDAO().getBDebuff(skillID).getStats().getStat(statID).setNumTalentos(talento);
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
    {   if (spellsACastear.tieneSpells()) castear(mundo); }

    // METODOS PROPIOS:
    //------------------------------------------------------------------------------------------------------------------

    private void getSpellDeListaSpellsACastear()
    {
        spellACastear = spellsACastear.get();
        caster.setSpellIDSeleccionado(spellACastear.spellID);
        caster.setParametrosSpell(spellACastear.parametrosSpell);
    }

    private void castear(MundoI mundo)
    {
        if (!isCasteando())
        {
            getSpellDeListaSpellsACastear();
            SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(caster.getSpellIDSeleccionado());
            if (spell != null)
            {   spell.castear(this, spellACastear.screenX, spellACastear.screenY, mundo); }
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
}
