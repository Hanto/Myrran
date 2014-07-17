package Model.Classes.Cuerpo;// Created by Hanto on 16/07/2014.

import com.badlogic.gdx.physics.box2d.*;

import static Data.Settings.PIXEL_METROS;

public class BodyFactory
{
    public enum darCuerpo
    {
        RECTANGULAR
        {
            @Override public void nuevo(ObjetoDinamico obj)
            {
                BodyDef bd = new BodyDef();
                bd.type = BodyDef.BodyType.KinematicBody;
                bd.position.set(obj.getBoxAncho() / 2, obj.getBoxAlto() / 2);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(obj.getBoxAncho() / 2, obj.getBoxAlto() / 2);

                FixtureDef fixDef = new FixtureDef();
                fixDef.shape = shape;

                Body body = obj.getWorld().createBody(bd);
                body.createFixture(fixDef);

                shape.dispose();
                obj.setBody(body);
            }
        },
        CIRCLE
        {
            @Override public void nuevo(ObjetoDinamico obj)
            {
                BodyDef bd = new BodyDef();
                bd.type = BodyDef.BodyType.KinematicBody;
                bd.position.set(obj.getBoxAncho() / 2, obj.getBoxAlto() / 2);

                CircleShape shape = new CircleShape();
                shape.setRadius(obj.getBoxAncho() / 2);

                FixtureDef fixDef = new FixtureDef();
                fixDef.shape = shape;

                Body body = obj.getWorld().createBody(bd);
                body.createFixture(fixDef);

                shape.dispose();
                obj.setBody(body);
            }
        };

        public abstract void nuevo(ObjetoDinamico obj);

        private darCuerpo() {}
    }
    public enum crearCuerpo
    {
        RECTANGULAR
        {
            @Override public Body nuevo(World world, int ancho, int alto)
            {
                BodyDef bd = new BodyDef();
                bd.type = BodyDef.BodyType.KinematicBody;
                bd.position.set(ancho * PIXEL_METROS / 2, alto * PIXEL_METROS / 2);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(ancho * PIXEL_METROS / 2, alto * PIXEL_METROS/ 2);

                FixtureDef fixDef = new FixtureDef();
                fixDef.shape = shape;

                Body body = world.createBody(bd);
                body.createFixture(fixDef);

                shape.dispose();
                return body;
            }
        },
        CIRCLE
        {
            @Override public Body nuevo(World world, int ancho, int alto)
            {
                BodyDef bd = new BodyDef();
                bd.type = BodyDef.BodyType.KinematicBody;
                bd.position.set(ancho * PIXEL_METROS / 2, alto * PIXEL_METROS / 2);

                CircleShape shape = new CircleShape();
                shape.setRadius(ancho * PIXEL_METROS / 2);

                FixtureDef fixDef = new FixtureDef();
                fixDef.shape = shape;

                Body body = world.createBody(bd);
                body.createFixture(fixDef);

                shape.dispose();
                return body;
            }
        };

        public abstract Body nuevo(World world, int ancho, int alto);

        private crearCuerpo() {}
    }
}
