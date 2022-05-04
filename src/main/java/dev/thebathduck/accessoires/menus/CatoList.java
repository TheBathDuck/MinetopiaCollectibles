package dev.thebathduck.accessoires.menus;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemBuilder;
import dev.thebathduck.accessoires.utils.ItemManager;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CatoList implements Listener {

    public static void open(Player player, String cato) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        String coloredName = Utils.color(config.getString("categorieen." + cato + ".name"));
        Inventory inventory = Bukkit.createInventory(null, 6*9, Utils.color("&6Catogorie: ") + coloredName);
        ItemManager.fillLowerBar(inventory);
        ItemStack arrowBack = new ItemBuilder(Material.SPECTRAL_ARROW).setColoredName("&6Ga terug.")
                .toItemStack();
        inventory.setItem(49, arrowBack);

        // Load funkos
        for(String id : config.getStringList("categorieen." + cato + ".items")) {
            ItemStack accessory = ItemManager.getAccessory(id, player);
            if (accessory == null) {
                Bukkit.broadcastMessage("&aCollectible &4" + id + ": &cniet gevonden.");
            }
            inventory.setItem(inventory.firstEmpty(), accessory);
        }


        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getView().getTitle().contains("Catogorie: ")) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
            if (e.getCurrentItem().getType().equals(Material.SPECTRAL_ARROW)) {
                MenuBrowser.open(player);
                return;
            }
            if(e.getCurrentItem().getType().equals(Material.AIR)) return;
            ItemStack clickedItem = e.getCurrentItem();
            player.getInventory().addItem(clickedItem);
        }
    }
}
