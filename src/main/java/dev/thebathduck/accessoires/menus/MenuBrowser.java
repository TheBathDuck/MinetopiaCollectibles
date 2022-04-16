package dev.thebathduck.accessoires.menus;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static dev.thebathduck.accessoires.utils.Utilities.*;

public class MenuBrowser implements Listener {

    private static final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    public static void open(Player player) {
        FileConfiguration config = plugin.getConfig();

        Inventory inventory = Bukkit.createInventory(null, 27, color("&6Accessoires Browser"));

        for (String catoName : config.getConfigurationSection("categorieen").getKeys(false)) {
            String catoDisplayName = color(config.getString("categorieen." + catoName + ".name"));
            String noColorName = ChatColor.stripColor(catoDisplayName);

            ItemStack catoItem = createItemstack(Material.valueOf(config.getString("categorieen." + catoName + ".displayitem")),
                    catoDisplayName,
                    createLore("&7Klik hier om alle accessoiren in de &f" + noColorName + " &7te bekijken."));

            ItemManager.applyNBTTag(catoItem, "configValue", catoName);
            inventory.setItem(inventory.firstEmpty(), catoItem);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals(color("&6Accessoires Browser"))) {
            e.setCancelled(true);

            ItemStack is = e.getCurrentItem();

            if(is == null || is.getType() == Material.AIR) return;

            String clickedConfigValue = ItemManager.getNBTString(is, "configValue");
            if (clickedConfigValue == null) return;
            CatoList.open(player, clickedConfigValue);
        }
    }

}
