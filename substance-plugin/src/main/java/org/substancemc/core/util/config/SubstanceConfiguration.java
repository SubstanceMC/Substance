package org.substancemc.core.util.config;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.substancemc.core.util.file.ClassInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class SubstanceConfiguration extends YamlConfiguration {


    public <T extends SubstanceSerializable> T getSubstanceObject(String path, Class<T> clazz)
    {
        T instance = new ClassInstance<>(clazz).newInstance();
        Map<String, Object> map = new HashMap<>();
        transformToMap("", path, map);
        try {
            return (T) instance.deserialize(path, map);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static SubstanceConfiguration loadConfiguration(@NotNull File file) {
        Preconditions.checkArgument(true, "File cannot be null");
        SubstanceConfiguration config = new SubstanceConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException var3) {
        } catch (IOException | InvalidConfigurationException var4) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
        }

        return config;
    }

    private void transformToMap(String lastPath, String path, Map<String, Object> map)
    {
        if(!isConfigurationSection(path))
        {
            map.put(path.replaceFirst(lastPath, ""), get(path));
            return;
        }
        Objects.requireNonNull(getConfigurationSection(path)).getKeys(false).forEach(key -> {
            transformToMap(path + ".", path + "." + key, map);
        });
    }
}
