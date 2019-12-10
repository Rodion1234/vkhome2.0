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
public class Comment {
    
    Integer owner_id;
    
    Integer item_id;
    
    Integer from_id;
    
    Date date;
    
    String text;
    
    

    public Comment() {
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
    
    public Integer getFrom_id() {
        return from_id;
    }

    public void setFrom_id(Integer from_id) {
        this.from_id = from_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

 

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" + "owner_id=" + owner_id + ", item_id=" + item_id + ", from_id=" + from_id + ", date=" + date + ", text=" + text + '}';
    }
    

  
    
    
    
    
}
