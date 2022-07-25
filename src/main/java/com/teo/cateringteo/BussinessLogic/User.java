package com.teo.cateringteo.BussinessLogic;

import java.io.Serializable;

/**
 * The type user
 * uses an enum class so that I wouldn't implement three different classes
 * (based on the enum AccountType, I know if the user is either a client, employee or admin)
 */
public class User implements Serializable {
    public enum AccountType {CLIENT, EMPLOYEE, ADMIN};

    private int id;
    private String username;
    private String password;
    private AccountType type;

    public User() {
    }

    public User(int id, String username, String password, AccountType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
