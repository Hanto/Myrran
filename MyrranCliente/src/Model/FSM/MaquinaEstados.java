package Model.FSM;// Created by Hanto on 15/07/2014.

import Interfaces.EntidadesPropiedades.Maquinable;
import Model.FSM.PlayerEstados.PlayerEstado;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import org.slf4j.LoggerFactory;

public class MaquinaEstados
{
    private ObjectMap<Class<? extends PlayerEstado>, PlayerEstado> estados = new ObjectMap<>();
    private PlayerEstado estadoInicial;
    private PlayerEstado estadoActual;
    private PlayerEstado estadoSiguiente;
    private Maquinable entidad;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public PlayerEstado getEstado()       { return estadoActual; }
    public Maquinable getEntidad()  { return entidad; }

    public MaquinaEstados(Maquinable entidad)
    {   this.entidad = entidad; }

    public void addEstado(PlayerEstado estado)
    {
        if (estados.size == 0)  { estadoInicial = estado; }
        estados.put(estado.getClass(), estado);
    }

    public void setEstadoSiguiente(Class<? extends PlayerEstado> claseEstado)
    {
        estadoSiguiente = estados.get(claseEstado);
        if (estadoSiguiente == null) logger.error("Estado [{}] no existe en la maquina de estados", claseEstado);
    }

    public void actualizar(float delta)
    {
        if (estadoActual == null && estadoInicial != null)
        {
            estadoActual = estadoInicial;
            estadoActual.enter();
        }
        if (estadoActual != null)
        {
            estadoActual.update(delta);
        }
        if (estadoSiguiente != null && estadoSiguiente != estadoActual)
        {
            estadoActual.exit();
            estadoActual = estadoSiguiente;
            estadoSiguiente = null;
            estadoActual.enter();
        }
    }

    public <T extends PlayerEstado> T getEstado(Class<T> claseEstado)
    {   return claseEstado.cast(estados.get(claseEstado)); }
}
