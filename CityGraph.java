import java.util.HashMap;
import java.util.Map;

public class CityGraph {
    private Map<String, Map<String, Integer>> graph;

    public CityGraph() {
        this.graph = new HashMap<>();
    }

    public void addCity(String city, Map<String, Integer> distances) {
        graph.put(city, new HashMap<>(distances));
    }

    public Map<String, Map<String, Integer>> getGraph() {
        return graph;
    }
}
