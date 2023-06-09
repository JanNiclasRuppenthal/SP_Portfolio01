package QuadTree;

import javafx.collections.ObservableFloatArray;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<MeshView> objects;
    private Quadtree[] nodes;
    private double x;
    private double y;
    private double width;
    private double height;

    public Quadtree(double x, double y, double width, double height) {
        this.level = 0;
        this.objects = new ArrayList<>();
        this.nodes = new Quadtree[4];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void clear() {
        objects.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    private void split() {
        double subWidth = width / 2;
        double subHeight = height / 2;
        double xMid = x + subWidth;
        double yMid = y + subHeight;

        nodes[0] = new Quadtree(x, y, subWidth, subHeight);
        nodes[1] = new Quadtree(xMid, y, subWidth, subHeight);
        nodes[2] = new Quadtree(x, yMid, subWidth, subHeight);
        nodes[3] = new Quadtree(xMid, yMid, subWidth, subHeight);
    }

    public void insert(MeshView meshView) {
        if (!shouldAddToQuadtree(meshView)) {
            return;
        }

        if (nodes[0] != null) {
            int index = getIndex(meshView);

            if (index != -1) {
                nodes[index].insert(meshView);
                return;
            }
        }

        objects.add(meshView);

        if (objects.size() > 1 && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                MeshView obj = objects.get(i);
                int index = getIndex(obj);
                if (index != -1) {
                    nodes[index].insert(obj);
                    i++;
                } else {
                    // Dreieck passt nicht in die Unterteilungen, bleibt in der aktuellen Ebene
                    i++;
                }
            }
        }
    }


    public boolean shouldAddToQuadtree(MeshView meshView) {
        // Überprüfen Sie hier Ihre Kriterien, um zu entscheiden, ob das Dreieck zum Quadtree hinzugefügt werden soll.
        // Sie können verschiedene Kriterien verwenden, wie z.B. die Position des Dreiecks, die Ausdehnung oder andere Eigenschaften.

        // Beispiel: Hinzufügen, wenn das Dreieck vollständig innerhalb des Quadtree-Bereichs liegt
        Bounds triangleBounds = meshView.getBoundsInParent();
        Bounds quadtreeBounds = new BoundingBox(x, y, width, height);
        return quadtreeBounds.contains(triangleBounds);
    }

    private int getIndex(MeshView meshView) {
        // Holen Sie die Eckpunkte des Dreiecks
        Point3D[] trianglePoints = getTrianglePoints(meshView);

        // Überprüfen Sie, in welchen Unterteilungen die Eckpunkte liegen
        boolean[] isInSubdivision = new boolean[4];
        for (Point3D point : trianglePoints) {
            double centerX = x + (width / 2);
            double centerY = y + (height / 2);
            boolean topQuad = point.getY() <= centerY;
            boolean bottomQuad = point.getY() > centerY;
            boolean leftQuad = point.getX() <= centerX;
            boolean rightQuad = point.getX() > centerX;

            if (topQuad && leftQuad) {
                isInSubdivision[0] = true; // Top left
            }
            if (topQuad && rightQuad) {
                isInSubdivision[1] = true; // Top right
            }
            if (bottomQuad && leftQuad) {
                isInSubdivision[2] = true; // Bottom left
            }
            if (bottomQuad && rightQuad) {
                isInSubdivision[3] = true; // Bottom right
            }
        }

        // Überprüfen Sie, ob alle Eckpunkte in derselben Unterteilung liegen
        boolean sameSubdivision = isInSubdivision[0] && isInSubdivision[1] && isInSubdivision[2] && isInSubdivision[3];

        if (sameSubdivision) {
            // Das Dreieck liegt vollständig in einer Unterteilung
            // Geben Sie den Index dieser Unterteilung zurück
            for (int i = 0; i < 4; i++) {
                if (isInSubdivision[i]) {
                    return i;
                }
            }
        } else {
            // Das Dreieck überlappt mehrere Unterteilungen oder liegt außerhalb
            // Geben Sie -1 zurück, um anzuzeigen, dass keine passende Unterteilung gefunden wurde
            return -1;
        }

        return -1;
    }

    private Point3D[] getTrianglePoints(MeshView meshView) {
        // Hier sollten Sie den Code einfügen, um die Eckpunkte des Dreiecks aus dem MeshView-Objekt zu extrahieren.
        // Je nachdem, wie Sie die Dreiecke in Ihrer Anwendung definieren und erstellen, können Sie verschiedene Methoden verwenden, um die Eckpunkte zu erhalten.
        // Stellen Sie sicher, dass die Rückgabe ein Array von Point3D-Objekten ist, das die Eckpunkte repräsentiert.
        // Der Index 0 des Arrays sollte den ersten Eckpunkt, der Index 1 den zweiten Eckpunkt usw. enthalten.
        // Passen Sie diese Methode entsprechend Ihrer Implementierung an.
        // Hier ist ein Beispiel für die Verwendung der getMesh- und getPoints-Methoden eines TriangleMesh-Objekts:

        TriangleMesh triangleMesh = (TriangleMesh) meshView.getMesh();
        ObservableFloatArray meshPoints = triangleMesh.getPoints();

        Point3D[] trianglePoints = new Point3D[3];
        for (int i = 0; i < 3; i++) {
            int startIndex = i * 3;
            double x = meshPoints.get(startIndex);
            double y = meshPoints.get(startIndex + 1);
            double z = meshPoints.get(startIndex + 2);
            trianglePoints[i] = new Point3D(x, y, z);
        }

        return trianglePoints;
    }


    public boolean contains(MeshView meshView) {
        if (objects.contains(meshView)) {
            return true;
        }

        int index = getIndex(meshView);
        if (index != -1 && nodes[0] != null) {
            return nodes[index].contains(meshView);
        }

        return false;
    }
}
