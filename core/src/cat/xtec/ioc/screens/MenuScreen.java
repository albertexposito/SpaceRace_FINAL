package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

import static cat.xtec.ioc.helpers.AssetManager.button;

/**
 * Created by Albert on 18/03/2017.
 */

public class MenuScreen implements Screen {
    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textStyle;
    private Label textLbl;
    private int dificil, facil, mig;
    private int velocitatEntreAsteroids;
    private TextButton[] buttons;
    private Batch batch;

    private TextButton.TextButtonStyle textButtonStyle;

    public MenuScreen(Batch prevBatch, Viewport prevViewport, SpaceRace game) {

        stage = new Stage(prevViewport, prevBatch);
        batch = stage.getBatch();

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = AssetManager.font;
        textButtonStyle.up = new TextureRegionDrawable(button);

        this.game = game;


        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

        camera.setToOrtho(true);

        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        stage = new Stage(viewport);

        stage.addActor(new Image(AssetManager.background));

        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("Escull la dificultat", textStyle);

        buttons = new TextButton[3];
        buttons[0] = new TextButton("Facil", textButtonStyle);
        buttons[1] = new TextButton("Mig", textButtonStyle);
        buttons[2] = new TextButton("Dificil", textButtonStyle);

        Container containerDif = new Container(textLbl);
        containerDif.setTransform(true);
        containerDif.center();
        containerDif.setPosition(Settings.GAME_WIDTH / 2,(float)(Settings.GAME_HEIGHT * 0.15));

        Container containerFacil = new Container(buttons[0]);
        containerFacil.setTransform(true);
        containerFacil.center();
        containerFacil.setPosition((float)(Settings.GAME_WIDTH * 0.2), Settings.GAME_HEIGHT / 2);

        Container containerMig = new Container(buttons[1]);
        containerMig.setTransform(true);
        containerMig.center();
        containerMig.setPosition((float)(Settings.GAME_WIDTH * 0.5 - containerDif.getWidth() / 2), Settings.GAME_HEIGHT / 2);

        Container containerDificil = new Container(buttons[2]);
        containerDificil.setTransform(true);
        containerDificil.center();
        containerDificil.setPosition((float)(Settings.GAME_WIDTH * 0.8 - containerDif.getWidth() / 2), Settings.GAME_HEIGHT / 2);

        stage.addActor(containerFacil);
        stage.addActor(containerDificil);
        stage.addActor(containerDif);
        stage.addActor(containerMig);

        buttons[0].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.this.game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport(), 0));
                dispose();
            }
        });
        buttons[1].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.this.game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport(), 1));
                dispose();
            }
        });

        buttons[2].addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MenuScreen.this.game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport(), 2));
                dispose();
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);

        // Si es fa clic en la pantalla, canviem la pantalla
        /*if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }*/
        //if (Gdx.input.)


    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}