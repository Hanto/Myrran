package DB.Datos.TipoBDebuff;

import DAO.TipoBDebuff.TipoBDebuffDAO;
import DAO.TipoBDebuff.TipoBDebuffXML;

public enum TipoBDebuffDAOFactory
{
    XML("XML")
    {
        @Override public TipoBDebuffDAO getTipoBDebuffDAO()
        {   return new TipoBDebuffXML(TipoBDebuffXMLDB.get()); }
    };

    public abstract TipoBDebuffDAO getTipoBDebuffDAO();

    private TipoBDebuffDAOFactory(String nombre) {}

}