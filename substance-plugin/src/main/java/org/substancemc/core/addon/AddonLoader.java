package org.substancemc.core.addon;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.file.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class AddonLoader {

    private final File addonDir;
    private URL[] urls;
    private DefiningClassLoader classLoader;

    public AddonLoader(DataFolderFile addonDir)
    {
        this.addonDir = addonDir.getFile();
        try{
            this.urls = new AddonURLCollector(this.addonDir).collect();
            this.classLoader = new DefiningClassLoader(urls, getClass().getClassLoader());
        }catch (Exception ignored)
        {
            this.urls = new URL[0];
        }

    }

    ArrayList<SubstanceAddon> loadedAddons = new ArrayList<>();
    private boolean isLoadedAddon(String id)
    {
        return loadedAddons.stream().anyMatch(addon -> addon.getId().equals(id));
    }
    public List<SubstanceAddon> load()
    {
        if(addonDir == null) return new ArrayList<>();
        if(!addonDir.exists())
        {
            boolean ignored = addonDir.mkdirs();
            return new ArrayList<>();
        }

        List<File> loadAgain = new ArrayList<>();
        int maxIterations = 0;
        for(File file : Objects.requireNonNull(addonDir.listFiles(getFilter()))) {
            SubstancePlugin.get().getLogger().info("Loading " + file.getName() + "...");
            maxIterations++;
            try {
                SubstanceAddon loaded = load(new DataFolderFile(file));
                if (loaded != null){
                    loadedAddons.add(loaded);
                }else{
                    loadAgain.add(file);
                }
            } catch (Exception e) {
                loadAgain.add(file);
            }
        }
        int currentIterations = 0;
        while(!loadAgain.isEmpty() && currentIterations < maxIterations)
        {
            List<File> toRemove = new ArrayList<>();
            for(File file : loadAgain)
            {
                try {
                    SubstanceAddon loaded = load(new DataFolderFile(file));
                    if (loaded != null) {
                        loadedAddons.add(loaded);
                        toRemove.add(file);
                    }
                } catch (Exception ignored) {
                }
            }
            toRemove.forEach(loadAgain::remove);
            currentIterations++;
        }

        for(File notLoadable : loadAgain)
        {
            SubstancePlugin.get().getLogger().severe("Could not index Addon in file " + notLoadable.getPath() + ". Reasons could be conflicting or missing dependencies, or malformed Addon Code.");
        }

        return loadedAddons;
    }

    public FilenameFilter getFilter()
    {
        return ((dir, name) -> name.endsWith(".jar"));
    }

    public SubstanceAddon load(File file) throws IOException, ClassNotFoundException, IllegalAccessException {
        YamlConfiguration dependencyConfig = new YAMLJarParser(file, "deps.yml").parse();
        if(dependencyConfig != null) {
            AtomicBoolean valid = new AtomicBoolean(!dependencyConfig.getStringList("deps").isEmpty());
            dependencyConfig.getStringList("deps").forEach(id -> {
                if(!isLoadedAddon(id)) valid.set(false);
            });
            if(!valid.get()) throw new IOException("Can not load addon now, deps not loaded yet!");
        }

        ClassParser<?> parser = new ClassParser<>(file, classLoader, Object.class);
        List<Class<?>> parsed;
        try{
            parsed = parser.parse(file);
        } catch (Exception e)
        {
            return null;
        }
        ClassParser<SubstanceAddon> mainClassFinder = new ClassParser<>(SubstanceAddon.class);
        Class<? extends SubstanceAddon> found = mainClassFinder.findFitting(parsed);
        if(found == null)
        {
            throw new RuntimeException("Could not parse addon " + file.getPath() + ", mainClass not present");
        }
        ClassInstance<? extends SubstanceAddon> instance = new ClassInstance<>(found);
        return instance.newInstance();
    }

    public SubstanceAddon load(DataFolderFile file) throws IOException, ClassNotFoundException, IllegalAccessException {
        if(!file.getFile().exists()) return null;
        return load(file.getFile());
    }

}
