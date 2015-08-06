package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 04/08/2015.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesPropiedades.EspacialInterpolado;
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

        ProyectilI proyectil = ProyectilFactory.ESFERA24x24.nuevo(mundo);

        float origenX, origenY;

        if (caster instanceof EspacialInterpolado)
        {
            origenX = ((EspacialInterpolado) caster).getCuerpo().getCentroXinterpolada() - proyectil.getCuerpo().getAncho()/2;
            origenY = ((EspacialInterpolado) caster).getCuerpo().getCentroYinterpolada() - proyectil.getCuerpo().getAlto()/2;
        }
        else if (caster instanceof Espacial)
        {
            origenX = ((Espacial) caster).getX() - proyectil.getCuerpo().getAncho()/2;
            origenY = ((Espacial) caster).getY() - proyectil.getCuerpo().getAlto()/2;
        }
        else return;

        proyectil.setPosition(origenX, origenY);
        proyectil.setDireccion(targetX, targetY);
        proyectil.setSpell(spell);
        proyectil.setDaño(spell.getValorTotal(caster, STAT_DAÑO));
        proyectil.setVelocidadMax(spell.getValorTotal(caster, STAT_VELOCIDAD));
        proyectil.setDuracionMaxima(spell.getValorTotal(caster, STAT_DURACION));
        mundo.añadirProyectil(proyectil);
    }
}
