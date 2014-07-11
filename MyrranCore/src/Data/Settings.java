package Data;// Created by Hanto on 07/04/2014.

import DAO.Settings.DAO.SettingsDAO;
import DAO.Settings.SettingsDAOFactory;

import java.util.HashMap;

public class Settings
{
    public static void inicializar()
    {
        SettingsDAO settings = SettingsDAOFactory.XML.getSettingsDAO();

        //LibGDX:
        GDX_Horizontal_Resolution   = settings.getInt("GDX_Horizontal_Resolution", 1600);
        GDX_Vertical_Resolution     = settings.getInt("GDX_Vertical_Resolution", 900);

        //General
        TILESIZE                    = settings.getInt("TILESIZE", 24);

        //BOX2d:
        PIXEL_METROS                = settings.getFloat("PIXEL_METROS", 0.01f);
        METROS_PIXEL                = settings.getFloat("METROS_PIXEL", 100f);

        //Network:
        NETWORK_PuertoTCP           = settings.getInt("NETWORK_PuertoTCP", 54555);
        NETWORK_PuertoUDP           = settings.getInt("NETWORK_PuertoUDP", 54777);
        NETWORK_Client_Timeout      = settings.getInt("NETWORK_Client_Timeout", 5000*10000);
        NETWORK_DistanciaVisionMobs = settings.getFloat("NETWORK_DistanciaVisionMobs",1.2f );
        NETWORK_Update_Time         = settings.getInt("NETWORK_Update_Time", 40);
        NETWORK_Delta_Time          = NETWORK_Update_Time/1000f;

        //Spells, Debuffs:
        BDEBUFF_DuracionTick        = settings.getFloat("BDEBUFF_DuracionTick",1.0f);

        //Recursos:
        RECURSOS_XML                            = settings.getString("RECURSOS_XML",                            "Data/");
        RECURSOS_Atlas_Carpeta_Imagenes_Origen  = settings.getString("RECURSOS_Atlas_Carpeta_Imagenes_Origen",  "Images/");
        RECURSOS_Atlas_Carpeta_Imagenes_Destino = settings.getString("RECURSOS_Atlas_Carpeta_Imagenes_Destino", "Atlas/");
        RECURSOS_Atlas_Atlas_Extension          = settings.getString("RECURSOS_Atlas_Atlas_Extension",          "atlas");
        RECURSOS_CarpetaParticulas              = settings.getString("RECURSOS_CarpetaParticulas",              "Particles/");
        RECURSOS_CarpetaImagesParticulas        = settings.getString("RECURSOS_CarpetaImagenesParticulas",      "Particles/Images/");

        //XML
        XML_DataTipoSpells          = settings.getString("XML_DataTipoSpells",         "DataTipoSpells.xml");
        XML_DataSpells              = settings.getString("XML_DataSpells",             "DataSpells.xml");
        XML_DataTipoBDebuffs        = settings.getString("XML_DataTipoBDebuffs",       "DataTipoBDebuffs.xml");
        XML_DataBDebuffs            = settings.getString("XML_DataBDebuffs",           "DataBDebuffs.xml");
        XML_DataTerrenos            = settings.getString("XML_DataTerrenos",           "DataTerrenos.xml");
        XML_DataAcciones            = settings.getString("XML_DataAcciones",           "DataAcciones.xml");

        XML_TexturasAtlas           = settings.getString("XML_TexturasAtlas",          "TexturasAtlas.xml");
        XML_TexturasMisc            = settings.getString("XML_TexturasMisc",           "Texturas.xml");
        XML_TexturasIconosAcciones  = settings.getString("XML_TexturasIconosAcciones", "Texturas.xml");
        XML_TexturasIconoSpells     = settings.getString("XML_TexturasIconoSpells",    "Texturas.xml");
        XML_TexturasTerrenos        = settings.getString("XML_TexturasTerrenos",       "Texturas.xml");
        XML_TexturasFuentes         = settings.getString("XML_TexturasFuentes",        "Texturas.xml");

        XML_Particulas              = settings.getString("XML_Particulas",             "Particulas.xml");
        XML_PixieSlot               = settings.getString("XML_PixieSlot",              "PixieSlot.xml");
        XML_AnimacionesCasteo       = settings.getString("XML_AnimacionesCasteo",      "AnimacionesCasteo.xml");
        XML_AnimacionesProyectil    = settings.getString("XML_AnimacionesProyectil",   "AnimacionesProyectil.xml");

        //Atlas Recursos:
        ATLAS_GenerarAtlas          = settings.getBoolean("ATLAS_GenerarAtlas",         false);
        ATLAS_PixiePcCuerpos_LOC    = settings.getString("ATLAS_PixiePcCuerpos_LOC",   "PixiePcCuerpos/");
        ATLAS_PixiePcSlots_LOC      = settings.getString("ATLAS_PixiePcSlots_LOC",     "PixiePcSlots/");
        ATLAS_TexturasTerrenos_LOC  = settings.getString("ATLAS_TexturasTerrenos_LOC", "TexturasTerrenos/");
        ATLAS_TexturasIconos_LOC    = settings.getString("ATLAS_TexturasIconos_LOC",   "TexturasIconos/");
        ATLAS_TexturasMisc_LOC      = settings.getString("ATLAS_TexturasMisc_LOC",     "TexturasMisc/");
        ATLAS_AnimacionesSpells_LOC = settings.getString("ATLAS_AnimacionesSpells_LOC","AnimacionesSpells/");
        ATLAS_Fuentes_LOC           = settings.getString("ATLAS_Fuentes_LOC",          "Fonts/");

        //Pixie:
        PIXIE_Player_numFilas       = settings.getInt("PIXIE_Player_numFilas", 7);
        PIXIE_Player_numColumnas    = settings.getInt("PIXIE_Player_numColumnas", 6);

        //Mapa:
        MAPTILE_Horizontal_Resolution= settings.getInt("MAPTILE_Horizontal_Resolution", 1980);
        MAPTILE_Vertical_Resolution = settings.getInt("MAPTILE_Vertical_Resolution", 1200);
        MAPTILE_NumTilesX           = (int)Math.ceil((double) Settings.MAPTILE_Horizontal_Resolution /(double) Settings.TILESIZE);
        MAPTILE_NumTilesY           = (int)Math.ceil((double) Settings.MAPTILE_Vertical_Resolution /(double) Settings.TILESIZE);
        MAPTILE_posHorNeg           = Settings.MAPTILE_Horizontal_Resolution /4;
        MAPTILE_posHorPos           = Settings.MAPTILE_Horizontal_Resolution * 3/4;
        MAPTILE_posVerNeg           = Settings.MAPTILE_Vertical_Resolution /4;
        MAPTILE_posVerPos           = Settings.MAPTILE_Vertical_Resolution * 3/4;

        MAPA_Max_Capas_Terreno      = settings.getInt("MAPA_Max_Capas_Terreno", 4);
        MAPA_Max_TilesX             = settings.getInt("MAPA_Max_TilesX", 2000);
        MAPA_Max_TilesY             = settings.getInt("MAPA_Max_TilesY" ,2000);

        MAPAVIEW_TamañoX            = settings.getInt("MAPAVIEW_TamañoX", 2);  //18;
        MAPAVIEW_TamañoY            = settings.getInt("MAPAVIEW_TamañoY", 2);  //20;

        //Fuentes
        FUENTE_Nombres              = settings.getString("FUENTE_Nombres", "14");

        //Barra Spells:
        BARRASPELLS_Ancho_Casilla   = settings.getInt("BARRASPELLS_Ancho_Casilla", 32+2);
        BARRASPELLS_Alto_Casilla    = settings.getInt("BARRASPELLS_Alto_Casilla" , 32+2);

        //Barra Terrenos:
        BARRATERRENOS_EditarTerrenoID = settings.getString("BARRATERRENOS_EditarTerrenoID", "EDITARTERRENO");

        RECURSO_BARRASPELLS_Textura_Casillero   = settings.getString("RECURSO_BARRASPELLS_Textura_Casillero",   "Casillero");
        RECURSO_BARRASPELLS_RebindButtonON      = settings.getString("RECURSO_BARRASPELLS_RebindButtonON",      "RebindOn");
        RECURSO_BARRASPELLS_RebindButtonOFF     = settings.getString("RECURSO_BARRASPELLS_RebindButtonOFF",     "RebindOff");
        RECURSO_BARRATERRENOS_Borrar_Terreno    = settings.getString("RECURSO_BARRATERRENOS_Borrar_Terreno",    "Borrar");
        RECURSO_NAMEPLATE_Nameplate             = settings.getString("RECURSO_NAMEPLATE_Nameplate",             "Nameplate");
        RECURSO_NAMEPLATE_Nameplate_Fondo       = settings.getString("RECURSO_NAMEPLATE_Nameplate_Fondo",       "NameplateFondo");
        RECURSO_PIXIEPC_Sombra                  = settings.getString("RECURSO_PIXIEPC_Sombra",                  "Sombra");
        RECURSO_Grid                            = settings.getString("RECURSO_Grid",                            "Grid");
        RECURSO_SPELLTOOLTIP_TalentoFondo       = settings.getString("RECURSO_SPELLTOOLTIP_TalentoFondo",       "CasillaTalentoFondo");
        RECURSO_SPELLTOOLTIP_Talento            = settings.getString("RECURSO_SPELLTOOLTIP_Talento",            "CasillaTalento");

        //Icono Accion
        ICONO_Accion_Ancho                      = settings.getInt("ICONO_Accion_Ancho", 32);
        ICONO_Accion_Alto                       = settings.getInt("ICONO_Accion_Alto", 32);
    }

