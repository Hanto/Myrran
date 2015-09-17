package View.Classes.UI.SpellView;


import Interfaces.Misc.Spell.SpellI;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SpellView2 extends Group
{
    private SpellI spell;

    private Table tabla;

    public SpellView2(SpellI spell)
    {
        this.spell = spell;
        this.tabla = new Table().bottom().left();
        this.setTransform(false);

        this.addActor(tabla);

        crearTabla();
    }

    public void crearTabla()
    {

    }

}
