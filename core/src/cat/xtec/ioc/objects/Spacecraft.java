package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Set;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Spacecraft extends Actor {

    // Distintes posicions de la spacecraft, recta, pujant i baixant
    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private float touchedVerticalPosition;
    private int width, height;
    private int direction;
    private Stage currentStage;
    private ScrollHandler scrollHandler;

    private float velocityX,velocityY;

    private Rectangle collisionRect;

    public Spacecraft(float x, float y, int width, int height, Stage stage, ScrollHandler scrollHandler) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        currentStage = stage;
        this.scrollHandler = scrollHandler;

        // Inicialitzem la spacecraft a l'estat normal
        direction = SPACECRAFT_STRAIGHT;
        //Inicialitzem la posició de la nau amb la posició inicial;
        touchedVerticalPosition = position.y;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

    public void act(float delta) {

        super.act(delta);
        /*
        switch (direction) {
            case SPACECRAFT_UP:
                if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_DOWN:
                if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_STRAIGHT:
                break;
        }*/

        //Obté la posició vertical de desti de l'input handler i es mourà tota l0'estona cap a ella
        //!! Per alguna raó quan arriba al seu destí i el dit no s'ha retirat alterna molt rapidament
        //entre moures cap amunt i cap avall, no dificulta la jugabilitat pero la textura de la nau
        //canvia molt rapidament i pot ser molest

        //Si el touch esta per sobre de la nau...
        if(position.y > touchedVerticalPosition){

            //La movem cap amunt
            direction = SPACECRAFT_UP;
            if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
            }

        } else if (position.y < touchedVerticalPosition) {
            //Si es per sota la movem cap avall
            direction = SPACECRAFT_DOWN;
            if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
            }

        } else {
            goStraight();
        }

        collisionRect.set(position.x, position.y + 3, width, 10);

    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = SPACECRAFT_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = SPACECRAFT_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = SPACECRAFT_STRAIGHT;
        touchedVerticalPosition = position.y;
    }

    public void shoot(){

        //currentStage.addActor(new Projectile(position.x, position.y, Settings.PROJECTILE_WIDTH, Settings.PROJECTILE_HEIGHT, scrollHandler));

    }

    public void shoot(Vector2 touchedPosition){
        //Creem un projectil
        currentStage.addActor(new Projectile(position.x + width, position.y + height / 2, Settings.PROJECTILE_WIDTH, Settings.PROJECTILE_HEIGHT, scrollHandler, touchedPosition));

    }


    // Obtenim el TextureRegion depenent de la posició de la spacecraft
    public TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case SPACECRAFT_STRAIGHT:
                return AssetManager.spacecraft;
            case SPACECRAFT_UP:
                return AssetManager.spacecraftUp;
            case SPACECRAFT_DOWN:
                return AssetManager.spacecraftDown;
            default:
                return AssetManager.spacecraft;
        }
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.SPACECRAFT_STARTX;
        position.y = Settings.SPACECRAFT_STARTY;
        direction = SPACECRAFT_STRAIGHT;
        collisionRect = new Rectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    public void setVerticalPosition(float y){
        touchedVerticalPosition = y;
    }

    public float getPosicioVertical () {
        return position.y;
    }
}
