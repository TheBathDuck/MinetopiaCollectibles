package dev.thebathduck.accessoires.menus;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemBuilder;
import dev.thebathduck.accessoires.utils.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static dev.thebathduck.accessoires.utils.Utilities.*;

public class CatoList implements Listener {

    private static final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    public static void open(Player player, String cato) {
        FileConfiguration config = plugin.getConfig();

        String coloredName = color(config.getString("categorieen." + cato + ".name"));
        Inventory inventory = Bukkit.createInventory(null, 54, "§6Catogorie: " + coloredName);

        ItemStack arrowBack = new ItemBuilder(Material.SPECTRAL_ARROW).setColoredName("&6Ga terug.")
                .toItemStack();
        inventory.setItem(49, arrowBack);

        // Load funkos
        for(String id : config.getStringList("categorieen." + cato + ".items")) {
            ItemStack accessory = ItemManager.getAccessory(id, player);
            inventory.setItem(inventory.firstEmpty(), accessory);
        }
        fillRowGlass(inventory, 6, 15);

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().contains("§6Catogorie: ")) {
            e.setCancelled(true);
            ItemStack is = e.getCurrentItem();
            if(is == null || is.getType() == Material.AIR) return;
            if (is.getType().equals(Material.SPECTRAL_ARROW)) {
                MenuBrowser.open(player);
                return;
            }
            if (e.getSlot() > 44)return;
            player.getInventory().addItem(is);
        }
    }
}
