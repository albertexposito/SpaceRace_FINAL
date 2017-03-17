package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Asteroid extends Scrollable {

    private Circle collisionCircle;

    Random r;

    final int anguloDiagonal = 80;
    int assetAsteroid;
    float verticalVelocity;

    float explosionTime = 0;

    int hitPoints;

    public boolean destroyed;

    ScrollHandler scrollHandler;

    public Asteroid(float x, float y, float width, float height, float velocity, int hitPoints, ScrollHandler scrollHandler) {
        super(x, y, width, height, velocity);

        this.hitPoints = hitPoints;

        this.scrollHandler =scrollHandler;

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetAsteroid = r.nextInt(15);
        verticalVelocity = r.nextFloat() * anguloDiagonal - anguloDiagonal / 2;

        setOrigin();

        //Rotacio

        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.8f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);


        // Equivalent:
        // this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        this.addAction(repeat);

    }

    public void setOrigin() {

        this.setOrigin(width / 2 + 1, height / 2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        position.y += verticalVelocity * delta;

        // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX

        setVisible(true);
        destroyed = false;

        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);

        hitPoints = (int)newSize;

        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 17 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetAsteroid = r.nextInt(15);
        verticalVelocity = r.nextFloat() * 40 - 20;
        setOrigin();
    }

    public void hit(){
        this.hitPoints--;
       // this.width -= 1*17;
       // this.height -= 1*17;
    }

    public void destroy() {
        destroyed = true;
        this.setVisible(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //   batch.draw (AssetManager.asteroid[assetAsteroid], position.x, position.y, width, height);
        batch.draw(AssetManager.asteroid[assetAsteroid], position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());



    }



    // Retorna true si hi ha col·lisió
    public boolean collides(Spacecraft nau) {

        //    if (position.x <= nau.getX() + nau.getWidth()) {
        // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
        if (false)
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        else
            return false;
        //  }
        //   return false;
    }

    public boolean collides(Projectile projectile) {

        if (!destroyed)
            return (Intersector.overlaps(collisionCircle, projectile.getCollisionRect()));
        else
            return false;
    }

}
