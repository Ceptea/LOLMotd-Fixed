package tf.matthew;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import tf.matthew.ReloadCommand;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

public class LOLMotd extends Plugin {

    private static LOLMotd instance;
    private Configuration config;
    private List<String> legacyMotd;
    private List<String> modernMotd;

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
        String prefix = ChatColor.DARK_PURPLE.toString();
        return String.join("\n",
                "",
                prefix + "    __    ____  __    __  ___      __      __",
                prefix + "   / /   / __ \\/ /   /  |/  /___  / /_____/ /",
                prefix + "  / /   / / / / /   / /|_/ / __ \\/ __/ __  / ",
                prefix + " / /___/ /_/ / /___/ /  / / /_/ / /_/ /_/ /  ",
                prefix + "/_____/\\____/_____/_/  /_/\\____/\\__/\\__,_/   ",
                ""
        );
    }

    public void loadConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            File configFile = new File(getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                try (InputStream in = getResourceAsStream("config.yml")) {
                    if (in != null) {
                        Files.copy(in, configFile.toPath());
                    }
                }
            }

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

            this.legacyMotd = colorizeList(config.getStringList("motds.legacy"));
            this.modernMotd = colorizeList(config.getStringList("motds.modern"));

            if (legacyMotd.isEmpty()) {
                legacyMotd = List.of(color("&fLegacy MOTD &8(fallback)"));
            }
            if (modernMotd.isEmpty()) {
                modernMotd = List.of(color("&fModern MOTD &8(fallback)"));
            }

        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not load config.yml.", e);
        }
    }

    private List<String> colorizeList(List<String> rawList) {
        return rawList.stream()
                .map(this::color)
                .toList();
    }

    private String color(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text.replace("%newline%", "\n"));
    }

    public static LOLMotd getInstance() {
        return instance;
    }

    public List<String> getLegacyMotd() {
        return legacyMotd;
    }

    public List<String> getModernMotd() {
        return modernMotd;
    }
}