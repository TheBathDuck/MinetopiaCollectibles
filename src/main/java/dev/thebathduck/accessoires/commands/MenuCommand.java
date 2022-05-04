package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.menus.MenuBrowser;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
            player.sendMessage(Utils.color(config.getString("messages.nopermission")));
            return false;
        }

        if(args.length == 1) {

            if (args[0].equalsIgnoreCase("menu")) {
                MenuBrowser.open(player);
                return false;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                player.sendMessage(Utils.color("&6Configuratie herladen.. Check je console voor errors!"));
                return false;
            }
            sendHelp(player);
        }

        sendHelp(player);
        return false;
    }

    public void sendHelp(Player player) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        player.sendMessage(Utils.color("&cMinetopiaCollectibles &6- Versie: &c" + plugin.getDescription().getVersion()));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&6/collectibles &cmenu &6- &7Pak een collectible."));
        player.sendMessage(Utils.color("&6/collectibles &creload &6- &7Herlaad de configuratie."));
        player.sendMessage(Utils.color("&6/editcollectible &cglow &6- &7Laat je collectible gloeien!."));
        player.sendMessage(Utils.color("&6/collectibles &crename <Naam> &6- &7Geef je collectible een naam."));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&6Gemaakt door &c" + plugin.getDescription().getAuthors()));

    }
}
