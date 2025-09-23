package com.lolmotd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.logging.Level;

public class LOLMotd extends Plugin {

    private Configuration config;
    public static String legacyMotd;
    public static String modernMotd;
    private static LOLMotd instance;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        getProxy().getPluginManager().registerListener(this, new MotdListener());
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
        getLogger().info(asciiArt());
    }

    @Override
    public void onDisable() {
        getLogger().info(asciiArt());
    }

    private String asciiArt() {
        String prefix = ChatColor.DARK_RED.toString();
        return "\n" +
                prefix + "    __    ____  __    __  ___      __      __\n" +
                prefix + "   / /   / __ \\/ /   /  |/  /___  / /_____/ /\n" +
                prefix + "  / /   / / / / /   / /|_/ / __ \\/ __/ __  / \n" +
                prefix + " / /___/ /_/ / /___/ /  / / /_/ / /_/ /_/ /  \n" +
                prefix + "/_____\\/\\____/_____/_/  /_/\\____/\\__\\/\\__,_/   \n" +
                prefix + "                                             \n" +
                prefix + "- By @ 14kl - v1.0-SNAPSHOT\n" +
                "\n";
    }

    public void loadConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            File configFile = new File(getDataFolder(), "config.yml");

            if (!configFile.exists()) {
                try (InputStream in = getResourceAsStream("config.yml")) {
                    java.nio.file.Files.copy(in, configFile.toPath());
                }
            }

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

            legacyMotd = formatMotd(config.getString("motds.legacy", "&aLegacy MOTD"));
            modernMotd = formatMotd(config.getString("motds.modern", "&bModern MOTD"));

        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not load config!", e);
        }
    }

    private String formatMotd(String raw) {
        if (raw == null) return "";
        return ChatColor.translateAlternateColorCodes('&', raw.replace("%newline%", "\n"));
    }

    public static LOLMotd getInstance() {
        return instance;
    }

    public class ReloadCommand extends Command implements TabExecutor {
        public ReloadCommand() {
            super("lolmotd", "lolmotd.reload", "motdreload");
        }

        @Override
        public void execute(CommandSender sender, String[] args) {
            loadConfig();
            sender.sendMessage(ChatColor.GREEN + "[LOLMotd] Configuration reloaded.");
        }

        @Override
        public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
            return Collections.emptyList();
        }
    }
}
