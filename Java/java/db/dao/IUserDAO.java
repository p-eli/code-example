package db.dao;

import db.entity.User;

/**
 * Project: Airport
 * File: IUserDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface IUserDAO extends IEntityDAO<User> {
    User findByLogin(String login);

    User get(String login, String password);
}
