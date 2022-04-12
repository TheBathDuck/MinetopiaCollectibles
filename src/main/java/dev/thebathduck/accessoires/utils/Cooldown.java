package dev.thebathduck.accessoires.utils;

import dev.thebathduck.accessoires.Accessoires;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

public class Cooldown {
    private static ArrayList<Player> onCooldown = new ArrayList<>();

    public static void setCooldown(Player player) {
        if(onCooldown.contains(player)) return;
        onCooldown.add(player);
        Plugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                onCooldown.remove(player);
            }
        }, 20);
    }

    public static boolean isOnCooldown(Player player) {
        if(onCooldown.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

}
