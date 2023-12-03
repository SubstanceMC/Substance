package org.substancemc.core.addon;

import org.bukkit.configuration.file.YamlConfiguration;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.addon.snippet.AddonSnippet;
import org.substancemc.core.util.config.SubstanceConfiguration;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;

import java.util.ArrayList;
import java.util.List;

public interface SubstanceAddon extends SubstanceManager {

    String getVersion();
    String getId();
    default AddonSnippet<?, ?>[] getSnippets()
    {
        return new AddonSnippet[0];
    }

    default void saveDefaultConfig(String path)
    {
        DataFolderFile file = new DataFolderFile(path + ".yml");
        if(!file.getFile().exists()) SubstancePlugin.get().getResourceExtractor().extract(file, getClass().getClassLoader());
    }

    default SubstanceConfiguration getConfig(String path)
    {
        return SubstanceConfiguration.loadConfiguration(new DataFolderFile(path + ".yml").getFile());
    }
    default boolean showsInMetrics()
    {
        return true;
    }
    default String[] getAuthors()
    {
        return new String[]{};
    }
}