    //LibGDX:
    public static int GDX_Horizontal_Resolution;
    public static int GDX_Vertical_Resolution;

    //&General
    public static int TILESIZE;

    //BOX2d:
    public static float PIXEL_METROS;
    public static float METROS_PIXEL;

    //Network:
    public static int NETWORK_PuertoTCP;
    public static int NETWORK_PuertoUDP;
    public static int NETWORK_Client_Timeout;
    public static int NETWORK_Update_Time;
    public static float NETWORK_Delta_Time = NETWORK_Update_Time;
    public static float NETWORK_DistanciaVisionMobs;

    //Spells, Debuffs:
    public static float BDEBUFF_DuracionTick;

    //Recursos:
    public static String RECURSOS_XML;
    public static String RECURSOS_Atlas_Carpeta_Imagenes_Origen;
    public static String RECURSOS_Atlas_Carpeta_Imagenes_Destino;
    public static String RECURSOS_Atlas_Atlas_Extension;
    public static String RECURSOS_CarpetaParticulas;
    public static String RECURSOS_CarpetaImagesParticulas;

    //XML
    public static String XML_DataTipoSpells;
    public static String XML_DataSpells;
    public static String XML_DataTipoBDebuffs;
    public static String XML_DataBDebuffs;
    public static String XML_DataTerrenos;
    public static String XML_DataAcciones;

