package com.trapido02;

import com.trapido02.commands.Live;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;

public final class Streamtools extends JavaPlugin implements Listener {
    private static Streamtools instance;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Starting Streamtools!");

        // Prevent "NullPointerException"
        Objects.requireNonNull(getCommand("stream")).setTabCompleter(new Live());
        Objects.requireNonNull(getCommand("stream")).setExecutor(new Live());
    }

    @Override
    public @NotNull Logger getSLF4JLogger() {
        return super.getSLF4JLogger();
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down Streamtools...");
    }

    public static Streamtools getInstance() {
        return instance;
    }
}
