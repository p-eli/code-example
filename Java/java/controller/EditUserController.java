package controller;

import db.datatable.UsersDatatable;
import db.entity.User;
import ui.MessagesHelper;
import ui.form.UserForm;

import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: EditUserController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Named
@RequestScoped
public class EditUserController implements Serializable {


    @Inject
    UsersDatatable usersDatatable;

    @Inject
    UserForm userForm;

    public String save() {
        try {
            User user = userForm.getUser();
            String type = (user.getId() == null) ? "vytvořen" : "upraven";
            if (userForm.getIsNew()) {
                usersDatatable.persist(user);
            } else {
                usersDatatable.merge(user);
            }
            String msg = String.format("Uživatel %s %s byl %s", user.getFirstname(), user.getLastname(), type);
            MessagesHelper.ShowInfo(msg);
            return "list";
        } catch (EJBTransactionRolledbackException ex) {
            return null;
        }
    }

    public String create() {
        userForm.create();
        return "create-user";
    }

    public String list(){
        return "list-users";
    }

    public String remove(User user) {
        usersDatatable.remove(user);

        String msg = String.format("Uživatel #%d - %s %s byl smazán", user.getId(), user.getFirstname(), user.getLastname());
        MessagesHelper.ShowInfo(msg);

        return "list";
    }

    public String update(User user) {
        userForm.setUser(user);
        user.setPassword(null);
        return "update";
    }
}