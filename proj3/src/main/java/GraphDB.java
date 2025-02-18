import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private Map<Long, Node> nodeMap = new HashMap<>();
    private List<String> locationsList = new LinkedList<>();

    private class Node {
        private long id;
        private double lat;
        private double lon;
        private String locationName;
        private List<Long> adjacent;
        private Map<Long, Double> distanceTo;

        private Node(long id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.locationName = null;
            this.adjacent = new LinkedList<>();
            this.distanceTo = new HashMap<>();
        }

        private void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        private void addEdge(long id) {
            adjacent.add(id);
            double distance = distance(this.id, id);
            distanceTo.put(id, distance);
        }

    }

    void addNode(long id, double lat, double lon) {
        nodeMap.put(id, new Node(id, lat, lon));
    }

    void addEdge(long aid, long bid) {
        nodeMap.get(aid).addEdge(bid);
        nodeMap.get(bid).addEdge(aid);
    }

    void setLocationName(long id, String locationName) {
        nodeMap.get(id).setLocationName(locationName);
        locationsList.add(locationName);
    }

    String getName(long id) {
        return nodeMap.get(id).locationName;
    }

    Iterable<Long> getNodeAdj(long id) {
        return nodeMap.get(id).adjacent;
    }

    double getDistanceTo(long startID, long destID) {
        if (startID == destID) {
            return 0.0;
        }
        return nodeMap.get(startID).distanceTo.get(destID);
    }

    List<Map<String, Object>> getLocationsByName(String locationName) {
        List<Map<String, Object>> ans = new LinkedList<>();
        String cleanLocationName = cleanString(locationName);
        for (long id : vertices()) {
            Node curNode = nodeMap.get(id);
            String curName = curNode.locationName;
            if (curName != null && cleanString(curName).equals(cleanLocationName)) {
                Map<String, Object> map = new HashMap<>();
                map.put("lat", curNode.lat);
                map.put("lon", curNode.lon);
                map.put("name", curNode.locationName);
                map.put("id", curNode.id);
                ans.add(map);
            }
        }

        return ans;
    }

    List<String> getLocationsList() {
        return locationsList;
    }




    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        for (long id : vertices()) {
            if (nodeMap.get(id).adjacent.size() == 0) {
                nodeMap.remove(id);
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return new ArrayList<>(nodeMap.keySet());
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodeMap.get(v).adjacent;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        long ansID = 0;
        double closestDistance = Double.MAX_VALUE;
        for (long id : vertices()) {
            double distance = distance(lon, lat, lon(id), lat(id));
            closestDistance = Math.min(closestDistance, distance);
            ansID = closestDistance == distance ? id : ansID;
        }
        return ansID;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodeMap.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodeMap.get(v).lat;
    }
}
