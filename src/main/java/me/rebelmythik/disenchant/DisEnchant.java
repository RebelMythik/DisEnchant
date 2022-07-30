package me.rebelmythik.disenchant;

import me.rebelmythik.disenchant.commands.disEnchantCommand;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.MultiLineChart;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        getCommand("disenchantreload").setExecutor(new disEnchantCommand(this));

        saveDefaultConfig();
        updateConfig();
        if (!getConfig().getString("EconomyProvider").equalsIgnoreCase("Vault")) {
            return;
        } else {
            if (hasEconomy()) {
                //get right economy (hopefully)
                RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                eco = economy.getProvider();
            }
        }
        int pluginId = 15894;
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        metrics.addCustomChart(new MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Configuration cfg = this.getConfig().getDefaults();
    public void updateConfig() {
        try {
            if(new File(getDataFolder() + "/config.yml").exists()) {
                boolean changesMade = false;
                YamlConfiguration tmp = new YamlConfiguration();
                tmp.load(getDataFolder() + "/config.yml");
                for(String str : cfg.getKeys(true)) {
                    if(!tmp.getKeys(true).contains(str)) {
                        tmp.set(str, cfg.get(str));
                        changesMade = true;
                    }
                }
                if(changesMade)
                    tmp.save(getDataFolder() + "/config.yml");
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
