package ru.markov.vkproject;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.File;
import java.util.ArrayList;

import java.util.List;
import ru.markov.vkproject.database.FileReaderDB;
import ru.markov.vkproject.database.FileWriterDB;
import ru.markov.vkproject.entity.Group;
import ru.markov.vkproject.entity.UserF;
import ru.markov.vkproject.vkapi.GroupVKAPI;
import ru.markov.vkproject.vkapi.UserFriendVKAPI;

public class MainApp {

    public static void main(String[] args) throws ApiException, ClientException {

        List<Group> groups = new GroupVKAPI().searchGroup("команда навального");
        FileWriterDB fileWriterDB = new FileWriterDB();
        fileWriterDB.writeGroups(groups, new File("C:\\\\Users\\\\User\\\\Desktop\\\\VKProject\\\\vkapi v3\\\\vkhome2.0\\\\БД\\\\first try\\\\groups.csv"));
//        try {
//            WorkDB dB = new WorkDB();
//        UserFriendVKAPI vkapi = new UserFriendVKAPI();
//        FileWriterDB fileWriterDB = new FileWriterDB("C:\\Users\\rodion\\Desktop\\vk home\\vktest\\БД\\first try\\friends.csv");
//        List<UserF> users1 = vkapi.getMembersExecute("142989213", 0, vkapi.getCountMembers("142989213"));
//        List<UserF> user2 = vkapi.getFriendsExecute(users1);
//        System.out.println("Size - " + user2.size());
//        for (UserF user : user2) {
//            System.out.println(user);
//        }
//        fileWriterDB.writeUsers(users1, "163692928");
//        List<Integer> list = vkapi.getFriends(232964357);
//        
//        for (Integer integer : list) {
//            System.out.println(integer);
//        }
//            int i = 0;
//            i = dB.setEmployFriends(user, "142989213", i);
//142989213
//140293827
//142970331
//            List<User> users = vkapi.getFriendsIsMember("36528739", user);
//
//            dB.setEmployFriends(users, "36528739", i);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
