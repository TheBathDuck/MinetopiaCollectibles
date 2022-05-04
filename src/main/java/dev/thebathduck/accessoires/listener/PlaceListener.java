package dev.thebathduck.accessoires.listener;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlaceListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST) // Omdat mtvehicles schijt is.
    public void onPlace(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getItem() == null) return;
        if(e.getClickedBlock() == null) return;
        ItemStack item = e.getItem();
        Block block = e.getClickedBlock();
        if (!(e.getHand().equals(EquipmentSlot.HAND))) return;
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(!(ItemManager.getNBTboolean(item, "isPlaceable") == true)) return;
        double height = ItemManager.getNBTdouble(item, "height");
        Location location = block.getLocation();
        Location fixedLocation = new Location(location.getWorld(), location.getX() + 0.5, location.getY() - height, location.getZ() + 0.5, player.getLocation().getYaw() + 180, location.getPitch());
        String blockString = block.getType().toString().toLowerCase();
        if (blockString.contains("slab") || blockString.contains("step")) {
            if (block.getData() < 8) {
                fixedLocation.subtract(0, 0.5, 0);
            }
        }
        if (blockString.contains("carpet") || blockString.contains("snow")) {
            if (block.getData() < 8) {
                fixedLocation.subtract(0, 0.96, 0);
                if(blockString.contains("snow")) {
                    fixedLocation.add(0, 0.06, 0);
                }
            }
        }
        ArmorStand as = (ArmorStand) location.getWorld().spawn(fixedLocation, ArmorStand.class);

        as.setGravity(false);
        as.setCustomName(Utils.color("placeable_" +player.getUniqueId() + ""));

        // Check for debugmode
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        if(config.getBoolean("development.debugmode")) {
            as.setCustomNameVisible(true);
        } else {
            as.setCustomNameVisible(false);
        }

        as.setVisible(false);
        as.setInvulnerable(true);

        ItemStack hand = player.getInventory().getItemInHand();
        as.setHelmet(hand);
        if (hand.getAmount() != 1) {
            hand.setAmount(hand.getAmount() -1);
        } else {
            hand.setAmount(hand.getAmount() - 1);
            player.getInventory().setItemInHand(hand);
        }
        player.sendMessage(Utils.color("&6Je hebt je &c" + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " &6geplaatst."));



    }



}
