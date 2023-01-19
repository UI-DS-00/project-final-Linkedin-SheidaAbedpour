package graph;

import javax.swing.text.Position;

public class InnerEdge<E, V> {

    private E element;
    private Position position;
    private InnerVertex<V, E>[] endpoints;

    public InnerEdge(InnerVertex<V, E> u, InnerVertex<V, E> v,
                     E element) {

        this.element = element;
        endpoints = (InnerVertex<V, E>[]) new InnerVertex[]{u,v};
    }


    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public InnerVertex<V, E>[] getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(InnerVertex<V, E>[] endpoints) {
        this.endpoints = endpoints;
    }

}
