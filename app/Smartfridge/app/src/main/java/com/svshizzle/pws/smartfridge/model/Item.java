package com.svshizzle.pws.smartfridge.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arend-Jan on 28-10-2016.
 */

public class Item {
    private int closed = 0;
    private int open = 0;
    private String title = "";
    private int id = 0;
    private String barcode = "";

    public Item(int closed, int open, String title, int id, String barcode) {
        this.closed = closed;
        this.open = open;
        this.title = title;
        this.id = id;
        this.barcode = barcode;
    }
    public Item(){

    }
    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Item loadFromJson(JSONObject object){
        try {


            this.closed = object.getInt("Closed");
            this.open = object.getInt("Open");
            this.title = object.getString("Name");
            this.id = object.getInt("Id");
            this.barcode = object.getString("Barcode");
            return this;
        }catch (JSONException ignored){

        }
        return this;
    }
}
