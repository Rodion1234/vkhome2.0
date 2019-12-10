package ru.markov.vkproject.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.markov.vkproject.entity.UserF;

public class JPUserFriend {

    public int getNumberUser(String json) {

        JSONObject obj = new JSONObject(json);
        int count = obj.getJSONObject("response").getInt("count");
        
        return count;
    }

//    public List<User> getMembers(String json) {
//
//        List<User> users = new ArrayList<>();
//
//        JSONObject obj = new JSONObject(json);
//        JSONObject response = obj.getJSONObject("response");
//
//        JSONArray arr = response.getJSONArray("items");
//        for (int i = 0; i < arr.length(); i++) {
//            users.add(new User(arr.getString(i)));
//        }
//
//        return users;
//    }
    public List<UserF> getMembersExsecute(String response) {

        List<UserF> users = new ArrayList<>();

        response = response.replace("\"", "");
        String[] user = response.split(",");

        for (int i = 0; i < user.length; i++) {
            users.add(new UserF(user[i]));
        }

        return users;
    }

    public List<UserF> getFriends(String response) {
        List<UserF> users = new ArrayList<>();
        JSONArray arr = new JSONArray(response);
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            UserF user = null;
            if (!obj.isNull("user_id")) {
                if (!obj.isNull("repost")) {
                    user = new UserF();
                    user.setId(String.valueOf(obj.getInt("user_id")));
                    List<Integer> list = new ArrayList<>();
                    JSONArray arr1 = obj.getJSONArray("repost");
                    for (int j = 0; j < arr1.length(); j++) {
                        list.add(arr1.getInt(j));
                    }
                    user.setUsers(list);
                }
            }
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public boolean getError(String json) {

        if (!json.contains("error")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean membersOrUserIdIsNull(String json) {

        if (!json.contains("null")) {
            return true;
        } else {
            return false;
        }
    }
}
