package com.trapido02;

import com.trapido02.commands.Stream;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Streamtools extends JavaPlugin {
    private static Streamtools instance;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Starting Streamtools!");

        // Prevent "NullPointerException"
        Objects.requireNonNull(getCommand("stream")).setExecutor(new Stream());
    }

    @Override
    public void onDisable() {
        System.out.println("Shutting down Streamtools...");
    }

    public static Streamtools getInstance() {
        return instance;
    }
}
