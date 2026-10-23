package net.corearchive.database.lookup;

import java.sql.Connection;
import net.corearchive.database.statement.UserStatement;
import net.corearchive.utility.ErrorReporter;

public class PlayerLookup {

    public static boolean playerExists(Connection connection, String user) {
        try {
            return UserStatement.findId(connection, user) > -1;
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return false;
    }

}
