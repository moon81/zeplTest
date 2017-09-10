package com.zepl.test.model;

import java.util.Set;

public class User {
    private String name;

    // edge
    private Set<User> friends;

    public User(String name, Set<User> friends) {
        this.name = name;
        this.friends = friends;
    }

    // getter and setters
    public String getName() {
        return name;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public void setName(String name) {
        this.name = name;
    }
}
