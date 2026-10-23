package net.corearchive.database.statement;

import java.sql.ResultSet;
import java.sql.Statement;

import net.corearchive.database.ConsumerWriteBatch;
import net.corearchive.database.Database;

public class MaterialStatement {

    private MaterialStatement() {
        throw new IllegalStateException("Database class");
    }

    public static void insert(ConsumerWriteBatch batch, ConsumerWriteBatch.ReferenceKind kind, int batchCount, int id, String name) {
        try {
            batch.addReference(kind, batchCount, id, name);
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }
    }

    public static boolean hasMaterial(Statement statement, String query) {
        boolean result = false;

        try {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                result = true;
            }
            resultSet.close();
        }
        catch (Exception e) {
            Database.handleWriteFailure(e);
        }

        return result;
    }
}
