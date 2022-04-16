package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static dev.thebathduck.accessoires.utils.Utilities.color;
import static dev.thebathduck.accessoires.utils.Utilities.createItemstack;

public class GetCommand implements CommandExecutor {

    private final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            FileConfiguration config = plugin.getConfig();

            if (args.length == 1){
                if(player.hasPermission("accessoires.getitem")) {
                    if(config.getString("items." + args[0] + ".item") == null) {
                        player.sendMessage(color("&cItem niet gevonden."));
                        return false;
                    }
                    ArrayList<String> lore = new ArrayList<>();
                    for(String loreLine : config.getStringList("items." + args[0] + ".lore"))
                        lore.add(color(loreLine));
                    String materialString = config.getString("items." + args[0] + ".item");

                    ItemStack item = createItemstack(Material.valueOf(materialString),
                            color(config.getString("items." + args[0] + ".name")),
                            lore);

                    String key = config.getString("items." + args[0] + ".nbt.key");
                    String value = config.getString("items." + args[0] + ".nbt.value");
                    double height = config.getDouble("items." + args[0] + ".height");

                    ItemManager.applyNBTTag(item, "isPlaceable", true);
                    ItemManager.applyNBTTag(item, key, value);
                    ItemManager.applyNBTTag(item, "height", height);

                    player.getInventory().addItem(item);
                    player.sendMessage(color("&aTest item ontvangen. &7(Height " + height + ")"));
                }else player.sendMessage(color(config.getString("messages.nopermission")));
            }else player.sendMessage(color("&cGebruik: /getitem <Config naam>"));
        }else sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
        return false;
    }
}
