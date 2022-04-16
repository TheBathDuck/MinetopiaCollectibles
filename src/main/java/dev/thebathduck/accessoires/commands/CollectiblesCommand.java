package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static dev.thebathduck.accessoires.utils.Utilities.color;

public class CollectiblesCommand implements CommandExecutor {

    private final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;

            FileConfiguration config = plugin.getConfig();
            if(!(player.hasPermission("collectibles.edit"))) {
                player.sendMessage(color(config.getString("messages.nopermission")));
                return false;
            }

            player.sendMessage(color("&eMinetopiaCollectibles versie &6" + plugin.getDescription().getVersion() + "&e!"));
            player.sendMessage(color("&7&oCommands:"));
            player.sendMessage(color(" &e/getcollectible <Config Naam> &7- &6Pak een collectible bij naam."));
            player.sendMessage(color(" &e/collectibles &7- &6Scroll door de collectibles via een menu."));
            player.sendMessage(color(" &e/editcollectible <Optie> &7- &6Edit een collectible"));
        }else sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
        return false;
    }
}
