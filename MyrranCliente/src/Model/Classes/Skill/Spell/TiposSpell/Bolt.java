package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 04/08/2015.

import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.Classes.Mobiles.Proyectil.ProyectilFactory;
import Model.Skills.Spell.TipoSpell;

public class Bolt extends TipoSpell
{
    @Override public void inicializarSkill()
    {
        stats().setNumStats(4);
        spellSlots().setNumSlots(1);
        debuffSlots().setNumSlots(10);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster caster, int targetX, int targetY, MundoI mundo)
    {
        int STAT_DAÑO = 1;
        int STAT_VELOCIDAD = 2;
        int STAT_DURACION = 3;

        int ANCHO = 24;
        int ALTO = 24;

        float origenX, origenY;

        if (caster instanceof Espacial)
        {
            origenX = ((Espacial) caster).getX();
            origenY = ((Espacial) caster).getY();
        }
        else return;

        ProyectilI proyectil = ProyectilFactory.NUEVOPROYECTIL.nuevo(mundo.getWorld(), ANCHO, ALTO)
                .setSpell(spell)
                .setOwner(caster)
                .setID()
                .setPosition(origenX, origenY)
                .setDireccion(targetX, targetY)
                .setDaño(spell.getValorTotal(caster, STAT_DAÑO))
                .setVelocidad(spell.getValorTotal(caster, STAT_VELOCIDAD))
                .setDuracion(spell.getValorTotal(caster, STAT_DURACION))
                .build();

        mundo.añadirProyectil(proyectil);
    }
}
