package me.rebelmythik.disenchant.guis;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.disenchant.DisEnchant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisEnchantGUI {

    public static void DisEnchantmentGUI(DisEnchant plugin, Player pl, Map<Enchantment, Integer> enchs) {
        Player player = pl;
        Enchantment[] enchantmentArray = enchs.keySet().toArray(new Enchantment[enchs.size()]);
        List<String> guiConfig = plugin.getConfig().getStringList("GUI");
        String[] guiSetup = new String[guiConfig.size()];
        int c = 0;
        for (String str : guiConfig) {
            guiSetup[c] = str;
            c++;
        }

        InventoryGui gui = new InventoryGui(plugin, null, "DisEnchant Menu", guiSetup);

        ItemStack stack = new ItemStack(Material.RED_CONCRETE);
        gui.setFiller(stack);
        gui.addElement('1', new ItemStack(Material.BOOK), click -> true);
        gui.addElement('F', new ItemStack(Material.GRAY_STAINED_GLASS_PANE), click -> true);


        GuiElementGroup enchantList = new GuiElementGroup('g');
        for (int i = 0; i < enchantmentArray.length; i++) {
            final int j = i;
            ItemStack it = new ItemStack(player.getInventory().getItemInMainHand().getType());
            plugin.getLogger().info(enchantmentArray[j].getName());
            it.addUnsafeEnchantment(enchantmentArray[i], enchs.get(enchantmentArray[i]));
            enchantList.addElement(new DynamicGuiElement('g', (viewer) -> new StaticGuiElement('g', it, click -> {
                //check for currency

                    pl.getInventory().getItemInMainHand().removeEnchantment(enchantmentArray[j]);
                    click.getGui().setElement(slot, new StaticGuiElement('g', stack, 1, click1 -> {return true;}, enchantmentArray[j].getName() + " Successfully Removed", "&c", "E", "e"));
                    gui.draw();
                } else {
                    //they don't have enough so bully them for being poor
                    pl.sendMessage(plugin.getConfig().getString("messages.NotEnoughXP"));
                }
                return true;
            }, enchantmentArray[j].getName())));

        }
        gui.addElement(enchantList);
        gui.show(pl);
    }
}
