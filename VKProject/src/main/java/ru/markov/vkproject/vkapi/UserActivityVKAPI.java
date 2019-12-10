package ru.markov.vkproject.vkapi;

import com.google.gson.JsonElement;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.likes.responses.GetListResponse;
import com.vk.api.sdk.objects.wall.WallComment;
import com.vk.api.sdk.objects.wall.responses.GetCommentsResponse;
import com.vk.api.sdk.queries.likes.LikesGetListFilter;
import com.vk.api.sdk.queries.likes.LikesType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.markov.vkproject.jsonparsers.JPActivity;
import ru.markov.vkproject.entity.Comment;
import ru.markov.vkproject.entity.Like;
import ru.markov.vkproject.entity.Repost;

public class UserActivityVKAPI {

    public List<Comment> getComments(Integer owner_id, Integer post_id, Integer preview_length) {

        List<Comment> comments = new ArrayList<>();
        int offset = 0;
        GetCommentsResponse gcr;
        int count =1;
        while (offset < count) {
            try {
                gcr = Authorization.initVkApiClient()
                        .wall()
                        .getComments(Authorization.initUserActor(), post_id)
                        .ownerId(owner_id)
                        .previewLength(preview_length)
                        .count(100)
                        .offset(offset)
                        .execute();
                List<WallComment> wallComments = gcr.getItems();
                count = gcr.getCount();
                comments.addAll(new JPActivity().getComments(wallComments, owner_id, post_id));
                offset += 100;
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return comments;
    }

    public List<Like> getLike(Integer owner_id, Integer item_id, LikesType type) {

        GetListResponse glr;
        JsonElement response = null;
        int count = 0, offset = 0;
        String like = "";
        try {
            glr = Authorization.initVkApiClient()
                    .likes()
                    .getList(Authorization.initUserActor(), type)
                    .ownerId(owner_id)
                    .itemId(item_id)
                    .filter(LikesGetListFilter.LIKES)
                    .friendsOnly(Boolean.FALSE)
                    .offset(0)
                    .count(1)
                    .execute();
            count = glr.getCount();
        } catch (ApiException ex) {
            System.out.println(ex.getMessage());
        } catch (ClientException ex) {
            Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (offset < count) {
            try {

                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var like;\n"
                        + "var off =0;\n"
                        + "while(off<25000){\n"
                        + "like= like + API.likes.getList({\n"
                        + "\"type\": \"post\",\n"
                        + "\"owner_id\": \"" + owner_id + "\",\n"
                        + "\"item_id\": '" + item_id + "',\n"
                        + "\"filter\": \"likes\",\n"
                        + "\"friends_only\": '0',\n"
                        + "\"extended\": '0',\n"
                        + "\"friends_only\": '0',\n"
                        + "\"count\": '1000',\n"
                        + "\"offset\": (off+" + offset + ")\n"
                        + "}).items;\n"
                        + "off =off+1000;\n"
                        + "}\n"
                        + "return like;"
                ).execute();
                offset += 25000;
                like = like + response.toString();
            } catch (ApiException ex) {
                System.out.println(ex.getMessage());
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new JPActivity().getLike(like, owner_id, item_id);
    }

    public List<Repost> getRepost(Integer owner_id, Integer item_id, LikesType type) {
        List<Like> likes = getLike(owner_id, item_id, type);
        JsonElement response = null;
        List<Repost> reposts = new ArrayList<>();
        List<Integer> userId = new ArrayList<>();

        for (Like like : likes) {
            userId.add(like.getUser_id());
        }
        int counter = 0;
        List<Integer> newList = null;
        while (counter < userId.size()) {
            if (userId.size() - 1 - counter < 24) {
                newList = userId.subList(counter, userId.size() - 1);
            } else {
                newList = userId.subList(counter, counter + 24);
            }
            try {
                response = Authorization.initVkApiClient().execute().code(Authorization.initUserActor(),
                        "var count = 0;\n"
                        + "var repost;\n"
                        + "var listOfObjects = [];\n"
                        + "var mas = " + newList.toString() + ";\n"
                        + "while(count<25){\n"
                        + "	repost = API.wall.get\n"
                        + "	({\"owner_id\": mas[count],\n"
                        + "	\"v\": \"5.103\",\n"
                        + "	\"filter\": \"owner\",\n"
                        + "	\"count\": \"100\",\n"
                        + "	\"offset\": (0),\n"
                        + "	\"extended\": '1'}).items;\n"
                        + "	\n"
                        + "	var singleObj = {\n"
                        + "	\"user_id\":mas[count],\n"
                        + "	\"repost\": repost\n"
                        + "	};\n"
                        + "	listOfObjects.push(singleObj);\n"
                        + "	count=count+1;\n"
                        + "}\n"
                        + "\n"
                        + "return listOfObjects;"
                ).execute();
                counter = counter + 24;
//                System.out.println(response.toString());
                reposts.addAll(new JPActivity().getRepost(response.toString(), owner_id, item_id));
            } catch (ApiException ex) {
                System.out.println(ex.getMessage());
            } catch (ClientException ex) {
                Logger.getLogger(UserFriendVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return reposts;
    }
}
