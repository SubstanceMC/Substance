package org.substancemc.core.util.file;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class YAMLJarParser {

    private final File source;
    private final String path;

    public YAMLJarParser(File source, String path)
    {
        this.source = source;
        this.path = path;
    }

    public YamlConfiguration parse() throws IOException, ClassNotFoundException {
        YamlConfiguration toReturn = null;
        if(source == null)
        {
            throw new RuntimeException("No jar specified");
        }
        JarFile jar = new JarFile(source);
        URL[] jarUrl = {new URL("jar:file:" + source.getPath() + "!/")};
        URLClassLoader loader = URLClassLoader.newInstance(jarUrl, getClass().getClassLoader());
        Iterator<JarEntry> iterator = jar.entries().asIterator();
        while(iterator.hasNext())
        {
            JarEntry entry = iterator.next();
            if(entry.isDirectory() || !entry.getName().endsWith(path)) continue;
            InputStream stream = loader.getResourceAsStream(path);
            assert stream != null;
            InputStreamReader reader = new InputStreamReader(stream);
            return YamlConfiguration.loadConfiguration(reader);
        }

        return null;
    }


}
