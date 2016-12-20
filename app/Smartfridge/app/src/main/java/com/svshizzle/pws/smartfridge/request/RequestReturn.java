package com.svshizzle.pws.smartfridge.request;

/**
 * Created by Arend-Jan on 29-3-2016.
 * ____  ____  _____ _      ____           _  ____  _
 * /  _ \/  __\/  __// \  /|/  _ \         / |/  _ \/ \  /|
 * | / \||  \/||  \  | |\ ||| | \|_____    | || / \|| |\ ||
 * | |-|||    /|  /_ | | \||| |_/|\____\/\_| || |-||| | \||
 * \_/ \|\_/\_\\____\\_/  \|\____/      \____/\_/ \|\_/  \|
 */
public class RequestReturn {

    RequestReturn(String response, boolean error) {
        this.error = error;
        this.response = response;
    }

    public boolean isError() {
        return error;
    }



    public String getResponse() {
        return response;
    }

    private boolean error = false;
    private String response = "";

}
