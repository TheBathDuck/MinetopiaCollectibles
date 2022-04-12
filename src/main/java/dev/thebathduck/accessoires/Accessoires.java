package dev.thebathduck.accessoires;

import dev.thebathduck.accessoires.commands.*;
import dev.thebathduck.accessoires.listener.PickupListener;
import dev.thebathduck.accessoires.listener.PlaceListener;
import dev.thebathduck.accessoires.menus.CatoList;
import dev.thebathduck.accessoires.menus.MenuBrowser;
import dev.thebathduck.accessoires.utils.Format;
import dev.thebathduck.accessoires.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Accessoires extends JavaPlugin {

    private Utils utils;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        utils = new Utils(this);

        utils.isBlacklisted(this, false);

        Bukkit.getPluginManager().registerEvents(new PlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuBrowser(), this);
        Bukkit.getPluginManager().registerEvents(new CatoList(), this);
        Bukkit.getPluginManager().registerEvents(new PickupListener(), this);

        Bukkit.getServer().getPluginCommand("getcollectible").setExecutor(new GetCommand());

        Bukkit.getServer().getPluginCommand("collectibles").setExecutor(new MenuCommand());
        Bukkit.getServer().getPluginCommand("collectibles").setTabCompleter(new TabCompleterCollectibles());

        Bukkit.getServer().getPluginCommand("editcollectible").setExecutor(new EditCommand());
        Bukkit.getServer().getPluginCommand("mtcollectibles").setExecutor(new CollectiblesCommand());


        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> utils.isBlacklisted(this, true), 0, 24000); // Om de 20 minuten.

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
