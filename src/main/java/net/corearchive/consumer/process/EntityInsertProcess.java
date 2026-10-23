package net.corearchive.consumer.process;

import java.sql.Statement;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.statement.MaterialStatement;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.utility.Chat;

class EntityInsertProcess {

    static void process(ConsumerWriteBatch preparedStmt, Statement statement, int batchCount, Object name, int materialId) {
        if (name instanceof String) {
            String query = "SELECT id FROM " + ConfigHandler.prefix + "entity_map WHERE id = " + materialId + " LIMIT 1 OFFSET 0";
            boolean hasMaterial = !ConfigHandler.databaseType.isClickHouse() && MaterialStatement.hasMaterial(statement, query);
            if (!hasMaterial) {
                MaterialStatement.insert(preparedStmt, ConsumerWriteBatch.ReferenceKind.ENTITY, batchCount, materialId, (String) name);

                // validate ID maps to ensure mapping wasn't reloaded from database prior to this insertion completing
                ConfigHandler.entities.put((String) name, materialId);
                ConfigHandler.entitiesReversed.put(materialId, (String) name);
                if (materialId > ConfigHandler.entityId) {
                    ConfigHandler.entityId = materialId;
                }
            }
            else {
                Chat.console(Phrase.build(Phrase.CACHE_ERROR, "entity"));
                Chat.console(Phrase.build(Phrase.CACHE_RELOAD, Selector.FIRST));
                ConfigHandler.loadTypes(statement);
            }
        }
    }
}
