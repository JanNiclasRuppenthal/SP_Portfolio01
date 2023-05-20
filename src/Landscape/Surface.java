package Landscape;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Surface extends Node
{
    public static Box createSurface()
    {
        Box surface = new Box(1400,50,10000);

        surface.setRotationAxis(new Point3D(1,0,0));
        surface.rotateProperty().set(180);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GREEN); // Setze die diffuse Farbe der Box

        surface.setMaterial(material);

        return surface;
    }
}
