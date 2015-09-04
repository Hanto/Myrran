package Model.Classes.Mobiles.Modulares.Proyectil;// Created by Hanto on 04/09/2015.

import DB.DAO;
import InterfacesEntidades.EntidadesPropiedades.Caster;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Propiedades.ConsumibleBase;
import Model.Mobiles.Propiedades.IdentificableBase;
import Model.Mobiles.Propiedades.ProyectilStatsBase;

public enum ProyectilFactory
{
    NUEVOPROYECTIL
    {
        Proyectil proyectil;

        @Override public ProyectilFactory nuevo(MundoI mundo, int ancho, int alto)
        {
            proyectil = new Proyectil(
                    BodyFactory.crearCuerpoEtereo.RECTANGULAR.nuevo(mundo.getWorld(), ancho, alto),
                    new IdentificableBase(), new ProyectilStatsBase(), new ConsumibleBase());
            return this;
        }

        @Override public ProyectilFactory setID ()
        {   proyectil.setID(0); return this; }

        @Override public ProyectilFactory setID (int iD)
        {   proyectil.setID(iD); return this; }

        @Override public ProyectilFactory setOwner (Caster owner)
        {   proyectil.setOwner(owner); return this; }

        @Override public ProyectilFactory setSpell (SpellI spell)
        {   proyectil.setSpell(spell); return this; }

        @Override public ProyectilFactory setSpell (String spellID)
        {   proyectil.setSpell(DAO.spellDAOFactory.getSpellDAO().getSpell(spellID)); return this; }

        @Override public ProyectilFactory setPosition (float origenX, float origenY)
        {   proyectil.setPosition(origenX, origenY); return this; }

        @Override public ProyectilFactory setDireccion (float destinoX, float destinoY)
        {   proyectil.setDireccion(destinoX, destinoY); return this; }

        @Override public ProyectilFactory setDireccionEnGrados (float direccion)
        {   proyectil.setDireccion(direccion); return this; }

        @Override public ProyectilFactory setDaño (float daño)
        {   proyectil.setDaño(daño); return this; }

        @Override public ProyectilFactory setVelocidad (float velocidad)
        {   proyectil.setVelocidadMax(velocidad); return this; }

        @Override public ProyectilFactory setDuracion (float duracion)
        {   proyectil.setDuracionMaxima(duracion); return this; }

        @Override public Proyectil build()
        {   return proyectil; }
    };
    public abstract ProyectilFactory nuevo(MundoI mundo, int ancho, int alto);
    public abstract ProyectilFactory setID ();
    public abstract ProyectilFactory setID (int ID);
    public abstract ProyectilFactory setOwner (Caster owner);
    public abstract ProyectilFactory setSpell (SpellI spell);
    public abstract ProyectilFactory setSpell (String spellID);
    public abstract ProyectilFactory setDaño (float daño);
    public abstract ProyectilFactory setVelocidad (float velocidad);
    public abstract ProyectilFactory setDuracion (float duracion);
    public abstract ProyectilFactory setPosition (float origenX, float origenY);
    public abstract ProyectilFactory setDireccion (float destinoX, float destinoY);
    public abstract ProyectilFactory setDireccionEnGrados (float direccion);
    public abstract Proyectil build();
}
