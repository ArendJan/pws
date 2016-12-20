package com.svshizzle.pws.smartfridge.model;


/**
 * Created by Arend-Jan on 18-11-2016.
 */

public class ShoppingListItem {

    private String barcode = "";
    private String title = "";
    private int amount = 0;
    private boolean checked = true;

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
