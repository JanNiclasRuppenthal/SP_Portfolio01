import Controls.Controller;
import Landscape.LandscapeGenerator;
import Landscape.Player;
import Landscape.Lights;
import QuadTree.Quadtree;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends Application
{

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private PerspectiveCamera camera;
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

        for (int i = 0; i < 9; i++) // Erzeuge weitere 10 Landschaften vor dem Spieler
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

        camera = new PerspectiveCamera(false);
        camera.setTranslateZ(0);
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
                    if (!(node instanceof MeshView))
                    {
                        continue;
                    }

                    if (quadtree.shouldAddToQuadtree((MeshView) node) )
                    {
                        quadtree.insert((MeshView) node);
                    }
                }


                int isVisible = 0;
                int isInvisible = 0;
                for (Node node : landscape.getChildren())
                {
                    if (!(node instanceof MeshView))
                    {
                        continue;
                    }
                    if (quadtree.contains((MeshView) node))
                    {
                        node.setVisible(true);
                        isVisible++;
                    }
                    else
                    {
                        node.setVisible(false);
                        isInvisible++;
                    }
                }

                System.out.println(isVisible);
                System.out.println(isInvisible);;

            }
        };

        viewingFrustrum.start();

        AnimationTimer endlessLandscape = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // Fuege weitere Landschaften hinzu
//                if (camera.getTranslateZ() > depth - 35000) // mind. 10 Landschaften vor dem Spieler (10*3500)
//                {
//                    depth += 3500;
//                    landscapeGenerator.createLandscape(depth);
//                    landscapeGeneratorCollection.add(landscapeGenerator);
//                    landscape.getChildren().add(landscapeGenerator.surface);
//                    landscape.getChildren().addAll(landscapeGenerator.hills);
//                }

                // Loesche hinter der Kamera stehende Landschaften

//                for (LandscapeGenerator generator :
//                        landscapeGeneratorCollection)
//                {
//                    // Entferne alle hintenstehenden Objekte mit einem
//                    // grosszuegigem Abstand
//                    if (player.playerSphere.getTranslateZ() > generator.depth + 7000)
//                    {
//                        landscape.getChildren().remove(generator.surface);
//                        for (Node hills : generator.hills)
//                        {
//                            landscape.getChildren().remove(hills);
//                        }
//                    }
//                }
            }
        };

        endlessLandscape.start();
    }


    private boolean isMeshViewInViewingFrustrum(MeshView meshView, double cameraZ) {
        // Angenommen, das MeshView-Objekt hat eine Bounding-Box-Geometrie

        // Holen Sie die Bounding Box des MeshViews
        Bounds meshBounds = meshView.getBoundsInLocal();

        // Transformieren Sie die Bounding Box basierend auf der Transformation des MeshViews
        meshBounds = meshView.getLocalToSceneTransform().transform(meshBounds);

        double meshMinZ = meshBounds.getMinZ();
        double meshMaxZ = meshBounds.getMaxZ();

        // Überprüfen Sie, ob das MeshView im Viewing Frustrum liegt
        double nearPlane = cameraZ - 1000; // Nähe Schnittebene
        double farPlane = cameraZ + (3500 * 10); // Ferne Schnittebene

        // Überprüfen Sie die Sichtbarkeit des MeshView basierend auf dem Viewing Frustrum
        return meshMaxZ > nearPlane && meshMinZ < farPlane;
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
