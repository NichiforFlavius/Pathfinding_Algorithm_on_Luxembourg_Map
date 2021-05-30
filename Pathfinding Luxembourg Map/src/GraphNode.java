public class GraphNode {
    private final int coordx;
    private final int coordy;
    private final int id;

    GraphNode() {
        coordx = 0;
        coordy = 0;
        id = -1;
    }

    GraphNode(int x, int y, int auxId) {
        coordx = x;
        coordy = y;
        id = auxId;

    }

    int getCoordx() {
        return coordx;
    }

    int getCoordy() {
        return coordy;
    }

    int getId(){
        return  id;
    }
}
