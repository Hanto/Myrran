package zMain;

import Controller.Updater;
import DB.Datos.BDebuff.BDebuffXMLDB;
import DB.Datos.Spell.SpellXMLDB;
import DB.Datos.Terreno.TerrenoXMLDB;
import DB.Datos.TipoBDebuff.TipoBDebuffXMLDB;
import DB.Datos.TipoSpell.TipoSpellXMLDB;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Model.Pools.PoolDebuffeable;
import Model.Settings;
import com.badlogic.gdx.utils.Pools;

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

        Pools.set(Debuffeable.class, new PoolDebuffeable(20, 100));

        //Controlador controlador = new Controlador(new Mundo());
        Updater updater = new Updater();
        //updater.run();
        while (true) { updater.run(); }
    }
}
