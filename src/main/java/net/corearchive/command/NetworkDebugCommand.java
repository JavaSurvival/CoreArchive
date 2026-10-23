package net.corearchive.command;

import org.bukkit.command.CommandSender;

import net.corearchive.config.Config;
import net.corearchive.language.Phrase;
import net.corearchive.listener.channel.PluginChannelListener;
import net.corearchive.utility.Chat;
import net.corearchive.utility.Color;
import net.corearchive.utility.ErrorReporter;

public class NetworkDebugCommand {
    protected static void runCommand(CommandSender player, boolean permission, String[] args) {
        if (!permission || !Config.getGlobal().NETWORK_DEBUG) {
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.NO_PERMISSION));
            return;
        }

        try {
            PluginChannelListener.getInstance().sendTest(player, args.length == 2 ? args[1] : "");
        }
        catch (Exception e) {
            ErrorReporter.report(e);
        }
    }
}
