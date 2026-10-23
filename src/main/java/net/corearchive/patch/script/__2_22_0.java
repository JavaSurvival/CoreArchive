package net.corearchive.patch.script;

import java.sql.Statement;

import net.corearchive.config.Config;
import net.corearchive.config.ConfigHandler;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.patch.Patch;
import net.corearchive.utility.Chat;
import net.corearchive.utility.ErrorReporter;

public class __2_22_0 {

    protected static boolean patch(Statement statement) {
        try {
            if (Config.getGlobal().MYSQL) {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_5 VARCHAR(100);");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_6 VARCHAR(100);");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_7 VARCHAR(100);");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_8 VARCHAR(100);");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN color_secondary INT;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN waxed TINYINT DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN face TINYINT DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }
            }
            else {
                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_5 TEXT;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_6 TEXT;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_7 TEXT;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN line_8 TEXT;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN color_secondary INTEGER;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN waxed TINYINT DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }

                if (!Patch.continuePatch()) {
                    return false;
                }

                try {
                    statement.executeUpdate("ALTER TABLE " + ConfigHandler.prefix + "sign ADD COLUMN face TINYINT DEFAULT 0;");
                }
                catch (Exception e) {
                    Chat.console(Phrase.build(Phrase.PATCH_SKIP_UPDATE, ConfigHandler.prefix + "sign", Selector.FIRST, Selector.FIRST));
                }
            }
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }

        return true;
    }

}
