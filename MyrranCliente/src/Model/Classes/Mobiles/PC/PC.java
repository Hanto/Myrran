package Model.Classes.Mobiles.PC;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Propiedades.*;
import Interfaces.EntidadesPropiedades.TipoMobile.PCStats;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillPersonalizadoI;
import Interfaces.Misc.Spell.SpellPersonalizadoI;
import Model.Mobiles.Cuerpos.Cuerpo;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class PC extends PCNotificador implements Debuffeable
{
    private IDentificable identificable;
    private Caster caster;
    private CasterPersonalizable casterPersonalizado;
    private Vulnerable vulnerable;
    private Debuffeable debuffeable;
    private PCStats pcStats;
    private Animable animable;

    private Vector2 destino = new Vector2();
    private Vector2 vectorDireccion = new Vector2();

    private Cuerpo cuerpo;

    public PC(int connectionID, Cuerpo cuerpo,
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
        this.cuerpo = cuerpo;

        this.debuffeable.setNotificador(this);

        this.identificable.setID(connectionID);
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
        this.setSeguible(false);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        this.eliminarObservadores();
    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    public int getID()                                              {   return identificable.getID(); }
    public void setID(int iD)                                       {   identificable.setID(iD); }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setDireccion(float grados)                {}

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
    @Override public void actualizarIA(float delta, MundoI mundo)   { }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------
    public Cuerpo getCuerpo()                                       {   return cuerpo; }

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
    @Override public void añadirSkillsPersonalizados(String spellID)
    {   casterPersonalizado.añadirSkillsPersonalizados(spellID); }
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
    @Override public void setCastear(String spellID, Object parametrosSpell, int screenX, int screenY) {}
    @Override public void setCastear(boolean castear, int screenX, int screenY) {}

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setPosition (float x, float y)
    {
        super.setPosition(x, y);
        cuerpo.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setDireccion(float x, float y)
    {   destino.set(x, y); }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        animable.setNumAnimacion(numAnimacion);
        notificarSetNumAnimacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        vulnerable.modificarHPs(HPs);
        notificarSetModificarHPs(HPs);
    }

    // ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta)
    {   actualizarAuras(delta); }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        vectorDireccion.set(destino).sub(posicion);
        float distancia = vectorDireccion.len();

        float velocidad = getVelocidadMax()*velocidadMod;

        if (distancia > 100 || distancia <= 2.0)
        {   setPosition(destino.x, destino.y); return; }

        //si nos desincronizamos y nos separamos mucho del objetivo, subimos la velocidad por encima del limite:
        velocidad = (distancia * 0.01f) * velocidad + velocidad;

        //normalizamos a la vez que multiplicamos por la velocidad:
        getVelocidad().set(vectorDireccion).scl(velocidad / distancia);
        setPosition(posicion.x + getVelocidad().x * delta, posicion.y + getVelocidad().y * delta);
    }

    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable)  {}
    @Override public void checkColisionesConMuro()  {}
}
