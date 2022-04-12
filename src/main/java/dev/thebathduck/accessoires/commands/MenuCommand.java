package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.menus.MenuBrowser;
import dev.thebathduck.accessoires.utils.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.plaf.SplitPaneUI;
import java.awt.*;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
            return false;
        }
        Player player = (Player) sender;
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        if(!(player.hasPermission("collectibles.menu"))) {
            player.sendMessage(Format.chat(config.getString("messages.nopermission")));
            return false;
        }

        if(args.length == 1) {

            if (args[0].equalsIgnoreCase("menu")) {
                MenuBrowser.open(player);
                return false;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                player.sendMessage(Format.chat("&6Configuratie herladen.. Check je console voor errors!"));
                return false;
            }
            sendHelp(player);
        }

        sendHelp(player);
        return false;
    }

    public void sendHelp(Player player) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        player.sendMessage(Format.chat("&cMinetopiaCollectibles &6- Versie: &c" + plugin.getDescription().getVersion()));
        player.sendMessage(Format.chat(""));
        player.sendMessage(Format.chat("&6/collectibles &cmenu &6- &7Pak een collectible."));
        player.sendMessage(Format.chat("&6/collectibles &creload &6- &7Herlaad de configuratie."));
        player.sendMessage(Format.chat(""));
        player.sendMessage(Format.chat("&6Gemaakt door &c" + plugin.getDescription().getAuthors()));

    }
}
