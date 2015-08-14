package Model.Classes.Mobiles.PC;// Created by Hanto on 08/04/2014.

import Interfaces.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Cuerpos.Cuerpo;

import java.util.Iterator;

public class PC extends PCNotificador implements PCI
{
    protected int iD;
    protected int numAnimacion = 5;
    protected float actualHPs;
    protected float maxHPs;
    protected Cuerpo cuerpo;


    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                                    { return iD; }
    @Override public void setID(int iD)                                             { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                            {}
    @Override public void setDireccion(float grados)                                {}

    // ANIMABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getNumAnimacion()                                          { return numAnimacion; }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                                           { return actualHPs; }
    @Override public float getMaxHPs()                                              { return maxHPs; }
    @Override public void setActualHPs(float HPs)                                   { modificarHPs(HPs - actualHPs);}
    @Override public void setMaxHPs(float HPs)                                      { this.maxHPs = HPs; }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Cuerpo getCuerpo()                                             { return cuerpo; }

    // PCSTATS: TODO
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getIDProyectiles()                                         { return 0; }
    @Override public String getNombre()                                             { return null; }
    @Override public int getNivel()                                                 { return 0; }
    @Override public void setNombre (String nombre)                                 {}
    @Override public void setNivel (int nivel)                                      {}

    // CASTER: TODO
    //------------------------------------------------------------------------------------------------------------------

    @Override public boolean isCasteando()                                          { return false; }
    @Override public float getActualCastingTime()                                   { return 0; }
    @Override public float getTotalCastingTime()                                    { return 0; }
    @Override public String getSpellIDSeleccionado()                                { return null; }
    @Override public Object getParametrosSpell()                                    { return null; }
    @Override public void setCastear(boolean castear, int screenX, int screenY)     {}
    @Override public void setTotalCastingTime(float castingTime)                    {}
    @Override public void setSpellIDSeleccionado(String spellID)                    {}
    @Override public void setParametrosSpell(Object parametrosDTO)                  {}

    // CASTER PERSONALIZADO: TODO
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirSkillsPersonalizados(String spellID)                {}
    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento) {}
    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID)      { return null; }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID)      { return null; }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado()  { return null; }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado()  { return null; }

    // CONSTRUCTOR: TODO
    //------------------------------------------------------------------------------------------------------------------

    public PC(int connectionID, Cuerpo cuerpo)
    {
        this.iD = connectionID;
        this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
    }

    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);
        posicion.set(x, y);
        notificarSetPosition();
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificarSetNumAnimacion();
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        notificarSetDispose();
        this.eliminarObservadores();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificarSetModificarHPs(HPs);
    }

    @Override public void actualizar (float delta, MundoI mundo)
    {

    }
}
