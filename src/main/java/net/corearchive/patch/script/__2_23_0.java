package net.corearchive.patch.script;

import java.sql.Statement;

import net.corearchive.config.Config;
import net.corearchive.config.ConfigHandler;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.utility.Chat;
import net.corearchive.utility.ErrorReporter;

public class __2_23_0 {

    protected static boolean patch(Statement statement) {
        try {
            if (Config.getGlobal().MYSQL) {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "skull MODIFY owner VARCHAR(255);");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "skull", Selector.FIRST, Selector.FIRST));
                }
            }

            __2_23_1.patch(statement);
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return true;
    }

}
