package ui.form_field;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project: Airport
 * File: PhoneNumberConverter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@FacesConverter("PhoneNumberConverter")
@Named
public class PhoneNumberConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Pattern r = Pattern.compile("((\\+\\d{3}\\s)?\\d{3}\\s\\d{3}\\s\\d{3})?");
        Matcher m = r.matcher(value);
        if (!m.matches()) {
            FacesMessage msg =
                    new FacesMessage(getMessage());
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        return value;
    }

    public String getAsString(FacesContext var1, UIComponent var2, Object var3) {
        return (String) var3;
    }

    public String getMessage() {
        return "Nesprávný formát tel. čísla (+xxx) xxx xxx xxx";
    }
}
