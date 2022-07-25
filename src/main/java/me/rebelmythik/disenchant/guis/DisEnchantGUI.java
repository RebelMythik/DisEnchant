package me.rebelmythik.disenchant.guis;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.disenchant.DisEnchant;
import me.rebelmythik.disenchant.utils.EconomyProvider;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        gui.addElement(new StaticGuiElement('B', new ItemStack(Material.BOOK), 1, click -> {return true;}, plugin.getConfig().getString("B.display_name") + plugin.getConfig().getStringList("B.lore")));
        gui.addElement('F', new ItemStack(Material.GRAY_STAINED_GLASS_PANE), click -> true);

        GuiElementGroup enchantList = new GuiElementGroup('S');
        for (int i = 0; i < enchantmentArray.length; i++) {
            final int j = i;
            ItemStack it = new ItemStack(pl.getInventory().getItemInMainHand().getType());
            plugin.getLogger().info(enchantmentArray[j].getName());
            it.addUnsafeEnchantment(enchantmentArray[i], enchs.get(enchantmentArray[i]));
            enchantList.addElement(new DynamicGuiElement('S', (viewer) -> new StaticGuiElement('S', it, click -> {
                //check for currency
                if (EconomyProvider.canPurchase(plugin, pl, plugin.getConfig().getInt("CostToRemove"))) {
                    //take da money
                    EconomyProvider.removeBalance(plugin, pl, plugin.getConfig().getInt("CostToRemove"));
                    //remove enchantment
                    int slot = click.getSlot();

                    pl.getInventory().getItemInMainHand().removeEnchantment(enchantmentArray[j]);
                    click.getGui().setElement(slot, new StaticGuiElement('S', stack, 1, click1 -> {return true;}, enchantmentArray[j].getName() + plugin.getConfig().getStringList("S.lore")));
                    gui.draw();
                } else {
                    //they don't have enough so bully them for being poor
                    pl.sendMessage(plugin.getConfig().getString("messages.NotEnoughXP"));
                }
                return true;
            }, enchantmentArray[j].getName() + plugin.getConfig().getStringList("S.lore"))));

        }
        gui.addElement(enchantList);
        gui.show(pl);
    }
}
