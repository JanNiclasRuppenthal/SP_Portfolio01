package Landscape;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.Collection;

public class Hill extends Node
{
    public static Collection<Sphere> createHills(int depth)
    {
        Collection<Sphere> collectionHills = new ArrayList<>();

        for (int i = 0; i < 10; i++) // Erstelle 25 Berge aus Halbkugeln
        {
            int radius = 100 + (int) (Math.random() * 100);

            // Erzeuge eine Halbkugel
            Sphere hill = new Sphere(radius); // radius = [100, 200]

            double xPosition = (int) (Math.random() * 1400);

            if (xPosition < radius)
            {
                xPosition = radius;
            } else if (1400 - radius < xPosition)
            {
                xPosition = 1400-radius;
            }

            // Berechne die Position der Halbkugel basierend auf der Box
            hill.setTranslateX(xPosition); // X-Position der Box [0+radius, 14000 - radius]
            hill.setTranslateY(600); // Y-Position der Box [600]
            double zPosition =  (depth+200) + (Math.random() * (9000-radius));
            hill.setTranslateZ(zPosition); // Z-Position der Box [depth, 9000-radius]

            // Setze das Material der Halbkugel
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.RED); // Setze die Farbe der Halbkugel

//            PhongMaterial material = new PhongMaterial();
//            Image texture = new Image("/black-stone.jpg");
//            material.setDiffuseMap(texture);

            hill.setMaterial(material);

            collectionHills.add(hill);
        }

        return collectionHills;
    }
}
