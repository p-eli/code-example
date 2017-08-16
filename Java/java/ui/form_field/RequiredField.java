package ui.form_field;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 * Project: Airport
 * File: RequiredField.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@FacesConverter("RequiredField")
@Named
public class RequiredField implements Converter {

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s.length() == 0) {
            FacesMessage msg =
                    new FacesMessage("Toto pole je povinné");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }

        return s;
    }
}
