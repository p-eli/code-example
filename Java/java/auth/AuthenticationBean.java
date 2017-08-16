package auth;

import db.dao.IUserDAO;
import db.entity.EmployeeType;
import db.entity.User;
import org.omnifaces.util.Faces;
import ui.MessagesHelper;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Project: Airport
 * File: AuthenticationBean.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ManagedBean
@SessionScoped
public class AuthenticationBean implements Serializable {
    private User user;
    private String login;
    private String password;

    @EJB
    IUserDAO userRepository;

    public boolean isAuthorized() {
        return user != null;
    }

    public boolean isAdmin() {
        return isAuthorized() && user.getType() == EmployeeType.Admin;
    }

    public boolean isTechnician() {
        return isAuthorized() && user.getType() == EmployeeType.Technician;
    }

    public boolean isTechnicianOrAdmin() {
        return isAdmin() || isTechnician();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String actionLogout() {
        user = null;
        Faces.invalidateSession();
        MessagesHelper.ShowInfo("Uživatel byl odhlášen");
        return "logout";
    }

    public String actionLogin() throws IOException {
        User user = userRepository.get(login, password);
        if (user != null) {
            this.user = user;
            return "login";
        } else {
            MessagesHelper.ShowError("Nesprávné uživatelské jméno nebo heslo");
            return "failed";
        }
    }

    public void preRenderViewEvent() {
        Map map = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        if (map.containsKey("bad-login")) {
            MessagesHelper.ShowError("Přístup odepřen");
        }

        if (map.containsKey("session-expired")) {
            MessagesHelper.ShowError("Přihlášení vypršelo");
        }
    }

    public User getUser() {
        return user;
    }

}
