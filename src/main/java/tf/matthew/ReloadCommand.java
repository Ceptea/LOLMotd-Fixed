package tf.matthew;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Collections;

public class ReloadCommand extends Command implements TabExecutor {
    public ReloadCommand() {
        super("lolmotd", "lolmotd.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            LOLMotd.getInstance().loadConfig();
            sender.sendMessage(new TextComponent(ChatColor.GREEN + "Configuration reloaded."));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7")));
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&f⭐ &a&lLOLMotd &8| &7Usage")));
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7")));
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8• &fUsage: &a/lolmotd reload")));
            sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7")));
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
