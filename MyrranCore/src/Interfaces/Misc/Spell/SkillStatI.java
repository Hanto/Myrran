package Interfaces.Misc.Spell;

import Interfaces.Misc.Observable.ModelI;

public interface SkillStatI extends ModelI
{
    public int getID();
    public String getNombre();
    public float getValorBase();
    public boolean getisMejorable();
    public int getTalentosMaximos();
    public int getCosteTalento();
    public float getBonoTalento();
    public int getNumTalentos();
    public float getBonosPorObjetos();

    public void setID(byte id);
    public void setNombre (String nombre);
    public void setValorBase (float valorBase);
    public void setIsMejorable(boolean b);
    public void setTalentosMaximos(int talentoMaximo);
    public void setCosteTalento (int costeTalento);
    public void setBonoTalento (float bonoTalento);

    public void setFullStats(SkillStatI skillStat);
    public void setBaseStats(int id, String nombre, float valor);
    public void setTalentosStats(int maxNumeroTalentos, int coste, float bono);
    public void setNumTalentos(int valor);

    public float getTotal();



}
