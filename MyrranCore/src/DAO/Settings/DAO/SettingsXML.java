package DAO.Settings.DAO;// Created by Hanto on 06/07/2014.

import DAO.Settings.SettingsXMLDB;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class SettingsXML implements SettingsDAO
{
    private HashMap<String, String> strings;
    private HashMap<String, Float> floats;
    private HashMap<String, Integer> ints;
    private HashMap<String, Boolean> booleans;

    private SettingsXMLDB settings;
    private Logger logger = (Logger)LoggerFactory.getLogger("SettingsXML");

    public SettingsXML()
    {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        settings = new SettingsXMLDB();

        strings = settings.strings;
        floats = settings.floats;
        ints = settings.ints;
        booleans = settings.booleans;
    }

    @Override public String getString(String key, String defaultValue)
    {
        String string = strings.get(key);
        if (string != null) { return string; }
        else { logger.warn("{} no encontrado, usando valor por defecto: {}", key, defaultValue); return defaultValue; }
    }

    @Override public float getFloat(String key, float defaultValue)
    {
        Float f = floats.get(key);
        if (f != null) { return f; }
        else { logger.warn("{} no encontrado, usando valor por defecto: {}", key, defaultValue); return defaultValue; }
    }

    @Override public int getInt(String key, int defaultValue)
    {
        Integer i = ints.get(key);
        if (i != null) { return i; }
        else { logger.warn("{} no encontrado, usando valor por defecto: {}", key, defaultValue); return defaultValue; }
    }

    @Override public boolean getBoolean(String key, boolean defaultValue)
    {
        Boolean b = booleans.get(key);
        if (b != null) { return b; }
        else { logger.warn("{} no encontrado, usando valor por defecto: {}", key, defaultValue); return defaultValue; }
    }

    @Override public void setString(String key, String value)   { strings.put(key, value); settings.salvarDatos(); }
    @Override public void setFloat(String key, float value)     { floats.put(key, value); settings.salvarDatos(); }
    @Override public void setInt(String key, int value)         { ints.put(key, value); settings.salvarDatos(); }
    @Override public void setBoolean(String key, boolean value) { booleans.put(key, value); settings.salvarDatos(); }
}
