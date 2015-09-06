package Model.Classes.Mobiles.Modulares.PC;

public class SpellACastear
{
    public String spellID;
    public Object parametrosSpell;
    public int screenX;
    public int screenY;

    public SpellACastear(String spellID, Object parametrosSpell, int screenX, int screenY)
    {
        this.spellID = spellID;
        this.parametrosSpell = parametrosSpell;
        this.screenX = screenX;
        this.screenY = screenY;
    }
}
