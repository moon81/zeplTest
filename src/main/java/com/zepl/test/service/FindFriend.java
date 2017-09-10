package com.zepl.test.service;

public interface FindFriend {
    void addFriend(String a, String b);
    void removeFriend(String a, String b);

    String[] getFriends(String user);
    // Return true if 'a' is a friend of 'b'
    boolean areFirstDegree(String a, String b);
    // Return true if 'a' is a friend of a friend of 'b'
    boolean areSecondDegree(String a, String b);
    // Return true if 'a' is a friend of a friend of a friend of 'b'
    boolean areThirdDegree(String a, String b);

    boolean areFriend(String a, String b, int degree);

}