    public static String XML_TexturasAtlas;
    public static String XML_TexturasMisc;
    public static String XML_TexturasIconosAcciones;
    public static String XML_TexturasIconoSpells;
    public static String XML_TexturasTerrenos;
    public static String XML_TexturasFuentes;

    public static String XML_Particulas;
    public static String XML_PixieSlot;
    public static String XML_AnimacionesCasteo;
    public static String XML_AnimacionesProyectil;

    //Mobiles Recursos:
    public static boolean ATLAS_GenerarAtlas;
    public static String ATLAS_PixiePcCuerpos_LOC;
    public static String ATLAS_PixiePcSlots_LOC;
    public static String ATLAS_TexturasTerrenos_LOC;
    public static String ATLAS_TexturasIconos_LOC;
    public static String ATLAS_TexturasMisc_LOC;
    public static String ATLAS_AnimacionesSpells_LOC;
    public static String ATLAS_Fuentes_LOC;

    //Pixie:
    public static int PIXIE_Player_numFilas;
    public static int PIXIE_Player_numColumnas;

    //Mapa:
    public static int MAPTILE_Horizontal_Resolution;
    public static int MAPTILE_Vertical_Resolution;
    public static int MAPTILE_NumTilesX;
    public static int MAPTILE_NumTilesY;
    public static int MAPTILE_posHorNeg;
    public static int MAPTILE_posHorPos;
    public static int MAPTILE_posVerNeg;
    public static int MAPTILE_posVerPos;

