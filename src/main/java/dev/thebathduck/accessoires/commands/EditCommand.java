package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import dev.thebathduck.accessoires.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class EditCommand implements CommandExecutor {
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
            player.sendMessage(Utils.color(config.getString("messages.nopermission")));
            return false;
        }

        if(args.length == 0) {
            player.sendMessage(Utils.color("&cGeen argument opgegeven."));
            player.sendMessage(Utils.color("&e/editcollect glow &f- &7&oLaat je collectible gloeien!"));
            player.sendMessage(Utils.color("&e/editcollect rename <Naam> &f- &7&oVerander de naam van je collectible"));
            return false;
        }
        if(args[0].equalsIgnoreCase("glow")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean nbtValue = ItemManager.getNBTboolean(item, "isPlaceable");
            if(item == null || !nbtValue) {
                player.sendMessage(Utils.color("&cGeen geldig item in je hand."));
                return false;
            }
            ItemMeta meta = item.getItemMeta();
            if(item.getItemMeta().hasEnchant(Enchantment.ARROW_DAMAGE)) {
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);
                item.removeEnchantment(Enchantment.ARROW_DAMAGE);
                player.sendMessage(Utils.color("&eGlow effect verwijderd!"));
                return false;
            }
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            player.sendMessage(Utils.color("&eGlow effect toegevoegd!"));
            return false;
        }

        if(args[0].equalsIgnoreCase("rename")) {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            String msg = builder.toString();
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean nbtValue = ItemManager.getNBTboolean(item, "isPlaceable");
            if(item == null || nbtValue) {
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Utils.color(msg));
                item.setItemMeta(meta);
                player.sendMessage(Utils.color("&eItem naam veranderd naar: " + msg));
                return false;
            }
            player.sendMessage(Utils.color("&cJe hebt geen collectible vast."));
            return false;
        }


        player.sendMessage(Utils.color("&cGeen argument opgegeven."));
        player.sendMessage(Utils.color("&e/editcollect glow &f- &7&oLaat je collectible gloeien!"));
        player.sendMessage(Utils.color("&e/editcollect rename <Naam> &f- &7&oVerander de naam van je collectible"));
        return false;
    }
}
