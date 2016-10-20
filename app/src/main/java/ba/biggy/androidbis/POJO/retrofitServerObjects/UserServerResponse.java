package ba.biggy.androidbis.POJO.retrofitServerObjects;


import java.util.ArrayList;
import java.util.List;

import ba.biggy.androidbis.POJO.User;

public class UserServerResponse {

    private List<User> user = new ArrayList<User>();


    public List<User> getUser() {
        return user;
    }


}
