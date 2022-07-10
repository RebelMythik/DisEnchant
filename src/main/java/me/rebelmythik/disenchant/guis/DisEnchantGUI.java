package me.rebelmythik.disenchant.guis;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import me.rebelmythik.disenchant.DisEnchant;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DisEnchantGUI {

    public static void DisEnchantmentGUI(DisEnchant plugin, Player pl, Map<Enchantment, Integer> enchs) {
        Player player = pl;
        Enchantment[] enchantmentArray = enchs.keySet().toArray(new Enchantment[enchs.size()]);


        String[] guiSetup = {
                "FFFF1FFFF",
                "ggggggggg",
                "ggggggggg",
                "ggggggggg",
                "FFFFFFFFF"
        };

        InventoryGui gui = new InventoryGui(plugin, null, "DisEnchant Menu", guiSetup);
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS, 1));
        gui.addElement('1', new ItemStack(Material.BOOK), click -> {return true;});
        gui.addElement('F', new ItemStack(Material.GRAY_STAINED_GLASS_PANE), click -> {return true;});

        GuiElementGroup enchantList = new GuiElementGroup('g');
        for (int i = 0; i < enchantmentArray.length; i++) {
            final int j = i;
            ItemStack it = new ItemStack(player.getInventory().getItemInMainHand().getType());
            it.addUnsafeEnchantment(enchantmentArray[i], enchs.get(enchantmentArray[i]));
            enchantList.addElement(new DynamicGuiElement('g', (viewer) -> new StaticGuiElement('g', it, click -> {
                pl.getInventory().getItemInMainHand().removeEnchantment(enchantmentArray[j]);
                ArrayUtils.remove(enchantmentArray, j);
                gui.draw();
                return true;
            }, enchantmentArray[j].getName())));
        }
        gui.addElement(enchantList);
        gui.show(pl);
    }
}
