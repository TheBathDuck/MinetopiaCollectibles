package dev.thebathduck.accessoires.utils;

import dev.thebathduck.accessoires.Accessoires;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static void fillLowerBar(Inventory inventory) {
        ItemStack empty = getEmptyPane();
        int slot = 45;
        for (int i = 0; i < 9; i++) {
            inventory.setItem(slot, empty);
            slot++;
        }
    }


    public static ItemStack getEmptyPane() {
        ItemStack emptyPane = new ItemBuilder(Material.STAINED_GLASS_PANE)
                .setDurability((short) 0).setColoredName("&6 ").toItemStack();
        return emptyPane;
    }


    public static void applyNBTTag(ItemStack itemStack, String key, Object value) {
        ItemStack is = NBTEditor.set(itemStack, value, key);
        ItemMeta itemMeta = is.getItemMeta();
        itemStack.setItemMeta(itemMeta);
    }

    public static void removeNBTTag(ItemStack stack, String obj) {
        ItemStack is = NBTEditor.set(stack, null, obj);
        ItemMeta meta = is.getItemMeta();
        stack.setItemMeta(meta);
    }

    public static int getNBTint(ItemStack item, String object) {
        return NBTEditor.getInt(item, object);
    }

    public static boolean getNBTboolean(ItemStack item, Object object) {
        return NBTEditor.getBoolean(item, object);
    }

    public static double getNBTdouble(ItemStack item, Object object) {
        return NBTEditor.getDouble(item, object);
    }

    public static String getNBTString(ItemStack item, String object) {
        return NBTEditor.getString(item, object);
    }

    public static ItemStack getAccessory(String id, Player player) {
        FileConfiguration config = JavaPlugin.getPlugin(Accessoires.class).getConfig();
        String materialString = config.getString("items."+id+".item");
        if(materialString == null) {
            player.sendMessage(Utils.color("&cEr ging iets mis met het aanmaken van de collectible &4" + id + "&c. (MOGELIJK_CONFIG_ERROR)"));
        }
        ItemStack item = new ItemStack(Material.valueOf(materialString));
        if(item == null) {
            player.sendMessage(Utils.color("&cEr ging iets mis met het aanmaken van de collectible &4" + id + "&c. (MOGELIJK_CONFIG_ERROR)"));
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.color(config.getString("items." + id + ".name")));
        List<String> lore = new ArrayList<>();
        for(String loreLine : config.getStringList("items." + id + ".lore")) {
            lore.add(Utils.color(loreLine));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        String key = config.getString("items."+id+".nbt.key");
        String value = config.getString("items."+id+".nbt.value");
        double height = config.getDouble("items."+id+".height");
        applyNBTTag(item, "isPlaceable", true);
        applyNBTTag(item, key, value);
        applyNBTTag(item, "height", height);

        return item;
    }
}