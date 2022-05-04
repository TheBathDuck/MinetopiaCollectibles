package dev.thebathduck.accessoires.listener;

import dev.thebathduck.accessoires.utils.Cooldown;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PickupListener implements Listener {
    @EventHandler
    public void pickupCollectible(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();
        if(!(entity instanceof ArmorStand)) return;
        ArmorStand as = (ArmorStand) entity;
        if (!entity.getName().contains("placeable_")) return;
        e.setCancelled(true);
        if(!player.isSneaking()) return;

        if(!isOwner(player, as.getCustomName())) {

            if(!player.hasPermission("collecticles.bypass")) {
                player.sendMessage(Utils.color("&cDit item is niet van jou!"));
                return;
            } else {
                player.sendMessage(Utils.color("&6Je hebt een item &cgeforceerd &6opgepakt."));
            }
        }

        if(Cooldown.isOnCooldown(player) == true) {
            player.sendMessage(Utils.color("&cJe kan dit niet zo snel doen."));
            return;
        }

        Cooldown.setCooldown(player);
        ItemStack item = as.getHelmet();
        item.setAmount(1);
        player.getInventory().addItem(item);
        as.remove();

        player.sendMessage(Utils.color("&6Je hebt je &c" + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " &6opgepakt."));
    }

    public boolean isOwner(Player player, String asName) {
        asName = asName.replaceAll("placeable_", "");
        UUID uuid = UUID.fromString(asName);
        if(player.getUniqueId().equals(uuid)) {
            return true;
        }
        return false;

    }
}
