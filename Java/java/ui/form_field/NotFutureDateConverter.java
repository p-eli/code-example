package ui.form_field;

import org.joda.time.DateTime;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import java.util.Date;

/**
 * Project: Airport
 * File: NotFutureDateConverter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@FacesConverter("NotFutureDateConverter")
@Named
public class NotFutureDateConverter extends DateConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Date date = (Date) super.getAsObject(context, component, value);

        Date today = new Date();
        DateTime dt = new DateTime(today);
        DateTime tomorrow = dt.plusDays(1);
        if (tomorrow.toDate().before(date)) {
            FacesMessage msg =
                    new FacesMessage("Nesprávné datum.",
                            "Datum nesmí být v budoucnosti");

            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        return date;

    }

}
