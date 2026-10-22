package net.corearchive.database.statement;

import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.Database;

public class SessionStatement {

    private SessionStatement() {
        throw new IllegalStateException("Database class");
    }

    public static void insert(ConsumerWriteBatch batch, int batchCount, int time, int user, int wid, int x, int y, int z, int action) {
        try {
            batch.addSession(batchCount, time, user, wid, x, y, z, action);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }
}
