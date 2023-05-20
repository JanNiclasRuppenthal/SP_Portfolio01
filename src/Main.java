
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 * @author afsal villan
 * @version 1.0
 *
 * http://www.genuinecoder.com
 */
public class Main extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        Box landscape = new Box(1400,50,10000);
        landscape.setRotationAxis(new Point3D(1,0,0));
        landscape.rotateProperty().set(180);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GREEN); // Setze die diffuse Farbe der Box

        landscape.setMaterial(material);;


        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE); // Setze die Farbe des PointLight
        pointLight.setTranslateX(700); // Setze die Position des PointLight
        pointLight.setTranslateY(-400);
        pointLight.setTranslateZ(100);

        Group group = new Group();
        group.getChildren().add(pointLight);
        group.getChildren().add(landscape);

        Camera camera = new PerspectiveCamera(false);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.BLUE);
        scene.setCamera(camera);

        landscape.translateXProperty().set(700);
        landscape.translateYProperty().set(HEIGHT-200);
        landscape.translateZProperty().set(650);


        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    landscape.translateZProperty().set(landscape.getTranslateZ() - 100);
                    break;
                case S:
                    landscape.translateZProperty().set(landscape.getTranslateZ() + 100);
                    break;
            }
        });


        primaryStage.setTitle("Landscape Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}