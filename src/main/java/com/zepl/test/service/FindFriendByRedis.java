package com.zepl.test.service;

import com.zepl.test.exception.UserNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;


@Service
public class FindFriendByRedis implements FindFriend {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    public void addFriend(String a, String b) {
        redisTemplate.opsForSet().add(a, b);
        redisTemplate.opsForSet().add(b, a);
    }

    public void removeFriend(String a, String b) {
        redisTemplate.opsForSet().remove(a, b);
        redisTemplate.opsForSet().remove(b, a);
    }

    public String[] getFriends(String user) {
        Set<String> s = redisTemplate.opsForSet().members(user);
        return s.stream().toArray(String[]::new);
    }

    public boolean areFirstDegree(String a, String b) {
        if (!redisTemplate.hasKey(a)) throw new UserNotFoundException(a);
        if (!redisTemplate.hasKey(b)) throw new UserNotFoundException(b);
        return areFriend(a, b, 1);
    }

    public boolean areSecondDegree(String a, String b) {
        if (!redisTemplate.hasKey(a)) throw new UserNotFoundException(a);
        if (!redisTemplate.hasKey(b)) throw new UserNotFoundException(b);
        return areFriend(a, b, 2);
    }

    public boolean areThirdDegree(String a, String b) {
        if (!redisTemplate.hasKey(a)) throw new UserNotFoundException(a);
        if (!redisTemplate.hasKey(b)) throw new UserNotFoundException(b);
        return areFriend(a, b, 3);
    }

    public boolean areFriend(String a, String b, int degree) {
        // find friend of friend
        if (degree > 1) {
            for(Object friend : redisTemplate.opsForSet().members(a)) {
                String friendName = String.valueOf(friend);
                // remove circular edge
                if (a.equals(friendName)) continue;
                // exit condition, friend found
                if (areFriend(friendName, b, degree - 1)) return true;
            }
        }
        // last depth, find friend
        return redisTemplate.opsForSet().members(a).contains(b);
    }
}
