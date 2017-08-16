package auth;

import java.util.Arrays;
import java.util.List;

/**
 * Project: Airport
 * File: Authorization.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public class Authorization {
    private static List<String> adminPage = Arrays.asList(
            "/airport/admin/planeEdit.xhtml",
            "/airport/admin/planeModelEdit.xhtml",
            "/airport/admin/testTypeEdit.xhtml",
            "/airport/admin/testTypeParameterEdit.xhtml",
            "/airport/admin/user-create-edit.xhtml"
    );

    private static List<String> technicianPage = Arrays.asList(
            "/airport/admin/testEdit.xhtml",
            "/airport/admin/create-update-test.xhtml"

    );

    public static boolean check(AuthenticationBean auth, String url) {
        if (adminPage.contains(url)) {
            return auth.isAdmin();
        } else if (technicianPage.contains(url)) {
            return auth.isTechnician();
        } else {
            return true;
        }
    }
}
