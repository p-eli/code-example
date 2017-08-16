package db.entity;

/**
 * Project: Airport
 * File: PrintableTestResult.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public class PrintableTestResult {
    private TestResult res;

    public PrintableTestResult(TestResult res) {
        this.res = res;
    }

    public TestResult getRes() {
        return res;
    }

    public void setRes(TestResult res) {
        this.res = res;
    }

    public String getCss() {
        return res.isValid() ? "valid-result" : "invalid-result";
    }
}
