package com.zepl.test.service;

import com.zepl.test.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class FindFriendByHashSet implements FindFriend {

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<String, Set<String>> userAndFriends = new HashMap<>();

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

    public void addFriend(String user, String friend) {
        connect(user, friend);
        connect(friend, user);
    }

    public void removeFriend(String user, String friend) {
        disconnect(user, friend);
        disconnect(friend, user);
    }


    public String[] getFriends(String user) {
        return userAndFriends.get(user).stream().toArray(String[]::new);
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
        // find friend of friend
        if (degree > 1) {
            for(String friend : userAndFriends.get(a)) {
                // remove circular edge
                if (a.equals(friend)) continue;
                if (userAndFriends.get(a).contains(b)) return true;
                // exit condition, friend found
                if (areFriend(friend, b, degree - 1)) return true;
            }
        }
        // last depth, find friend
        return userAndFriends.get(a).contains(b);
    }

    //--------------------------------------------------------------------------------------------------------------//
    // not in spec, but
    // Find Friend on DFS, BFS
    //

    public Map<Integer,String> friendPath(String a, String b, int maxDegree) {
        // null check
        if (userAndFriends.get(a) == null) throw new UserNotFoundException(a);
        if (userAndFriends.get(b) == null) throw new UserNotFoundException(b);
        Map<Integer, String> degreeAndUser = new LinkedHashMap<>();

        return findFriendDFS(a, b, maxDegree, degreeAndUser);
    }

    public Map<Integer,String> findFriendDFS(String a, String b, int degree, Map<Integer, String> degreeAndUser) {
        if (userAndFriends.get(a).contains(b)) {
            degreeAndUser.put(degree -1, b);
            degreeAndUser.put(degree, a);
            return degreeAndUser;
        }
        // find friend of friend
        if (degree > 1) {
            for(String friend : userAndFriends.get(a)) {
                // remove circular edge
                if (a.equals(friend)) continue;
                // exit condition, friend found
                if (findFriendDFS(friend, b, degree - 1, degreeAndUser) != null) {
                    degreeAndUser.put(degree, a);
                    return degreeAndUser;
                }
            }
        }
        // Cannot find
        return null;
    }

    public Integer checkDepthToFriend(String a, String b, int degree) {
        Set<String> friends = userAndFriends.get(a);
        for(int depth = 0; depth < degree; depth++) {
            if (friends.contains(b)) {
                return depth;
            } else {
                Set<String> nextLevelFriends = new HashSet<>();
                for (String friend : friends) {
                    nextLevelFriends.addAll(userAndFriends.get(friend));
                }
                nextLevelFriends.removeAll(friends);
                friends = nextLevelFriends;
            }
        }
        // a is not b's friend, in degree
        return null;
    }

    private void connect(String user, String friend) {
        Set<String> friends = userAndFriends.get(user);
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
        userAndFriends.put(user, friends);
    }

    private void disconnect(String user, String friend) {
        Set<String> friends = userAndFriends.get(user);
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.remove(friend);
        userAndFriends.put(user, friends);
    }
}
