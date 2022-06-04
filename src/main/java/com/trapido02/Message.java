package com.trapido02;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {
    // Send client message
    public static void sendClient(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    // Send client message component
    public static void sendClient(CommandSender sender, Component component) {
        sender.sendMessage(component);
    }

    // Send global message
    public static void sendServer(CommandSender sender, String message) {
        Bukkit.getServer().broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }

    // Send global message component
    public static void sendServer(CommandSender sender, Component component) {
        Bukkit.getServer().broadcast(component);
    }

    // Send URL
    public static void senderServerURL(CommandSender sender, String message, String URL, NamedTextColor COLOR) {
        final TextComponent textComponent2 = Component.text()
                .content(message)
                .color(TextColor.color(COLOR))
                .clickEvent(ClickEvent.openUrl(URL))
                .build();

        Bukkit.getServer().broadcast(textComponent2);
    }

    public static void senderServerURL(CommandSender sender, String message, String URL) {
        senderServerURL(sender, message, URL, NamedTextColor.LIGHT_PURPLE);
    }
}
