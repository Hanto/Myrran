package View.Classes.UI.SpellView2;

import DB.RSC;
import Interfaces.Misc.Controlador.ControladorSkillStatsI;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillStatI;
import Model.Settings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TalentosView extends Actor
{
    private SkillI skill;
    private SkillStatI stat;
    private ControladorSkillStatsI controlador;

    private TextureRegion fondo;
    private TextureRegion frente;

    private int barraTalentos00_25;
    private int barraTalentos25_50;

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public TalentosView(SkillI skillI, int statID, final ControladorSkillStatsI controlador)
    {
        this.skill = skillI;
        this.stat = skillI.getStats().getStat(statID);
        this.controlador = controlador;

        fondo = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_SPELLTOOLTIP_TalentoFondo);
        frente = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_SPELLTOOLTIP_Talento);

        this.setWidth(fondo.getRegionWidth());
        this.setHeight(fondo.getRegionHeight());

        this.addListener(new InputListener()
        {
             @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
             {
                 if (button == Input.Buttons.LEFT)
                 {
                     if         (x < getWidth() / 2)            setNumTalentosToModel(stat.getNumTalentos() - 1);
                     else if    (x > getWidth() / 2)            setNumTalentosToModel(stat.getNumTalentos() + 1);
                 }
                 if (button == Input.Buttons.RIGHT)
                 {
                     if         (stat.getNumTalentos() <= 25)   setNumTalentosToModel((int) x / 3);
                     else if    (stat.getNumTalentos() > 25)    setNumTalentosToModel((int) x / 3 + 25);
                 }
                 return true;
             }
        });
    }

    // MODIFICACION VISTA Y CONTROLADOR:
    //------------------------------------------------------------------------------------------------------------------

    private void setNumTalentosToModel(int numTalentos)
    {
        if (controlador != null)
        {   controlador.setTalentos(skill.getID(), stat.getID(), numTalentos); }
    }

    public void setNumTalentos(int numTalentos)
    {
        barraTalentos00_25 = ((int)getWidth()/25) * (numTalentos > 25 ? 25 : numTalentos);
        barraTalentos25_50 = ((int)getWidth()/25) * (numTalentos > 50 ? 25 : numTalentos - 25);
    }

    // DRAW
    //------------------------------------------------------------------------------------------------------------------

    @Override public void draw (Batch batch, float alpha)
    {
        batch.setColor(getColor());

        if (!stat.getisMejorable()) batch.setColor(Color.GRAY);
        batch.draw(fondo, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        batch.setColor(255 / 255f, 180 / 255f, 0 / 255f, 0.75f);
        batch.draw(frente, getX(), getY(), getOriginX(), getOriginY(), barraTalentos00_25, getHeight(), getScaleX(), getScaleY(), getRotation());

        if (stat.getNumTalentos() > 25)
        {
            batch.setColor(255/255f, 0/255f, 0/255f, 0.55f);
            batch.draw(frente, getX(), getY(), getOriginX(), getOriginY(), barraTalentos25_50, getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
