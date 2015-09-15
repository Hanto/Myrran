package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;

//* @author Ivan Delgado Huerta
public class SkillStat extends AbstractModel
{
    private int id;
    private String nombre;                          //Nombre del SkillStat: por ej: "Daño, velocidad, Casting Time"
    private float valorBase;                        //Valor Base del SkillStat: por ej: 100 de Daño
    private boolean isMejorable = false;            //Indica si es un SkillStat mejorable por Talentos
    private int talentosMaximos;                    //numero de Talentos maximos que se pueden gastar en este SkillStat
    private int costeTalento;                       //coste por mejorar cada punto de talento
    private float bonoTalento;                      //Valor con el que mejora el valorBase por punto de talento

    private int numTalentos =0;
    private float bonosPorObjetos =0;

    //GET:
    public int getID()                              { return id;}
    public String getNombre()                       { return nombre; }
    public float getValorBase()                     { return valorBase; }
    public boolean getisMejorable()                 { return isMejorable; }
    public int getTalentosMaximos()                 { return talentosMaximos; }
    public int getCosteTalento()                    { return costeTalento; }
    public float getBonoTalento()                   { return bonoTalento; }
    public int getNumTalentos()                     { return numTalentos; }
    public float getBonosPorObjetos()               { return bonosPorObjetos; }


    //SET:
    public void setID(byte id)                      { this.id = id; }
    public void setNombre (String nombre)           { this.nombre = nombre; notificarActualizacion(); }
    public void setValorBase (float valorBase)      { this.valorBase = valorBase; notificarActualizacion(); }
    public void setIsMejorable(boolean b)           { this.isMejorable = b; notificarActualizacion(); }
    public void setTalentosMaximos(int talentoMaximo){ this.talentosMaximos = talentoMaximo; notificarActualizacion(); }
    public void setCosteTalento (int costeTalento)  { this.costeTalento = costeTalento; notificarActualizacion(); }
    public void setBonoTalento (float bonoTalento)  { this.bonoTalento = bonoTalento; notificarActualizacion(); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillStat(int id, String nombre, float valor)
    {
        this.id = id;
        this.nombre = nombre;
        this.valorBase = valor;
        this.isMejorable = false;
    }
    
    //CONSTRUCTOR: (constructor Copia)
    public SkillStat(SkillStat skillStat)
    {
        this.id = skillStat.id;
        this.nombre = skillStat.nombre;
        this.valorBase = skillStat.valorBase;
        this.talentosMaximos = skillStat.talentosMaximos;
        this.costeTalento = skillStat.costeTalento;
        this.bonoTalento = skillStat.bonoTalento;
        this.isMejorable = skillStat.isMejorable;
        this.numTalentos = skillStat.numTalentos;
        this.bonosPorObjetos = skillStat.bonosPorObjetos;
    }

    //------------------------------------------------------------------------------------------------------------------


    public void setSkillStats(int id, String nombre, float valor)
    {
        this.id = id;
        this.nombre = nombre;
        valorBase = valor;
        isMejorable = false;
        notificarActualizacion();
    }

    public void setTalentosStats(int maxNumeroTalentos, int coste, float bono)
    {
        talentosMaximos = maxNumeroTalentos;
        costeTalento = coste;
        bonoTalento = bono;
        isMejorable = true;
        if (numTalentos > talentosMaximos) setNumTalentos(talentosMaximos);
        notificarActualizacion();
    }

    public void setNumTalentos(int valor)
    {
        if (numTalentos>=0 && numTalentos <= talentosMaximos)
        {
            this.numTalentos = valor;
            notificarActualizacion();
        }
    }

    public float getValorTotal()
    {   return valorBase + numTalentos * bonoTalento + bonosPorObjetos; }

    private void notificarActualizacion()
    {
        DTOsSkill.setSkillStat setSkillStat = new DTOsSkill.setSkillStat(this);
        notificarActualizacion("setSkillStat", null, setSkillStat);
    }
}
    
    