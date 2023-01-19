package graph;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class AdjacencyMapGraph<V, E> {

    private ArrayList<InnerVertex<V,E>> vertices = new ArrayList<>();
    private ArrayList<InnerEdge<E,V>> edges = new ArrayList<>();

    public AdjacencyMapGraph() {}

    public int numVertices() {
        return vertices.size();
    }

    public int numEdges() {
        return edges.size();
    }

    public InnerEdge<E,V> getEdge(InnerVertex<V,E> u, InnerVertex<V,E> v) {
        return u.getEdges().get(v);
    }

    public InnerVertex<V,E>[] endVertices(InnerEdge<E,V> e) {
        return e.getEndpoints();
    }

    public InnerVertex<V,E> opposite(InnerVertex<V,E> v, InnerEdge<E,V> e) {
        InnerVertex<V,E>[] endpoints = e.getEndpoints();
        if (endpoints[0] == v)
            return endpoints[1];
        else if (endpoints[1] == v)
            return endpoints[0];
        else
            throw new IllegalArgumentException("v is not incident to this edge");
    }

    public InnerVertex<V,E> insertVertex(V element) {
        InnerVertex<V,E> vertex = new InnerVertex<>(element);
        vertices.add(vertex);
        return vertex;
    }

    public InnerEdge<E,V> insertEdge(InnerVertex<V,E> u, InnerVertex<V,E> v, E element) throws IllegalArgumentException {
        if (getEdge(u,v) == null) {
            InnerEdge<E,V> edge = new InnerEdge<>(v,u,element);
            u.getEdges().put(v,edge);
            v.getEdges().put(u,edge);
            return edge;
        }
        else
            throw new IllegalArgumentException("Edge from u to v exists");
    }


    public void removeVertex(InnerVertex<V,E> vertex) {
        for (InnerEdge<E,V> edge: vertex.getEdges().values())
            edges.remove(edge);
        vertices.remove(vertex);
    }

}
