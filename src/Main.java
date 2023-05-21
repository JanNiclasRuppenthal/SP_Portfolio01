
import Controls.Controller;
import Landscape.Landscape;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//TODO: Mit der zweiten Teilaufgabe beginnen.

public class Main extends Application {

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        Group landscape = Landscape.createLandscape();


        Camera camera = new PerspectiveCamera(false);
        camera.setTranslateZ(-3500);
        Scene scene = new Scene(landscape, WIDTH, HEIGHT, true);
        scene.setFill(Color.LIGHTBLUE);
        scene.setCamera(camera);

        Controller.addControls(primaryStage, landscape);

        primaryStage.setTitle("Landscape Generator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}