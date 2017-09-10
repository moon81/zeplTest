package com.zepl.test.controller;

import com.zepl.test.service.FindFriend;
import com.zepl.test.service.FindFriendByGraph;
import com.zepl.test.service.FindFriendByHashSet;
import com.zepl.test.service.FindFriendByRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class APIController {

    @Autowired
    private FindFriendByGraph findFriendByGraph;

    @Autowired
    private FindFriendByHashSet findFriendByHashSet;

    @Autowired
    private FindFriendByRedis findFriendByRedis;

    @RequestMapping("/list")
    @ResponseBody
    public String[] list(@RequestParam("user") String user) {
        return findFriendByHashSet.getFriends(user);
    }

    @RequestMapping("/find")
    @ResponseBody
    public Map<String, Object> find(@RequestParam("a") String a,
                                    @RequestParam("b") String b,
                                    @RequestParam("method") String method,
                                    @RequestParam("degree") int degree) {
        Map<String, Object> result = new HashMap<>();

        FindFriend service;
        boolean areFriend;
        switch (method) {
            case "graph":
                service = findFriendByGraph;
                break;
            case "redis":
                service = findFriendByRedis;
                break;
            default:
                service = findFriendByHashSet;
                break;
        }

        long now = System.currentTimeMillis();
        switch (degree) {
            case 1:
                areFriend = service.areFirstDegree(a, b);
                break;
            case 2:
                areFriend = service.areSecondDegree(a, b);
                break;
            case 3:
                areFriend = service.areThirdDegree(a, b);
                break;
            default:
                // greater than 3..
                areFriend = service.areFriend(a, b, degree);
                break;
        }
        long duration = System.currentTimeMillis() - now;

        result.put("areFriend", areFriend);
        result.put("duration", duration);

        return result;
    }
}
