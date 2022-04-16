package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.menus.MenuBrowser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.thebathduck.accessoires.utils.Utilities.color;

public class MenuCommand implements CommandExecutor {

    private final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            FileConfiguration config = plugin.getConfig();
            if(!(player.hasPermission("collectibles.menu"))) {
                player.sendMessage(color(config.getString("messages.nopermission")));
                return false;
            }

            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("menu")) {
                    MenuBrowser.open(player);
                    return false;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    player.sendMessage(color("&6Configuratie herladen.. Bekijk je console voor fouten!"));
                    return false;
                }
                sendHelp(player);
            }

            sendHelp(player);
        }else sender.sendMessage("Alleen een speler kan dit command uitvoeren!");
        return false;
    }

    public void sendHelp(Player player) {
        player.sendMessage(color("&cMinetopiaCollectibles &6- Versie: &c" + plugin.getDescription().getVersion()));
        player.sendMessage(color(""));
        player.sendMessage(color("&6/collectibles &cmenu &6- &7Pak een collectible."));
        player.sendMessage(color("&6/collectibles &creload &6- &7Herlaad de configuratie."));
        player.sendMessage(color(""));
        player.sendMessage(color("&6Gemaakt door &c" + plugin.getDescription().getAuthors()));
    }
}