    public static int MAPA_Max_Capas_Terreno;
    public static int MAPA_Max_TilesX;
    public static int MAPA_Max_TilesY;

    public static int MAPAVIEW_TamañoX;
    public static int MAPAVIEW_TamañoY;

    //Fuentes
    public static String FUENTE_Nombres;

    //Barra Spells:
    public static int BARRASPELLS_Ancho_Casilla;
    public static int BARRASPELLS_Alto_Casilla;

    //Barra Terrenos:
    public static String BARRATERRENOS_EditarTerrenoID;

    public static String RECURSO_BARRASPELLS_Textura_Casillero;
    public static String RECURSO_BARRASPELLS_RebindButtonON;
    public static String RECURSO_BARRASPELLS_RebindButtonOFF;
    public static String RECURSO_BARRATERRENOS_Borrar_Terreno;
    public static String RECURSO_NAMEPLATE_Nameplate;
    public static String RECURSO_NAMEPLATE_Nameplate_Fondo;
    public static String RECURSO_PIXIEPC_Sombra;
    public static String RECURSO_Grid;
    public static String RECURSO_SPELLTOOLTIP_TalentoFondo;
    public static String RECURSO_SPELLTOOLTIP_Talento;

    //Icono Accion
    public static int ICONO_Accion_Ancho;
    public static int ICONO_Accion_Alto;

