package InterfacesEntidades.EntidadesTipos;// Created by Hanto on 10/06/2014.

import DTO.DTOsPlayer;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Corporeo;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import InterfacesEntidades.EntidadesPropiedades.MaquinablePlayer;

public interface PlayerI extends PCI, EspacialInterpolado, MaquinablePlayer, Corporeo
{
    public boolean notificadorContieneDatos();
    public DTOsPlayer.PlayerDTOs getDTOs();
    public void setNumTalentosSkillPersonalizadoFromServer(String skillID, int statID, int valor);
}
