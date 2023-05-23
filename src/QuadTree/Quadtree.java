package QuadTree;

import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;

public class Quadtree {
    private static final int MAX_OBJECTS = 10;
    private static final int MAX_LEVELS = 5;

    private int level;
    private List<Node> objects;
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

    private int getIndex(Node node) {
        int index = -1;
        double xMid = x + (width / 2);
        double yMid = y + (height / 2);
        boolean topQuad = (node.getTranslateY() < yMid && node.getTranslateY() + node.getBoundsInLocal().getHeight() < yMid);
        boolean bottomQuad = (node.getTranslateY() > yMid);
        boolean leftQuad = (node.getTranslateX() < xMid && node.getTranslateX() + node.getBoundsInLocal().getWidth() < xMid);
        boolean rightQuad = (node.getTranslateX() > xMid);

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

    public void insert(Node node) {
        if (!shouldAddToQuadtree(node)) {
            return;
        }

        if (nodes[0] != null) {
            int index = getIndex(node);

            if (index != -1) {
                nodes[index].insert(node);
                return;
            }
        }

        objects.add(node);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
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

    public boolean shouldAddToQuadtree(Node node) {
        double nodeX = node.getTranslateX();
        double nodeY = node.getTranslateY();
        double nodeWidth = node.getBoundsInLocal().getWidth();
        double nodeHeight = node.getBoundsInLocal().getHeight();

        boolean insideX = nodeX + nodeWidth >= x && nodeX <= x + width;
        boolean insideY = nodeY + nodeHeight >= y && nodeY <= y + height;

        return insideX && insideY;
    }

    public List<Node> retrieve(Node node) {
        List<Node> result = new ArrayList<>();
        int index = getIndex(node);

        if (index != -1 && nodes[0] != null) {
            result.addAll(nodes[index].retrieve(node));
        }

        result.addAll(objects);
        return result;
    }

    public boolean contains(Node node) {
        if (!objects.contains(node)) {
            return false;
        }

        int index = getIndex(node);
        if (index != -1 && nodes[0] != null) {
            return nodes[index].contains(node);
        }

        return true;
    }
}
