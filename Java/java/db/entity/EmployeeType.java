package db.entity;

/**
 * Project: Airport
 * File: EmployeeType.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public enum EmployeeType {
    Other,
    Technician,
    Admin;

    @Override
    public String toString() {
        switch (this) {
            case Admin:
                return "Administrátor";
            case Technician:
                return "Technik";
            case Other:
                return "Běžný uživatel";
            default:
                return super.toString();
        }
    }

    public String getPrintable() {
        return this.toString();
    }
}
