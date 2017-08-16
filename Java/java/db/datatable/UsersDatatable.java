package db.datatable;

import db.dao.IUserDAO;
import db.entity.EmployeeType;
import db.entity.User;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Airport
 * File: UsersDatatable.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ViewScoped
@Named
public class UsersDatatable implements Serializable {

    @Inject
    IUserDAO userDao;

    private List<User> values;

    public List<User> getValues() {
        if (values == null)
            refresh();
        return values;
    }

    public List<User> getTechnicians() {
        List<User> list = new ArrayList<User>();
        for (User u : getValues()) {
            if (u.getType() == EmployeeType.Technician) {
                list.add(u);
            }
        }
        return list;
    }

    public void refresh() {
        values = userDao.queryAll();
    }

    public void merge(User user) {
        userDao.merge(user);
        refresh();
    }

    public void remove(User user) {
        userDao.remove(user);
        refresh();
    }

    public void persist(User user) {
        userDao.persist(user);
        refresh();
    }
}