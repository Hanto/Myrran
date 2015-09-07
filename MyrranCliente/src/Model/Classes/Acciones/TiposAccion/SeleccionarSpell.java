package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 05/05/2014.

import DB.DAO;
import DTOs.DTOsInputManager;
import Interfaces.EntidadesPropiedades.Misc.MaquinablePlayer;
import Interfaces.Misc.Model.ModelI;
import Interfaces.Misc.Spell.SpellI;
import Model.Classes.Acciones.Accion;
import Model.Settings;

public class SeleccionarSpell extends Accion
{
    public SeleccionarSpell(SpellI spell)
    {
        iD = spell.getID();
        parametros = spell.getTipoSpell().getID();
    }

    public SeleccionarSpell(String idSpell)
    {
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(idSpell);
        iD = spell.getID();
        parametros = spell.getTipoSpell().getID();
    }

    @Override public void accionKeyDown(MaquinablePlayer player, ModelI inputManager)
    {
        if (parametros.equals(Settings.BARRATERRENOS_EditarTerrenoID))
        inputManager.notificarActualizacion("mostrarBarraTerrenos", null, new DTOsInputManager.MostrarBarraTerrenos());
        //{ controlador.mostrarBarraTerrenos(); /*player.setParametrosSpell(new ParametrosEditarTerreno());*/}

        player.getInput().setSpellID(iD);
    }

    @Override public void accionKeyUp(MaquinablePlayer player, ModelI inputManager)
    {
        if (parametros.equals(Settings.BARRATERRENOS_EditarTerrenoID))
            inputManager.notificarActualizacion("ocultarBarraTerrenos", null, new DTOsInputManager.OcultarBarraTerrenos());
            //controlador.ocultarBarraTerrenos();
    }
}
