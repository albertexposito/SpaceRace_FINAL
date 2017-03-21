package cat.xtec.ioc.objects;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

/**
 * Created by ALUMNEDAM on 10/03/2017.
 */

public class Projectile extends Actor {

    private Rectangle collisionRect;
    private float velocityX;
    private float velocityY;
    private Vector2 source;
    private Vector2 target;
    private Vector2 vector;
    private float rotation;
    private int width, height;
    private ScrollHandler scroller;
    float relVector;
    boolean started = false;

    public Projectile(float x, float y, int width, int height, ScrollHandler scroller, Vector2 target) {

        this.width = width;
        this.height = height;

        //Origen (Extrem de la nau)
        source = new Vector2(x, y);
        this.target = target;

        //Vector absolut de les coordenades d'origen y destí(punt de la pantalla clicat)
        vector = new Vector2(Math.abs(target.x - source.x), Math.abs(target.y - source.y));

        //A partir del vector y la velocitat total obtenim la velocitat vertical i horitzontal del projectil
        relVector = 1/(vector.x + vector.y);
        velocityX = relVector * vector.x * Settings.PROJECTILE_VELOCITY;
        velocityY = relVector * vector.y * Settings.PROJECTILE_VELOCITY;

        //utilitzem l'arcosinus per obtenir l'angle del projectil
        rotation =  (float)(Math.acos(relVector * vector.x) * 180.0d/Math.PI);

        //Al ser el vector absolut l'hem de multiplicar la valocitat i l'angle per -1 en cas que l'alçada sigui negativa
        if(target.y < source.y){
            velocityY *=-1;
            rotation *=-1;
        }

        Gdx.app.log("velocidad", " | y = " + relVector * vector.x + " | angle = " + Math.acos(relVector * vector.x) * 180.0d/Math.PI );



        collisionRect = new Rectangle();
        //velocityX = Settings.PROJECTILE_VELOCITY;
        this.scroller = scroller;

        //setOrigin();
    }

    public void setOrigin() {

        this.setOrigin(width / 2, height / 2);

    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.source.x += velocityX * delta;
        this.source.y += velocityY * delta;

        collisionRect.set(source.x, source.y, width, height + 2);
        if (source.x > Settings.GAME_WIDTH) {
            this.remove();
            Gdx.app.log("Proyectil", "desaparece");
        }

        collides(scroller.getAsteroids());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
       //batch.draw(AssetManager.projectile, source.x, source.y, width, height );
        //	draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
        //dibuixar projectil amb rotacio (No funcional)
        batch.draw(AssetManager.projectile, source.x, source.y, 0 , 5,
                width, height, 1, 1, rotation);
    }

    public boolean collides(ArrayList<Asteroid> asteroids) {
        //Obtenim els asteroids de la classe scrollHandler i comprovem que aquest projectil no esta colisionant amb cap.
        for (Asteroid asteroid : asteroids) {

            if (asteroid.collides(this)) {
                Gdx.app.log("Colision", "destruir Asteroide");

                asteroid.hit();
                this.remove();
                if (asteroid.getHitPoints() == 0) {
                    asteroid.destroy();

                    return true;
                }

            }

        }
        return false;
    }


}