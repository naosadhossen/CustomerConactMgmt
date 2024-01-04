package com.lut.student.customerconactmgmt;

import kotlin.text.UStringsKt;

public class TokenResponse {
    private String token;
    private class user {
        private String id;
        private String name;
        private String username;
        private String email;

        public String getusername(){return name;}
        public String getemail(){return email;}

    }

    // Getters and setters
    public String getToken() {
        return token;
    }

}
