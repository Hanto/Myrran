package View.Gamestate.Vista2;  //Created by Hanto on 14/04/2015.

import Controller.Controlador;
import DTO.DTOsPC;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Model.AbstractModel;
import Model.GameState.Mundo;
import View.Gamestate.MundoView;
import View.Gamestate.Vistas.MapaView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CampoVision extends AbstractModel implements PropertyChangeListener, Espacial
{
    //Model:
    private MundoView mundoView;
    private Mundo mundo;
    private Controlador controlador;
    private Espacial centro;
    private int connectionID;

    //Datos:
    private List<MobPC> listaPCsCercanos = new ArrayList<>();
    protected MapaView mapaView;

    @Override public float getX()                       { return 0; }
    @Override public float getY()                       { return 0; }
    @Override public int getMapTileX()                  { return 0; }
    @Override public int getMapTileY()                  { return 0; }
    @Override public int getUltimoMapTileX()            { return 0; }
    @Override public int getUltimoMapTileY()            { return 0; }
    @Override public void setPosition(float x, float y) {  }
    @Override public void setUltimoMapTile(int x, int y){  }

    //Constructor:
    public CampoVision(Espacial espacial, MundoView mundoView)
    {
        this.mundoView = mundoView;
        this.controlador = mundoView.controlador;
        this.mundo = mundoView.mundo;
        this.centro = espacial;


        this.centro.añadirObservador(this);
    }

    private void añadirPC (MobPC mobPC)
    {
        if (!listaPCsCercanos.contains(mobPC))
        {
            listaPCsCercanos.add(mobPC);
            //NOTIFICAR A LA VISTA:
        }

    }

    private void eliminarPC (MobPC mobPC)
    {
        if (listaPCsCercanos.contains(mobPC))
        {
            listaPCsCercanos.remove(mobPC);
            //NOTIFICAR A LA VISTA:
        }
    }

    public void dispose()
    {
        centro.eliminarObservador(this);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPC.CrearPC)
        {   }//añadirPC (((DTOsPC.CrearPC) evt.getNewValue()).); }

        if (evt.getNewValue() instanceof DTOsPC.EliminarPC)
        {   }//eliminarPC (((DTOsCampoVision.EliminarPC) evt.getNewValue()).pc);}

        if (evt.getNewValue() instanceof DTOsPC.Posicion)
        {   //setPosition(((DTOsCampoVision.Posicion) evt.getNewValue()).posX,
            //            ((DTOsCampoVision.Posicion) evt.getNewValue()).posY);
        }

        if (evt.getNewValue() instanceof DTOsPC.Dispose)
        {   }//dispose(); }

    }


}
