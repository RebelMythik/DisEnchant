package me.rebelmythik.disenchant.utils;

import me.rebelmythik.disenchant.DisEnchant;
import org.bukkit.entity.Player;
import ru.soknight.peconomy.api.PEconomyAPI;
import ru.soknight.peconomy.database.model.WalletModel;

public class EconomyProvider {

    public static long getBalance(DisEnchant plugin, Player player) {
        String currencyName = plugin.getConfig().getString("EconomyProvider");
        PEconomyAPI api = PEconomyAPI.get();
        // Vault Economy

        switch (plugin.getConfig().getString("EconomyProvider").toUpperCase()) {
            case "VAULT":
                return (long) plugin.eco.getBalance(player);

            case "EXP":
                return (long) ExperienceGetter.getExp(player);

            case "PECONOMY":
                WalletModel wallet = api.getWallet("SoKnight");
                if(wallet == null) {
                    System.err.println("SoKnight has no wallet!");
                }


                return (float) api.getAmount(plugin.getConfig().getString("WalletName"));
        }
        return 0;
    }

    public static void removeBalance(DisEnchant plugin, Player player, long cost) {
        String currencyName = plugin.getConfig().getString("EconomyProvider");
        // Vault Economy
            switch(plugin.getConfig().getString("EconomyProvider").toUpperCase()) {
                case "VAULT" :
                    plugin.eco.withdrawPlayer(player, cost);

                    break;
                case "EXP" :
                    ExperienceGetter.changeExp(player, (int)(-cost));

                    break;
                case "PECONOMY" :
                    break;
            }
    }

    public static boolean hasBalance(DisEnchant plugin, Player player) {
        String currencyName = plugin.getConfig().getString("EconomyProvider");
        // Vault Economy
        switch(plugin.getConfig().getString("EconomyProvider").toUpperCase()) {
            case "VAULT" :
                if (!hasEconomy(plugin)) return false;
                return plugin.eco.hasAccount(player);

            case "EXP" :
                return true;

            case "PECONOMY" :
                return false;
        }

        return false;
    }
    public static boolean canPurchase(DisEnchant plugin, Player player, long cost) {
        if (!hasBalance(plugin, player)) return false;

        if (getBalance(plugin, player) < cost) return false;

        return true;

    }

    public static boolean hasEconomy(DisEnchant plugin) {
        if (plugin.eco == null) {
            return false;
        }
        return true;
    }
}
