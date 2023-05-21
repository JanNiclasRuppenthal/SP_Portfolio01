package Controls;

import Landscape.Landscape;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Controller
{
    public static void addControls(Stage primaryStage, Group landscape)
    {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:

                    landscape.translateZProperty().set(landscape.getTranslateZ() - 100);
                    Landscape.player.translateZProperty().set(Landscape.player.getTranslateZ() + 100);

                    // Bewege das PointLight ebenfalls nach vorne
                    Landscape.pointLight.translateZProperty().set(Landscape.pointLight.getTranslateZ() + 100);

                    // Kein Schnitt, bewege den Spieler auf den Boden
                    Landscape.player.setTranslateY(500);

                    // Überprüfe den Schnitt mit anderen Kugeln
                    Sphere sphere = null;
                    for (Node hill : landscape.getChildren())
                    {
                        if (hill instanceof Sphere && hill != Landscape.player)
                        {
                            sphere = (Sphere) hill;
                            if (sphere.getBoundsInParent().intersects(Landscape.player.getBoundsInParent()))
                            {
                                // Es gibt einen Schnitt, lasse den Spieler über der Kugel schweben
                                Landscape.player.setTranslateY(sphere.getTranslateY() - sphere.getRadius() - 100);
                                break;
                            }
                        }
                    }

                    break;
                case S:
                    landscape.translateZProperty().set(landscape.getTranslateZ() + 100);
                    Landscape.player.translateZProperty().set(Landscape.player.getTranslateZ() - 100);
                    Landscape.pointLight.translateZProperty().set(Landscape.pointLight.getTranslateZ() - 100);
                    break;
            }
        });
    }
}
