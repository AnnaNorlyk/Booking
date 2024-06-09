package org.example.booking;

public class User {
    private int userID;
    private String nameOfUser;
    private String unilogin;

    public User(int id, String name, String unilogin) {
        this.userID = id;
        this.nameOfUser = name;
        this.unilogin = unilogin;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return nameOfUser;
    }

    public String getUnilogin() {
        return unilogin;
    }

}
