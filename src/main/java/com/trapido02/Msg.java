package com.trapido02;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {
    // Send local message
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    // Send global message
    public static void serverSend(String message) {
        Bukkit.getServer().broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }

    // Send click event
    public static void serverSendURL(String message, String URL) {
        final TextComponent textComponent2 = Component.text()
                .content(message)
                .color(TextColor.color(NamedTextColor.LIGHT_PURPLE))
                .clickEvent(ClickEvent.openUrl(URL))
                .build();

        Bukkit.getServer().broadcast(textComponent2);
    }
}
