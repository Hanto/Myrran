package DAO.Settings;

import DAO.Settings.DAO.SettingsDAO;
import DAO.Settings.DAO.SettingsXML;

public enum SettingsDAOFactory
{
    XML("XML")
    {
        @Override public SettingsDAO getSettingsDAO()
        {   return new SettingsXML(); }
    };


    public abstract SettingsDAO getSettingsDAO();
    private SettingsDAOFactory(String nombre) {}

}