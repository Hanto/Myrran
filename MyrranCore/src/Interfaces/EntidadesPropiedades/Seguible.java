package Interfaces.EntidadesPropiedades;

import Model.AI.Steering.Huella;

import java.util.Iterator;

public interface Seguible
{
    public Iterator<Huella> getListaHuellasIterator();
    public void setTiempoDecayHuellas (float tiempoDecayHuellas);
}
