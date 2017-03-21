package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;

public class InputHandler implements InputProcessor {

    // Enter per a la gestió del moviment d'arrastrar
    int previousY = 0;
    //Enter de control de posicio
    float touchPosition;

    // Objectes necessaris
    private Spacecraft spacecraft;
    private GameScreen screen;
    private Vector2 stageCoord;

    private Stage stage;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        spacecraft = screen.getSpacecraft();
        stage = screen.getStage();

    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.SPACE) {
            spacecraft.shoot();
            return true;
        }
        return false;

    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (screen.getCurrentState()) {

            case READY:
                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case RUNNING:

                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));

                //Si el touch es registra a l'esquerra de la pantalla
                if (stageCoord.x <= Settings.GAME_WIDTH * 0.2) {
                    //previousY = screenY;


                    touchPosition = stageCoord.y;
                    //Quan toquem la part esquerra de la pantalla simplement actualitzem una
                    //posició de destí, a la qual es moura la nau.
                    spacecraft.setVerticalPosition(touchPosition);

                    /*
                    if(spacecraft.getPosicioVertical() < touchPosition){
                        spacecraft.goUp();
                    } else if (spacecraft.getPosicioVertical() > touchPosition){
                        spacecraft.goDown();
                    } else {
                        spacecraft.goStraight();
                    }


                    Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);

                    if (actorHit != null)
                        Gdx.app.log("HIT", actorHit.getName());
                    */
                }
                //Si el touch es registra a la dreta de la pantalla
                else {
                    //Disparem un projectil
                    spacecraft.shoot(stageCoord);
                }

                break;
            // Si l'estat és GameOver tornem a iniciar el joc
            case GAMEOVER:
                screen.reset();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // Quan deixem anar el dit acabem un moviment
        // i posem la nau en l'estat normal
        if(screen.getCurrentState() == GameScreen.GameState.RUNNING && stageCoord != null) {
            if (stageCoord.x <= Settings.GAME_WIDTH * 0.2) {
                spacecraft.goStraight();
            }
        }

        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        /*
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)
        /*
            if (screenX < Settings.GAME_WIDTH * 0.4) {
                // Si la Y és major que la que tenim
                // guardada és que va cap avall
                if (previousY < screenY) {
                    spacecraft.goDown();
                } else {
                    // En cas contrari cap a dalt
                    spacecraft.goUp();
                }
            }
        // Guardem la posició de la Y
        previousY = screenY;
        */

        //S'ha de arreglar, quan es registra un touchDown (per exemple un dispar) el touchDragged deixa de funcionar
        if(screen.getCurrentState() == GameScreen.GameState.RUNNING && stageCoord != null) {
            if (stageCoord.x <= Settings.GAME_WIDTH * 0.2) {

                //Quan movem el dit, acutalitzem la posicio vertical de destí de la nau.
                //Potser no es massa correcte crear un Vector2 cada cop que movem el dit, s'hauria d'arreglar.
                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                spacecraft.setVerticalPosition(stageCoord.y);
                //Prova per a obtenir la altura del dispositiu y poder fer la comprovacio
                Gdx.app.log("screen", "height: " + Gdx.graphics.getHeight()+" | screenY: " + screenY);
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
