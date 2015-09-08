package Model.Mobiles.Propiedades;// Created by Hanto on 08/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Misc.Spell.SpellI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ProyectilStatsIDBase implements ProyectilStats, IDentificable
{
    protected int iD;
    protected Caster owner;
    protected SpellI spell;
    protected float daño;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    // PROYECTILSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Caster getOwner()              { return owner; }
    @Override public SpellI getSpell()              { return spell; }
    @Override public float getDaño()                { return daño; }
    @Override public void setSpell(SpellI spell)    { this.spell = spell; }
    @Override public void setDaño(float daño)       { this.daño = daño; }
    @Override public void setOwner(Caster caster)   { this.owner = caster; }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                    { return iD; }
    @Override public void setID(int iD)
    {
        if (iD >=0) this.iD = iD;
        else
        {
            if (owner == null)
            {   logger.error("ERROR: no se puede generar el ID del proyectil con el Owner NULL"); }

            double hashcode;
            if (owner instanceof PCI) {   //el int en java como tiene signo, tiene 31 bits para datos:
                hashcode = ((PCI) owner).getID() * (10000) +
                           ((PCI) owner).getIDProyectiles();
                this.iD = ((int) hashcode);
            }
            if (owner instanceof MobI) {
                hashcode = 10000 * 10000 +
                        ((MobI) owner).getID() * (10000);
                //+proyectilStats.getOwner().getIDProyectiles();
                this.iD = ((int) hashcode);
            }
        }
    }
}
