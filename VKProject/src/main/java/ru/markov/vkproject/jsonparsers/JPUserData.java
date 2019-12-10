/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.markov.vkproject.jsonparsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.markov.vkproject.entity.User;

public class JPUserData {

    public int getNumberUser(String json) {

        JSONObject obj = new JSONObject(json);
        int count = obj.getJSONObject("response").getInt("count");

        return count;
    }

    public List<User> getMembersExsecute(String response) {

        List<User> users = new ArrayList<>();

        JSONArray arr = new JSONArray(response.replace("][", ","));
        for (int i = 0; i < arr.length(); i++) {
            User user = new User();
            JSONObject obj = arr.getJSONObject(i);
            user.setId(obj.getInt("id"));
            user.setFirstName(obj.getString("first_name"));
            user.setLastName(obj.getString("last_name"));
            if (obj.has("sex")) {
                user.setSex(obj.getInt("sex"));
            }
            if (obj.has("bdate")) {

                String bdate[] = obj.getString("bdate").split("\\.");
                if (bdate.length == 3) {
                    user.setbDateYear(Integer.valueOf(bdate[2]));
                }
            }
            if (obj.has("city")) {
                user.setCity(obj.getJSONObject("city").getString("title"));
            }
            if (obj.has("country")) {
                user.setCountry(obj.getJSONObject("country").getString("title"));
            }

            users.add(user);
        }
        return users;
    }

}
