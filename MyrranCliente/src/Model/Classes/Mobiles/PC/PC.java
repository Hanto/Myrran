package Model.Classes.Mobiles.PC;// Created by Hanto on 08/04/2014.

import Interfaces.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Model.AbstractModel;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Cuerpos.Cuerpo;
import Model.Settings;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class PC extends AbstractModel implements PCI
{
    protected int iD;

    protected int ultimoMapTileX;
    protected int ultimoMapTileY;
    protected float x;
    protected float y;
    protected int numAnimacion = 5;

    protected float actualHPs;
    protected float maxHPs;

    protected Cuerpo cuerpo;
    public PCNotificador notificador;

    @Override public int getID()            { return iD; }

    @Override
    public void setID(int iD)
    {
        this.iD = iD;
    }

    @Override public int getNumAnimacion()  { return numAnimacion; }
    @Override public float getX()           { return x; }
    @Override public float getY()           { return y; }
    @Override public int getUltimoMapTileX(){ return ultimoMapTileX; }
    @Override public int getUltimoMapTileY(){ return ultimoMapTileY; }
    @Override public int getMapTileX()      { return (int)(x / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()      { return (int)(y / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }

    //TODO
    @Override public float getActualHPs()                       { return actualHPs; }
    @Override public float getMaxHPs()                          { return maxHPs; }
    @Override public void setActualHPs(float HPs)               { modificarHPs(HPs - actualHPs);}
    @Override public void setMaxHPs(float HPs)                  { this.maxHPs = HPs; }
    @Override public String getNombre()                         { return null; }
    @Override public int getIDProyectiles()                     { return 0; }
    @Override public int getNivel()                             { return 0; }
    @Override public Vector2 getVelocidad()                     { return null; }
    @Override public float getVelocidadMod()                    { return 0; }

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

    @Override public float getVelocidadMax()                    { return 0; }

    @Override
    public float getAceleracionMax()
    {
        return 0;
    }

    @Override public SkillPersonalizadoI getSkillPersonalizado(String skillID)  { return null; }
    @Override public SpellPersonalizadoI getSpellPersonalizado(String spellID)  { return null; }
    @Override public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado() { return null; }
    @Override public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado() { return null; }
    @Override public boolean isCasteando()                      { return false; }
    @Override public float getActualCastingTime()               { return 0; }
    @Override public float getTotalCastingTime()                { return 0; }
    @Override public String getSpellIDSeleccionado()            { return null; }
    @Override public Object getParametrosSpell()                { return null; }
    @Override public Cuerpo getCuerpo()                         { return null; }

    @Override public void setUltimoMapTile (int x, int y)       { ultimoMapTileX = x; ultimoMapTileY = y; }
    @Override public void setNombre (String nombre)             {}
    @Override public void setNivel (int nivel)                  {}
    @Override public void setVelocidaMod(float velocidadMod)    {}

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

    @Override public void setVelocidadMax(float velocidadMax)   {}

    @Override
    public void setAceleracionMax(float aceleracionMax)
    {

    }

    @Override public void setDireccion(float x, float y)        {}
    @Override public void setDireccion(float grados)            {}
    @Override public void setCastear(boolean castear, int screenX, int screenY) {}
    @Override public void setTotalCastingTime(float castingTime){}
    @Override public void setSpellIDSeleccionado(String spellID){}
    @Override public void setParametrosSpell(Object parametrosDTO){}
    @Override public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento) {}

    //Constructor:
    public PC(int connectionID, Cuerpo cuerpo)
    {
        this.notificador = new PCNotificador(this);
        this.iD = connectionID;
        this.cuerpo = cuerpo;
    }

    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);
        this.x = x; this.y = y;
        notificador.setPosition(x, y);
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificador.setNumAnimacion(numAnimacion);
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
        notificador.setDispose();
        this.eliminarObservadores();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificador.setModificarHPs(HPs);
    }

    @Override public void añadirSkillsPersonalizados(String spellID)
    {

    }

    @Override public void actualizar (float delta, MundoI mundo)
    {

    }

    @Override
    public Vector2 getPosition()
    {
        return null;
    }

    @Override
    public float getOrientation()
    {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return null;
    }

    @Override
    public float getAngularVelocity()
    {
        return 0;
    }

    @Override
    public float getBoundingRadius()
    {
        return 0;
    }

    @Override
    public boolean isTagged()
    {
        return false;
    }

    @Override
    public void setTagged(boolean tagged)
    {

    }

    @Override
    public Vector2 newVector()
    {
        return null;
    }

    @Override
    public float vectorToAngle(Vector2 vector)
    {
        return 0;
    }

    @Override public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        return null;
    }

    @Override
    public float getMaxLinearSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {

    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {

    }

    @Override
    public float getMaxAngularSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {

    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {

    }
}
