package Controller;// Created by Hanto on 08/04/2014.

import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;
import zMain.MyrranClient;

import static Model.Settings.FIXED_TimeStep;


public class Updater implements Screen
{
    private MyrranClient myrranCliente;
    private Cliente cliente;
    private Controlador controlador;
    private Mundo mundo;
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    private double timeStep = 0f;

    private double newTime;
    private double currentTime;
    private double deltaTime;

    //private int contador = 0;
    //private float total = 0;
    //private float media;

    public Updater(MyrranClient myrranCliente)
    {
        this.myrranCliente = myrranCliente;

        mundo = new Mundo();
        controlador = new Controlador(mundo);
        cliente = controlador.getCliente();

        currentTime = TimeUtils.nanoTime() / 1000000000.0;
    }

    @Override public void render(float delta)
    {
        newTime = TimeUtils.nanoTime() / 1000000000.0;
        deltaTime = (newTime - currentTime);
        currentTime = newTime;

        //total += deltaTime;
        timeStep += deltaTime;

        while (timeStep >= FIXED_TimeStep)
        {
            //contador++;
            //media = total/contador;
            //logger.trace("TimeStep Medio: {}",media);

            timeStep -= FIXED_TimeStep;

            mundo.actualizarUnidades(FIXED_TimeStep);
            mundo.actualizarFisica(FIXED_TimeStep);
            mundo.enviarDatosAServidor(cliente);
        }
        mundo.interpolarPosicion((float) timeStep / FIXED_TimeStep);
        controlador.render(delta);
    }

    @Override public void show()
    {   logger.trace("SHOW (Inicializando Screen):"); }

    @Override public void pause()
    {   logger.trace("PAUSE (Pausando pantalla):"); }

    @Override public void resume()
    {   logger.trace("RESUME (Pantalla reanudada):"); }

    @Override public void resize(int anchura, int altura)
    {
        logger.trace("RESIZE (Redimensionando Screen) a: {} x {}", anchura, altura);
        controlador.resize(anchura, altura);
    }

    @Override public void hide()
    {
        logger.trace("HIDE (Cerrando pantalla):");
        dispose();
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE (Liberando memoria):");
        controlador.dispose();
    }
}
