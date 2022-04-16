package dev.thebathduck.accessoires.listener;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.thebathduck.accessoires.utils.Utilities.color;
import static dev.thebathduck.accessoires.utils.Utilities.getItemDisplayname;

public class PlaceListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST) // Omdat mtvehicles schijt is. - KingDevCode: Ik ga akkoord
    public void onPlace(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        ItemStack item = e.getItem();
        Block block = e.getClickedBlock();

        if (item == null || block == null || e.getHand() != EquipmentSlot.HAND) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!ItemManager.getNBTboolean(item, "isPlaceable")) return;

        double height = ItemManager.getNBTdouble(item, "height");
        Location location = block.getLocation();
        Location fixedLocation = new Location(location.getWorld(), location.getX() + 0.5, location.getY() - height, location.getZ() + 0.5, player.getLocation().getYaw() + 180, location.getPitch());
        String blockString = block.getType().toString();

        if (blockString.contains("SLAB") || blockString.contains("STEP")) {
            if (block.getData() < 8) {
                fixedLocation.subtract(0, 0.5, 0);
            }
        }
        if (blockString.contains("CARPET") || blockString.contains("SNOW")) {
            if (block.getData() < 8) {
                fixedLocation.subtract(0, 0.96, 0);
                if(blockString.contains("snow")) {
                    fixedLocation.add(0, 0.06, 0);
                }
            }
        }
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(fixedLocation, EntityType.ARMOR_STAND);

        as.setGravity(false);
        as.setCustomName(color("placeable_" + player.getUniqueId() + ""));
        as.setVisible(false);
        as.setInvulnerable(true);

        ItemStack hand = player.getInventory().getItemInMainHand();
        as.setHelmet(hand);
        hand.setAmount(hand.getAmount() - 1);

        // Check for debugmode
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        as.setCustomNameVisible(config.getBoolean("development.debugmode"));

        player.sendMessage(color("&6Je hebt je &c" + ChatColor.stripColor(getItemDisplayname(item)) + " &6geplaatst."));
    }
}
