package org.substancemc.core.util.resource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.util.file.DataFolderFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

public class ResourceExtractor {

    public ResourceExtractor(DataFolderFile... files)
    {
        SubstancePlugin.get().saveDefaultConfig();
        for(DataFolderFile file : files)
        {
            extract(file);
        }
    }

    public void extract(DataFolderFile file, @Nullable ClassLoader classLoader)
    {
        saveResource(file, false, classLoader);
    }

    public void extract(DataFolderFile file)
    {
        extract(file, null);
    }

    @Nullable
    public InputStream getResource(@NotNull String filename, @Nullable ClassLoader classLoader) {
        try {
            URL url;
            if(classLoader == null) url = getClass().getClassLoader().getResource(filename);
            else url = classLoader.getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }

    public void saveResource(@NotNull DataFolderFile file, boolean replace, @Nullable ClassLoader classLoader) {
        String resourcePath = file.getRelativePath();
        if (!resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = getResource(resourcePath, classLoader);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
            } else {
                File outFile = file.getFile();
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new DataFolderFile(resourcePath.substring(0, Math.max(lastIndex, 0))).getFile();
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        SubstancePlugin.get().getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    SubstancePlugin.get().getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

}
