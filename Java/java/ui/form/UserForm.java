package ui.form;

import db.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: UserForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class UserForm implements Serializable {

    private User user = new User();

    private boolean newUser = true;

    public void create() {
        user = new User();
        newUser = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        newUser = false;
    }

    public boolean getIsNew() {
        return newUser;
    }
}
