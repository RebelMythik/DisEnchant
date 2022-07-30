package me.rebelmythik.disenchant.commands;

import me.rebelmythik.disenchant.DisEnchant;
import me.rebelmythik.disenchant.utils.ColorCode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    DisEnchant plugin;
    ColorCode colorCodes = new ColorCode();

    public ReloadCommand(DisEnchant  plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("disenchantreload")) {
            if(!sender.hasPermission("disenchant.reload")) {
                sender.sendMessage(colorCodes.cm(plugin.getConfig().getString("messages.no-permission")));
                return true;
            }
            sender.sendMessage(colorCodes.cm(plugin.getConfig().getString("messages.reload-message")));
            plugin.reloadConfig();
        }
        return false;
    }
}
