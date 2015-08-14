package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 08/04/2014.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Cuerpos.Cuerpo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class Proyectil extends ProyectilNotificador implements ProyectilI
{
    protected int iD;                                                               // ProyectilStats:
    protected Cuerpo cuerpo;                                                        // Corporeo:
    protected Caster owner;                                                         // ProyectilStats:
    protected SpellI spell;
    protected float daño;
    protected float duracionActual = 0.0f;                                          //Consumible
    protected float duracionMaxima = 5f;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());    // Logger:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }

    // PROYECTILSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Caster getOwner()                                  { return owner; }
    @Override public SpellI getSpell()                                  { return spell; }
    @Override public float getDaño()                                    { return daño; }
    @Override public void setSpell(SpellI spell)                        { this.spell = spell; }
    @Override public void setDaño(float daño)                           { this.daño = daño; }

    // CONSUMIBLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getDuracionActual()                          { return duracionActual; }
    @Override public float getDuracionMaxima()                          { return duracionMaxima; }
    @Override public void setDuracionActual(float duracionActual)       { this.duracionActual = duracionActual; }
    @Override public void setDuracionMaxima(float duracionMaxima)       { this.duracionMaxima = duracionMaxima; }

    //CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Proyectil(Cuerpo cuerpo)
    {   this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
        this.cuerpo.setCalculosInterpolados(true);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        notificarSetDispose();
    }

    public void setID()
    {
        if (owner == null)
        {   logger.error("ERROR: no se puede generar el ID del proyectil con el Owner NULL"); }

        if (owner instanceof PCI)
        {   //el int en java como tiene signo, tiene 31 bits para datos:
            //Destino un bit para determinar si es de player o de mob:
            double hashcode = Math.pow(2, 31 - 1);
            //Destino 10 bits para dejar sitio para 1,025 players 2^10
            hashcode = hashcode + owner.getID() * Math.pow(2, 31 - 1 - 10);
            //Cada player podra disparar 2^20 = 1,048,576 pepos antes de tener que reiniciar el contador
            hashcode = hashcode + ((PCI) owner).getIDProyectiles();
            iD = (int)hashcode;
        }
        if (owner instanceof MobI)
        {   iD = 0; }
    }

    public void setID(int iD)
    {   this.iD = iD; }

    @Override public void setOwner (Caster caster)
    {   this.owner = caster; }

    @Override public void setVelocidadMax(float velocidadMax)
    {
        this.velocidadMax = velocidadMax;
        cuerpo.setVelocidad(velocidadMax);
    }

    @Override public void setPosition(float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.posicion.x = x;
        this.posicion.y = y;
        notificarSetPosition();
    }

    @Override public boolean consumirse (float delta)
    {
        duracionActual += delta;
        if (duracionActual > duracionMaxima ) return true;
        else return false;
    }

    private void getPosicionInterpoladaCuerpo()
    {
        this.posicion.x = cuerpo.getX();
        this.posicion.y = cuerpo.getY();
        notificarSetPosition();
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