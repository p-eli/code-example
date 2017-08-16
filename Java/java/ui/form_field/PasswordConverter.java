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
 * File: PasswordConverter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@FacesConverter("PasswordConverter")
@Named
public class PasswordConverter implements Converter {

    public static int MIN_LENGTH = 8;

    public int getMinLength() {
        return MIN_LENGTH;
    }

    private void showError(String message) {
        FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ConverterException(msg);
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.length() > 0) {
            if (value.length() < MIN_LENGTH) {
                showError(String.format("Heslo musí mít alespoň %d znaků.", MIN_LENGTH));
            }
        }
        return value;
    }

    public String getAsString(FacesContext var1, UIComponent var2, Object var3) {
        return (String) var3;
    }
}
