import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class DrawMap extends JPanel {
    private Integer nrOfClicks = 0;
    ReadMap auxMap = new ReadMap();
    int[] parent;
    GraphNode NodStart = new GraphNode();
    GraphNode NodEnd = new GraphNode();

    public GraphNode getNode(Point p, Vector<GraphNode> vec) {
        if (p != null) {
            for (GraphNode iterator : vec) {
                if (distance(p, iterator))
                    return iterator;
            }
        }
        return new GraphNode();
    }

    public boolean distance(Point p, GraphNode n) {
        return Math.sqrt(Math.pow(p.getX() - n.getCoordx(), 2) + Math.pow(p.getY() - n.getCoordy(), 2)) < 5;
    }

    public DrawMap() {
        auxMap.readNodes();
        auxMap.readList();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                nrOfClicks++;
                    if (nrOfClicks%2 == 1) {

                        NodStart = getNode(e.getPoint(), auxMap.convertedNodeList);
                        System.out.println(" The index of the start node is: " + NodStart.getId());
                    }
                    if (nrOfClicks%2 == 0) {

                        NodEnd = getNode(e.getPoint(), auxMap.convertedNodeList);
                        System.out.println(" The index of the end node is: " + NodEnd.getId());
                        if(NodStart.getId() != -1 && NodEnd.getId() != -1){
                            System.out.println("  Using the Dijkstra algorithm to find the minimal-cost path,");
                            System.out.println("  " + java.time.LocalTime.now());
                            Dijkstra(NodStart.getId(), NodEnd.getId());
                            System.out.println("  " + java.time.LocalTime.now());
                            System.out.println();

                            System.out.println("  Using the  Bellman-Ford algorithm to find the minimal-cost path,");
                            System.out.println("  " + java.time.LocalTime.now());
                            BellmanFord(NodStart.getId(), NodEnd.getId());
                            System.out.println("  " + java.time.LocalTime.now());
                            System.out.println();
                            repaint();
                        }
                        else{
                            System.out.println(" An invalid position was found!");
                        }
                    }
                }
        });
    }

    protected void Dijkstra(int start, int end) {

        int numberOfNodes = auxMap.convertedNodeList.size();
        PriorityQueue<Pair<Integer, Integer>> pqueue = new PriorityQueue<>(numberOfNodes, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> obj1, Pair<Integer, Integer> obj2) {
                return obj1.getFirst() - obj2.getFirst();
            }
        });
        int[] distance = new int[numberOfNodes];
        for (int index = 0; index < numberOfNodes; index++)
            distance[index] = Integer.MAX_VALUE;

        pqueue.add(new Pair<>(0, start));
        distance[start] = 0;

        boolean[] visited = new boolean[numberOfNodes];
        parent = new int[numberOfNodes];

        for (int index = 0; index < numberOfNodes; index++) {
            parent[index] = -1;
        }

        while (!pqueue.isEmpty()) {
            int node = pqueue.peek().getSecond();
            pqueue.poll();
            visited[node] = true;
            for (Arcs arc : auxMap.listaAdiacenta.elementAt(node)) {
                int neighbour = arc.getEndNode();
                int length = arc.getLength();
                if (!visited[neighbour] && distance[neighbour] > distance[node] + length) {
                    parent[neighbour] = node;
                    distance[neighbour] = distance[node] + length;
                    pqueue.add(new Pair<>(distance[neighbour], neighbour));
                }
            }
        }
        System.out.println("  the total cost of the path is: " + distance[end]);
    }

    protected void BellmanFord(int start, int end)
    {
        int numberOfNodes = auxMap.convertedNodeList.size();
        PriorityQueue<Pair<Integer, Integer>> pqueue = new PriorityQueue<>(numberOfNodes, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> obj1, Pair<Integer, Integer> obj2) {
                return obj1.getFirst() - obj2.getFirst();
            }
        });

        int[] distance = new int[numberOfNodes];
        Boolean visited[] = new Boolean[numberOfNodes];
        int negative_cycle[] = new int[numberOfNodes];
        boolean check_negative_cycle = true;

        for (int index = 0; index < numberOfNodes; index++)
            distance[index] = Integer.MAX_VALUE;
        for (int index = 0; index < numberOfNodes; index++)
            visited[index] = false;

        distance[start] = 0;
        visited[start]=true;
        pqueue.add(new Pair<>(0, start));
        while(!pqueue.isEmpty())
        {
            int node = pqueue.peek().getSecond();
            pqueue.poll();
            visited[node] = false;
            for (int index=0; index< auxMap.listaAdiacenta.elementAt(node).size(); index++)
            {
                int neighbour = auxMap.listaAdiacenta.elementAt(node).elementAt(index).getEndNode();
                if (distance[neighbour] > distance[node] + auxMap.listaAdiacenta.elementAt(node).elementAt(index).getLength())
                {
                    distance[neighbour] = distance[node] + auxMap.listaAdiacenta.elementAt(node).elementAt(index).getLength();
                    negative_cycle[neighbour] = negative_cycle[node]+1;
                    if(negative_cycle[neighbour] > numberOfNodes-1)
                    {
                        System.out.println("Negative circle");
                        check_negative_cycle=false;
                        break;
                    }
                    if(!visited[neighbour])
                    {
                        visited[neighbour] = true;
                        pqueue.add(new Pair<>(distance[neighbour],neighbour));
                    }
                }
            }
            if (check_negative_cycle==false)
                break;
        }
        if (check_negative_cycle)
            System.out.println("  the total cost of the path is: " + distance[end]);
        else
            System.out.println("No solution");
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int index = 0; index < auxMap.listaAdiacenta.size(); index++) {
            for (int index1 = 1; index1 < auxMap.listaAdiacenta.elementAt(index).size(); index1++) {
                Integer coordXNode1, coordYNode1, coordXNode2, coordYNode2;
                coordXNode1 = auxMap.convertedNodeList.elementAt(index).getCoordx();
                coordYNode1 = auxMap.convertedNodeList.elementAt(index).getCoordy();
                coordXNode2 = auxMap.convertedNodeList.elementAt(auxMap.listaAdiacenta.elementAt(index).elementAt(index1).getEndNode()).getCoordx();
                coordYNode2 = auxMap.convertedNodeList.elementAt(auxMap.listaAdiacenta.elementAt(index).elementAt(index1).getEndNode()).getCoordy();
                g.drawLine(coordXNode1, coordYNode1, coordXNode2, coordYNode2);
                g.setColor(Color.BLUE);
            }
        }
        if (nrOfClicks%2 == 0 && nrOfClicks>=2) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            for (int node = NodEnd.getId(); parent[node] != -1; node = parent[node]) {
                int end = node;
                int start = parent[node];
                g2.drawLine(auxMap.convertedNodeList.elementAt(end).getCoordx(), auxMap.convertedNodeList.elementAt(end).getCoordy(),
                        auxMap.convertedNodeList.elementAt(start).getCoordx(), auxMap.convertedNodeList.elementAt(start).getCoordy());
                g2.setColor(Color.RED);
            }
        }
    }
}
