package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 04/08/2015.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesPropiedades.Corporal;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Classes.Mobiles.Proyectil.ProyectilFactory;
import Model.Classes.Skill.Spell.TipoSpell;

public class Bolt extends TipoSpell
{
    @Override public void inicializarSkillStats()
    {
        setID(this.getClass().getSimpleName().toUpperCase());
        setNumSkillStats(4);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster caster, int targetX, int targetY, MundoI mundo)
    {
        int STAT_DAÑO = 1;
        int STAT_VELOCIDAD = 2;
        int STAT_DURACION = 3;

        int ANCHO = 24;
        int ALTO = 24;

        float origenX, origenY;

        if (caster instanceof Corporal)
        {
            origenX = ((Corporal) caster).getCuerpo().getCentroXinterpolada() - ANCHO/2;
            origenY = ((Corporal) caster).getCuerpo().getCentroYinterpolada() - ALTO/2;
        }
        else if (caster instanceof Espacial)
        {
            origenX = ((Espacial) caster).getX() - ANCHO/2;
            origenY = ((Espacial) caster).getY() - ALTO/2;
        }
        else return;

        ProyectilI proyectil = ProyectilFactory.ESFERA.nuevo(mundo, ANCHO, ALTO)
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

        System.out.println(proyectil.getID());
    }
}
