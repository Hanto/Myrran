package DB.Datos.BDebuff;

import DAO.BDebuff.BDebuffDAO;
import DAO.BDebuff.BDebuffDAOFactoryI;
import DAO.BDebuff.BDebuffXML;

public enum BDebuffDAOFactory implements BDebuffDAOFactoryI
{
    XML("XML")
    {
        @Override public BDebuffDAO getBDebuffDAO()
        {   return new BDebuffXML(BDebuffXMLDB.get()); }
    };

    public abstract BDebuffDAO getBDebuffDAO();
    private BDebuffDAOFactory(String nombre) {}

}