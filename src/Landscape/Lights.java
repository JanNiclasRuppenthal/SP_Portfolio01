package Landscape;

import javafx.scene.PointLight;
import javafx.scene.paint.Color;

public class Lights
{
    public PointLight pointLight;

    public Lights()
    {
        createLights();
    }

    public PointLight createLights()
    {
        pointLight = new PointLight();
        pointLight.setColor(Color.WHITE); // Setze die Farbe des PointLight
        pointLight.setTranslateX(700); // Setze die Position des PointLight
        pointLight.setTranslateY(-400);
        pointLight.setTranslateZ(-1000);

        return pointLight;
    }
}