    //KEYCODES:
    public static final HashMap<Integer, String> keycodeNames = new HashMap<>();
    static  {   keycodeNames.put(7, "0");
                keycodeNames.put(8, "1");
                keycodeNames.put(9, "2");
                keycodeNames.put(10, "3");
                keycodeNames.put(11, "4");
                keycodeNames.put(12, "5");
                keycodeNames.put(13, "6");
                keycodeNames.put(14, "7");
                keycodeNames.put(15, "8");
                keycodeNames.put(16, "9");
                keycodeNames.put(29, "A");
                keycodeNames.put(57, "ALT_LEFT");
                keycodeNames.put(58, "ALT_RIGHT");
                keycodeNames.put(75, "APOSTROPHE");
                keycodeNames.put(77, "@");
                keycodeNames.put(30, "B");
                keycodeNames.put(4, "BACK");
                keycodeNames.put(73, "\\");
                keycodeNames.put(31, "C");
                keycodeNames.put(5, "CALL");
                keycodeNames.put(27, "CAMERA");
                keycodeNames.put(28, "CLEAR");
                keycodeNames.put(55, ",");
                keycodeNames.put(32, "D");
                keycodeNames.put(67, "BACKSPACE");
                keycodeNames.put(112, "FORWARD_DEL");
                keycodeNames.put(23, "CENTER");
                keycodeNames.put(20, "DOWN");
                keycodeNames.put(21, "LEFT");
                keycodeNames.put(22, "RIGHT");
                keycodeNames.put(19, "UP");
                keycodeNames.put(33, "E");
                keycodeNames.put(6, "ENDCALL");
                keycodeNames.put(66, "ENTER");
                keycodeNames.put(65, "ENVELOPE");
                keycodeNames.put(70, "=");
                keycodeNames.put(64, "EXPLORER");
                keycodeNames.put(34, "F");
                keycodeNames.put(80, "FOCUS");
                keycodeNames.put(35, "G");
                keycodeNames.put(68, "Ñ");
                keycodeNames.put(36, "H");
                keycodeNames.put(79, "HEADSETHOOK");
                keycodeNames.put(3, "HOME");
                keycodeNames.put(37, "I");
                keycodeNames.put(38, "J");
                keycodeNames.put(39, "K");
                keycodeNames.put(40, "L");
                keycodeNames.put(71, "[");
                keycodeNames.put(41, "M");
                keycodeNames.put(90, "FAST_FORWARD");
                keycodeNames.put(87, "NEXT");
                keycodeNames.put(85, "PLAY_PAUSE");
                keycodeNames.put(88, "PREVIOUS");
                keycodeNames.put(89, "REWIND");
                keycodeNames.put(86, "STOP");
                keycodeNames.put(82, "MENU");
                keycodeNames.put(69, "MINUS");
                keycodeNames.put(91, "MUTE");
                keycodeNames.put(42, "N");
                keycodeNames.put(83, "NOTIFICATION");
                keycodeNames.put(78, "NUM");
                keycodeNames.put(43, "O");
                keycodeNames.put(44, "P");
                keycodeNames.put(56, ".");
                keycodeNames.put(81, "+");
                keycodeNames.put(18, "#");
                keycodeNames.put(26, "POWER");
                keycodeNames.put(45, "Q");
                keycodeNames.put(46, "R");
                keycodeNames.put(72, "]");
                keycodeNames.put(47, "S");
                keycodeNames.put(84, "SEARCH");
                keycodeNames.put(74, ";");
                keycodeNames.put(59, "SHIFT_LEFT");
                keycodeNames.put(60, "SHIFT_RIGHT");
                keycodeNames.put(76, "/");
                keycodeNames.put(1, "SOFT_LEFT");
                keycodeNames.put(2, "SOFT_RIGHT");
                keycodeNames.put(62, "SPACE");
                keycodeNames.put(17, "STAR");
                keycodeNames.put(63, "SYM");
                keycodeNames.put(48, "T");
                keycodeNames.put(61, "TAB");
                keycodeNames.put(49, "U");
                keycodeNames.put(0, "UNKNOWN");
                keycodeNames.put(50, "V");
                keycodeNames.put(25, "VOLUME_DOWN");
                keycodeNames.put(24, "VOLUME_UP");
                keycodeNames.put(51, "W");
                keycodeNames.put(52, "X");
                keycodeNames.put(53, "Y");
                keycodeNames.put(54, "Z");
                keycodeNames.put(129, "CONTROL_LEFT");
                keycodeNames.put(130, "CONTROL_RIGHT");
                keycodeNames.put(131, "ESCAPE");
                keycodeNames.put(132, "END");
                keycodeNames.put(133, "INSERT");
                keycodeNames.put(92, "PAGE_UP");
                keycodeNames.put(93, "PAGE_DOWN");
                keycodeNames.put(94, "PICTSYMBOLS");
                keycodeNames.put(95, "SWITCH_CHARSET");
                keycodeNames.put(255, "BUTTON_CIRCLE");
                keycodeNames.put(96, "BUTTON_A");
                keycodeNames.put(97, "BUTTON_B");
                keycodeNames.put(98, "BUTTON_C");
                keycodeNames.put(99, "BUTTON_X");
                keycodeNames.put(100, "BUTTON_Y");
                keycodeNames.put(101, "BUTTON_Z");
                keycodeNames.put(102, "BUTTON_L1");
                keycodeNames.put(103, "BUTTON_R1");
                keycodeNames.put(104, "BUTTON_L2");
                keycodeNames.put(105, "BUTTON_R2");
                keycodeNames.put(106, "BUTTON_THUMBL");
                keycodeNames.put(107, "BUTTON_THUMBR");
                keycodeNames.put(108, "BUTTON_START");
                keycodeNames.put(109, "BUTTON_SELECT");
                keycodeNames.put(110, "BUTTON_MODE");
                keycodeNames.put(144, "N0");
                keycodeNames.put(145, "N1");
                keycodeNames.put(146, "N2");
                keycodeNames.put(147, "N3");
                keycodeNames.put(148, "N4");
                keycodeNames.put(149, "N5");
                keycodeNames.put(150, "N6");
                keycodeNames.put(151, "N7");
                keycodeNames.put(152, "N8");
                keycodeNames.put(153, "N9");
    }
}
