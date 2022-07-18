package me.rebelmythik.disenchant.utils;

import me.rebelmythik.disenchant.DisEnchant;
import org.bukkit.entity.Player;

public class EconomyProvider {


    public static long getBalance(DisEnchant plugin, Player player) {
        String currencyname = plugin.getConfig().getString("EconomyProvider");
        // Vault Economy

        switch(plugin.getConfig().getString("EconomyProvider").toUpperCase()) {
            case "VAULT" :


                break;
            case "EXP" :


                break;
            case "PECONOMY" :

        }
        return 0;
    }

    public static void removeBalance(DisEnchant plugin) {
        String currencyname = plugin.getConfig().getString("EconomyProvider");
        // Vault Economy
        if (currencyname == "Vault") {

        }
        // EXP
        if (currencyname == "XP") {


            // PEconomy
        }
        if (currencyname == "PEconomy") {

        }
    }

    public static boolean hasBalance(DisEnchant plugin, Player player) {
        String currencyName = plugin.getConfig().getString("EconomyProvider");
        // Vault Economy
        if (currencyName == "Vault") {
            if (!hasEconomy(plugin)) return false;
            return plugin.eco.hasAccount(player);
        }

        // EXP
        if (currencyName == "XP") {
            return true;

        // PEconomy
        }
        if (currencyName == "PEconomy") {
            return false;
        }

        return false;
    }
    public static boolean canPurchase(DisEnchant plugin, Player player, long cost) {
        if (!hasBalance(plugin, player)) return false;

        if (getBalance(plugin) < cost) return false;

        return true;

    }



    public static boolean hasEconomy(DisEnchant plugin) {
        if (plugin.eco == null) {
            return false;
        }
        return true;
    }
}
