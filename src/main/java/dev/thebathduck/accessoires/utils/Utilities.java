package dev.thebathduck.accessoires.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Utilities {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static String getItemDisplayname(ItemStack item){
        if (item.hasItemMeta()){
            if (item.getItemMeta().getDisplayName() != null){
                return item.getItemMeta().getDisplayName();
            }
        }
        return StringUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " "));
    }
    public static ItemStack createItemstack(Material material, String title, ArrayList<String> lore){
        ItemStack stack = new ItemStack(material);
        ItemMeta stack_meta = stack.getItemMeta();
        if (title != null)stack_meta.setDisplayName(title);
        if (lore != null)stack_meta.setLore(lore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public static ArrayList<String> createLore(String... args){
        ArrayList<String> lines = new ArrayList<>();
        for (String arg : args)
            lines.addAll(splitString(color(arg), 40));
        return lines;
    }
    public static ArrayList<String> splitString(String s, int length){
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sentence = new StringBuilder();
        String[] words = s.split(" ");
        String latestColor = "§7";
        for (String word : words) {
            if (word.contains("§"))
                latestColor = "§" + word.charAt(word.indexOf("§") + 1);
            if (sentence.length() + word.length() + 1 > length) {
                list.add(sentence.toString().trim());
                sentence = new StringBuilder();
                sentence.append(latestColor).append(word).append(" ");
            } else sentence.append(latestColor).append(word).append(" ");
        }
        list.add(sentence.toString().trim());
        return list;
    }
    public static ItemStack editItemMeta(ItemStack stack, String displayname, ArrayList<String> lore){
        ItemStack newItem = stack.clone();
        ItemMeta meta = newItem.getItemMeta();
        if (displayname != null)meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        if (lore != null)meta.setLore(lore);
        newItem.setItemMeta(meta);
        return newItem;
    }
    public static ItemStack createGlass(String title, int color, ArrayList<String> lore){
        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color);
        return editItemMeta(stack, title, lore);
    }
    public static void fillRowGlass(Inventory inventory, int row, int color){
        for (int i = 0; i < 9; i++) {
            if (inventory.getItem((row*9)-9+i) == null)inventory.setItem((row*9)-9+i, createGlass(" ", color, null));
        }
    }
}
