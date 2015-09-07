package View.Classes.UI.SpellView;// Created by Hanto on 27/06/2014.

import DB.RSC;
import Model.Settings;
import InterfacesEntidades.EntidadesPropiedades.Misc.CasterPersonalizable;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class CasilleroTalentos extends Actor
{
    private TextureRegion fondo;
    private TextureRegion frente;

    private int ancho25;
    private int ancho50;
    private int numTalentos = 0;

    private CasterPersonalizable caster;


    public CasilleroTalentos(CasterPersonalizable caster, final String skillID, final int statID, int numTalentos)
    {
        this.caster = caster;

        fondo = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_SPELLTOOLTIP_TalentoFondo);
        frente = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_SPELLTOOLTIP_Talento);

        int ancho = fondo.getRegionWidth();
        int alto = fondo.getRegionHeight();

        this.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                if (button == Input.Buttons.LEFT)
                {
                    if (x < getWidth() / 2)
                    {
                        int valor = CasilleroTalentos.this.caster.getSkillPersonalizado(skillID).getNumTalentos(statID);
                        CasilleroTalentos.this.caster.setNumTalentosSkillPersonalizado(skillID, statID, valor-1);
                    }
                    else if (x > getWidth() / 2)
                    {

                        int valor = CasilleroTalentos.this.caster.getSkillPersonalizado(skillID).getNumTalentos(statID);
                        CasilleroTalentos.this.caster.setNumTalentosSkillPersonalizado(skillID, statID, valor+1);

                    }
                }
                if (button == Input.Buttons.RIGHT)
                {
                    if (CasilleroTalentos.this.numTalentos <= 25)       CasilleroTalentos.this.caster.setNumTalentosSkillPersonalizado(skillID, statID, (int)(x/3));
                    else if (CasilleroTalentos.this.numTalentos > 25)   CasilleroTalentos.this.caster.setNumTalentosSkillPersonalizado(skillID, statID, (int)(x/3) + 25);
                }
                return true;
            }
        });

        this.setWidth(ancho);
        this.setHeight(alto);

        setNumTalentos(numTalentos);
    }

    public void setNumTalentos(int numTalentos)
    {
        this.numTalentos = numTalentos;

        ancho25 = ((int)getWidth()/25) * (numTalentos > 25 ? 25 : numTalentos);
        ancho50 = ((int)getWidth()/25) * (numTalentos > 50 ? 25 : numTalentos - 25);
    }

    @Override public void draw (Batch batch, float alpha)
    {
        batch.setColor(getColor());
        batch.draw(fondo, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        batch.setColor(255 / 255f, 180 / 255f, 0 / 255f, 0.75f);
        batch.draw(frente, getX(), getY(), getOriginX(), getOriginY(), ancho25, getHeight(), getScaleX(), getScaleY(), getRotation());

        if (numTalentos > 25)
        {
            batch.setColor(255/255f, 0/255f, 0/255f, 0.55f);
            batch.draw(frente, getX(), getY(), getOriginX(), getOriginY(), ancho50, getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
