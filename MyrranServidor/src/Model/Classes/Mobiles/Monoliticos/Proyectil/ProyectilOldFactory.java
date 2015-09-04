package Model.Classes.Mobiles.Monoliticos.Proyectil;// Created by Hanto on 06/08/2015.

import DB.DAO;
import InterfacesEntidades.EntidadesPropiedades.Caster;
import Interfaces.Spell.SpellI;
import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum ProyectilOldFactory
{
    ESFERA()
    {
        ProyectilOld proyectil;

        @Override public ProyectilOldFactory nuevo (World world, int ancho, int alto)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(world, ancho, alto);
            proyectil = new ProyectilOld(cuerpo);

            return this;
        }

        @Override public ProyectilOldFactory setID ()
        {   proyectil.setID(); return this; }

        @Override public ProyectilOldFactory setID (int iD)
        {   proyectil.setID(iD); return this; }

        @Override public ProyectilOldFactory setOwner (Caster owner)
        {   proyectil.setOwner(owner); return this; }

        @Override public ProyectilOldFactory setSpell (SpellI spell)
        {   proyectil.setSpell(spell); return this; }

        @Override public ProyectilOldFactory setSpell (String spellID)
        {   proyectil.setSpell(DAO.spellDAOFactory.getSpellDAO().getSpell(spellID)); return this; }

        @Override public ProyectilOldFactory setPosition (float origenX, float origenY)
        {   proyectil.setPosition(origenX, origenY); return this; }

        @Override public ProyectilOldFactory setDireccion (float destinoX, float destinoY)
        {   proyectil.setDireccion(destinoX, destinoY); return this; }

        @Override public ProyectilOldFactory setDireccionEnGrados (float direccion)
        {   proyectil.setDireccion(direccion); return this; }

        @Override public ProyectilOldFactory setDaño (float daño)
        {   proyectil.setDaño(daño); return this; }

        @Override public ProyectilOldFactory setVelocidad (float velocidad)
        {   proyectil.setVelocidadMax(velocidad); return this; }

        @Override public ProyectilOldFactory setDuracion (float duracion)
        {   proyectil.setDuracionMaxima(duracion); return this; }

        @Override public ProyectilOld build()
        {   return proyectil; }
    };

    public abstract ProyectilOldFactory nuevo (World world, int ancho, int alto);
    public abstract ProyectilOldFactory setID ();
    public abstract ProyectilOldFactory setID (int ID);
    public abstract ProyectilOldFactory setOwner (Caster owner);
    public abstract ProyectilOldFactory setSpell (SpellI spell);
    public abstract ProyectilOldFactory setSpell (String spellID);
    public abstract ProyectilOldFactory setDaño (float daño);
    public abstract ProyectilOldFactory setVelocidad (float velocidad);
    public abstract ProyectilOldFactory setDuracion (float duracion);
    public abstract ProyectilOldFactory setPosition (float origenX, float origenY);
    public abstract ProyectilOldFactory setDireccion (float destinoX, float destinoY);
    public abstract ProyectilOldFactory setDireccionEnGrados (float direccion);
    public abstract ProyectilOld build();
}
