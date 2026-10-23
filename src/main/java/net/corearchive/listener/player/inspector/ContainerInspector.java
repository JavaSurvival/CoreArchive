package net.corearchive.listener.player.inspector;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.corearchive.config.ConfigHandler;
import net.corearchive.database.lookup.ChestTransactionLookup;
import net.corearchive.database.statement.EntitySpawnStatement;
import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.utility.Chat;
import net.corearchive.utility.Color;
import net.corearchive.utility.ErrorReporter;

public class ContainerInspector extends BaseInspector {

    public void performContainerLookup(final Player player, final Location finalLocation) {
        class BasicThread implements Runnable {
            @Override
            public void run() {
                try {
                    ConfigHandler.lookupEntityContainer.remove(player.getName());

                    try (Connection connection = getDatabaseConnection(player)) {
                        Statement statement = connection.createStatement();
                        List<String> blockData = ChestTransactionLookup.performLookup(null, statement, finalLocation, player, 1, 7, false);
                        for (String data : blockData) {
                            Chat.sendComponent(player, data);
                        }

                        statement.close();
                    }
                }
                catch (InspectionException e) {
                    Chat.sendMessage(player, e.getMessage());
                }
                catch (Exception e) {
                    ErrorReporter.report(e);
                }
            }
        }

        startInspection(player, new BasicThread());
    }

    public void performEntityContainerLookup(final Player player, final UUID entityUuid, final Location location) {
        class BasicThread implements Runnable {
            @Override
            public void run() {
                try {
                    ConfigHandler.lookupEntityContainer.remove(player.getName());
                    ConfigHandler.lookupType.remove(player.getName());

                    try (Connection connection = getDatabaseConnection(player)) {
                        Integer entitySpawnRowId = EntitySpawnStatement.findRowIdByUuid(connection, entityUuid);

                        if (entitySpawnRowId == null) {
                            ConfigHandler.lookupEntityContainer.remove(player.getName());
                            ConfigHandler.lookupType.remove(player.getName());
                            ConfigHandler.lookupCommand.remove(player.getName());
                            Chat.sendMessage(player, Color.DARK_AQUA + "CoreProtect " + Color.WHITE + "- " + Phrase.build(Phrase.NO_DATA_LOCATION, Selector.SECOND));
                            return;
                        }

                        try (Statement statement = connection.createStatement()) {
                            List<String> blockData = ChestTransactionLookup.performLookup(null, statement, location, player, 1, 7, false, entitySpawnRowId);
                            for (String data : blockData) {
                                Chat.sendComponent(player, data);
                            }
                        }
                    }
                }
                catch (InspectionException e) {
                    Chat.sendMessage(player, e.getMessage());
                }
                catch (Exception e) {
                    ErrorReporter.report(e);
                }
            }
        }

        startInspection(player, new BasicThread());
    }
}
