package DB.Datos.TipoSpell;// Created by Hanto on 13/05/2014.

import DAO.TipoSpell.TipoSpellDAO;
import DAO.TipoSpell.TipoSpellDAOFactoryI;
import DAO.TipoSpell.TipoSpellXML;

public enum TipoSpellDAOFactory implements TipoSpellDAOFactoryI
{
    XML("XML")
    {
        @Override
        public TipoSpellDAO getTipoSpellDAO()
        {   return new TipoSpellXML(TipoSpellXMLDB.get()); }
    };

    public abstract TipoSpellDAO getTipoSpellDAO();
    private TipoSpellDAOFactory (String nombre) {}
}
