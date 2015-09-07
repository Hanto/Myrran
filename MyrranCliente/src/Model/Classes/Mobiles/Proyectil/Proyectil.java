package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.Consumible;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Misc.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Model.Mobiles.Cuerpos.Cuerpo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class Proyectil extends ProyectilNotificador
{
    protected IDentificable identificable;
    protected ProyectilStats proyectilStats;
    protected Consumible consumible;
    protected Cuerpo cuerpo;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Proyectil(Cuerpo cuerpo,
                     IDentificable identificable, ProyectilStats proyectilStats,
                     Consumible consumible)
    {
        this.identificable = identificable;
        this.proyectilStats = proyectilStats;
        this.consumible = consumible;
        this.cuerpo = cuerpo;

        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
        this.setSeguible(false);
        this.cuerpo.setCalculosInterpolados(true);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        this.eliminarObservadores();
    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                                    {   return identificable.getID(); }

    // PROYECTILSTATS:
    //------------------------------------------------------------------------------------------------------------------
    @Override public Caster getOwner()                              {   return proyectilStats.getOwner(); }
    @Override public SpellI getSpell()                              {   return proyectilStats.getSpell(); }
    @Override public float getDaño()                                {   return proyectilStats.getDaño(); }
    @Override public void setSpell(SpellI spell)                    {   proyectilStats.setSpell(spell); }
    @Override public void setDaño(float daño)                       {   proyectilStats.setDaño(daño); }
    @Override public void setOwner(Caster caster)                   {   proyectilStats.setOwner(caster); }

    // CONSUMIBLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public float getDuracionActual()                      {   return consumible.getDuracionActual(); }
    @Override public float getDuracionMaxima()                      {   return consumible.getDuracionMaxima(); }
    @Override public void setDuracionMaxima(float duracionMaxima)   {   consumible.setDuracionMaxima(duracionMaxima); }
    @Override public void setDuracionActual(float duracionActual)   {   consumible.setDuracionActual(duracionActual); }
    @Override public boolean actualizarDuracion(float delta)        {   return consumible.actualizarDuracion(delta); }

    // CORPOREO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public Cuerpo getCuerpo()
    {   return cuerpo; }

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setID(int iD)
    {
        if (iD >= 0) identificable.setID(iD);
        else
        {
            if (proyectilStats.getOwner() == null)
            {   logger.error("ERROR: no se puede generar el ID del proyectil con el Owner NULL"); }

            double hashcode;
            if (proyectilStats.getOwner() instanceof PCI) {   //el int en java como tiene signo, tiene 31 bits para datos:
                hashcode = ((PCI) proyectilStats.getOwner()).getID() * (10000) +
                        ((PCI) proyectilStats.getOwner()).getIDProyectiles();
                identificable.setID((int) hashcode);
            }
            if (proyectilStats.getOwner() instanceof MobI) {
                hashcode = 10000 * 10000 +
                        ((MobI) proyectilStats.getOwner()).getID() * (10000);
                //+proyectilStats.getOwner().getIDProyectiles();
                identificable.setID((int) hashcode);
            }
        }
    }

    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        cuerpo.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setDireccion(float x, float y)
    {   cuerpo.setDireccion(x, y); }

    @Override public void setDireccion(float grados)
    {   cuerpo.setDireccion(grados); }

    @Override public void setVelocidadMax(float velocidadMax)
    {
        super.setVelocidadMax(velocidadMax);
        cuerpo.setVelocidad(velocidadMax);
    }

    @Override public void copiarUltimaPosicion()
    {   cuerpo.copiarUltimaPosicion(); }

    // ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarFisicaPorInterpolacion(float alpha)
    {
        cuerpo.interpolarPosicion(alpha);
        super.setPosition(cuerpo.getX(), cuerpo.getY());
        notificarSetPosition();
    }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        super.setPosition(cuerpo.getX(), cuerpo.getY());
        notificarSetPosition();
    }

    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable) {}
    @Override public void checkColisionesConMuro() {}
}
