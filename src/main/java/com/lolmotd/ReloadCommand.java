package com.lolmotd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;

public class ReloadCommand extends Command implements TabExecutor {

    public ReloadCommand() {
        super("lolmotd", "lolmotd.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            LOLMotd.getInstance().loadConfig();
            sender.sendMessage(ChatColor.GREEN + "[LOLMotd] Configuration reloaded.");
        } else {
            sender.sendMessage(ChatColor.RED + "[LOLMotd] Usage: /lolmotd reload");
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
