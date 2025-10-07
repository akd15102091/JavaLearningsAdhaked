package DesignSplitWiseLLD;

import java.util.*;

@SuppressWarnings("unused")
public class Group {
    private final String groupId;
    private final String name;
    private final List<User> members;
    private final Date createdAt;

    public Group(String name, List<User> members){
        this.groupId = UUID.randomUUID().toString();
        this.name = name;
        this.members = members;
        this.createdAt = new Date();
    }

    public String getGroupId(){
        return this.groupId;
    }

    public String getName(){
        return this.name;
    }
}
