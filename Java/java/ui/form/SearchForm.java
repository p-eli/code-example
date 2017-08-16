package ui.form;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Project: Airport
 * File: SearchForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class SearchForm implements Serializable {

    private Date hiredAfter;

    public Date getHiredAfter() {
        return hiredAfter;
    }

    public void setHiredAfter(final Date hiredAfter) {
        this.hiredAfter = hiredAfter;
    }
}