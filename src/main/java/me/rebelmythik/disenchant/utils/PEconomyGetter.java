package me.rebelmythik.disenchant.utils;

import me.rebelmythik.disenchant.DisEnchant;
import org.bukkit.entity.Player;
import ru.soknight.peconomy.api.PEconomyAPI;
import ru.soknight.peconomy.database.model.WalletModel;

public class PEconomyGetter {

    public static void removeFromWallet(DisEnchant plugin, Player player) {
        PEconomyAPI api = PEconomyAPI.get();

        WalletModel wallet = api.getWallet(player.getName());
        if (wallet == null) {
            return;
        }

        wallet.takeAmount("tokens", plugin.getConfig().getInt("CostToRemove"));
    }
}
