package dev.thebathduck.accessoires.commands;

import com.sun.tools.javac.Main;
import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.Format;
import dev.thebathduck.accessoires.utils.ItemManager;
import jdk.internal.org.jline.reader.ParsedLine;
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

public class GetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
            return false;
        }
        Player player = (Player) sender;
        JavaPlugin plugin = JavaPlugin.getPlugin(Accessoires.class);
        FileConfiguration config = plugin.getConfig();
        if(!(player.hasPermission("accessoires.getitem"))) {
            player.sendMessage(Format.chat(config.getString("messages.nopermission")));
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(Format.chat("&cGebruik: /getitem <Config naam>"));
            return false;
        }
        String id = args[0];

        if(config.getString("items." + id + ".item") == null) {
            player.sendMessage(Format.chat("&cItem niet gevonden."));
            return false;
        }

        String materialString = config.getString("items."+id+".item");
        ItemStack item = new ItemStack(Material.valueOf(materialString));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Format.chat(config.getString("items." + id + ".name")));
        List<String> lore = new ArrayList<>();
        for(String loreLine : config.getStringList("items." + id + ".lore")) {
            lore.add(Format.chat(loreLine));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        String key = config.getString("items."+id+".nbt.key");
        String value = config.getString("items."+id+".nbt.value");
        double height = config.getDouble("items."+id+".height");
        ItemManager.applyNBTTag(item, "isPlaceable", true);
        ItemManager.applyNBTTag(item, key, value);
        ItemManager.applyNBTTag(item, "height", height);

        player.getInventory().addItem(item);
        player.sendMessage(Format.chat("&aTest item ontvangen. &7(Height " + height + ")"));
        return false;
    }
}
