package com.lut.student.customerconactmgmt;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileResponse extends JSONObject {
    private String name;
    private String username;
    private String email;

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}

