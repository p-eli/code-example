package ui.form_field;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project: Airport
 * File: DateConverter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@FacesConverter("DateConverter")
@Named
public class DateConverter extends DateTimeConverter {

    public DateConverter() {
        setPattern("dd/MM/yyyy");
    }

    public String getFormat() {
        return this.getPattern();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.length() == 0) {
            return null;
        }

        if (this.getPattern().length() == value.length()) {
            try {
                return super.getAsObject(context, component, value);
            } catch (Exception e) {
                ;
            }
        }
        FacesMessage msg =
                new FacesMessage("Nesprávný format data.",
                        "Datum je očekáváno ve tvaru " + this.getPattern());
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ConverterException(msg);
    }

    @Override
    public String getAsString(FacesContext var1, UIComponent var2, Object var3) {
        return new SimpleDateFormat(this.getPattern()).format((Date) var3);
    }
}
