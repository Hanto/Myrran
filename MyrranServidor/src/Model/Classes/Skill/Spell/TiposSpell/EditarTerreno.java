package Model.Classes.Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import DTOs.DTOsNet;
import Model.Settings;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Model.Skills.Spell.TipoSpell;

public class EditarTerreno extends TipoSpell
{
    @Override public void inicializarSkillStats() 
    {   setNumSkillStats(1); }

    @Override public void ejecutarCasteo(SpellI skill, Caster Caster, int targetX, int targetY, MundoI mundo)
    {
        int tileX = (targetX / Settings.TILESIZE);
        int tileY = (targetY / Settings.TILESIZE);

        int numCapa = 0;
        short iDTerreno = 0;

        if (Caster.getParametrosSpell() instanceof DTOsNet.ParametrosEditarTerreno)
        {
            numCapa = ((DTOsNet.ParametrosEditarTerreno) Caster.getParametrosSpell()).capaTerrenoSeleccionada;
            iDTerreno = ((DTOsNet.ParametrosEditarTerreno) Caster.getParametrosSpell()).terrenoIDSeleccionado;
        }

        mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
    }
}
