package com.trapido02.commands;

import com.trapido02.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Live implements CommandExecutor, TabCompleter {
    enum Platforms {
        TWITCH,
        YOUTUBE,
        DISCORD
    }

    private static final String[] COMMANDS = { "TWITCH", "YOUTUBE", "DISCORD" };

    public static boolean contains(String v) {
        for (Platforms c : Platforms.values()) {
            if (c.name().equals(v.toUpperCase())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();
        //copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);
        //sort the list
        Collections.sort(completions);
        return completions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Make sure the command is called from a player and not the console
        if (!(sender instanceof Player player)) {
            Msg.send(sender, "&cOnly players can use this command.");
            return true;
        }

        if (args.length == 1) {
            if (contains(args[0])) {
                Msg.serverSend("&4" + player.getName() + " has started streaming on " + args[0].toUpperCase() + "!");
            }
            if (args[0].equalsIgnoreCase("discord")) {
                Msg.serverSendURL("[JOIN GULAG]", "https://discord.gg/3E2BsRUd8Q");
            }

            return true;
        }

        if (args.length == 2) {
            Msg.serverSend("&4" + player.getName() + " has started streaming on " + args[0].toUpperCase() + "!");

            if (contains(args[0])) {
                if (args[0].equalsIgnoreCase("discord")) {
                    Msg.serverSendURL("[JOIN DISCORD]", "https://discord.com/" + args[1]);
                } else if (args[0].equalsIgnoreCase("twitch")) {
                    Msg.serverSendURL("[CLICK HERE TO JOIN STREAM]", "https://twitch.tv/" + args[1]);
                } else if (args[0].equalsIgnoreCase("youtube")) {
                    Msg.serverSendURL("[CLICK HERE TO JOIN STREAM]", "https://youtube.com/" + args[1]);
                }
            }

            return true;
        }

        Msg.send(sender, "Incorrect usage of the command. /live [PLATFORM] [USERNAME | DISCORD LINK]");

        return true;
    }
}
