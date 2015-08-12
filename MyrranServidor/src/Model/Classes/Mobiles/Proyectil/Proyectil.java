package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 07/04/2014.

import DTO.DTOsProyectil;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Classes.AI.Steering.SteerableAbstract;
import Model.Cuerpos.Cuerpo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class Proyectil extends SteerableAbstract implements ProyectilI
{
    //Identificable:
    protected int iD;

    //ProyectilStats:
    protected Caster owner;
    protected SpellI spell;
    protected float daño;

    //Consumible:
    protected float duracionActual = 0.0f;
    protected float duracionMaxima = 5f;

    //Corporeol:
    protected Cuerpo cuerpo;

    //Logger:
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return iD; }
    @Override public void setID(int iD)                                 { this.iD = iD; }

    // PROYECTILSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Caster getOwner()                                  { return owner; }
    @Override public SpellI getSpell()                                  { return spell; }
    @Override public float getDaño()                                    { return daño; }
    @Override public void setOwner(Caster caster)                       { this.owner = caster; }
    @Override public void setSpell(SpellI spell)                        { this.spell = spell; }
    @Override public void setDaño(float daño)                           { this.daño = daño; }

    // CONSUMIBLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getDuracionActual()                          { return duracionActual; }
    @Override public float getDuracionMaxima()                          { return duracionMaxima; }
    @Override public void setDuracionActual(float duracionActual)       { this.duracionActual = duracionActual; }
    @Override public void setDuracionMaxima(float duracionMaxima)       { this.duracionMaxima = duracionMaxima; }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }


    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Proyectil( Cuerpo cuerpo )
    {   this.cuerpo = cuerpo; }

    @Override public void dispose()
    {
        cuerpo.dispose();
        DTOsProyectil.DisposeProyectil dispose = new DTOsProyectil.DisposeProyectil(this);
        notificarActualizacion("dispose", null, dispose);
        this.eliminarObservadores();
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setID()
    {
        if (owner == null)
        {   logger.error("ERROR: no se puede generar el ID del proyectil con el Owner NULL"); }

        if (owner instanceof PCI)
        {   //el int en java como tiene signo, tiene 31 bits para datos:
            //Destino un bit para determinar si es de player o de mob:
            double hashcode = Math.pow(2, 31 - 1);
            //Destino 10 bits para dejar sitio para 1,025 players 2^10
            hashcode = hashcode + ((PCI) owner).getID() * Math.pow(2, 31 - 1 - 10);
            //Cada player podra disparar 2^20 = 1,048,576 pepos antes de tener que reiniciar el contador
            hashcode = hashcode + ((PCI) owner).getIDProyectiles();
            iD = (int)hashcode;
        }
        if (owner instanceof MobI)
        {   iD = 0; }
    }

    @Override public void setVelocidadMax(float velocidadMax)
    {
        this.velocidadMax = velocidadMax;
        cuerpo.setVelocidad(velocidadMax);
    }

    @Override public void setDireccion(float x, float y)
    {   cuerpo.setDireccion(x, y); }

    @Override public void setDireccion(float grados)
    {   cuerpo.setDireccion(grados); }

    @Override public void setPosition(float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.posicion.x = x;
        this.posicion.y = y;

        DTOsProyectil.PosicionProyectil posicion = new DTOsProyectil.PosicionProyectil(this);
        notificarActualizacion("actualizarPosicion", null, posicion);
    }

    @Override public boolean consumirse (float delta)
    {
        duracionActual += delta;
        if (duracionActual > duracionMaxima ) return true;
        else return false;
    }

    private void getPosicionInterpoladaCuerpo()
    {
        this.posicion.x = cuerpo.getXinterpolada();
        this.posicion.y = cuerpo.getYinterpolada();

        DTOsProyectil.PosicionProyectil posicion = new DTOsProyectil.PosicionProyectil(this);
        notificarActualizacion("actualizarPosiciojn", null, posicion);
    }

    @Override public void copiarUltimaPosicion()
    {   cuerpo.copiarUltimaPosicion(); }

    @Override public void interpolarPosicion(float alpha)
    {
        cuerpo.interpolarPosicion(alpha);
        getPosicionInterpoladaCuerpo();
    }

    @Override public void actualizar (float delta, MundoI mundo)
    { }


}