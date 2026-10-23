package net.corearchive.database.statement;

import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.Database;

public class WorldStatement {

    private WorldStatement() {
        throw new IllegalStateException("Database class");
    }

    public static void insert(ConsumerWriteBatch batch, int batchCount, int id, String world) {
        try {
            batch.addReference(ConsumerWriteBatch.ReferenceKind.WORLD, batchCount, id, world);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }
}
