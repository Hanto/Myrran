package Interfaces.EntidadesTipos;// Created by Hanto on 27/07/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Interfaces.Network.ServidorI;
import com.badlogic.gdx.utils.Disposable;

public interface CampoVisionI extends Espacial, Disposable
{
    public int getConnectionID();
    public void radar();
    public void enviarDTOs(ServidorI servidor);
    public void setTargetLock(Espacial espacial);
}
