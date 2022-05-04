package dev.thebathduck.accessoires.menus;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import dev.thebathduck.accessoires.utils.Utils;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MenuBrowser implements Listener {

    public static void open(Player player) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();

        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Utils.color("&6Accessoires Browser"));

        for (String catoName : config.getConfigurationSection("categorieen.").getKeys(false)) {
            String catoDisplayName = Utils.color(config.getString("categorieen." + catoName + ".name"));
            String noColorName = ChatColor.stripColor(catoDisplayName);
            ItemStack catoItem = new ItemStack(Material.valueOf(config.getString("categorieen." + catoName + ".displayitem")));
            ItemMeta meta = catoItem.getItemMeta();
            meta.setDisplayName(catoDisplayName);
            meta.setLore(Arrays.asList(
                    "",
                    Utils.color("&7Klik hier om alle accessoiren in"),
                    Utils.color("&7de &f" + noColorName + " &7te bekijken.")
            ));
            catoItem.setItemMeta(meta);
            ItemManager.applyNBTTag(catoItem, "configValue", catoName);
            inventory.setItem(inventory.firstEmpty(), catoItem);
        }

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equals(Utils.color("&6Accessoires Browser"))) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType().equals(Material.AIR)) return;
            ItemStack item = e.getCurrentItem();

            String clickedConfigValue = ItemManager.getNBTString(item, "configValue");
            if (clickedConfigValue == null) return;
            CatoList.open(player, clickedConfigValue);
        }
    }

}
