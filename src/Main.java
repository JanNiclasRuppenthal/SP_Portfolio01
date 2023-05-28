import Controls.Controller;
import Landscape.LandscapeGenerator;
import Landscape.Player;
import Landscape.Lights;
import QuadTree.Quadtree;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends Application
{

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private Group landscape;
    private Controller controller;
    private LandscapeGenerator landscapeGenerator;
    private Collection<LandscapeGenerator> landscapeGeneratorCollection;
    private int depth = 0;
    @Override
    public void start(Stage primaryStage)
    {
        landscapeGeneratorCollection = new ArrayList<>();
        landscapeGenerator = new LandscapeGenerator();
        landscapeGeneratorCollection.add(landscapeGenerator);
        landscape = landscapeGenerator.createLandscape(depth);

        for (int i = 0; i < 4; i++) // Erzeuge weitere 4 Landschaften vor dem Spieler
        {
            depth += 3500;
            landscapeGenerator.createLandscape(depth);
            landscape.getChildren().add(landscapeGenerator.surface);
            landscape.getChildren().addAll(landscapeGenerator.hills);
        }

        Player player = new Player();
        landscape.getChildren().add(player.playerSphere);

        Lights light = new Lights();
        landscape.getChildren().add(light.pointLight);

        Quadtree quadtree = new Quadtree(0, 0, WIDTH, HEIGHT);

        Camera camera = new PerspectiveCamera(false);
        camera.setTranslateZ(-3500);
        Scene scene = new Scene(landscape, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);

        controller = new Controller(primaryStage, landscape, player, light, camera);
        controller.addControls();

        primaryStage.setTitle("Landscape Generator");
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer viewingFrustrum = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                quadtree.clear();

                for (Node node : landscape.getChildren())
                {
                    if (!(node instanceof Sphere))
                    {
                        continue;
                    }

                    if (node.getTranslateZ() >= camera.getTranslateZ()-1500
                                    && quadtree.shouldAddToQuadtree((Sphere) node) )
                    {
                        quadtree.insert((Sphere) node);
                    }
                }

                for (Node node : landscape.getChildren())
                {
                    if (!(node instanceof Sphere))
                    {
                        continue;
                    }
                    if (quadtree.contains((Sphere) node))
                    {
                        node.setVisible(true);
                    } else
                    {
                        node.setVisible(false);
                    }
                }

            }
        };

        viewingFrustrum.start();

        AnimationTimer endlessLandscape = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // Fuege weitere Landschaften hinzi
                if (camera.getTranslateZ() > depth - 14000) // mind. 4 Landschaften vor dem Spieler (4*3500)
                {
                    depth += 3500;
                    landscapeGenerator.createLandscape(depth);
                    landscapeGeneratorCollection.add(landscapeGenerator);
                    landscape.getChildren().add(landscapeGenerator.surface);
                    landscape.getChildren().addAll(landscapeGenerator.hills);
                }

                // Loesche hinter der Kamera stehende Landschaften

                for (LandscapeGenerator generator :
                        landscapeGeneratorCollection)
                {
                    // Entferne alle hintenstehenden Objekte mit einem
                    // grosszuegigem Abstand
                    if (player.playerSphere.getTranslateZ() > generator.depth + 7000)
                    {
                        landscape.getChildren().remove(generator.surface);
                        for (Sphere hills : generator.hills)
                        {
                            landscape.getChildren().remove(hills);
                        }
                    }
                }



            }
        };

        endlessLandscape.start();
    }

    @Override
    public void stop()
    {
        controller.interrupt();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
