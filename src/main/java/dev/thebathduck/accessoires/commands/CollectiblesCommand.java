package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CollectiblesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
            return false;
        }
        Player player = (Player) sender;
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        if(!(player.hasPermission("collectibles.edit"))) {
            player.sendMessage(Utils.color(config.getString("messages.nopermission")));
            return false;
        }

        player.sendMessage(Utils.color("&eMinetopiaCollectibles versie &6" + plugin.getDescription().getVersion() + "&e!"));
        player.sendMessage(Utils.color("&7&oCommands:"));
        player.sendMessage(Utils.color(" &e/getcollectible <Config Naam> &7- &6Pak een collectible bij naam."));
        player.sendMessage(Utils.color(" &e/collectibles &7- &6Scroll door de collectibles via een menu."));
        player.sendMessage(Utils.color(" &e/editcollectible <Optie> &7- &6Edit een collectible"));

        return false;
    }
}
