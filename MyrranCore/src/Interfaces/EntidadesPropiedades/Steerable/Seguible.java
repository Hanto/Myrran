package Interfaces.EntidadesPropiedades.Steerable;

import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Model.AI.Huellas.Huella;

import java.util.Iterator;

public interface Seguible
{
    public Iterator<Huella> getListaHuellasIterator();
    public void setTiempoDecayHuellas (float tiempoDecayHuellas);
    public void añadirHuella(Espacial espacial);

    //UPDATE:
    public void actualizarHuellas(float delta);
}
