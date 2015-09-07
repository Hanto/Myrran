package Model.Classes.Mobiles.Mob;// Created by Hanto on 04/09/2015.

import Interfaces.BDebuff.AuraI;
import InterfacesEntidades.EntidadesPropiedades.Debuffeable;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Colisionable;
import InterfacesEntidades.EntidadesPropiedades.IDentificable;
import InterfacesEntidades.EntidadesPropiedades.MobStats;
import InterfacesEntidades.EntidadesPropiedades.Vulnerable;
import InterfacesEntidades.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;
import Model.AI.Huellas.Huellas;

import java.util.Iterator;

public class Mob extends MobNotificador implements MobI, Debuffeable
{
    private IDentificable identificable;
    private Vulnerable vulnerable;
    private Debuffeable debuffeable;
    private MobStats mobStats;

    public Mob(int iD, int ancho, int alto,
               IDentificable identificable, Vulnerable vulnerable,
               Debuffeable debuffeable, MobStats mobStats)
    {
        this.identificable = identificable;
        this.vulnerable = vulnerable;
        this.debuffeable = debuffeable;
        this.mobStats = mobStats;


        this.identificable.setID(iD);
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setHuellas(new Huellas());
        this.setSeguible(true);
        this.setTiempoDecayHuellas(5f);

        this.vulnerable.setMaxHPs(10000);
        this.vulnerable.setActualHPs(10000);
    }

    @Override public void dispose()
    {

    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                                    {   return identificable.getID(); }
    @Override public void setID(int iD)                             {   identificable.setID(iD); }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setDireccion(float x, float y)            { }
    @Override public void setDireccion(float grados)                { }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public float getActualHPs()                           {   return vulnerable.getActualHPs(); }
    @Override public float getMaxHPs()                              {   return vulnerable.getMaxHPs(); }
    @Override public void setActualHPs(float HPs)                   {   vulnerable.setActualHPs(HPs); }
    @Override public void setMaxHPs(float HPs)                      {   vulnerable.setMaxHPs(HPs); }

    // DEBUFFEABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void añadirAura(AuraI aura)                    {   debuffeable.añadirAura(aura); }
    @Override public void eliminarAura(AuraI aura)                  {   debuffeable.eliminarAura(aura); }
    @Override public Iterator<AuraI> getAuras()                     {   return debuffeable.getAuras(); }
    @Override public void actualizarAuras(float delta)              {   debuffeable.actualizarAuras(delta); }

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setOrientacion(float radianes)
    {
        super.setOrientacion(radianes);
        notificarSetOrientacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        vulnerable.modificarHPs(HPs);
        notificarAddModificarHPs(HPs);
    }

    // ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta)
    {
        actualizarAuras(delta);
        actualizarHuellas(delta);
    }

    @Override public void actualizarIA(float delta, MundoI mundo)
    {

    }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        if (getSteeringBehavior() != null)
        {
            calcularSteering(delta);

            setPosition(nuevaPosX, nuevaPosY);
            if (getOrientation() != nuevaOrientacion)
                setOrientacion(nuevaOrientacion);
        }
    }

    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void checkColisionesConMob(Colisionable colisionable)  {}
    @Override public void checkColisionesConMuro() {}
}
