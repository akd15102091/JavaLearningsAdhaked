package DesignStackOverflow;

import java.util.UUID;

@SuppressWarnings({"unused"})
public class Tag {
    private String id;
    private String name;

    public Tag(String name) {
        this.name = name;
        this.id = UUID.randomUUID().toString() ;
    }

    public String getName(){
        return name;
    }
}
