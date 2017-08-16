package ui;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project: Airport
 * File: TestStatus.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public class TestStatus {
    private Date date;


    public TestStatusEnum getStatus() {
        Date actualDate = new Date();
        if ((date != null) && (date.after(actualDate))) {
            return TestStatusEnum.ACTUAL;
        }
        return TestStatusEnum.OUTDATE;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDate() {
        if (date != null) {
            return new SimpleDateFormat("dd.MM.yyyy").format(date);
        } else {
            return "Žádné testy";
        }
    }
}
