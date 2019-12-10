/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.markov.vkproject.entity;

/**
 *
 * @author rodion
 */
public class Like {
    
    Integer owner_id;;
    
    Integer item_id;
       
    Integer user_id;
    
    String date;

    public Like() {
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Like{" + "owner_id=" + owner_id + ", item_id=" + item_id + ", user_id=" + user_id + ", date=" + date + '}';
    }
    
    
    
}
