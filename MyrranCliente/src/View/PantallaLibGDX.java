package View;// Created by Hanto on 08/04/2014.

import Controller.Controlador;
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
import DB.Recursos.PixiePCRecursos.PixiePCRecursosXMLDB;
import DB.Recursos.SkillRecursos.SkillRecursosXMLDB;
import DB.Recursos.TerrenoRecursos.TerrenoRecursosXMLDB;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;
import zMain.MyrranClient;

import static Data.Settings.FIXED_TimeStep;


public class PantallaLibGDX implements Screen
{
    private MyrranClient myrranCliente;
    private Controlador controlador;
    private Mundo mundo;
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    private double timeStep = 0f;

    private double newTime;
    private double currentTime;
    private double deltaTime;

    private int contador = 0;
    private float total = 0;
    private float media;

    public PantallaLibGDX(MyrranClient myrranCliente)
    {
        this.myrranCliente = myrranCliente;

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
        FuentesRecursosXMLDB.get();
        SkillRecursosXMLDB.get();
        ParticulaRecursosXMLDB.get();

        mundo = new Mundo();
        controlador = new Controlador(mundo);

        currentTime = TimeUtils.nanoTime() / 1000000.0;
    }

    @Override public void show()
    {   logger.trace("SHOW (Inicializando Screen):"); }

    @Override public void render(float delta)
    {
        newTime = TimeUtils.nanoTime() / 1000000.0;
        deltaTime = (newTime - currentTime);
        currentTime = newTime;

        total += deltaTime;

        timeStep += deltaTime;

        while (timeStep >= 30)
        {
            timeStep -= 30;
            mundo.actualizarFisica(FIXED_TimeStep);
            mundo.actualizarUnidades(FIXED_TimeStep);

            contador++;
            media = total/contador;
            System.out.println(media);
        }
        mundo.interpolacionEspacial((float)timeStep/30f);

        controlador.render(delta);
    }

    @Override public void resize(int anchura, int altura)
    {   logger.trace("RESIZE (Redimensionando Screen) a: {} x {}", anchura, altura);
        controlador.resize(anchura, altura);
    }

    @Override public void pause()
    {   logger.trace("PAUSE (Pausando pantalla):"); }

    @Override public void resume()
    {   logger.trace("RESUME (Pantalla reanudada):"); }

    //El metodo Hide se ejecuta al cerrar la pantalla
    @Override public void hide()
    {   //Despues de cerrar la pantalla es neccesario liberar la memoria de todas las texturas que hayamos usado, por eso llamamos al metodo Dispose
        logger.trace("HIDE (Cerrando pantalla):");
        dispose();
    }

    //Es el metodo para liberar los recursos usados
    @Override public void dispose()
    {   logger.trace("DISPOSE (Liberando memoria):");
        controlador.dispose();
    }
}
