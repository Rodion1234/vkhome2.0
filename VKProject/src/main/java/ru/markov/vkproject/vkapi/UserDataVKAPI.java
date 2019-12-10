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

import org.json.JSONArray;
import org.json.JSONObject;
import ru.markov.vkproject.entity.User;
import ru.markov.vkproject.jsonparsers.JPUserData;

/*This is API for parsing data from VK. I write graduation work and use java for parsing. You can use js, php and others language 
 for java VK compani create sdk*/
public class UserDataVKAPI {

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
                Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userID;
    }

    public List<User> getMembersExecute(String groupId, int offset, int count) {
        JsonElement response = null;
        List<User> userID = new ArrayList<>();
        String user = "";
        while (offset < count) {
            try {
                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var off=0;\n"
                        + "var members = API.groups.getMembers\n"
                        + "({\"group_id\": '" + groupId + "',\n"
                        + "\"v\": \"5.103\",\n"
                        + "\"sort\": \"id_asc\",\n"
                        + "\"count\": \"1000\",\n"
                        + "\"offset\": (off+" + offset + "),\n"
                        + "\"fields\": \"sex, bdate, city, country\"}).items;\n"
                        + "off=off+1000;\n"
                        + "while(off<10000){\n"
                        + "members = members+API.groups.getMembers\n"
                        + "({\"group_id\": '" + groupId + "',\n"
                        + "\"v\": \"5.103\",\n"
                        + "\"sort\": \"id_asc\",\n"
                        + "\"count\": \"1000\",\n"
                        + "\"offset\": (off+" + offset + "),\n"
                        + "\"fields\": \"sex, bdate, city, country\"}).items;\n"
                        + "off=off+1000;\n"
                        + "}\n"
                        + "\n"
                        + "return members;").execute();
                offset += 10000;
                user = user + response.toString();
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return new JPUserData().getMembersExsecute(user);
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
            Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return gmr.getCount();
    }

    public List<Integer> getFriends(Integer userId) {
        GetResponse gr = null;
        try {
            gr = Authorization.initVkApiClient()
                    .friends()
                    .get(Authorization.initUserActor())
                    .userId(userId)
                    .order(FriendsGetOrder.NAME)
                    .count(5000)
                    .offset(0)
                    .execute();
            System.out.println(gr.getCount());
        } catch (ApiException ex) {
            System.out.println("Too many requests per second (6): Too many requests per second");
        } catch (ClientException ex) {
            Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gr.getItems();
    }

    public List<String> getPhoneUser(String groupId, int offset, int count) {
        JsonElement response = null;
        List<String> users = new ArrayList<>();
        String user = "";
        while (offset < count) {
            try {
                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var off=0;\n"
                        + "var members = API.groups.getMembers\n"
                        + "({\"group_id\": '" + groupId + "',\n"
                        + "\"v\": \"5.103\",\n"
                        + "\"sort\": \"id_asc\",\n"
                        + "\"count\": \"1000\",\n"
                        + "\"offset\": (off+" + offset + "),\n"
                        + "\"fields\": \"sex, bdate, city, country\"}).items;\n"
                        + "off=off+1000;\n"
                        + "while(off<5000){\n"
                        + "members = members+API.groups.getMembers\n"
                        + "({\"group_id\": '" + groupId + "',\n"
                        + "\"v\": \"5.103\",\n"
                        + "\"sort\": \"id_asc\",\n"
                        + "\"count\": \"1000\",\n"
                        + "\"offset\": (off+" + offset + "),\n"
                        + "\"fields\": \"sex, bdate, city, country, photo_50, photo_100, photo_200_orig, photo_200, photo_400_orig, photo_max, photo_max_orig, online, online_mobile, lists, domain, has_mobile, contacts, connections, site, education, universities, schools, can_post, can_see_all_posts, can_see_audio, can_write_private_message, status, last_seen, common_count, relation, relatives\"}).items;\n"
                        + "off=off+1000;\n"
                        + "}\n"
                        + "\n"
                        + "return members;").execute();
                offset += 5000;
                users.addAll(getPhoneList(response.toString()));
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return users;
    }

    public List<String> getPhoneList(String response) {

        List<String> users = new ArrayList<>();

        JSONArray arr = new JSONArray(response.replace("][", ","));
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            String user = "";

            if (obj.has("home_phone")) {
                String number = obj.getString("home_phone");
                number = number.replaceAll("[^0-9]", "");
                number = number.replaceAll(" ", "");
                number = number.replaceAll("-", "");
                number = number.replaceAll("\\(", "");
                number = number.replaceAll("\\)", "");
                if (number.length() >= 11 && number.length() <= 12) {
                    user = user + obj.getString("home_phone") + " ";
                }
            }
            if (obj.has("mobile_phone")) {
                String number = obj.getString("mobile_phone");
                number = number.replaceAll("[^0-9]", "");
                number = number.replaceAll(" ", "");
                number = number.replaceAll("-", "");
                number = number.replaceAll("\\(", "");
                number = number.replaceAll("\\)", "");
                if (number.length() >= 11 && number.length() <= 12) {
                    user = user + obj.getString("mobile_phone");
                }
            }

            if (user != "") {
                user = obj.getString("first_name") + " " + obj.getString("last_name") + "   " + user;
                users.add(user);
            }

        }
        return users;
    }
}
