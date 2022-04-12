package dev.thebathduck.accessoires.utils;

import dev.thebathduck.accessoires.Accessoires;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.bukkit.Bukkit.getServer;

public class Format {
    public static String chat(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
