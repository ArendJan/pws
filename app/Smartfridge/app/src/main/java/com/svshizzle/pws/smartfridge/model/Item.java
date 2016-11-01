package com.svshizzle.pws.smartfridge.model;

/**
 * Created by Arend-Jan on 28-10-2016.
 */

public class Item {
    int closed = 0;
    int open = 0;
    String title = "";
    int id = 0;
    String barcode = "";

    public Item(int closed, int open, String title, int id, String barcode) {
        this.closed = closed;
        this.open = open;
        this.title = title;
        this.id = id;
        this.barcode = barcode;
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
}
