package DB;// Created by Hanto on 11/06/2014.

import DAO.Settings.SettingsDAOFactory;
import DB.Datos.BDebuff.BDebuffDAOFactory;
import DB.Datos.Spell.SpellDAOFactory;
import DB.Datos.Terreno.TerrenoDAOFactory;
import DB.Datos.TipoBdDebuff.TipoBDebuffDAOFactory;
import DB.Datos.TipoSpell.TipoSpellDAOFactory;

public class DAO
{
    public static final SettingsDAOFactory settingsDAOFactory = SettingsDAOFactory.XML;
    public static final TerrenoDAOFactory terrenoDAOFactory = TerrenoDAOFactory.XML;
    public static final TipoSpellDAOFactory tipoSpellDAOFactory = TipoSpellDAOFactory.XML;
    public static final SpellDAOFactory spellDAOFactory = SpellDAOFactory.XML;
    public static final TipoBDebuffDAOFactory tipoBDebuffDAOFactory = TipoBDebuffDAOFactory.XML;
    public static final BDebuffDAOFactory debuffDAOFactory = BDebuffDAOFactory.XML;
}
