package Model.FSM;// Created by Hanto on 16/07/2014.

public abstract class Estado
{
    protected MaquinaEstados maquinaEstados;

    public Estado(MaquinaEstados maquinaEstados)
    {   this.maquinaEstados = maquinaEstados; }

    public MaquinaEstados getMaquinaEstados()
    {   return maquinaEstados; }

    public abstract void enter();
    public abstract void update(float deltaTime);
    public abstract void exit();
}
