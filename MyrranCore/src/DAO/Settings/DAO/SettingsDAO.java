package DAO.Settings.DAO;// Created by Hanto on 06/07/2014.

public interface SettingsDAO
{
    public String getString(String key, String defaultValue);
    public float getFloat(String key, float defaultValue);
    public int getInt(String key, int defaultValue);
    public boolean getBoolean(String key, boolean defaultValue);

    public void setString(String key, String value);
    public void setFloat(String key, float value);
    public void setInt(String key, int value);
    public void setBoolean(String key, boolean value);
}
