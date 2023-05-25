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

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
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
//        for (Node node : landscape.getChildren()) {
//            if (!(node instanceof Sphere))
//            {
//                continue;
//            }
//            if (quadtree.shouldAddToQuadtree((Sphere) node)) {
//                quadtree.insert((Sphere) node);
//            }
//        }
//
//
//        int countTrue = 0;
//        int countFalse = 0;
//        for (Node node : landscape.getChildren())
//        {
//            if (!(node instanceof Sphere)) continue;
//            if (quadtree.contains((Sphere) node))
//            {
//                countTrue++;
////                ((Sphere) node).setMaterial(new PhongMaterial(Color.BLUE));
//                ((Sphere) node).setVisible(true);
//            }
//            else
//            {
//                countFalse++;
////                ((Sphere) node).setMaterial(new PhongMaterial(Color.YELLOW));
//                node.setVisible(false);
//            }
//        }
//
//        System.out.println(countTrue);
//        System.out.println(countFalse);

        Camera camera = new PerspectiveCamera(false);
        camera.setTranslateZ(-3500);
        Scene scene = new Scene(landscape, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);

        Controller.addControls(primaryStage, landscape, camera);

        primaryStage.setTitle("Landscape Generator");
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                quadtree.clear();

                for (Node node : landscape.getChildren()) {
                    if (!(node instanceof Sphere)) {
                        continue;
                    }

//                    System.out.println(node.getTranslateZ());
                    if (node.getTranslateZ() >= camera.getTranslateZ()-1500
                                    && quadtree.shouldAddToQuadtree((Sphere) node) ) {
                        quadtree.insert((Sphere) node);
                    }
                }

//                int countTrue = 0;
//                int countFalse = 0;
                for (Node node : landscape.getChildren()) {
                    if (!(node instanceof Sphere)) {
                        continue;
                    }
                    if (quadtree.contains((Sphere) node)) {
//                        countTrue++;
                        node.setVisible(true);
                    } else {
//                        countFalse++;
                        node.setVisible(false);
                    }
                }

//                System.out.println(countTrue);
//                System.out.println(countFalse);
            }
        };

        animationTimer.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
