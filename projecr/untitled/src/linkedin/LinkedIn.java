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

        Set<InnerVertex<user.User,?>> visited = new HashSet<>();
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
                        connectingList.add(opposite.getElement());
                    }

                }
            }

            if (levelNumber >= 5)
                break;

            level = nextLevel;
        }

        return connectingList;
    }

    public ArrayList<User> suggestedUsers(Map<String, Integer> priority) {
        ArrayList<User> connections = new ArrayList<>();
        connections = getConnections(adjacencyMapGraph.getVertex(user));
        SuggestedUser suggestedUser = new SuggestedUser(user, priority, connections);
        return suggestedUser.suggestedUsers();
    }

}
