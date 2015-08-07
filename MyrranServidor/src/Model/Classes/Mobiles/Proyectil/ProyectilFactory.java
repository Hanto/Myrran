package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 06/08/2015.

import DB.DAO;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Cuerpos.BodyFactory;
import Model.Cuerpos.Cuerpo;

public enum ProyectilFactory
{
    ESFERA()
    {
        Proyectil proyectil;

        @Override public ProyectilFactory nuevo (MundoI mundo, int ancho, int alto)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(mundo.getWorld(), ancho, alto);
            proyectil = new Proyectil(mundo, cuerpo);

            return this;
        }

        @Override public ProyectilFactory setID ()
        {   proyectil.setID(); return this; }

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

    public abstract ProyectilFactory nuevo (MundoI mundo, int ancho, int alto);
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
