package Interfaces.EntidadesPropiedades.Steerable;

import Model.AI.Steering.Huella;

import java.util.Iterator;

public interface Seguible
{
    public Iterator<Huella> getListaHuellasIterator();
    public void setTiempoDecayHuellas (float tiempoDecayHuellas);

    //UPDATE:
    public void actualizarHuellas(float delta);
}
