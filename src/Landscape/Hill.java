package Landscape;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.Collection;

public class Hill extends Node
{
    public static Sphere createHill()
    {
        // Erzeuge eine Halbkugel
        Sphere hill = new Sphere(100); // radius = [100, 200]

        // Berechne die Position der Halbkugel basierend auf der Box
        hill.setTranslateX(1300); // X-Position der Box [0+radius, 14000 - radius]
        hill.setTranslateY(600); // Y-Position der Box [600]
        hill.setTranslateZ(5500 - 100); // Z-Position der Box [-3500, 5500-radius]

        // Setze das Material der Halbkugel
        PhongMaterial hillMaterial = new PhongMaterial();
        hillMaterial.setDiffuseColor(Color.BROWN); // Setze die Farbe der Halbkugel
        hill.setMaterial(hillMaterial);

        return hill;
    }

    public static Collection<Sphere> createHills()
    {
        Collection<Sphere> collectionHills = new ArrayList<>();

        for (int i = 0; i < 20; i++)
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
            double zPosition =  -3500 + (Math.random() * (9000-radius));
            hill.setTranslateZ(zPosition); // Z-Position der Box [-3500, 5500-radius]

            // Setze das Material der Halbkugel
            PhongMaterial hillMaterial = new PhongMaterial();
            hillMaterial.setDiffuseColor(Color.BROWN); // Setze die Farbe der Halbkugel
            hill.setMaterial(hillMaterial);

            collectionHills.add(hill);
        }

        return collectionHills;
    }
}