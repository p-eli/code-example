package ui.form_field;

import db.dao.IUserDAO;
import org.omnifaces.util.Messages;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: UniqueLoginValidator.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@FacesConverter("uniqueLoginValidator")
@Named
public class UniqueLoginValidator implements Converter, Serializable {

    @EJB
    private IUserDAO dao;


    public Object getAsObject(FacesContext context, UIComponent uiComponent, String login) {
        boolean isValid = (dao.findByLogin(login) == null);
        if (!isValid) {
            FacesMessage msg = Messages.createError(String.format("Login \"%s\" již někdo užívá", login));
            context.addMessage(uiComponent.getClientId(context), msg);
            throw new ConverterException(msg);
        }
        return login;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return (String) o;
    }
}