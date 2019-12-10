/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.markov.vkproject.entity;

import java.util.Date;

/**
 *
 * @author rodion
 */
public class Repost {

    private Integer user_id;

    private Integer owner_id;

    private Integer item_id;
    
    private Date date;

    @Override
    public String toString() {
        return "Repost{" + "user_id=" + user_id + ", owner_id=" + owner_id + ", item_id=" + item_id + ", date=" + date + '}';
    }

    
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    
    

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
    
    

}
