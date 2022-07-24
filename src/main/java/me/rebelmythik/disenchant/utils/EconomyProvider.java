package me.rebelmythik.disenchant.utils;

import me.rebelmythik.disenchant.DisEnchant;
import org.bukkit.entity.Player;
import ru.soknight.peconomy.api.PEconomyAPI;
import ru.soknight.peconomy.database.model.WalletModel;

import java.util.Objects;

public class EconomyProvider {

    public static long getBalance(DisEnchant plugin, Player player) {
        String currencyName = Objects.requireNonNull(plugin.getConfig().getString("EconomyProvider")).toUpperCase();
        PEconomyAPI api = PEconomyAPI.get();
        // Vault Economy

        switch (currencyName) {
            case "VAULT":
                return (long) plugin.eco.getBalance(player);

            case "EXP":
                return ExperienceGetter.getExp(player);

            case "PECONOMY":
                String playerName = player.getName();
                WalletModel wallet = api.getWallet(playerName);
                float balance = wallet.getAmount(Objects.requireNonNull(plugin.getConfig().getString("WalletName")));
                return (long) balance;
        }
        return 0;
    }

    public static void removeBalance(DisEnchant plugin, Player player, long cost) {
        String currencyName = Objects.requireNonNull(plugin.getConfig().getString("EconomyProvider")).toUpperCase();

        // Vault Economy
            switch(currencyName) {
                case "VAULT" :
                    plugin.eco.withdrawPlayer(player, cost);

                    break;
                case "EXP" :
                    ExperienceGetter.changeExp(player, (int)(-cost));

                    break;
                case "PECONOMY" :
                    PEconomyAPI api = PEconomyAPI.get();
                    WalletModel wallet = api.getWallet(player.getName());
                    wallet.takeAmount(plugin.getConfig().getString("WalletName"), plugin.getConfig().getInt("CostToRemove"));
                    api.updateWallet(wallet);
                    break;
            }
    }

    public static boolean hasBalance(DisEnchant plugin, Player player) {
        String currencyName = Objects.requireNonNull(plugin.getConfig().getString("EconomyProvider")).toUpperCase();

        // Vault Economy
        switch(currencyName) {
            case "VAULT" :
                if (!hasEconomy(plugin)) return false;
                return plugin.eco.hasAccount(player);

            case "EXP" :
                return true;

            case "PECONOMY" :
                String playerName = player.getName();
                PEconomyAPI api = PEconomyAPI.get();
                WalletModel wallet = api.getWallet(playerName);
                return wallet != null;

        }

        return false;
    }
    public static boolean canPurchase(DisEnchant plugin, Player player, long cost) {
        if (!hasBalance(plugin, player)) return false;

        return getBalance(plugin, player) >= cost;

    }

    public static boolean hasEconomy(DisEnchant plugin) {
        return plugin.eco != null;
    }
}
