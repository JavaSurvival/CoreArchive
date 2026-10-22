package net.corearchive.patch.script;

import java.sql.Statement;

import net.corearchive.patch.Patch;
import net.corearchive.utility.ErrorReporter;

public class __2_18_1 {

    protected static boolean patch(Statement statement) {
        try {
            __2_18_0.createIndexes = false;
            Integer[] last_version = Patch.getDatabaseVersion(statement.getConnection(), true);
            if (last_version[0] == 2 && last_version[1] == 18 && last_version[2] == 0) {
                return __2_18_0.patch(statement);
            }
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return true;
    }

}
