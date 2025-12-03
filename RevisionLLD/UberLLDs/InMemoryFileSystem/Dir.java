package UberLLDs.InMemoryFileSystem;

import java.util.HashMap;
import java.util.Map;

public class Dir {
    String name;
    Dir parent;
    Map<String, Dir> children = new HashMap<>();

    Dir(String name, Dir parent) {
        this.name = name;
        this.parent = parent;
    }
}
