package ru.markov.vkproject.vkapi;

import com.google.gson.JsonElement;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.groups.responses.GetMembersResponse;
import com.vk.api.sdk.queries.friends.FriendsGetOrder;
import com.vk.api.sdk.queries.groups.GroupsGetMembersSort;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.markov.vkproject.jsonparsers.JPUserFriend;
import ru.markov.vkproject.entity.UserF;

/*This is API for parsing data from VK. I write graduation work and use java for parsing. You can use js, php and others language 
 for java VK compani create sdk*/
public class UserFriendVKAPI {

    /*Method gets user from group VK. There is execute method. It uses when you need parse great groap.
    becouse standart method have border time and numbers*/
    public List<Integer> getMembers(String groupId, int offset, int count) {
        GetMembersResponse gmr = null;
        List<Integer> userID = new ArrayList<>();
        while (offset < count) {
            try {
                gmr = Authorization.initVkApiClient()
                        .groups()
                        .getMembers(Authorization.initUserActor())
                        .groupId(groupId)
                        .sort(GroupsGetMembersSort.ID_ASC)
                        .offset(offset)
                        .count(1000)
                        .execute();
                offset += 1000;
                System.out.println(gmr.toString());
                userID.addAll(gmr.getItems());
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userID;
    }

    
    
    public List<UserF> getMembersExecute(String groupId, int offset, int count) {
        JsonElement response = null;
        List<UserF> userID = new ArrayList<>();
        String user = "";
        while (offset < count) {
            try {
                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var off=0;\n"
                        + "var members;\n"
                        + "while(off<25000){\n"
                        + "members = members + API.groups.getMembers"
                        + "({\"group_id\": '" + groupId + "',"
                        + " \"v\": \"5.102\","
                        + " \"sort\": \"id_asc\","
                        + " \"count\": \"1000\","
                        + " \"offset\": (off+" + offset + ")}).items;\n"
                        + "off=off+1000;\n"
                        + "}\n"
                        + "return members; ").execute();
                offset += 25000;
                user = user + response.toString();
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        userID.addAll(new JPUserFriend().getMembersExsecute(user));
        return userID;
    }

    public int getCountMembers(String groupId) {
        GetMembersResponse gmr = null;
        try {
            gmr = Authorization.initVkApiClient()
                    .groups()
                    .getMembers(Authorization.initUserActor())
                    .groupId(groupId)
                    .sort(GroupsGetMembersSort.ID_ASC)
                    .offset(0)
                    .count(0)
                    .execute();
        } catch (ApiException ex) {
            System.out.println("Too many requests per second (6): Too many requests per second");
        } catch (ClientException ex) {
            Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return gmr.getCount();
    }

    public List<UserF> getFriends(List<Integer> users) {
        GetResponse gr = null;
        int i = 0;
        List<UserF> users1 = new ArrayList<>();
        while (i < users.size()) {

            try {
                gr = Authorization.initVkApiClient()
                        .friends()
                        .get(Authorization.initUserActor())
                        .userId(Integer.valueOf(users.get(i)))
                        .order(FriendsGetOrder.NAME)
                        .count(5000)
                        .offset(0)
                        .execute();
                List<Integer> us = gr.getItems();
                UserF user = new UserF();
                user.setId(String.valueOf(users.get(i)));
                user.setUsers(us);
                users1.add(user);
                i++;
                System.out.println("Size: " + users.size() + " / " + i);
            } catch (ApiException ex) {
                if (ex.getCode() == 15 || ex.getCode() == 18) {
                    i++;
                }
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                i++;
            }

        }
        return users1;
    }

    public List<UserF> getFriendsExecute(List<UserF> users) {
        JsonElement response = null;
        List<Integer> masFri = new ArrayList<>();
        JPUserFriend parser = new JPUserFriend();
        List<UserF> newFriends = new ArrayList<>();
        for (UserF user : users) {
            try {
                masFri.add(Integer.valueOf(user.getId()));

            } catch (NumberFormatException ex) {

            }
        }
        int counter = 0;
        while (counter < masFri.size()) {
            try {
                List<Integer> subMasFri = null;
                if (masFri.size() - counter > 24) {
                    subMasFri = masFri.subList(counter, counter + 24);
                } else {
                    subMasFri = masFri.subList(counter, masFri.size() - 1);
                }
                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var listOfObjects = [];\n"
                        + "int counter =0;\n"
                        + "var masFri = " + subMasFri.toString() + ";"
                        + "\n"
                        + "while(counter<25){\n"
                        + "var friends = API.friends.get({\n"
                        + "\"user_id\": masFri[counter],\n"
                        + "\"order\": \"name\",\n"
                        + "\"count\": '5000',\n"
                        + "\"offset\": '0'\n"
                        + "}).items;\n"
                        + "\n"
                        + "var singleObj = {\n"
                        + "\"user_id\": masFri[counter],\n"
                        + "\"repost\": friends\n"
                        + "};\n"
                        + "\n"
                        + "listOfObjects.push(singleObj);\n"
                        + "\n"
                        + "counter=counter+1;\n"
                        + "\n"
                        + "}\n"
                        + "\n"
                        + "return listOfObjects;").execute();
                newFriends.addAll(parser.getFriends(response.toString()));
                counter = counter + 24;
                System.out.println("Size: " + masFri.size() + "/" + counter);
            } catch (ApiException ex) {
                if (ex.getCode() == 29) {
                    newFriends.addAll(getFriends(masFri.subList(counter, masFri.size() - 1)));
                    counter = masFri.size();
                }
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newFriends;
    }

    public List<UserF> getFriendsIsMember(List<UserF> users, List<UserF> friends) {
        List<Integer> list = new ArrayList<>();
        users.forEach((user) -> {
            list.add(Integer.valueOf(user.getId()));
        });
        List<UserF> usersFriends = new ArrayList<>();
        for (UserF user : friends) {
            List<Integer> userList = null;
            if (user.getUsers() != null) {
                if (user.getUsers().size() != 0) {
                    userList = new ArrayList<>();
                    for (Integer user1 : user.getUsers()) {
                        if (list.contains(user1)) {
                            userList.add(user1);
                        }
                    }
                }
            }
            if (userList != null) {
                if (userList.size() > 0) {
                    UserF userOne = new UserF();
                    userOne.setId(user.getId());
                    userOne.setUsers(userList);
                    usersFriends.add(userOne);
                }
            }
        }
        return usersFriends;
    }

//    public List<User> getFriendsIsMember(List<User> users, List<User> friends) {
//        List<Integer> list = new ArrayList<>();
//        users.forEach((user) -> {
//            list.add(Integer.valueOf(user.getId()));
//        });
//        List<User> usersFriends = new ArrayList<>();
//        friends.stream().map((friend) -> {
//            List<Integer> listFriends = new ArrayList<>();
//            friend.getUsers().stream().filter((user) -> (list.contains(user))).forEachOrdered((user) -> {
//                listFriends.add(user);
//            });
//            User user = new User();
//            user.setId(friend.getId());
//            user.setUsers(listFriends);
//            return user;
//        }).forEachOrdered((user) -> {
//            usersFriends.add(user);
//        });
//        return usersFriends;
//    }
//    public List<User> getFriendsIsMember(String groupId, List<User> member) {
//        JsonElement response = null;
//        List<User> friendsIsMembers = new ArrayList<>();
//        JsonParser parser = new JsonParser();
//        for (int i = 0; i < member.size();) {
//            try {
//                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
//                        "var friends = API.friends.get({\n"
//                        + "	\"user_id\": '" + member.get(i).getId() + "',\n"
//                        + "	\"order\": \"name\",\n"
//                        + "	\"count\": '5000',\n"
//                        + "	\"offset\": '0'\n"
//                        + "}).items;\n"
//                        + "\n"
//                        + "var size = friends.length;\n"
//                        + "var count = 0;\n"
//                        + "var ismember = null;\n"
//                        + "var member = null;\n"
//                        + "var user_id = null;\n"
//                        + "\n"
//                        + "while(count < size){\n"
//                        + "if((count+500)>size){\n"
//                        + "	ismember=API.groups.isMember({\n"
//                        + "	\"group_id\": '" + groupId + "',\n"
//                        + "	\"user_ids\": friends.slice(count, size),\n"
//                        + "	\"extended\": '0'\n"
//                        + "	});\n"
//                        + "}else{\n"
//                        + "	ismember = ismember + API.groups.isMember({\n"
//                        + "	\"group_id\": '" + groupId + "',\n"
//                        + "	\"user_ids\": friends.slice(count, count+500),\n"
//                        + "	\"extended\": '0'\n"
//                        + "	});\n"
//                        + "}\n"
//                        + "member = member + ismember@.member;\n"
//                        + "user_id = user_id + ismember@.user_id;\n"
//                        + "count=count+500;\n"
//                        + "}\n"
//                        + "\n"
//                        + "return  {\"member\": member, \"user_id\": user_id};").execute();
//                if (parser.membersOrUserIdIsNull(response.toString())) {
//                    friendsIsMembers = parser.getFriendsIsMemberExsecute(response.toString(), friendsIsMembers, member.get(i).getId());
//                    System.out.println("Size: " + member.size() + " / " + i);
//                }
//                i++;
//            } catch (ApiException ex) {
////                System.out.println(ex.getMessage());
////                System.out.println("Too many requests per second (6): Too many requests per second");
//            } catch (ClientException ex) {
//                Logger.getLogger(VKAPI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return friendsIsMembers;
//    }
}
