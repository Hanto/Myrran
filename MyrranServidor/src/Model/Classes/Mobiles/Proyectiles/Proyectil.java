package Model.Classes.Mobiles.Proyectiles;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Propiedades.*;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Model.Mobiles.Cuerpos.Cuerpo;

public class Proyectil extends ProyectilNotificador
{
    protected IDentificable identificable;
    protected ProyectilStats proyectilStats;
    protected Consumible consumible;
    protected Cuerpo cuerpo;

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
        this.cuerpo.setCalculosInterpolados(false);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        notificarSetDispose();
        this.eliminarObservadores();
    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                                    {   return identificable.getID(); }
    @Override public void setID(int iD)                             {   identificable.setID(iD); }

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
    //-----------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable)
    {
        if (colisionable instanceof Vulnerable) aplicarDaño((Vulnerable)colisionable);
        if (colisionable instanceof Debuffeable) aplicarDebuffs((Debuffeable)colisionable);
        consumible.setDuracionActual(consumible.getDuracionMaxima() + 1);
    }

    @Override public void checkColisionesConMuro()
    {   consumible.setDuracionActual(consumible.getDuracionMaxima() + 1); }

    private void aplicarDaño(Vulnerable vulnerable)
    {   vulnerable.modificarHPs(-proyectilStats.getDaño()); }

    private void aplicarDebuffs(Debuffeable debuffeable)
    {   proyectilStats.getSpell().aplicarDebuffs(proyectilStats.getOwner(), debuffeable); }
}
