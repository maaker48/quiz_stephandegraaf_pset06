package com.example.stephan.quiz;

import java.io.Serializable;

/**
 * Created by Stephan on 16-11-2017.
 */

public class Item implements Serializable {
    String id;
    String category;

    public Item(String id, String category)
    {
        this.id = id;
        this.category = category;
    }
    public String id(Item item){
        return item.id;
    }
    public String category(Item item){
        return item.category;
    }
}