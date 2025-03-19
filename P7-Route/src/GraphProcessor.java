import java.security.InvalidAlgorithmParameterException;
import java.io.*;
import java.util.*;


/**
 * Models a weighted graph of latitude-longitude points
 * and supports various distance and routing operations.
 * To do: Add your name(s) as additional authors
 * @author Brandon Fain
 * @author Owen Astrachan modified in Fall 2023
 *
 */
public class GraphProcessor {
    /**
     * Creates and initializes a graph from a source data
     * file in the .graph format. Should be called
     * before any other methods work.
     * @param file a FileInputStream of the .graph file
     * @throws Exception if file not found or error reading
     */

    // include instance variables here

    private Map<Point, List<Point>> myGraph;
    // maybe have a map from names to points?
    // array of edges??


    public GraphProcessor(){
        myGraph = new HashMap<Point, List<Point>>();
    }

    /**
     * Creates and initializes a graph from a source data
     * file in the .graph format. Should be called
     * before any other methods work.
     * @param file a FileInputStream of the .graph file
     * @throws IOException if file not found or error reading
     */

    public void initialize(FileInputStream file) throws IOException {
       // GraphProcessor g = new GraphProcessor(); // do I keep this?
        Scanner s = new Scanner(file);
        int numVertices = s.nextInt();
        int numEdges = s.nextInt();
        Point[] points = new Point[numVertices]; // we might want to make this an instance variable
        s.nextLine(); // do I need this?
        for (int k = 0; k < numVertices; k++) {
            String str = s.nextLine();
            String[] line = str.split(" ");
            //double lat = s.nextDouble(); // why don't these work???
            //double lon = s.nextDouble();
            double lat = Double.parseDouble(line[1]);
            double lon = Double.parseDouble(line[2]);
            Point p = new Point(lat, lon);
            points[k] = p;
            myGraph.put(p, new ArrayList<Point>());
        }
        for (int k = 0; k < numEdges; k++) {
            String str = s.nextLine();
            String[] line = str.split(" ");
            //int startIndex = s.nextInt();
            //int endIndex = s.nextInt();
            int startIndex = Integer.parseInt(line[0]);
            int endIndex = Integer.parseInt(line[1]);
            Point start = points[startIndex];
            Point end = points[endIndex];
            myGraph.get(start).add(end);
            myGraph.get(end).add(start);
        }
        s.close();
    }

    /**
     * NOT USED IN FALL 2023, no need to implement
     * @return list of all vertices in graph
     */

    public List<Point> getVertices(){
        return new ArrayList<Point>(myGraph.keySet());
    }

    /**
     * NOT USED IN FALL 2023, no need to implement
     * @return all edges in graph
     */
    public List<Point[]> getEdges(){
        List<Point[]> edges = new ArrayList<Point[]>();
        return null;
    }

    /**
     * Searches for the point in the graph that is closest in
     * straight-line distance to the parameter point p
     * @param p is a point, not necessarily in the graph
     * @return The closest point in the graph to p
     */
    public Point nearestPoint(Point p) {
        Point nearest = new Point(1000000, 1000000);
        for (Point vertex : myGraph.keySet()) {
            if (p.distance(vertex) < p.distance(nearest)) {
                nearest = vertex;
            }
        }
        return nearest;
    }


    /**
     * Calculates the total distance along the route, summing
     * the distance between the first and the second Points, 
     * the second and the third, ..., the second to last and
     * the last. Distance returned in miles.
     * @param start Beginning point. May or may not be in the graph.
     * @param end Destination point May or may not be in the graph.
     * @return The distance to get from start to end
     */
    public double routeDistance(List<Point> route) {
        double d = 0.0;
        for (int k = 0; k < route.size() - 1; k++) {
            d += route.get(k).distance(route.get(k+1));
        }
        return d;
    }
    

    /**
     * Checks if input points are part of a connected component
     * in the graph, that is, can one get from one to the other
     * only traversing edges in the graph
     * @param p1 one point
     * @param p2 another point
     * @return true if and onlyu if p2 is reachable from p1 (and vice versa)
     */
    public boolean connected(Point p1, Point p2) {
        if (myGraph.containsKey(p1) && myGraph.containsKey(p2)) {
            Set<Point> visited = new HashSet<Point>();
            Stack<Point> stack = new Stack<Point>();
            stack.push(p1);
            while (! stack.isEmpty()) {
                Point current = stack.pop();
                visited.add(current);
                if (current.equals(p2)) return true;
                for (Point adj : myGraph.get(current)) {
                    if (! visited.contains(adj)) {
                        stack.push(adj);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the shortest path, traversing the graph, that begins at start
     * and terminates at end, including start and end as the first and last
     * points in the returned list. If there is no such route, either because
     * start is not connected to end or because start equals end, throws an
     * exception.
     * @param start Beginning point.
     * @param end Destination point.
     * @return The shortest path [start, ..., end].
     * @throws IllegalArgumentException if there is no such route, 
     * either because start is not connected to end or because start equals end.
     */
    public List<Point> route(Point start, Point end) throws IllegalArgumentException {
        if (! connected(start, end) || start.distance(end) == 0) {
            throw new IllegalArgumentException("No path between start and end");
        }

        Map<Point, Point> predMap = new HashMap<>();
        Map<Point, Double> distMap = new HashMap<>();

        Comparator<Point> comp = (a, b) -> Double.compare(distMap.get(a), distMap.get(b));
        PriorityQueue<Point> pq = new PriorityQueue<>(comp);

        Point current = start;
        pq.add(current);
        distMap.put(current, 0.0);

        while (! pq.isEmpty()) {
            current = pq.remove();
            for (Point adj : myGraph.get(current)) {
                double dist = current.distance(adj);
                if (!distMap.containsKey(adj) || distMap.get(adj) > distMap.get(current) + dist) {
                    distMap.put(adj, distMap.get(current) + dist);
                    predMap.put(adj, current);
                    pq.add(adj);
                }
            }
        }

        List<Point> path = new ArrayList<Point>();
        path.add(end);
        Point p = predMap.get(end);
        path.add(p);
        int predSize = predMap.keySet().size();
        for (int k = 0; k < predSize; k++) {
            if (predMap.get(p) == null) break;
            p = predMap.get(p);
            path.add(p);
        }

        Collections.reverse(path);

        return path;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //String name = "data/usa.graph";
        String name = "data/simple.graph";
        GraphProcessor gp = new GraphProcessor();
        gp.initialize(new FileInputStream(name));
        System.out.println("running GraphProcessor");
    }


    
}
