package InterfacesEntidades.EntidadesTipos;// Created by Hanto on 10/06/2014.

import DTOs.DTOsNet;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Corporeo;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import InterfacesEntidades.EntidadesPropiedades.Misc.MaquinablePlayer;

public interface PlayerI extends PCI, EspacialInterpolado, MaquinablePlayer, Corporeo
{
    public boolean notificadorContieneDatos();
    public DTOsNet.PlayerDTOs getDTOs();
    public void setNumTalentosSkillPersonalizadoFromServer(String skillID, int statID, int valor);
}
