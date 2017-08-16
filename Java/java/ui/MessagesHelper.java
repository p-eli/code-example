package ui;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 * Project: Airport
 * File: MessagesHelper.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public class MessagesHelper {

    public static void Show(FacesMessage.Severity severity, String text) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(severity, text, null));
    }

    public static void ShowInfo(String text) {
        Show(FacesMessage.SEVERITY_INFO, text);
    }

    public static void ShowError(String text) {
        Show(FacesMessage.SEVERITY_ERROR, text);
    }

}
