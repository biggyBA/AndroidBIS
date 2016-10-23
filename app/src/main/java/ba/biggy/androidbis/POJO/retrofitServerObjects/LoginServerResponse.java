package ba.biggy.androidbis.POJO.retrofitServerObjects;


import ba.biggy.androidbis.POJO.Sparepart;
import ba.biggy.androidbis.POJO.User;

public class LoginServerResponse {

    private User[] user;
    private Sparepart[] sparepart;
    private String result;

    public User[] getUser() {
        return user;
    }

    public Sparepart[] getSparepart() {
        return sparepart;
    }

    public String getResult() {
        return result;
    }


}
