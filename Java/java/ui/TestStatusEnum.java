package ui;

/**
 * Project: Airport
 * File: TestStatusEnum.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public enum TestStatusEnum {
    OUTDATE,
    ACTUAL,
    OLD;

    @Override
    public String toString() {
        switch (this) {
            case OLD:
                return "Neaktuální";
            case OUTDATE:
                return "Propadlé";
            case ACTUAL:
                return "Aktuální";
            default:
                return super.toString();
        }
    }

    public String toCssClas() {
        switch (this) {
            case OUTDATE:
                return "outdate_item";
            case ACTUAL:
                return "actual_item";
            case OLD:
                return "old_item";
            default:
                return super.toString();
        }
    }
}
