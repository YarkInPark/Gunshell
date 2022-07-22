package com.jazzkuh.gunshell.common;

import com.jazzkuh.gunshell.GunshellPlugin;
import com.jazzkuh.gunshell.api.objects.GunshellAmmunition;
import com.jazzkuh.gunshell.api.objects.GunshellFireable;
import com.jazzkuh.gunshell.utils.config.ConfigurationFile;
import com.jazzkuh.gunshell.utils.config.KeyDirectoryConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class WeaponRegistry {

    private final GunshellPlugin plugin;
    private final String FILE_SEPARATOR = "/";

    private @Getter @Setter HashMap<String, GunshellFireable> weapons = new HashMap<>();
    private @Getter @Setter HashMap<String, GunshellAmmunition> ammunition = new HashMap<>();

    private @Getter KeyDirectoryConfiguration weaponConfigurations;
    private @Getter KeyDirectoryConfiguration ammoConfigurations;

    public WeaponRegistry(GunshellPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerFireables(String directory, String defaultFile) {
        ConfigurationFile configurationFile = new ConfigurationFile(plugin, directory + FILE_SEPARATOR + defaultFile, true);
        configurationFile.saveConfig();

        weaponConfigurations = new KeyDirectoryConfiguration(plugin, directory);

        HashMap<String, GunshellFireable> fireableRegistry = new HashMap<>();
        for (String key : getWeaponConfigurations().getConfigurations().keySet()) {
            FileConfiguration fileConfiguration = getWeaponConfigurations().getConfigurations().get(key);
            if (fileConfiguration == null) {
                plugin.getLogger().warning("Weapon configuration for " + key + " could not be loaded.");
                continue;
            }
            ConfigurationSection configuration = fileConfiguration.getConfigurationSection(key);
            if (configuration == null) {
                plugin.getLogger().warning("Weapon configuration for " + key + " could not be loaded.");
                continue;
            }

            GunshellFireable gunshellFireable = new GunshellFireable(key, configuration);
            fireableRegistry.put(key, gunshellFireable);
        }
        setWeapons(fireableRegistry);
        plugin.getLogger().info(getWeapons().size() + " fireables have been loaded into memory.");
    }

    public void registerAmmunition(String directory, String defaultFile) {
        ConfigurationFile configurationFile = new ConfigurationFile(plugin, directory + FILE_SEPARATOR + defaultFile, true);
        configurationFile.saveConfig();

        ammoConfigurations = new KeyDirectoryConfiguration(plugin, directory);

        HashMap<String, GunshellAmmunition> ammoRegistry = new HashMap<>();
        for (String key : getAmmoConfigurations().getConfigurations().keySet()) {
            FileConfiguration fileConfiguration = getAmmoConfigurations().getConfigurations().get(key);
            if (fileConfiguration == null) {
                plugin.getLogger().warning("Ammo configuration for " + key + " could not be loaded.");
                continue;
            }
            ConfigurationSection configuration = fileConfiguration.getConfigurationSection(key);
            if (configuration == null) {
                plugin.getLogger().warning("Ammo configuration for " + key + " could not be loaded.");
                continue;
            }

            GunshellAmmunition gunshellAmmunition = new GunshellAmmunition(key, configuration);
            ammoRegistry.put(key, gunshellAmmunition);
        }
        setAmmunition(ammoRegistry);
        plugin.getLogger().info(getWeapons().size() + " ammunition types have been loaded into memory.");
    }
}
