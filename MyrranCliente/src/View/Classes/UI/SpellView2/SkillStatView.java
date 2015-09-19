package View.Classes.UI.SpellView2;

import DB.RSC;
import Interfaces.Misc.Controlador.ControladorSkillStatsI;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillStatI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.Align;

import static Model.Settings.df;

public class SkillStatView
{
    private SkillI skill;
    private SkillStatI stat;
    private ControladorSkillStatsI controlador;

    private TalentosView casillero;
    private Texto nombre;
    private Texto valorBase;
    private Texto total;
    private Texto talentos;
    private Texto costeTalento;
    private Texto bonoTalento;
    private Texto maxTalentos;

    public Container<TalentosView> cCasillero;
    public Container<Texto> cNombre;
    public Container<Texto> cValorBase;
    public Container<Texto> cTotal;
    public Container<Texto> cTalentos;
    public Container<Texto> cCosteTalento;
    public Container<Texto> cBonoTalento;
    public Container<Texto> cMaxTalentos;

    public SkillStatView(SkillI skill, int statID, ControladorSkillStatsI controlador)
    {
        this.skill = skill;
        this.stat = skill.getStats().getStat(statID);
        this.controlador = controlador;

        crearView();
    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        BitmapFont fuente20 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_20);
        BitmapFont fuente14 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_14);
        BitmapFont fuente11 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_11);
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        nombre        = new Texto( stat.getNombre(),
                        fuente11, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        valorBase     = new Texto( df.format(stat.getValorBase()),
                        fuente14, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);

        casillero     = new TalentosView( skill, stat.getID(), controlador);

        total         = new Texto( df.format(stat.getTotal()),
                        fuente14, Color.GREEN, Color.BLACK, Align.left, Align.bottom, 1);

        talentos      = new Texto ( stat.getisMejorable() ? Integer.toString(stat.getNumTalentos()) : "-",
                        fuente11, Color.YELLOW, Color.BLACK, Align.left, Align.bottom, 1);

        costeTalento  = new Texto ( stat.getisMejorable() ? Integer.toString(stat.getCosteTalento()) : "-",
                        fuente11, Color.YELLOW, Color.BLACK, Align.left, Align.bottom, 1);

        bonoTalento   = new Texto ( stat.getisMejorable() ? df.format(stat.getBonoTalento()) : "-",
                        fuente11, Color.YELLOW, Color.BLACK, Align.left, Align.bottom, 1);

        maxTalentos   = new Texto ( stat.getisMejorable() ? Integer.toString(stat.getTalentosMaximos()) : "-",
                        fuente11, Color.YELLOW, Color.BLACK, Align.left, Align.bottom, 1);

        cNombre = new Container<>(nombre);
        cCasillero = new Container<>(casillero);
        cValorBase = new Container<>(valorBase);
        cTotal = new Container<>(total);
        cTalentos = new Container<>(talentos);
        cCosteTalento = new Container<>(costeTalento);
        cBonoTalento = new Container<>(bonoTalento);
        cMaxTalentos = new Container<>(maxTalentos);

    }

    // MODIFICACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    private void setCasillero(int numTalentos)  { casillero.setNumTalentos(numTalentos); }
    private void setValorBase(float nuevoValor) { valorBase.setTexto(df.format(nuevoValor)); }
    private void setTotal(float nuevoValor)     { total.setTexto(df.format(nuevoValor)); }
    private void setNumTalentos(int talentos)   { this.talentos.setTexto(stat.getisMejorable() ? Integer.toString(talentos) : "-"); }
    private void setCosteTalento(int coste)     { costeTalento.setTexto(stat.getisMejorable() ? Integer.toString(coste) : "-"); }
    private void setBonoTalento(float bono)     { bonoTalento.setTexto(stat.getisMejorable() ? df.format(bono) : "-"); }
    private void setMaxTalentos(int max)        { maxTalentos.setTexto(stat.getisMejorable() ? Integer.toString(max) : "-"); }

    public void setSkillStatView()
    {
        setCasillero(stat.getNumTalentos());
        setValorBase(stat.getValorBase());
        setTotal(stat.getTotal());
        setNumTalentos(stat.getNumTalentos());
        setCosteTalento(stat.getCosteTalento());
        setBonoTalento(stat.getBonoTalento());
        setMaxTalentos(stat.getTalentosMaximos());
    }
}
