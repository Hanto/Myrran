package Model.AI.FSM;// Created by Hanto on 15/07/2014.

import InterfacesEntidades.EntidadesPropiedades.Maquinable;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.utils.ObjectMap;
import org.slf4j.LoggerFactory;

public class MaquinaEstados
{
    private Estado estadoInicial;
    private Estado estadoActual;
    private Estado estadoSiguiente;
    private Maquinable maquinable;
    private ObjectMap<Class<? extends Estado>, Estado> estados = new ObjectMap<>();
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Estado getEstado()           { return estadoActual; }
    public Maquinable getMaquinable()   { return maquinable; }

    public MaquinaEstados(Maquinable maquinable)
    {   this.maquinable = maquinable; }

    public void addEstado(Estado estado)
    {
        if (estados.size == 0)  { estadoInicial = estado; }
        estados.put(estado.getClass(), estado);
    }

    public void setEstadoSiguiente(Class<? extends Estado> claseEstado)
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

    public <T extends Estado> T getEstado(Class<T> claseEstado)
    {   return claseEstado.cast(estados.get(claseEstado)); }
}
