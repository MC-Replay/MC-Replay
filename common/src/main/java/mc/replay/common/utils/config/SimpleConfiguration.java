package mc.replay.common.utils.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SimpleConfiguration extends YamlConfiguration {

    private final File file;

    public SimpleConfiguration(JavaPlugin plugin, String name, boolean overwrite) throws Exception {
        this.file = new File(plugin.getDataFolder(), name + ".yml");

        if (!this.file.exists()) {
            plugin.saveResource(name + ".yml", overwrite);
        }

        this.load(this.file);
    }

    public SimpleConfiguration(JavaPlugin plugin, String name) throws Exception {
        this(plugin, name, false);
    }

    public void reload() throws Exception {
        this.load(this.file);
    }

    public void save() throws Exception {
        this.save(this.file);
    }
}
