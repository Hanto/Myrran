package zMain;

import Controller.Updater;
import DB.Datos.BDebuff.BDebuffXMLDB;
import DB.Datos.Spell.SpellXMLDB;
import DB.Datos.Terreno.TerrenoXMLDB;
import DB.Datos.TipoBDebuff.TipoBDebuffXMLDB;
import DB.Datos.TipoSpell.TipoSpellXMLDB;
import Model.Settings;

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

        //Controlador controlador = new Controlador(new Mundo());
        Updater updater = new Updater();
        while (true) {}
    }
}
