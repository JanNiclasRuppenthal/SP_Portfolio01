package Landscape;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Player
{
    public Sphere playerSphere;

    public Player()
    {
        playerSphere = createPlayerSphere();
    }

    private Sphere createPlayerSphere()
    {
        Sphere player = new Sphere(50); // radius
        player.setMaterial(new PhongMaterial(Color.BLUE));
        player.setTranslateX(700);
        player.setTranslateY(500);
        player.setTranslateZ(-3700); // Zu Beginn kein Schnitt mit einer Halbkugel (Berg)

        return player;
    }
}
