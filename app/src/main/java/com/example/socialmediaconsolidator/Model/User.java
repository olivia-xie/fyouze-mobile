package com.example.socialmediaconsolidator.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String email;

    boolean useFacebook;
    boolean useTwitter;
    boolean useInstagram;


    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUseFacebook() {
        return useFacebook;
    }

    public void setUseFacebook(boolean useFacebook) {
        this.useFacebook = useFacebook;
    }

    public boolean isUseTwitter() {
        return useTwitter;
    }

    public void setUseTwitter(boolean useTwitter) {
        this.useTwitter = useTwitter;
    }

    public boolean isUseInstagram() {
        return useInstagram;
    }

    public void setUseInstagram(boolean useInstagram) {
        this.useInstagram = useInstagram;
    }
}
