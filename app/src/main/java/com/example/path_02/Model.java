package com.example.path_02;

import android.widget.ImageView;
import android.widget.TextView;

public class Model {

    private String full_name, category;
    private int profilePic;



    public Model (String full_name, String category, int profilePic){
        this.full_name = full_name;
        this.category = category;
        this.profilePic = profilePic;
    }
    public String getFull_name() {
        return full_name;
    }

    public String getCategory() {
        return category;
    }

    public int getProfilePic() {
        return profilePic;
    }
}
