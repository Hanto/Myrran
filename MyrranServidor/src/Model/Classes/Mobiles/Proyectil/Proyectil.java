package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 07/04/2014.

import Interfaces.AI.SistemaAggroI;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Vulnerable;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Mobiles.Cuerpos.Cuerpo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Proyectil extends ProyectilNotificador implements ProyectilI
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
    {
        this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
        this.cuerpo.setCalculosInterpolados(false);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        notificarSetDispose();
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

    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        cuerpo.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setVelocidadMax(float velocidadMax)
    {
        super.setVelocidadMax(velocidadMax);
        cuerpo.setVelocidad(velocidadMax);
    }

    @Override public void setDireccion(float x, float y)
    {   cuerpo.setDireccion(x, y); }

    @Override public void setDireccion(float grados)
    {   cuerpo.setDireccion(grados); }

    // COLISIONES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisiones(List<Colisionable> listaColisionables, SistemaAggroI aggro)
    {
        boolean haColisionado = false;

        for (Colisionable mobCercano : listaColisionables)
        {
            if (aggro.sonEnemigos(owner, mobCercano) && getHitbox().overlaps(mobCercano.getHitbox()) )
            {
                if (mobCercano instanceof Vulnerable) aplicarDaño((Vulnerable)mobCercano);
                if (mobCercano instanceof Debuffeable) aplicarDebuffs((Debuffeable)mobCercano);
                haColisionado = true;
            }
        }

        if (haColisionado) duracionActual = duracionMaxima+1;
    }

    private void aplicarDaño(Vulnerable vulnerable)
    {   vulnerable.modificarHPs(-daño); }

    private void aplicarDebuffs(Debuffeable debuffeable)
    {   spell.aplicarDebuffs(owner, debuffeable); }


    // METODOS DE ACTUALIZACION
    //------------------------------------------------------------------------------------------------------------------

    @Override public boolean actualizarDuracion(float delta)
    {
        duracionActual += delta;
        return duracionActual > duracionMaxima;
    }

    private void getPosicionInterpoladaCuerpo()
    {
        super.setPosition(cuerpo.getX(), cuerpo.getY());
        notificarSetPosition();
    }

    @Override public void copiarUltimaPosicion()
    {   cuerpo.copiarUltimaPosicion(); }

    @Override public void interpolarPosicion(float alpha)
    {
        cuerpo.interpolarPosicion(alpha);
        getPosicionInterpoladaCuerpo();
    }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {   getPosicionInterpoladaCuerpo(); }
}
