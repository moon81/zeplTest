package com.zepl.test.service;

import com.zepl.test.exception.UserNotFoundException;
import com.zepl.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FindFriendByGraph implements FindFriend {

    @Autowired
    private ResourceLoader resourceLoader;

    // edge map
    private Map<String, User> userMap = new HashMap<>();

    @PostConstruct
	private void init() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/friends.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            String[] users = line.split(",");
            addFriend(users[0], users[1]);
        }
    }

    public void addFriend(String a, String b) {
        User userA = userMap.get(a);
        if (userA == null) userA = new User(a, new HashSet<>());
        User userB = userMap.get(b);
        if (userB == null) userB = new User(b, new HashSet<>());

        connect(userA, userB);
        connect(userB, userA);
    }

    public void removeFriend(String a, String b) {
        User userA = userMap.get(a);
        if (userA == null) userA = new User(a, new HashSet<>());
        User userB = userMap.get(b);
        if (userB == null) userB = new User(b, new HashSet<>());

        disconnect(userA, userB);
        disconnect(userB, userA);
    }

    public String[] getFriends(String user) {
        Set<User> s = userMap.get(user).getFriends();
        return s.stream().map(u -> u.getName()).toArray(String[]::new);
    }

    public boolean areFirstDegree(String a, String b) {
        return areFriend(a, b, 1);
    }

    public boolean areSecondDegree(String a, String b) {
        return areFriend(a, b, 2);
    }

    public boolean areThirdDegree(String a, String b) {
        return areFriend(a, b, 3);
    }

    public boolean areFriend(String a, String b, int degree) {
        if (userMap.get(a) == null) throw new UserNotFoundException(a);
        if (userMap.get(b) == null) throw new UserNotFoundException(b);

        return areFriend(userMap.get(a), userMap.get(b), degree);
    }

    private boolean areFriend(User a, User b, int degree) {
        // loop friend in friend
        if (degree > 1) {
            for(User friend : a.getFriends()) {
                // remove circular edge
                if (a.equals(friend)) continue;
                // exit condition, friend found
                if (a.getFriends().contains(b)) return true;
                if (areFriend(friend, b, degree - 1)) return true;
            }
        }
        // last depth, find friend
        return a.getFriends().contains(b);
    }

    private void connect(User a, User b) {
        Set<User> friendSet = a.getFriends();
        friendSet.add(b);
        a.setFriends(friendSet);
        userMap.put(a.getName(), a);
    }

    private void disconnect(User a, User b) {
        Set<User> friendSet = a.getFriends();
        friendSet.remove(b);
        a.setFriends(friendSet);
    }
}
