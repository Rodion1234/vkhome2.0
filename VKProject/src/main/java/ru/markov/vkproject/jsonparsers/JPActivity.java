/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.markov.vkproject.jsonparsers;

import com.vk.api.sdk.objects.wall.WallComment;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.markov.vkproject.entity.Comment;
import ru.markov.vkproject.entity.Like;
import ru.markov.vkproject.entity.Repost;

/**
 *
 * @author rodion
 */
public class JPActivity {
    
    public List<Comment> getComments(List<WallComment> wallComments, Integer owner_id, Integer item_id) {
        List<Comment> comments = new ArrayList<>();
        for (WallComment wallComment : wallComments) {
            Comment comment = new Comment();

            comment.setFrom_id(wallComment.getFromId());
            comment.setOwner_id(owner_id);
            comment.setItem_id(item_id);
            comment.setDate(new java.util.Date((long) wallComment.getDate() * 1000));
            comment.setText(wallComment.getText());

            comments.add(comment);
        }
        return comments;
    }

    public List<Like> getLike(String response, Integer owner_id, Integer item_id) {
        List<Like> likes = new ArrayList();

        String[] like = response.replace("\"", "").split(",");

        for (int i = 0; i < like.length; i++) {
            Like like1 = new Like();
            like1.setOwner_id(owner_id);
            like1.setItem_id(item_id);
            like1.setUser_id(Integer.valueOf(like[i]));
            likes.add(like1);
        }
        return likes;
    }

    public List<Repost> getRepost(String response, Integer owner_id, Integer item_id) {
        JSONArray arr = new JSONArray(response);
        List<Repost> reposts = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = arr.getJSONObject(i);
            Repost repost = null;
            if (!object.isNull("repost")) {

                JSONArray arrReposts = object.getJSONArray("repost");
                for (int j = 0; j < arrReposts.length(); j++) {
                    JSONObject object1 = arrReposts.getJSONObject(j);
                    if (object1.has("copy_history")) {
                        JSONArray arrRepost = object1.getJSONArray("copy_history");
                        for (int k = 0; k < arrRepost.length(); k++) {
                            JSONObject object2 = arrRepost.getJSONObject(k);
                            if (object2.has("id")) {
                                if (object2.getInt("id") == item_id) {
                                    repost = new Repost();
                                    repost.setItem_id(item_id);
                                    repost.setOwner_id(owner_id);
                                }
                            }
                        }
                    } else {
                        break;
                    }
                    if (repost != null) {
                        repost.setDate(new java.util.Date((long) object1.getInt("date") * 1000));
                    }
                }
                if (repost != null) {
                    if (!object.isNull("user_id")) {
                        repost.setUser_id(object.getInt("user_id"));
                    }
                    boolean flag = true;
                    for (Repost repost1 : reposts) {
                        if (repost.getUser_id() != null) {
                            if (repost.getUser_id().equals(repost1.getUser_id())) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == true) {
                        reposts.add(repost);
                    }
                }
            }
        }
        return reposts;
    }

    public boolean getError(String json) {

        if (!json.contains("error")) {
            return true;
        } else {
            return false;
        }

    }
    
}
