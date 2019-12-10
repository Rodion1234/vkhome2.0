/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.markov.vkproject.vkapi;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import java.util.Scanner;

/**
 *
 * @author rodion
 */
public class Authorization {

    
    private static UserActor actor = null;
    private static VkApiClient vk = null;
    private Authorization() {
    }
    
    public static VkApiClient initVkApiClient(){
        if(vk==null){
            TransportClient transportClient = HttpTransportClient.getInstance();
            vk = new VkApiClient(transportClient);
            return vk;
        }else return vk;
    }

    public static UserActor initUserActor() throws ApiException, ClientException {
        if (actor == null) {
            initVkApiClient();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Insert code:");
            String code = scanner.nextLine();
            UserAuthResponse authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(0, "", "", code)
                    .execute();
            actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

            return actor;
        } else {
            return actor;
        }
    }

}
