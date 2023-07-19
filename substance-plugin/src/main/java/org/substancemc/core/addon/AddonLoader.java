package org.substancemc.core.addon;

import org.substancemc.core.util.file.ClassInstance;
import org.substancemc.core.util.file.ClassParser;
import org.substancemc.core.util.file.DataFolderFile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddonLoader {

    private final File addonDir;

    public AddonLoader(DataFolderFile addonDir)
    {
        this.addonDir = addonDir.getFile();
    }

    public List<SubstanceAddon> load()
    {
        ArrayList<SubstanceAddon> loadedAddons = new ArrayList<>();
        if(addonDir == null) return new ArrayList<>();
        if(!addonDir.exists())
        {
            boolean ignored = addonDir.mkdirs();
            return new ArrayList<>();
        }
        for(File file : Objects.requireNonNull(addonDir.listFiles(getFilter())))
        {
            SubstanceAddon loaded = load(new DataFolderFile(file));
            if(loaded != null) loadedAddons.add(loaded);
        }
        return loadedAddons;
    }

    public FilenameFilter getFilter()
    {
        return ((dir, name) -> name.endsWith(".jar"));
    }

    public SubstanceAddon load(File file)
    {
        ClassParser<?> parser = new ClassParser<>(file, Object.class);
        List<Class<?>> parsed = parser.parse();
        ClassParser<SubstanceAddon> mainClassFinder = new ClassParser<>(file, SubstanceAddon.class);
        Class<? extends SubstanceAddon> found = mainClassFinder.findFitting(parsed);
        if(found == null)
        {
            throw new RuntimeException("Could not parse addon " + file.getPath() + ", mainClass not present");
        }
        ClassInstance<? extends SubstanceAddon> instance = new ClassInstance<>(found);
        return instance.newInstance();
    }

    public SubstanceAddon load(DataFolderFile file)
    {
        if(!file.getFile().exists()) return null;
        return load(file.getFile());
    }

}
