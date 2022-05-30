package com.trapido02.commands;

import com.trapido02.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Stream implements CommandExecutor {
    enum Platforms {
        TWITCH,
        YOUTUBE,
        DISCORD
    }

    public static boolean contains(String v) {
        for (Platforms c : Platforms.values()) {
            if (c.name().equals(v.toUpperCase())) {
                return true;
            }
        }

        return false;
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
            if (contains(args[0])) {
                Msg.serverSend("&4" + player.getName() + " has started streaming on " + args[0].toUpperCase() + "!");

                if (args[0].equalsIgnoreCase("discord")) {
                    Msg.serverSendURL("[JOIN DISCORD]", args[1]);
                } else {
                    Msg.serverSendURL("[CLICK HERE TO JOIN STREAM]", args[1]);
                }
            }

            return true;
        }

        Msg.send(sender, "Incorrect usage of the command. /live [PLATFORM] [URL]");

        return true;
    }
}
