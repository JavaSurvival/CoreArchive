package net.corearchive.patch.script;

import java.sql.Statement;

import net.corearchive.config.Config;
import net.corearchive.config.ConfigFile;
import net.corearchive.config.ConfigHandler;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.patch.Patch;
import net.corearchive.utility.Chat;
import net.corearchive.utility.ErrorReporter;

public class __2_21_0 {

    protected static boolean patch(Statement statement) {
        try {
            if (Config.getGlobal().MYSQL) {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "item ADD COLUMN rolled_back TINYINT DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "item", Selector.FIRST, Selector.FIRST));
                }
            }
            else {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "item ADD COLUMN rolled_back INTEGER DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "item", Selector.FIRST, Selector.FIRST));
                }
            }

            if (!Patch.continuePatch()) {
                return false;
            }

            ConfigFile.modifyLine("language.yml", "LOOKUP_VIEW_PAGE: \"To view a page, type \\\"{0}\\\".\"", null);
            ConfigFile.modifyLine("language.yml", "PREVIEW_CONTAINER: \"You can't preview container transactions.\"", null);
            ConfigFile.sortFile("language.yml");
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return true;
    }

}
