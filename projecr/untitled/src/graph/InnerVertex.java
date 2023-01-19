package graph;

import javax.swing.text.Position;
import java.util.HashMap;
import java.util.Map;

public class InnerVertex<V, E> {

    private V element;
    private Position position;
    private Map<InnerVertex<V, E>, InnerEdge<E, V>> edges;

    public InnerVertex(V element) {
        this.element = element;
        edges = new HashMap<>();
    }


    public V getElement() {
        return element;
    }

    public void setElement(V element) {
        this.element = element;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public Map<InnerVertex<V, E>, InnerEdge<E, V>> getEdges() {
        return edges;
    }

    public void setEdges(Map<InnerVertex<V, E>, InnerEdge<E, V>> edges) {
        this.edges = edges;
    }
}
