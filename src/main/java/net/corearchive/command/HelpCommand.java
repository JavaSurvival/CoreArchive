package net.corearchive.command;

import java.util.Locale;

import org.bukkit.command.CommandSender;

import net.corearchive.language.Phrase;
import net.corearchive.language.Selector;
import net.corearchive.utility.Chat;
import net.corearchive.utility.Color;

public class HelpCommand {
    protected static void runCommand(CommandSender player, boolean permission, String[] args) {
        int resultc = args.length;
        if (permission) {
            if (resultc > 1) {
                String helpcommand_original = args[1];
                String helpcommand = args[1].toLowerCase(Locale.ROOT);
                helpcommand = helpcommand.replaceAll("[^a-zA-Z]", "");
                Chat.sendMessage(player, Color.WHITE + "----- " + Color.DARK_AQUA + Phrase.build(Phrase.HELP_HEADER, "CoreArchive") + Color.WHITE + " -----");
                if (helpcommand.equals("help")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca help " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_LIST));
                }
                else if (helpcommand.equals("inspect") || helpcommand.equals("inspector") || helpcommand.equals("in")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + Phrase.build(Phrase.HELP_INSPECT_1));
                    Chat.sendMessage(player, "* " + Phrase.build(Phrase.HELP_INSPECT_2));
                    Chat.sendMessage(player, "* " + Phrase.build(Phrase.HELP_INSPECT_3));
                    Chat.sendMessage(player, "* " + Phrase.build(Phrase.HELP_INSPECT_4));
                    Chat.sendMessage(player, "* " + Phrase.build(Phrase.HELP_INSPECT_5));
                    Chat.sendMessage(player, "* " + Phrase.build(Phrase.HELP_INSPECT_6));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_INSPECT_7));
                }
                else if (helpcommand.equals("params") || helpcommand.equals("param") || helpcommand.equals("parameters") || helpcommand.equals("parameter")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup " + Color.GREY + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_1, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "u:<users> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_2, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "t:<time> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_3, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "r:<radius> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_4, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "a:<action> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_5, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "i:<include> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_6, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "e:<exclude> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_7, Selector.FIRST));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "f:<filter> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_FILTER_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_PARAMETER, "/ca help <param>"));
                }
                else if (helpcommand.equals("rollback") || helpcommand.equals("rollbacks") || helpcommand.equals("rb") || helpcommand.equals("ro")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca rollback " + Color.GREY + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_1, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "u:<users> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_2, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "t:<time> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_3, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "r:<radius> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_4, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "a:<action> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_5, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "i:<include> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_6, Selector.SECOND));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "e:<exclude> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_7, Selector.SECOND));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_PARAMETER, "/ca help <param>"));
                }
                else if (helpcommand.equals("restore") || helpcommand.equals("restores") || helpcommand.equals("re") || helpcommand.equals("rs")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca restore " + Color.GREY + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_1, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "u:<users> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_2, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "t:<time> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_3, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "r:<radius> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_4, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "a:<action> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_5, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "i:<include> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_6, Selector.THIRD));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "e:<exclude> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PARAMS_7, Selector.THIRD));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_PARAMETER, "/ca help <param>"));
                }
                else if (helpcommand.equals("lookup") || helpcommand.equals("lookups") || helpcommand.equals("l")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup <params>");
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca l <params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_LOOKUP_1));
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup <page> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_LOOKUP_2));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_PARAMETER, "/ca help params"));
                }
                else if (helpcommand.equals("purge") || helpcommand.equals("purges")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca purge " + Color.GREY + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_1));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "t:<time> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_3));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "r:<world/radius> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_4));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "i:<include> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_5));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "e:<exclude> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_6));
                    Chat.sendMessage(player, Color.DARK_AQUA + "| " + Color.GREY + "a:kill " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_7));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_PURGE_2, "/ca purge t:30d"));
                }
                else if (helpcommand.equals("reload")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca reload " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_RELOAD_COMMAND));
                }
                else if (helpcommand.equals("status")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca status " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_STATUS));
                }
                else if (helpcommand.equals("teleport")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca teleport <world> <x> <y> <z> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_TELEPORT));
                }
                else if (helpcommand.equals("u") || helpcommand.equals("user") || helpcommand.equals("users") || helpcommand.equals("uuser") || helpcommand.equals("uusers")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup u:<users> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_USER_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_USER_2));
                }
                else if (helpcommand.equals("t") || helpcommand.equals("time") || helpcommand.equals("ttime")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup t:<time> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_TIME_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_TIME_2));
                }
                else if (helpcommand.equals("r") || helpcommand.equals("radius") || helpcommand.equals("rradius")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup r:<radius> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_RADIUS_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_RADIUS_2));
                }
                else if (helpcommand.equals("a") || helpcommand.equals("action") || helpcommand.equals("actions") || helpcommand.equals("aaction")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup a:<action> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_ACTION_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_ACTION_2));
                }
                else if (helpcommand.equals("f") || helpcommand.equals("filter") || helpcommand.equals("ffilter")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup a:<chat|command|sign> f:<filter> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_FILTER_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_FILTER_2));
                }
                else if (helpcommand.equals("i") || helpcommand.equals("include") || helpcommand.equals("iinclude") || helpcommand.equals("b") || helpcommand.equals("block") || helpcommand.equals("blocks") || helpcommand.equals("bblock") || helpcommand.equals("bblocks")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup i:<include> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_INCLUDE_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_INCLUDE_2));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.LINK_WIKI_BLOCK, "https://coreprotect.net/wiki-blocks"));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.LINK_WIKI_ENTITY, "https://coreprotect.net/wiki-entities"));
                }
                else if (helpcommand.equals("e") || helpcommand.equals("exclude") || helpcommand.equals("eexclude")) {
                    Chat.sendMessage(player, Color.DARK_AQUA + "/ca lookup e:<exclude> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_EXCLUDE_1));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.HELP_EXCLUDE_2));
                    Chat.sendMessage(player, Color.GREY + Color.ITALIC + Phrase.build(Phrase.LINK_WIKI_BLOCK, "https://coreprotect.net/wiki-blocks"));
                }
                else {
                    Chat.sendMessage(player, Color.WHITE + Phrase.build(Phrase.HELP_NO_INFO, Color.WHITE, "/ca help " + helpcommand_original));
                }
            }
            else {
                Chat.sendMessage(player, Color.WHITE + "----- " + Color.DARK_AQUA + Phrase.build(Phrase.HELP_HEADER, "CoreArchive") + Color.WHITE + " -----");
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca help " + Color.GREY + "<command> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "inspect " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_INSPECT_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "rollback " + Color.DARK_AQUA + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_ROLLBACK_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "restore " + Color.DARK_AQUA + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_RESTORE_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "lookup " + Color.DARK_AQUA + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_LOOKUP_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "purge " + Color.DARK_AQUA + "<params> " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_PURGE_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "reload " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_RELOAD_COMMAND));
                Chat.sendMessage(player, Color.DARK_AQUA + "/ca " + Color.GREY + "status " + Color.WHITE + "- " + Phrase.build(Phrase.HELP_STATUS_COMMAND));
            }
        }
        else {
            Chat.sendMessage(player, Color.DARK_AQUA + "CoreArchive " + Color.WHITE + "- " + Phrase.build(Phrase.NO_PERMISSION));
        }
    }
}
