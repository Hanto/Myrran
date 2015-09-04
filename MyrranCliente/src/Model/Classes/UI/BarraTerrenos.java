package Model.Classes.UI;// Created by Hanto on 14/05/2014.

import DAO.Terreno.TerrenoDAO;
import DB.DAO;
import DTO.DTOsBarraTerrenos;
import InterfacesEntidades.EntidadesPropiedades.Caster;
import Interfaces.Geo.TerrenoI;
import Interfaces.Observable.AbstractModel;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

import static DTO.DTOsParametrosSpell.ParametrosEditarTerreno;

public class BarraTerrenos extends AbstractModel
{
    private Array<Short>barraTerrenos = new Array<>();
    private Caster Caster;

    private short parametroTerrenoID = 0;
    private int parametroNumCapa = 0;

    public short getTerrenoID (int posX)                { return barraTerrenos.get(posX); }
    public int getTamaño()                              { return barraTerrenos.size; }

    public BarraTerrenos (Caster player)
    {
        crearBarraTerrenos();
        this.Caster = player;
    }

    public void crearBarraTerrenos()
    {
        barraTerrenos.clear();

        TerrenoDAO terrenoDAO = DAO.terrenoDAOFactory.getTerrenoDAO();

        Iterator<TerrenoI> iterator = terrenoDAO.getIterator();
        while (iterator.hasNext())
        {   barraTerrenos.add(iterator.next().getID()); }

        Object crearBarraTerrenos = new DTOsBarraTerrenos.CrearBarraTerreno();
        notificarActualizacion("crearBarraTerrenos", null, crearBarraTerrenos);
    }

    public void moverTerreno(int posOrigen, int posDestino)
    {
        short origen = barraTerrenos.get(posOrigen);
        setTerreno(posOrigen, getTerrenoID(posDestino));
        setTerreno(posDestino, origen);
    }

    public void setTerreno (int posX, short terrenoID)
    {
        barraTerrenos.set(posX, terrenoID);

        Object setTerrenoDTO = new DTOsBarraTerrenos.SetTerreno(posX);
        notificarActualizacion("setTerreno", null, setTerrenoDTO);
    }

    public void setParametroTerrenoID(short terrenoID)
    {
        //if (caster.getParametrosSpell() instanceof ParametrosEditarTerreno)
        {
            //ParametrosEditarTerreno editarTerreno = (ParametrosEditarTerreno)caster.getParametrosSpell();
            ParametrosEditarTerreno editarTerreno = new ParametrosEditarTerreno(parametroNumCapa, terrenoID);
            parametroTerrenoID = terrenoID;
            //editarTerreno.terrenoIDSeleccionado = terrenoID;
            Caster.setParametrosSpell(editarTerreno);
        }
    }

    public void setParametroNumCapa(int numCapa)
    {
        //if (caster.getParametrosSpell() instanceof ParametrosEditarTerreno)
        {
            //ParametrosEditarTerreno editarTerreno = (ParametrosEditarTerreno)caster.getParametrosSpell();
            ParametrosEditarTerreno editarTerreno = new ParametrosEditarTerreno(numCapa, parametroTerrenoID);
            parametroNumCapa = numCapa;
            //editarTerreno.capaTerrenoSeleccionada = numCapa;
            Caster.setParametrosSpell(editarTerreno);
        }
    }
}
