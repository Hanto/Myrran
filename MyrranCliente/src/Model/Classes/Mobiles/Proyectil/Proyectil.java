package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 08/04/2014.

import DTO.DTOsProyectil;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Model.AbstractClases.AbstractModel;
import Interfaces.Spell.SpellI;
import Model.Cuerpos.Cuerpo;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.math.Vector2;
import org.slf4j.LoggerFactory;

public class Proyectil extends AbstractModel implements ProyectilI
{
    //ProyectilStats:
    protected int iD;
    protected Cuerpo cuerpo;
    protected SpellI spell;
    protected Caster owner;
    protected float daño;

    //Espacial:
    protected int ultimoMapTileX = 0;
    protected int ultimoMapTileY = 0;
    protected Vector2 posicion = new Vector2();
    protected Vector2 velocidad = new Vector2();
    protected float velocidadMax=0.0f;
    protected float velocidadMod=1.0f;

    //Consumible
    protected float duracionActual = 0.0f;
    protected float duracionMaxima = 5f;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //GET:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return iD; }
    @Override public Vector2 getPosition()                              { return posicion; }
    @Override public float getX()                                       { return posicion.x; }
    @Override public float getY()                                       { return posicion.y; }
    @Override public Vector2 getVelocidad()                             { return velocidad; }
    @Override public int getUltimoMapTileX()                            { return ultimoMapTileX; }
    @Override public int getUltimoMapTileY()                            { return ultimoMapTileY; }
    @Override public int getMapTileX()                                  { return (int)(posicion.x / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                                  { return (int)(posicion.y / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public float getVelocidadMod()                            { return velocidadMod; }

    @Override
    public float getVelocidadAngular()
    {
        return 0;
    }

    @Override
    public float getVelocidadAngularMax()
    {
        return 0;
    }

    @Override
    public float getAceleracionAngularMax()
    {
        return 0;
    }

    @Override public float getVelocidadMax()                            { return velocidadMax; }

    @Override
    public float getAceleracionMax()
    {
        return 0;
    }

    @Override public float getDuracionActual()                          { return duracionActual; }
    @Override public float getDuracionMaxima()                          { return duracionMaxima; }
    @Override public Cuerpo getCuerpo()                                 { return cuerpo; }
    @Override public SpellI getSpell()                                  { return spell; }
    @Override public Caster getOwner()                                  { return owner; }
    @Override public float getDaño()                                    { return daño; }

    //SET:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setUltimoMapTile (int x, int y)               { ultimoMapTileX = x; ultimoMapTileY = y; }
    @Override public void setDireccion(float x, float y)                { cuerpo.setDireccion(x, y); }
    @Override public void setDireccion(float grados)                    { cuerpo.setDireccion(grados); }
    @Override public void setVelocidaMod(float velocidadMod)            { this.velocidadMod = velocidadMod; }

    @Override
    public void setVelocidadAngular(float velocidadAngular)
    {

    }

    @Override
    public void setVelocidadAngularMax(float velocidadAngularMax)
    {

    }

    @Override
    public void setAceleracionAngularMax(float aceleracionAngularMax)
    {

    }

    @Override public void setDuracionActual(float duracionActual)       { this.duracionActual = duracionActual; }
    @Override public void setDuracionMaxima(float duracionMaxima)       { this.duracionMaxima = duracionMaxima; }
    @Override public void setSpell(SpellI spell)                        { this.spell = spell; }
    @Override public void setDaño(float daño)                           { this.daño = daño; }

    //Constructor:
    public Proyectil(Cuerpo cuerpo)
    {   this.cuerpo = cuerpo;
        this.cuerpo.setCalculosInterpolados(true);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        DTOsProyectil.DisposeProyectil dispose = new DTOsProyectil.DisposeProyectil(this);
        notificarActualizacion("dispose", null, dispose);
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

    @Override
    public void setAceleracionMax(float aceleracionMax)
    {

    }

    @Override public void setPosition(float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.posicion.x = x;
        this.posicion.y = y;

        DTOsProyectil.PosicionProyectil posicion = new DTOsProyectil.PosicionProyectil(this);
        notificarActualizacion("actualizarPosiciojn", null, posicion);
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