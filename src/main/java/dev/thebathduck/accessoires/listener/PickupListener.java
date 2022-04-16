package dev.thebathduck.accessoires.listener;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

import static dev.thebathduck.accessoires.utils.Utilities.color;
import static dev.thebathduck.accessoires.utils.Utilities.getItemDisplayname;

public class PickupListener implements Listener {
    private final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);
    HashMap<Player, Long> cooldownList = new HashMap<>();
    private int cooldownTime = 3;
    @EventHandler
    public void pickupCollectible(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        if(!(entity instanceof ArmorStand)) return;

        ArmorStand as = (ArmorStand) entity;
        if (!entity.getName().contains("placeable_")) return;

        e.setCancelled(true);
        if (!player.isSneaking())return;

        if(cooldownList.containsKey(player)) {
            player.sendMessage(color("&cWacht tot de cooldown voorbij is vooraleer je terug probeerd! &8(&4" + Math.round((cooldownTime - (Math.round(( - (cooldownList.get(player) - System.currentTimeMillis())) / 100f)/10f)) * 10)/10f + "&8)"));
            return;
        }

        if(!isOwner(player, as.getCustomName())) {

            if(player.hasPermission("collecticles.bypass"))
                player.sendMessage(color("&6Je hebt een item &cgeforceerd &6opgepakt!"));
            else{
                player.sendMessage(color("&cDit item is niet van jouw!"));
                return;
            }
        }
        cooldownList.put(player, System.currentTimeMillis());
        ItemStack item = as.getHelmet();
        item.setAmount(1);
        player.getInventory().addItem(item);
        as.remove();

        player.sendMessage(color("&6Je hebt je &c" + ChatColor.stripColor(getItemDisplayname(item)) + " &6opgepakt."));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            cooldownList.remove(player);
        }, 20L * cooldownTime);
    }

    public boolean isOwner(Player player, String asName) {
        asName = asName.replaceAll("placeable_", "");
        UUID uuid = UUID.fromString(asName);
        return player.getUniqueId().equals(uuid);

    }
}
