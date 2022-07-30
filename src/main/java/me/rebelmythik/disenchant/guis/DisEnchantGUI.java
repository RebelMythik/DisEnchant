package me.rebelmythik.disenchant.guis;

import de.themoep.inventorygui.*;
import me.rebelmythik.disenchant.DisEnchant;
import me.rebelmythik.disenchant.utils.ColorCode;
import me.rebelmythik.disenchant.utils.EconomyProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DisEnchantGUI {

    public static void DisEnchantmentGUI(DisEnchant plugin, Player pl, Map<Enchantment, Integer> enchs) {
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
        List<String> bookLore = plugin.getConfig().getStringList("ItemConfiguration.B.lore");
        bookLore.add(0, plugin.getConfig().getString("ItemConfiguration.B.display_name"));
        String[] lore = new String[bookLore.size()];
        lore = bookLore.toArray(lore);

        gui.addElement(new StaticGuiElement('B', new ItemStack(Material.BOOK), 1, (GuiElement.Action) click -> {return true;}, lore));
        gui.addElement('F', new ItemStack(Material.GRAY_STAINED_GLASS_PANE), click -> true);
        GuiElementGroup enchantList = new GuiElementGroup('S');
        for (int i = 0; i < enchantmentArray.length; i++) {
            final int j = i;
            ItemStack it = new ItemStack(pl.getInventory().getItemInMainHand().getType());
            it.addUnsafeEnchantment(enchantmentArray[i], enchs.get(enchantmentArray[i]));
            List<String> itemLore = plugin.getConfig().getStringList("ItemConfiguration.S.lore");
            itemLore.add(0, enchantmentArray[j].getName());
            String[] itLore = new String[itemLore.size()];
            itLore = itemLore.toArray(itLore);
            ColorCode colorCodes = new ColorCode();


            String[] finalItLore = itLore;
            enchantList.addElement(new DynamicGuiElement('S', (viewer) -> new StaticGuiElement('S', it, click -> {
                //check for currency
                if (EconomyProvider.canPurchase(plugin, pl, plugin.getConfig().getInt("CostToRemove"))) {
                    //take da money
                    EconomyProvider.removeBalance(plugin, pl, plugin.getConfig().getInt("CostToRemove"));
                    //remove enchantment
                    int slot = click.getSlot();

                    pl.getInventory().getItemInMainHand().removeEnchantment(enchantmentArray[j]);
                    click.getGui().setElement(slot, new StaticGuiElement('S', stack, 1, click1 -> {return true;}, "&cEnchantment Removed"));
                    gui.draw();
                } else {
                    //they don't have enough so bully them for being poor
                    pl.sendMessage(colorCodes.cm(plugin.getConfig().getString("messages.NotEnough")));
                }
                return true;
            }, finalItLore)));

        }
        gui.addElement(enchantList);
        gui.show(pl);
    }
}
