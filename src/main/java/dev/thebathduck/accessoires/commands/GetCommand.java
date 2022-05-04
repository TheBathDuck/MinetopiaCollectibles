package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        if(!(sender.hasPermission("accessoires.getitem"))) {
            sender.sendMessage(Utils.color(config.getString("messages.nopermission")));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(Utils.color("&cGebruik: /givecollectible <Config naam> <Speler>"));
            return false;
        }
        String id = args[0];

        if(config.getString("items." + id + ".item") == null) {
            sender.sendMessage(Utils.color("&cItem niet gevonden."));
            return false;
        }


        if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            target.getInventory().addItem(ItemManager.getAccessory(id, target));
            target.sendMessage(Utils.color("&aJe hebt een collectible ontvangen van &e" + sender.getName()));
        } else {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Utils.color("&cJe kan alleen een collectible geven aan een speler."));
                return false;
            }
            Player player = (Player) sender;
            player.getInventory().addItem(ItemManager.getAccessory(id, player));
            player.sendMessage(Utils.color("&aJe hebt een collectible ontvangen."));
        }
        return false;
    }
}
