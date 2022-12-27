package fr.anthony.procedural;

import org.bukkit.plugin.java.JavaPlugin;

public final class Procedural extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("paste").setExecutor(new CommandPaste());
        System.out.println("Hello, World!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
