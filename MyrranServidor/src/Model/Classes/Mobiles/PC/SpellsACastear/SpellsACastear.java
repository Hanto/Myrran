package Model.Classes.Mobiles.PC.SpellsACastear;

import java.util.ArrayDeque;

public class SpellsACastear
{
    private ArrayDeque<SpellACastear>listaSpellsACastear;

    public SpellsACastear()
    {   listaSpellsACastear = new ArrayDeque<>(); }


    public boolean tieneSpells()
    {   return !listaSpellsACastear.isEmpty(); }

    public void add(String spellID, Object parametrosSpell, int screenX, int screenY)
    {   listaSpellsACastear.addLast(new SpellACastear(spellID, parametrosSpell, screenX, screenY));}

    public void add(SpellACastear spellACastear)
    {   listaSpellsACastear.addLast(spellACastear); }

    public SpellACastear get()
    {   return listaSpellsACastear.poll(); }
}
