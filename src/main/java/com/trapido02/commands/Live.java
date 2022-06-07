package com.trapido02.commands;

import com.trapido02.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
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

import static com.trapido02.Streamtools.api;

public class Live implements CommandExecutor, TabCompleter {
    enum Platforms {
        TWITCH,
        YOUTUBE,
        DISCORD
    }

    private static final String[] COMMANDS = { "TWITCH", "YOUTUBE", "DISCORD", "STOP" };

    private static boolean isStreaming = false;

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
        final List<String> completions = new ArrayList<>();

        // Copy matches of first argument from list (ex: if first arg is 'm' will return just 'minecraft')
        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);

        Collections.sort(completions);
        return completions;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Make sure the command is called from a player and not the console
        if (!(sender instanceof Player player)) {
            Message.sendClient(sender, "&cOnly players can use this command.");
            return true;
        }

        // Luckperms initialize user for setting node
        String group = "Producer";
        User user = api.getPlayerAdapter(Player.class).getUser(player);

        // Display the proper display name
        Component comp = ((Player) sender).displayName();

        // Check if player tries to run stop argument
        if (args[0].equalsIgnoreCase("stop")) {
            if (isStreaming) {
                InheritanceNode node = InheritanceNode.builder(group).value(false).build();
                DataMutateResult result = user.data().add(node);

                Message.sendServer(sender, comp.append(Component
                        .text(" has STOPPED streaming!"))
                        .color(NamedTextColor.GREEN)
                );
                Message.sendClient(player, "Stopped streaming!");

                isStreaming = false;
            } else {
                Message.sendClient(sender, "You aren't streaming.");
                return true;
            }

            return true;
        }

        if (args.length == 1 && contains(args[0])) {
            if (!isStreaming) {
                Message.sendServer(sender, comp.append(Component
                        .text(" has started streaming on " + args[0].toUpperCase() + "!"))
                        .color(NamedTextColor.DARK_PURPLE)
                );

                // If the user didn't provide with a Discord invite, default it to the Gulag
                if (args[0].equalsIgnoreCase("discord")) {
                    Message.senderServerURL(sender, "[JOIN GULAG]", "https://discord.gg/BUNxGdVerg");
                }

                isStreaming = true;
            } else {
                Message.sendClient(sender, "You are already streaming.");
            }

            return true;
        }

        if (args.length == 2 && contains(args[0])) {
            if (args[0].equalsIgnoreCase("stop")) { return true; }

            if (!isStreaming) {
                Message.sendServer(sender, comp.append(Component
                        .text(" has started streaming on " + args[0].toUpperCase() + "!"))
                        .color(NamedTextColor.DARK_PURPLE)
                );

                if (args[0].equalsIgnoreCase("discord")) {
                    Message.senderServerURL(sender, "[JOIN DISCORD]", "https://discord.com/" + args[1]);
                } else if (args[0].equalsIgnoreCase("twitch")) {
                    Message.senderServerURL(sender, "[JOIN STREAM]", "https://twitch.tv/" + args[1]);
                } else if (args[0].equalsIgnoreCase("youtube")) {
                    Message.senderServerURL(sender, "[JOIN STREAM]", "https://youtube.com/" + args[1]);
                }

                InheritanceNode node = InheritanceNode.builder(group).value(true).build();
                DataMutateResult result = user.data().add(node);

                isStreaming = true;
            } else {
                Message.sendClient(sender, "You are already streaming.");
            }

            return true;
        }

        Message.sendClient(sender, "Incorrect usage of the command. /live [PLATFORM] [LINK]");

        return true;
    }
}
