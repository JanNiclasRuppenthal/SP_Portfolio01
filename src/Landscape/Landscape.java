package Landscape;

import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

import java.util.Collection;

public class Landscape
{
    public static Box surface;
    public static Collection<Sphere> hills;
    public static PointLight pointLight;

    public static Group createLandscape()
    {
        Group groupOfLandscapeElements = new Group();
        pointLight = Lights.createLights();
        groupOfLandscapeElements.getChildren().add(pointLight);

        surface = Surface.createSurface();
        setSurfaceToTheCorrectPositionInScene(surface);
        groupOfLandscapeElements.getChildren().add(surface);

//        Sphere hill = Hill.createHill();
//        groupOfLandscapeElements.getChildren().addAll(hill);

        hills = Hill.createHills();
        groupOfLandscapeElements.getChildren().addAll(hills);

        return groupOfLandscapeElements;
    }

    private static void setSurfaceToTheCorrectPositionInScene(Box surface)
    {
        surface.translateXProperty().set(700);
        surface.translateYProperty().set(800-200);
        surface.translateZProperty().set(650);
    }
}