package UberLLDs.InMemoryFileSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FileSystem {
    // Root and current working directory
    private final Dir root;
    private Dir current;

    public FileSystem() {
        root = new Dir("/", null);
        current = root;
    }

    // -------------- mkdir --------------
    // Creates directory along a given path
    public void mkdir(String path) {
        String[] parts = path.split("/");
        Dir node = path.startsWith("/") ? root : current;

        for (String p : parts) {
            if (p.isEmpty() || p.equals(".")) continue;

            if (p.equals("..")) {
                if (node.parent != null) node = node.parent;
                continue;
            }

            node.children.putIfAbsent(p, new Dir(p, node));
            node = node.children.get(p);
        }
    }


    // -------------- pwd --------------
    public String pwd() {
        if (current == root) return "/";

        LinkedList<String> names = new LinkedList<>();
        Dir node = current;

        while (node != null && node != root) {
            names.addFirst(node.name);
            node = node.parent;
        }
        return "/" + String.join("/", names);
    }

    public boolean cd(String path) {
        String[] parts = path.split("/");

        // If absolute path -> start from root
        // If relative path -> start from current directory
        Dir start = path.startsWith("/") ? root : current;

        // Use helper DFS function to resolve wildcard possibilities
        Dir result = resolve(start, parts, 0);

        if (result == null) return false;
        current = result;
        return true;
    }


    /**
     * Recursive resolver for cd wildcard:
     *
     * At each token:
     *   "."   -> stay at same directory
     *   ".."  -> go to parent (if exists)
     *   "X"   -> go to child "X" if exists
     *   "*"   -> try ".", then "..", then each child (in lexicographic order)
     *
     * Returns the directory reached after fully matching the path.
     * Returns null if no valid path exists.
     */
    private Dir resolve(Dir node, String[] parts, int idx) {

        // BASE CASE: all path tokens consumed
        if (idx == parts.length) return node;

        String token = parts[idx];

        // Skip empty "" or "."
        if (token.isEmpty() || token.equals(".")) {
            return resolve(node, parts, idx + 1);
        }

        // Handle ".."
        if (token.equals("..")) {
            if (node.parent == null) return null;
            return resolve(node.parent, parts, idx + 1);
        }

        // Handle "*"
        if (token.equals("*")) {

            // 1. Try staying in the same directory (".")
            Dir stay = resolve(node, parts, idx + 1);
            if (stay != null) return stay;

            // 2. Try going to parent ("..")
            if (node.parent != null) {
                Dir up = resolve(node.parent, parts, idx + 1);
                if (up != null) return up;
            }

            // 3. Try each child (sorted for deterministic behavior)
            List<String> sortedChildren = new ArrayList<>(node.children.keySet());
            Collections.sort(sortedChildren);

            for (String childName : sortedChildren) {
                Dir child = node.children.get(childName);
                Dir next = resolve(child, parts, idx + 1);
                if (next != null) return next;
            }

            // no valid option in wildcard
            return null;
        }

        // Normal directory name
        if (!node.children.containsKey(token)) return null;

        return resolve(node.children.get(token), parts, idx + 1);
    }

}
