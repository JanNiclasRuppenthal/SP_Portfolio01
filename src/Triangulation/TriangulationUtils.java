package Triangulation;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

public class TriangulationUtils {

    public static MeshView triangulateSphere(Sphere sphere) {
        int divisions = 64;
        TriangleMesh mesh = new TriangleMesh();

        // Eckpunkte der Kugel berechnen und hinzufügen
        float[] points = new float[(divisions + 1) * (divisions + 1) * 3];
        float[] texCoords = new float[(divisions + 1) * (divisions + 1) * 2];

        int pointIndex = 0;
        int texCoordIndex = 0;

        for (int phiDiv = 0; phiDiv <= divisions; phiDiv++) {
            double phi = phiDiv * Math.PI / divisions;
            for (int thetaDiv = 0; thetaDiv <= divisions; thetaDiv++) {
                double theta = thetaDiv * 2 * Math.PI / divisions;

                float x = (float) (sphere.getRadius() * Math.sin(phi) * Math.cos(theta));
                float y = (float) (sphere.getRadius() * Math.sin(phi) * Math.sin(theta));
                float z = (float) (sphere.getRadius() * Math.cos(phi));

                points[pointIndex++] = x;
                points[pointIndex++] = y;
                points[pointIndex++] = z;

                texCoords[texCoordIndex++] = (float) theta / (2 * (float) Math.PI);
                texCoords[texCoordIndex++] = (float) phi / (float) Math.PI;
            }
        }

        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);

        // Dreiecksindizes hinzufügen, um Dreiecke zu definieren
        for (int phiDiv = 0; phiDiv < divisions; phiDiv++) {
            for (int thetaDiv = 0; thetaDiv < divisions; thetaDiv++) {
                int topLeft = phiDiv * (divisions + 1) + thetaDiv;
                int topRight = topLeft + 1;
                int bottomLeft = topLeft + (divisions + 1);
                int bottomRight = bottomLeft + 1;

                mesh.getFaces().addAll(
                        topLeft, 0, topRight, 0, bottomLeft, 0,
                        topRight, 0, bottomRight, 0, bottomLeft, 0
                );
            }
        }

        // Erstellen Sie eine MeshView-Instanz und setzen Sie die Mesh-Daten
        MeshView meshView = new MeshView(mesh);

        // Setzen Sie die CullFace, um die Rückseite der Dreiecke auszublenden
        meshView.setCullFace(CullFace.FRONT);

        meshView.setTranslateX(sphere.getTranslateX());
        meshView.setTranslateY(sphere.getTranslateY());
        meshView.setTranslateZ(sphere.getTranslateZ());

        // Linien und Farben sichtbar machen
        meshView.setDrawMode(DrawMode.LINE);
        meshView.setMaterial(new PhongMaterial(Color.RED));

        return meshView;
    }
}
