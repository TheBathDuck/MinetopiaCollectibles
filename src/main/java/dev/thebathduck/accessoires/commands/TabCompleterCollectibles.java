package dev.thebathduck.accessoires.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterCollectibles implements TabCompleter {

    private static final String[] completes = {"menu", "reload"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("menu");
            arguments.add("reload");
            return arguments;
        }


        return null;
    }
}
