package org.perdume.wildharder;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Wild_Harder extends JavaPlugin implements Listener {

    Manager mn;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(), this);
        this.getCommand("wildhard").setExecutor(new Command());
        mn = new Manager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
