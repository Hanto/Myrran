package Model.FSM;// Created by Hanto on 16/07/2014.

public abstract class Estado
{
    protected MaquinaEstados fsm;

    public Estado(MaquinaEstados maquinaEstados)
    {   this.fsm = maquinaEstados; }

    public MaquinaEstados getFSM()
    {   return fsm; }

    public abstract void enter();
    public abstract void update(float deltaTime);
    public abstract void exit();
}
