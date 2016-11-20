package com.svshizzle.pws.smartfridge.model;

import android.util.SparseBooleanArray;

/**
 * Created by Arend-Jan on 18-11-2016.
 */

public class ShoppingListItem {

    String barcode = "";
    String title = "";
    int amount = 0;
    boolean checked = true;

    public ShoppingListItem(String barcode, String title, int amount) {
        this.barcode = barcode;
        this.title = title;
        this.amount = amount;

    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setCheck(boolean checked) {
        this.checked = checked;
    }
    public String getAmountString(){
        return amount+"";
    }
}
