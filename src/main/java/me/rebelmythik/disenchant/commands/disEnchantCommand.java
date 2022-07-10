package me.rebelmythik.disenchant.commands;

import me.rebelmythik.disenchant.DisEnchant;
import me.rebelmythik.disenchant.guis.DisEnchantGUI;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class disEnchantCommand implements CommandExecutor {

    DisEnchant plugin;

    public disEnchantCommand(DisEnchant plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!command.getName().equalsIgnoreCase("disenchant")) return false;
        Player player = (Player) sender;
        PlayerInventory inv = player.getInventory();

        DisEnchantGUI.DisEnchantmentGUI(plugin, player, inv.getItemInMainHand().getEnchantments());
        return true;
    }

}
