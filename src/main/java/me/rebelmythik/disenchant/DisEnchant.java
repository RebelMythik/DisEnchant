package me.rebelmythik.disenchant;

import me.rebelmythik.disenchant.commands.disEnchantCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisEnchant extends JavaPlugin {

    public Economy eco = null;

    public boolean hasEconomy() {
        try {
            RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if (economy != null) return true;
        } catch(Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("disenchant").setExecutor(new disEnchantCommand(this));
        saveDefaultConfig();
        if (!getConfig().getString("EconomyProvider").equalsIgnoreCase("Vault")) {
            return;
        } else {
            if (hasEconomy()) {
                //get right economy (hopefully)
                RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                eco = economy.getProvider();
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
