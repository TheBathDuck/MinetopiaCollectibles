package dev.thebathduck.accessoires.commands;

import dev.thebathduck.accessoires.Accessoires;
import dev.thebathduck.accessoires.utils.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static dev.thebathduck.accessoires.utils.Utilities.color;

public class EditCommand implements CommandExecutor {

    private final Accessoires plugin = Accessoires.getPlugin(Accessoires.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            FileConfiguration config = plugin.getConfig();
            if(!(player.hasPermission("accessoires.getitem"))) {
                player.sendMessage(color(config.getString("messages.nopermission")));
                return false;
            }

            if(args.length == 0) {
                sendErrorMessage(player);
                return false;
            }else if (args.length == 1){
                if(args[0].equalsIgnoreCase("glow")) {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    boolean nbtValue = ItemManager.getNBTboolean(item, "isPlaceable");
                    if(item == null || !nbtValue) {
                        player.sendMessage(color("&cGeen geldig item in je hand."));
                        return false;
                    }
                    ItemMeta meta = item.getItemMeta();
                    if(meta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        item.setItemMeta(meta);
                        item.removeEnchantment(Enchantment.ARROW_DAMAGE);
                        player.sendMessage(color("&eJe hebt het glow effect verwijderd!"));
                    }else{
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        item.setItemMeta(meta);
                        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                        player.sendMessage(color("&eJe hebt een glow effect toegevoegd!"));
                    }
                }
            }else{
                if(args[0].equalsIgnoreCase("rename")) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 1; i < args.length; i++)
                        builder.append(args[i]).append(" ");
                    String msg = builder.toString();
                    ItemStack item = player.getInventory().getItemInMainHand();
                    boolean nbtValue = ItemManager.getNBTboolean(item, "isPlaceable");
                    if (item != null && nbtValue) {
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(color(msg));
                        item.setItemMeta(meta);
                        player.sendMessage(color("&eItem naam veranderd naar: " + msg));
                    }else player.sendMessage(color("&cJe hebt geen collectible vast."));
                }else sendErrorMessage(player);
            }
        }else sender.sendMessage("Alleen een speler kan dit command uitvoeren.");
        return false;
    }
    private void sendErrorMessage(Player player){
        player.sendMessage(color("&cGeen argument opgegeven."));
        player.sendMessage(color("&e/editcollect glow &f- &7&oLaat je collectible gloeien!"));
        player.sendMessage(color("&e/editcollect rename <Naam> &f- &7&oVerander de naam van je collectible"));
    }
}
