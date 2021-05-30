public class Arcs {

    private int endNode;
    private int length;

    Arcs(){
        endNode = 0;
        length = 0;
    }
    Arcs(int end, int leng){
        endNode = end;
        length = leng;
    }
    public int getEndNode() {
        return endNode;
    }
    public int getLength(){
        return length;
    }
}
