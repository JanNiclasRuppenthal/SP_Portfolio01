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

        for (int i = 0; i < 25; i++) // Erstelle 25 Berge aus Halbkugeln
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

        Sphere testHill = new Sphere(100);
        testHill.setTranslateX(-300);
        testHill.setTranslateY(600);
        testHill.setTranslateZ(-3500);
        testHill.setMaterial(new PhongMaterial(Color.WHITE));
        collectionHills.add(testHill);

        /*
        // Erzeuge eine Halbkugel
        Sphere hill = new Sphere(100); // radius = [100, 200]

        // Berechne die Position der Halbkugel basierend auf der Box
        hill.setTranslateX(1300); // X-Position der Box [0+radius, 14000 - radius]
        hill.setTranslateY(600); // Y-Position der Box [600]
        hill.setTranslateZ(-2000 - 100); // Z-Position der Box [-3500, 5500-radius]

        // Setze das Material der Halbkugel
        PhongMaterial hillMaterial = new PhongMaterial();
        hillMaterial.setDiffuseColor(Color.BROWN); // Setze die Farbe der Halbkugel
        hill.setMaterial(hillMaterial);


        // Erzeuge eine Halbkugel
        Sphere hill2 = new Sphere(200); // radius = [100, 200]

        // Berechne die Position der Halbkugel basierend auf der Box
        hill2.setTranslateX(1200); // X-Position der Box [0+radius, 14000 - radius]
        hill2.setTranslateY(600); // Y-Position der Box [600]
        hill2.setTranslateZ(-2600 - 200); // Z-Position der Box [-3500, 5500-radius]

        // Setze das Material der Halbkugel
        PhongMaterial hill2Material = new PhongMaterial();
        hill2Material.setDiffuseColor(Color.BROWN); // Setze die Farbe der Halbkugel
        hill2.setMaterial(hill2Material);



        // Erzeuge eine Halbkugel
        Sphere hill3 = new Sphere(120); // radius = [100, 200]

        // Berechne die Position der Halbkugel basierend auf der Box
        hill3.setTranslateX(500); // X-Position der Box [0+radius, 14000 - radius]
        hill3.setTranslateY(600); // Y-Position der Box [600]
        hill3.setTranslateZ(-2000 - 100); // Z-Position der Box [-3500, 5500-radius]

        // Setze das Material der Halbkugel // Setze die Farbe der Halbkugel
        hill3.setMaterial(hillMaterial);


        // Erzeuge eine Halbkugel
        Sphere hill4 = new Sphere(220); // radius = [100, 200]

        // Berechne die Position der Halbkugel basierend auf der Box
        hill4.setTranslateX(700); // X-Position der Box [0+radius, 14000 - radius]
        hill4.setTranslateY(600); // Y-Position der Box [600]
        hill4.setTranslateZ(-2600 - 200); // Z-Position der Box [-3500, 5500-radius]

        // Setze das Material der Halbkugel
        hill4.setMaterial(hill2Material);

        collectionHills.add(hill);
        collectionHills.add(hill2);
        collectionHills.add(hill3);
        collectionHills.add(hill4);
         */

        return collectionHills;
    }
}
