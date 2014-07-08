package DB;// Created by Ladrim on 24/04/2014.

import DB.Recursos.AccionRecursos.AccionRecursosDAOFactory;
import DB.Recursos.AtlasRecursos.AtlasRecursosDAOFactory;
import DB.Recursos.FuentesRecursos.FuentesRecursosDAOFactory;
import DB.Recursos.MiscRecursos.MiscRecursosDAOFactory;
import DB.Recursos.PixiePCRecursos.PixiePCRecursosDAOFactory;
import DB.Recursos.SkillRecursos.SkillRecursosDAOFactory;
import DB.Recursos.TerrenoRecursos.TerrenoRecursosDAOFactory;

public class RSC
{
    public static final TerrenoRecursosDAOFactory terrenoRecursosDAO = TerrenoRecursosDAOFactory.LOCAL;
    public static final SkillRecursosDAOFactory skillRecursosDAO = SkillRecursosDAOFactory.XML;
    public static final PixiePCRecursosDAOFactory pixiePCRecursosDAO = PixiePCRecursosDAOFactory.XML;
    public static final FuentesRecursosDAOFactory fuenteRecursosDAO = FuentesRecursosDAOFactory.XML;
    public static final MiscRecursosDAOFactory miscRecusosDAO = MiscRecursosDAOFactory.XML;
    public static final AccionRecursosDAOFactory accionRecursosDAO = AccionRecursosDAOFactory.XML;
    public static final AtlasRecursosDAOFactory atlasRecursosDAO = AtlasRecursosDAOFactory.LOCAL;
}
