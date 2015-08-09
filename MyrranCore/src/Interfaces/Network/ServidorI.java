package Interfaces.Network;// Created by Hanto on 09/08/2015.

public interface ServidorI
{
    public void enviarACliente(int connectionID, Object dto);
    public void enviarATodosClientes(Object dto);
    public void enviarATodosClientesMenosUno(int connectionID, Object dto);

}
