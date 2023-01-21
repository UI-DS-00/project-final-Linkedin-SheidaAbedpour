package linkedin;

import graph.AdjacencyMapGraph;
import graph.InnerEdge;
import graph.InnerVertex;
import main.Main;
import user.User;

import java.util.*;

public class LinkedIn {

    private final User user;
    private ArrayList<User> connectedUsers = new ArrayList<>();
    private final AdjacencyMapGraph<user.User,?> adjacencyMapGraph;
    private Queue<InnerVertex<user.User,?>> nextLevels = new LinkedList<>();
    private Set<InnerVertex<user.User,?>> visited;

    public LinkedIn(User user, AdjacencyMapGraph<user.User,?> adjacencyMapGraph) {
        this.user = user;
        this.adjacencyMapGraph = adjacencyMapGraph;
        setConnectedUsers();
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(ArrayList<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    private void setConnectedUsers() {
        for (String id: user.getConnectionId())
            connectedUsers.add(Main.getUser(id));
    }

    private ArrayList<User> getConnections(InnerVertex<user.User,?> vertex) {

        visited = new HashSet<>();
        Queue<InnerVertex<user.User,?>> level = new LinkedList<>();
        ArrayList<user.User> connectingList = new ArrayList<>();
        int levelNumber = 0;

        visited.add(vertex);
        level.add(vertex);

        while (!level.isEmpty()) {

            Queue<InnerVertex<user.User,?>> nextLevel = new LinkedList<>();

            for (InnerVertex<user.User,?> userInnerVertex: level) {

                levelNumber++;

                for (InnerVertex<user.User,?> opposite : userInnerVertex.getEdges().keySet()) {

                    if (!visited.contains(opposite)) {
                        visited.add(opposite);
                        nextLevel.add(opposite);
                        if(levelNumber != 1)
                            connectingList.add(opposite.getElement());
                    }

                }
            }

            level = nextLevel;

            if (nextLevels == null) {
                for (InnerVertex<user.User,?> newVertex: adjacencyMapGraph.getVertices())
                    if (!visited.contains(newVertex))
                        nextLevels.add(newVertex);
            }

            if (levelNumber >= 6 && connectingList.size() > 60) {
                nextLevels = nextLevel;
                break;
            }

        }

        return connectingList;
    }

    public ArrayList<User> suggestedUsers(Map<String, Integer> priority) {
        ArrayList<User> connections;
        connections = getConnections(adjacencyMapGraph.getVertex(user));

        SuggestedUser suggestedUser = new SuggestedUser(user, priority, connections);

        ArrayList<User> suggestedList = suggestedUser.suggestedUsers();
        while (suggestedList.size() < 20 || connections.size() == 0) {

            if (nextLevels.isEmpty()) {
                for (InnerVertex<user.User, ?> newUser : adjacencyMapGraph.getVertices())
                    if (!visited.contains(newUser))
                        connections.addAll(getConnections(newUser));
            }

            else {
                connections.addAll(getConnections(nextLevels.peek()));
            }

            suggestedUser.setConnections(connections);
            suggestedList = suggestedUser.suggestedUsers();
        }

        return suggestedList;
    }

}
