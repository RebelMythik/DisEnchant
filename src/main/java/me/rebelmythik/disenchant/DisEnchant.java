package me.rebelmythik.disenchant;

import me.rebelmythik.disenchant.commands.disEnchantCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisEnchant extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("disenchant").setExecutor(new disEnchantCommand(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
