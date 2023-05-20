package Controls;

import Landscape.Landscape;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller
{
    // move landscape and pointLight to the right direction
    public static void addControls(Stage primaryStage, Group landscape)
    {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    landscape.translateZProperty().set(landscape.getTranslateZ() - 100);
                    Landscape.pointLight.translateZProperty().set(Landscape.pointLight.getTranslateZ() + 100);
                    break;
                case S:
                    landscape.translateZProperty().set(landscape.getTranslateZ() + 100);
                    Landscape.pointLight.translateZProperty().set(Landscape.pointLight.getTranslateZ() - 100);
                    break;
            }
        });
    }
}
