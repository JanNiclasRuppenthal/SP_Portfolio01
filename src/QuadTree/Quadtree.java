package QuadTree;

import javafx.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<Sphere> objects;
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

    public void insert(Sphere sphere) {
        if (!shouldAddToQuadtree(sphere)) {
            return;
        }

        if (nodes[0] != null) {
            int index = getIndex(sphere);

            if (index != -1) {
                nodes[index].insert(sphere);
                return;
            }
        }

        objects.add(sphere);

        if (objects.size() > 1 && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    public boolean shouldAddToQuadtree(Sphere sphere) {
        double leftXPositionSphere = sphere.getTranslateX() - sphere.getRadius();
        double rightXPositionSphere = sphere.getTranslateX() + sphere.getRadius();
        double topYPositionSphere = sphere.getTranslateY() - sphere.getRadius();

        boolean isInsideBounds = leftXPositionSphere >= x && rightXPositionSphere <= x + width && topYPositionSphere >= y ;

        return isInsideBounds ;
    }

    private int getIndex(Sphere sphere) {
        int index = -1;
        double xMid = x + (width / 2);
        double yMid = y + (height / 2);
        boolean topQuad = (sphere.getTranslateY() < yMid && sphere.getTranslateY() + sphere.getRadius() < yMid);
        boolean bottomQuad = (sphere.getTranslateY() > yMid);
        boolean leftQuad = (sphere.getTranslateX() < xMid && sphere.getTranslateX() + sphere.getRadius() < xMid);
        boolean rightQuad = (sphere.getTranslateX() > xMid);

        if (topQuad) {
            if (leftQuad) {
                index = 0; // Top left
            } else if (rightQuad) {
                index = 1; // Top right
            }
        } else if (bottomQuad) {
            if (leftQuad) {
                index = 2; // Bottom left
            } else if (rightQuad) {
                index = 3; // Bottom right
            }
        }

        return index;
    }

    public List<Sphere> retrieve(Sphere sphere) {
        List<Sphere> result = new ArrayList<>();
        int index = getIndex(sphere);

        if (index != -1 && nodes[0] != null) {
            result.addAll(nodes[index].retrieve(sphere));
        }

        result.addAll(objects);
        return result;
    }

    public boolean contains(Sphere sphere) {
        if (objects.contains(sphere)) {
            return true;
        }

        int index = getIndex(sphere);
        if (index != -1 && nodes[0] != null) {
            return nodes[index].contains(sphere);
        }

        return false;
    }
}
