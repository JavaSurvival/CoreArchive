package net.corearchive.consumer.process;

import java.sql.Statement;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.MaterialStatement;
import net.corearchive.database.statement.WorldStatement;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.utility.Chat;

class WorldInsertProcess {

    static void process(ConsumerWriteBatch preparedStmt, int batchCount, Statement statement, Object world, int worldId) {
        if (world instanceof String) {
            String query = "SELECT id FROM " + ConfigHandler.prefix + "world WHERE id = " + worldId + " LIMIT 1 OFFSET 0";
            boolean hasMaterial = !ConfigHandler.databaseType.isClickHouse() && MaterialStatement.hasMaterial(statement, query);
            if (!hasMaterial) {
                WorldStatement.insert(preparedStmt, batchCount, worldId, (String) world);

                // validate ID maps to ensure mapping wasn't reloaded from database prior to this insertion completing
                ConfigHandler.worlds.put((String) world, worldId);
                ConfigHandler.worldsReversed.put(worldId, (String) world);
                if (worldId > ConfigHandler.worldId) {
                    ConfigHandler.worldId = worldId;
                }
            }
            else {
                Chat.console(Phrase.build(Phrase.CACHE_ERROR, "world"));
                Chat.console(Phrase.build(Phrase.CACHE_RELOAD, Selector.SECOND));
                ConfigHandler.loadWorlds(statement);
            }
        }
    }
}
