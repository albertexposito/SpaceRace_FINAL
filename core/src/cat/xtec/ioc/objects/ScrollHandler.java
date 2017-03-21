package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    Stage stage;

    // Asteroides
    int numAsteroids;
    int asteroidGap;
    int asteroidSpeed;
    int asteroidsDestroyed;

    int puntuacio;

    private ArrayList<Asteroid> asteroids;

    // Objecte Random
    Random r;

    public ScrollHandler(int dificultat) {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        puntuacio = 0;

        switch (dificultat){
            case 0:
                asteroidGap = Settings.ASTEROID_GAP_EASY;
                asteroidSpeed = Settings.ASTEROID_SPEED_EASY;
                break;
            case 1:
                asteroidGap = Settings.ASTEROID_GAP_MED;
                asteroidSpeed = Settings.ASTEROID_SPEED_MED;
                break;
            case 2:
                asteroidGap = Settings.ASTEROID_GAP_HARD;
                asteroidSpeed = Settings.ASTEROID_SPEED_HARD;
                break;
        }

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 6 asteroids
        numAsteroids = 10;
        asteroidsDestroyed = 0;

        // Creem l'ArrayList
        asteroids = new ArrayList<Asteroid>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 17;
        int initialHitPoints = (int) newSize;
        // Afegim el primer Asteroid a l'Array i al grup
        Asteroid asteroid = new Asteroid(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, asteroidSpeed, initialHitPoints, this);
        asteroids.add(asteroid);
        addActor(asteroid);

        // Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 17;
            initialHitPoints = (int)newSize;
            // Afegim l'asteroid.
            asteroid = new Asteroid(asteroids.get(asteroids.size() - 1).getTailX() + asteroidGap,
                    r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, asteroidSpeed, initialHitPoints, this);
            // Afegim l'asteroide a l'ArrayList
            asteroids.add(asteroid);
            // Afegim l'asteroide al grup d'actors
            addActor(asteroid);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.

        puntuacio++;

        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < asteroids.size(); i++) {

            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isLeftOfScreen()) {
                if (i == 0) {
                    asteroid.reset(asteroids.get(asteroids.size() - 1).getTailX() + asteroidGap);
                } else {
                    asteroid.reset(asteroids.get(i - 1).getTailX() + asteroidGap);
                }
            }
        }
    }

    public boolean collides(Spacecraft nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Asteroid asteroid : asteroids) {
            if (asteroid.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public void reiniciarPartida(){
        puntuacio = 0;
    }

    public void reset() {

        // Posem el primer asteroid fora de la pantalla per la dreta
        asteroids.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'asteroids.
        for (int i = 1; i < asteroids.size(); i++) {

            asteroids.get(i).reset(asteroids.get(i - 1).getTailX() + asteroidGap);

        }
    }

    public void asteroidDestroyed(){
        asteroidsDestroyed++;
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public int getPuntuacio() {
        return puntuacio;
    }
}