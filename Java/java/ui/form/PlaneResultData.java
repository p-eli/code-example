package ui.form;

import db.datatable.TestDatatable;
import db.datatable.TestResultDatatable;
import db.entity.PrintableTestResult;
import db.entity.Test;
import db.entity.TestResult;
import org.apache.commons.lang3.tuple.Pair;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project: Airport
 * File: PlaneResultData.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class PlaneResultData implements Serializable {

    @Inject
    TestDatatable testDatatable;

    @Inject
    TestResultDatatable testResultDatatable;
    private int testId = -1;

    public Collection<Pair<String, Object>> getTestSummary() {
        if (testId == -1)
            return null;

        Test test = testDatatable.getById(testId);
        Collection<Pair<String, Object>> list = new ArrayList<Pair<String, Object>>();
        list.add(Pair.of("Název testu", (Object) test.getType().getName()));
        list.add(Pair.of("Letadlo", (Object) test.getPlane()));
        list.add(Pair.of("Očekávaný interval kontroly", (Object) (test.getType().getCheckingInterval() + " dní")));
        list.add(Pair.of("Datum provedení", (Object) test.getDate()));
        list.add(Pair.of("Zodpovědná osoba", (Object) test.getTechnician()));
        list.add(Pair.of("Výsledek", (Object) (passed() ? "Prošel" : "Neprošel")));

        return list;
    }


    public Collection<PrintableTestResult> getResults() {
        List<PrintableTestResult> results = new ArrayList<PrintableTestResult>();
        Test test = testDatatable.getById(testId);
        for (TestResult res : testResultDatatable.getResults(test)) {
            results.add(new PrintableTestResult(res));
        }
        return results;
    }

    public boolean passed() {
        Collection<TestResult> results = testResultDatatable.getResults(testDatatable.getById(testId));
        for (TestResult res : results) {
            if (!res.isValid())
                return false;
        }
        return true;
    }

    public Test getTest() {
        return testDatatable.getById(testId);
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public boolean isTestSet() {
        return testId != -1;
    }
}
