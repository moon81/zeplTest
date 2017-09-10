# Friend Graph

# Web URL
http://ec2-13-124-175-134.ap-northeast-2.compute.amazonaws.com:8080/

# Data Set
Richard , Zuny, Anthony, Mina, Mark and about 4,000 names as user. ( names.txt )  
Each user have a 11 friend.  
And this relation was abuout 47,000. ( friend.txt )  
But, COUPLE1 and COUPLE2 has no friend except each other.  
It aims to worst case test. 

# Implement
## FindFriendByGraph
Just like graph algorithm.  
`User = Vertex, 
Edge = Friend.`   
This is same as undirected graph.  
And friend, friend of friend method is similar to DFS.  

## FindFriendByHashSet
If I received this condition at work. I think I will implement as hashset.  
User's another information is not necessary, then it is same as hash set.  
I like small request, and quick implement, just like prototyping.  
So I prepared this way.

## FindFriendByRedis
Service and datasource should be departed.  
If service server restart, or deploy, then memory's data will trash.  
And If web server's number is greater than 1, It will be a problem, too.  
Server's memory based approach is very fast to run, but it is very risky way.  
So, I use the redis.  
Redis provide set datastructure, so this is suitable for our service.  
Redis's data initialization will be once, and it just run below script, in redis-server.  

`cat redis_init_data.txt | redis-cli --pipe`

# Performance
degree, Unreachable friend find.

degree | Graph | Hash | Redis
------ | ----- | ---- | -----
1 | 0 | 0 | 30
2 | 0 | 0 | 330
3 | 0 | 1 | 6300
4 | 2 | 3 | 14000
  

# Future Work
As a matter of fact, redis is not good at find friend.  
So If we should deal with large number of user, then we need to use graph DB.  
I have no experience on graph db, so if I have to find friend. to recommendation.  
Then I'll find suitable graph db, with benchmark test.