package Landscape;

import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

import java.util.Collection;

public class LandscapeGenerator
{
    public Box surface;
    public Collection<Sphere> hills;

    public int depth;

    public Group createLandscape(int depth)
    {
        this.depth = depth;
        Group groupOfLandscapeElements = new Group();

        surface = Surface.createSurface();
        setSurfaceToTheCorrectPositionInScene(surface);
        groupOfLandscapeElements.getChildren().add(surface);

        hills = Hill.createHills(depth);
        groupOfLandscapeElements.getChildren().addAll(hills);

        return groupOfLandscapeElements;
    }

    private void setSurfaceToTheCorrectPositionInScene(Box surface)
    {
        surface.translateXProperty().set(700);
        surface.translateYProperty().set(600);
        surface.translateZProperty().set(650 + depth);
    }
}
