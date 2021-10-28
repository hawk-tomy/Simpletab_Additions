package stories.simpletab;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpletabAdditions extends JavaPlugin {

    public static Plugin plugin;
    public static FileConfiguration config;
    public static Class clname;

    @Override
    public void onEnable() {
        // Plugin startup logic
        clname = getClass();
        plugin = getPlugin(this.getClass());
        EventGen.EventGeneratorRunner();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
