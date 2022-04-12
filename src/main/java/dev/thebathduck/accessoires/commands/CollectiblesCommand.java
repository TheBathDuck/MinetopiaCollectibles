package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.Format;
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
            player.sendMessage(Format.chat(config.getString("messages.nopermission")));
            return false;
        }

        player.sendMessage(Format.chat("&eMinetopiaCollectibles versie &6" + plugin.getDescription().getVersion() + "&e!"));
        player.sendMessage(Format.chat("&7&oCommands:"));
        player.sendMessage(Format.chat(" &e/getcollectible <Config Naam> &7- &6Pak een collectible bij naam."));
        player.sendMessage(Format.chat(" &e/collectibles &7- &6Scroll door de collectibles via een menu."));
        player.sendMessage(Format.chat(" &e/editcollectible <Optie> &7- &6Edit een collectible"));

        return false;
    }
}
