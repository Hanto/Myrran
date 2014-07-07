package DB.Datos.TipoSpell;// Created by Hanto on 13/05/2014.

import DAO.TipoSpell.TipoSpellXML;
import DAO.TipoSpell.TipoSpellDAO;

public enum TipoSpellDAOFactory
{
    XML("XML")
    {
        @Override
        public TipoSpellDAO getTipoSpellDAO()
        {   return new TipoSpellXML(TipoSpellXMLDB.get()); }
    };

    public abstract TipoSpellDAO getTipoSpellDAO();
    private TipoSpellDAOFactory(String nombre) {}
}
