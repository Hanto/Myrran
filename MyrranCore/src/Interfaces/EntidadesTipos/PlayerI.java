package Interfaces.EntidadesTipos;// Created by Hanto on 10/06/2014.

import DTOs.DTOsNet;
import Interfaces.EntidadesPropiedades.Espaciales.Corporeo;
import Interfaces.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import Interfaces.EntidadesPropiedades.Propiedades.MaquinablePlayer;

public interface PlayerI extends PCI, EspacialInterpolado, MaquinablePlayer, Corporeo
{
    public boolean notificadorContieneDatos();
    public DTOsNet.PlayerDTOs getDTOs();
    public void setNumTalentosSkillPersonalizadoFromServer(String skillID, int statID, int valor);
}
