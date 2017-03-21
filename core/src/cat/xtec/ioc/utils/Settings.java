package cat.xtec.ioc.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // Propietats de la nau
    public static final float SPACECRAFT_VELOCITY = 150;
    public static final float SPACECRAFT_VELOCITY_X = 50;
    public static final int SPACECRAFT_WIDTH = 36;
    public static final int SPACECRAFT_HEIGHT = 15;
    public static final float SPACECRAFT_STARTX = 20;
    public static final float SPACECRAFT_STARTY = GAME_HEIGHT/2 - SPACECRAFT_HEIGHT/2;

    // Propietats projectil
    public static final float PROJECTILE_VELOCITY = 400;
    public static final int PROJECTILE_WIDTH = 10;
    public static final int PROJECTILE_HEIGHT= 6;


    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_ASTEROID = 3.5f;
    public static final float MIN_ASTEROID = 1f;

    // Configuració Scrollable
    public static final int ASTEROID_SPEED_EASY = -150;
    public static final int ASTEROID_SPEED_MED = -200;
    public static final int ASTEROID_SPEED_HARD = -350;

    public static final int ASTEROID_GAP_EASY = 75;
    public static final int ASTEROID_GAP_MED = 55;
    public static final int ASTEROID_GAP_HARD = 30;

    public static final int BG_SPEED = -300;


}

