package zMain;// Created by Hanto on 08/04/2014.

import Controller.Updater;
import DB.Datos.BDebuff.BDebuffXMLDB;
import DB.Datos.Spell.SpellXMLDB;
import DB.Datos.Terreno.TerrenoXMLDB;
import DB.Datos.TipoBdDebuff.TipoBDebuffXMLDB;
import DB.Datos.TipoSpell.TipoSpellXMLDB;
import DB.Recursos.AccionRecursos.AccionRecursosXMLDB;
import DB.Recursos.AtlasRecursos.AtlasRecursosLocalDB;
import DB.Recursos.FuentesRecursos.FuentesRecursosXMLDB;
import DB.Recursos.MiscRecursos.MiscRecursosXMLDB;
import DB.Recursos.ParticulasRecursos.ParticulaRecursosXMLDB;
import DB.Recursos.PixieMobRecursos.PixieMobRecursosXMLDB;
import DB.Recursos.PixiePCRecursos.PixiePCRecursosXMLDB;
import DB.Recursos.SkillBaseRecursos.SkillBaseRecursosXMLDB;
import DB.Recursos.SkillRecursos.SkillRecursosXMLDB;
import DB.Recursos.TerrenoRecursos.TerrenoRecursosXMLDB;
import DB.Recursos.TipoSkillRecursos.TipoSkillRecursosXMLDB;
import Model.Settings;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MyrranClient extends Game
{
    public String LOG = MyrranClient.class.getSimpleName();
    public enum tipoPantalla { pantallaMenu, pantallaJuego }

    public static void main (String[] arg)
    {
        System.setProperty("user.name", "Myrran");
        Settings.inicializar();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Myrran";
        cfg.fullscreen = false;
        cfg.vSyncEnabled = false;
        cfg.foregroundFPS = 5000;
        //cfg.useGL30 = true;
        cfg.fullscreen = false;
        cfg.width = Settings.GDX_Horizontal_Resolution;
        cfg.height = Settings.GDX_Vertical_Resolution;

        cfg.addIcon(Settings.RECURSOS_Atlas_Carpeta_Imagenes_Origen+ Settings.ATLAS_TexturasIconos_LOC+"FireBall.png", Files.FileType.Internal);
        new LwjglApplication(new MyrranClient(), cfg);
    }

    @Override public void create()
    {
        cargarDatos();
        nagevarA(tipoPantalla.pantallaJuego);
    }

    private void cargarDatos()
    {
        TipoBDebuffXMLDB.get();
        BDebuffXMLDB.get();
        TipoSpellXMLDB.get();
        SpellXMLDB.get();
        TerrenoXMLDB.get();

        AtlasRecursosLocalDB.get();
        MiscRecursosXMLDB.get();
        TerrenoRecursosXMLDB.get();
        AccionRecursosXMLDB.get();
        PixiePCRecursosXMLDB.get();
        PixieMobRecursosXMLDB.get();
        FuentesRecursosXMLDB.get();
        SkillBaseRecursosXMLDB.get();
        TipoSkillRecursosXMLDB.get();
        SkillRecursosXMLDB.get();
        ParticulaRecursosXMLDB.get();
    }

    public void nagevarA (tipoPantalla pantalla)
    {
        Screen screen;
        switch (pantalla)
        {
            case pantallaMenu:  screen = new Updater(this); break;
            case pantallaJuego: screen = new Updater(this); break;
            default:            screen = new Updater(this); break;
        }
        setScreen(screen);
    }
}
