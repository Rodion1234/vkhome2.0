
package ru.markov.vkproject.vkapi;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.SearchResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.markov.vkproject.entity.Group;




public class GroupVKAPI {

    
    public List<Group> searchGroup(String q) {
        SearchResponse searchResponse = null;
        List<Group> groups = new ArrayList<>();
            try {
                searchResponse = Authorization.initVkApiClient()
                        .groups()
                        .search(Authorization.initUserActor(), q)
                        .count(100)
                        .execute();
                
                
                int i =0;
                int count =100;
                for (com.vk.api.sdk.objects.groups.Group item : searchResponse.getItems()) {
                    Group group = new Group();
                    
                    group.setId(item.getId());
                    group.setName(item.getName());
                    group.setCountMembers(new UserDataVKAPI().getCountMembers(String.valueOf(item.getId())));
                    groups.add(group);
                    i++;
                    System.out.println("searchGroup size: " + count+" / " + i);
                }
    
                
            } catch (ApiException ex) {
                System.out.println("Too many requests per second (6): Too many requests per second");
            } catch (ClientException ex) {
                Logger.getLogger(UserDataVKAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return groups;
    }
    
}
