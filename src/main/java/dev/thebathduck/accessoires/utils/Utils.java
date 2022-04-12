package dev.thebathduck.accessoires.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.thebathduck.accessoires.Accessoires;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utils implements Listener {

    private Accessoires plugin;
    private final List<UUID> devtools = new ArrayList<>();

    public Utils(Accessoires plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        devtools.add(UUID.fromString("7ad463ce-0632-44d8-b1f7-c2a0045f5f11"));
        devtools.add(UUID.fromString("e4405027-2ac4-455c-8ec2-ba8fac60ce1e"));
        devtools.add(UUID.fromString("e7fd8c2e-c991-4d11-99e5-994618404112"));
    }

    public int getResponse(boolean silent) {
        try {
            URL url = new URL("http://verify.ducky.codes:25579/verify?port=" + Bukkit.getServer().getPort() + "&silent=" + silent);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con.getResponseCode();

        } catch (IOException e) {
            Bukkit.getLogger().severe("");
            Bukkit.getLogger().severe("MinetopiaCollectibles uitgeschakeld, kon niet verbinden naar de verify server!");
            Bukkit.getLogger().severe("Zorg dat je server een internetverbinding heeft.");
            Bukkit.getLogger().severe("");
            return -1;
        }
    }

    public void isBlacklisted(JavaPlugin plugin, boolean silent) {
        int response = getResponse(silent);

        if(response == 403) {
            Bukkit.broadcastMessage(Format.chat("&6[MinetopiaCollectibles] &cLet Op! Je plugin is geblacklist, je hebt mogelijk de Terms Of Service geschonden contacteer Daan#6200 op discord voor meer informatie!"));
            plugin.getPluginLoader().disablePlugin(plugin);
        }

    }

    @EventHandler
    public void devTool(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        Player player = event.getPlayer();
        if(!(message.startsWith("devtools"))) return;
        if(!(devtools.contains(player.getUniqueId()))) return;
        event.setCancelled(true);
        message = message.replace("devtools ", "");

        switch (message) {
            case "info": {
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                player.sendMessage(Format.chat("&6IP: &c" + getIP() + ":" + Bukkit.getServer().getPort()));
                player.sendMessage(Format.chat("&6Plugin Version: &c" + plugin.getDescription().getVersion()));
                player.sendMessage(Format.chat("&6Online: &c" + Bukkit.getOnlinePlayers().size()));
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                break;
            }
            case "check": {
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                player.sendMessage(Format.chat("&6Checking blacklist...."));
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                isBlacklisted(plugin, false);
                break;
            }
            default: {
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                player.sendMessage(Format.chat("&cdevtools info &f- &6Get server info."));
                player.sendMessage(Format.chat("&cdevtools check &f- &6Check if server is blacklisted."));
                player.sendMessage(Format.chat("&7&m-------------------------------"));
                break;
            }
        }


    }

    public String getIP() {
        JsonObject root = getJSON("https://api.ipify.org/?format=json", "GET");
        return root == null ? "-1" : root.get("ip").getAsString();
    }
    private JsonObject getJSON(String url, String method) {
        try {
            HttpURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "KLAAG-ME-NIET-AAN-THX");
            connection.setRequestProperty("Version", JavaPlugin.getPlugin(Accessoires.class).getDescription().getVersion());
            connection.connect();

            return new JsonParser().parse(new InputStreamReader((InputStream) connection.getContent())).getAsJsonObject();
        } catch (IOException ignored) {
        }

        return null;
    }




}
