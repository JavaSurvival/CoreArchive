package net.corearchive.patch.script;

import java.sql.Statement;

import net.corearchive.config.Config;
import net.corearchive.config.ConfigHandler;
import net.corearchive.patch.Patch;
import net.corearchive.utility.ErrorReporter;

public class __2_5_0 {

    protected static boolean patch(Statement statement) {
        try {
            if (Config.getGlobal().MYSQL) {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign MODIFY line_1 VARCHAR(100)");
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign MODIFY line_2 VARCHAR(100)");
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign MODIFY line_3 VARCHAR(100)");
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign MODIFY line_4 VARCHAR(100)");
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "user MODIFY user VARCHAR(32)");
                }
                catch (Exception e) {
                    ErrorReporter.report(e);
                }

                if (!Patch.continuePatch()) {
                    return false;
                }
            }

            statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "block ADD COLUMN meta BLOB");
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return true;
    }

}
