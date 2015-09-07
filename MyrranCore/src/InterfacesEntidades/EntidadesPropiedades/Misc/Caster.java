package InterfacesEntidades.EntidadesPropiedades.Misc;// Created by Hanto on 17/04/2014.

public interface Caster
{
    //GET:
    public boolean isCasteando();
    public float getActualCastingTime();
    public float getTotalCastingTime();
    public String getSpellIDSeleccionado();
    public Object getParametrosSpell();

    //SET:
    public void setCastear(boolean castear, int screenX, int screenY);
    public void setCastear(String spellID, Object parametrosSpell, int screenX, int screenY);
    public void setActualCastingTime(float castingTime);
    public void setTotalCastingTime(float castingTime);
    public void setSpellIDSeleccionado(String spellID);
    public void setParametrosSpell (Object parametrosDTO);

    //UPDATE:
    public void actualizarCastingTime(float delta);
}
