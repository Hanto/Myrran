package zMain;

import Controller.Controlador;
import DB.Datos.BDebuff.BDebuffXMLDB;
import DB.Datos.Spell.SpellXMLDB;
import DB.Datos.Terreno.TerrenoXMLDB;
import DB.Datos.TipoBDebuff.TipoBDebuffXMLDB;
import DB.Datos.TipoSpell.TipoSpellXMLDB;
import Data.Settings;
import Model.GameState.Mundo;

public class MyrranServer
{
    public static void main (String[] arg)
    {
        Settings.inicializar();

        TipoBDebuffXMLDB.get();
        BDebuffXMLDB.get();
        TipoSpellXMLDB.get();
        SpellXMLDB.get();
        TerrenoXMLDB.get();

        Controlador controlador = new Controlador(new Mundo());
        while (true) {}
    }
}
