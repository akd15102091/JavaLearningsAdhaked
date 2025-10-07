import java.util.*;

class Edge {
    int to;
    int weight;
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class NaryTree {
    public static void main(String[] args) {
        int N = 6;
        int k = 2;

        Map<Integer, List<Edge>> tree = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            tree.put(i, new ArrayList<>());
        }

        // Build the weighted tree
        addEdge(tree, 1, 2, 4);
        addEdge(tree, 1, 3, 3);
        addEdge(tree, 1, 4, 5);
        addEdge(tree, 3, 5, 2);
        addEdge(tree, 3, 6, 6);

        int root = 1;

        int maxWeight = maxTreeWeight(root, tree, k, N);
        System.out.println("Maximum Total Edge Weight: " + maxWeight);
    }

    static void addEdge(Map<Integer, List<Edge>> tree, int u, int v, int w) {
        tree.get(u).add(new Edge(v, w));
        tree.get(v).add(new Edge(u, w));
    }

    // You’ll define this function:
    static int maxTreeWeight(int src,  Map<Integer, List<Edge>> tree, int k, int N) {
        int totalNodes = N;
        boolean visited[] = new boolean[totalNodes + 1];

        return dfs(src, tree, k, visited);
    }

    private static int dfs(int src, Map<Integer, List<Edge>> tree, int k, boolean[] visited) {
        visited[src] = true;

        List<Integer> computedWeights = new ArrayList<>();

        for(int i=0; i<tree.get(src).size(); i++){
            Edge edge = tree.get(src).get(i);
            int to = edge.to;
            int weight = edge.weight;
            if(visited[to] == false){
                computedWeights.add(weight + dfs(to, tree, k, visited));
            }
        }

        Collections.sort(computedWeights, (a,b) -> b-a);
        int sum = 0;
        for(int i=0; i<k && i<computedWeights.size(); i++){
            sum += computedWeights.get(i);
        }

        return sum;
    }
}

