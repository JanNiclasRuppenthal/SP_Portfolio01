package Landscape;

import javafx.scene.image.Image;
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
        PhongMaterial material = new PhongMaterial();
        Image texture = new Image("/sonic_fur.png");
        material.setDiffuseMap(texture);
        player.setMaterial(material);
        player.setTranslateX(700);
        player.setTranslateY(500);
        player.setTranslateZ(-200); // Zu Beginn kein Schnitt mit einer Halbkugel (Berg)

        return player;
    }
}
