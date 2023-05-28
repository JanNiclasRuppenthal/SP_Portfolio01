package Controls;

import Landscape.Player;
import Landscape.Lights;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Controller extends Thread
{
    private Stage stage;
    private Group landscape;
    private Camera camera;
    private Player player;
    private Lights light;
    private double velocity = 0.0;

    private boolean cutWithAnotherSphere;
    private Sphere sphere = null;

    public Controller(Stage primaryStage, Group landscape, Player player, Lights light, Camera camera)
    {
        this.stage = primaryStage;
        this.landscape = landscape;
        this.camera = camera;
        this.player = player;
        this.light = light;
        this.start();
    }

    public void addControls()
    {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    velocity +=  0.1;
                    velocity = Math.round(velocity * 100) / 100.0;
                    break;
                case S:
                    velocity = Math.round(velocity * 100) / 100.0;
                    velocity += (velocity <= 0) ? 0 : -0.1;
                    break;
                case ESCAPE:
                    this.interrupt();
                    System.exit(0);
                    break;
            }
        });
    }

    public void setLandscape(Group landscape)
    {
        this.landscape = landscape;
    }

    @Override
    public void run()
    {
        while (!this.isInterrupted())
        {
            camera.setTranslateZ(camera.getTranslateZ() + (100 * velocity));
            player.playerSphere.translateZProperty().set(player.playerSphere.getTranslateZ() + (100 * velocity));

            // Bewege das PointLight ebenfalls nach vorne
            light.pointLight.translateZProperty().set(light.pointLight.getTranslateZ() + (100 * velocity));

            if (!cutWithAnotherSphere)
            {
                // Kein Schnitt, bewege den Spieler auf den Boden
                player.playerSphere.setTranslateY(500);
            }

            cutWithAnotherSphere = false;

            // Ueberpruefe den Schnitt mit anderen Kugeln
            for (Node hill : landscape.getChildren())
            {
                if (hill instanceof Sphere && hill.isVisible() && hill != player.playerSphere)
                {
                    sphere = (Sphere) hill;
                    if (sphere.getBoundsInParent().intersects(player.playerSphere.getBoundsInParent()))
                    {
                        // Es gibt einen Schnitt, lasse den Spieler über der Kugel schweben
                        player.playerSphere.setTranslateY(sphere.getTranslateY() - sphere.getRadius() - 100);
                        cutWithAnotherSphere = true;
                        break;
                    }
                }
            }

            try
            {
                sleep(50);
            } catch (InterruptedException e)
            {
                this.interrupt();
            }
        }
    }

}
