import Controls.Controller;
import Landscape.Landscape;
import QuadTree.Quadtree;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private Group landscape;

    @Override
    public void start(Stage primaryStage) {
        landscape = Landscape.createLandscape();


        Quadtree quadtree = new Quadtree(0, 0, WIDTH, HEIGHT);

// Füge die Nodes dem Quadtree hinzu, wenn sie sich im betrachteten Rechteck befinden
        for (Node node : landscape.getChildren()) {
            if (quadtree.shouldAddToQuadtree(node)) {
                quadtree.insert(node);
            }
        }


        int countTrue = 0;
        int countFalse = 0;
        for (Node node : landscape.getChildren())
        {
            if (quadtree.contains(node))
            {
                countTrue++;
            }
            else
            {
                countFalse++;
            }
        }

        System.out.println(countTrue);
        System.out.println(countFalse);


        Controller.addControls(primaryStage, landscape);

        Camera camera = new PerspectiveCamera(false);
        camera.setTranslateZ(-3500);
        Scene scene = new Scene(landscape, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);

        primaryStage.setTitle("Landscape Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
