package dev.thebathduck.accessoires.utils;

import dev.thebathduck.accessoires.Accessoires;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Cooldown {
    private static HashMap<Player, Integer> cooldownList = new HashMap<>();

    private static final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    public static void setCooldown(Player player, int timeInSeconds) {
        cooldownList.put(player, timeInSeconds);


    }

    public static boolean hasCooldown(Player player) {
        return cooldownList.containsKey(player);
    }
}