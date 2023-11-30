package org.substancemc.core.addon;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AddonURLCollector {

    private final File dir;
    public AddonURLCollector(File dir)
    {
        this.dir = dir;
    }

    public URL[] collect() throws MalformedURLException {
        if(!dir.isDirectory()) return new URL[0];
        File[] files = Objects.requireNonNull(dir.listFiles(getFilter()));
        URL[] urls = new URL[files.length];
        for(int i = 0; i < urls.length; i++)
        {
            urls[i] = getFileUrl(files[i]);
        }
        return urls;
    }

    private URL getFileUrl(File file) throws MalformedURLException {
        return new URL("jar:file:" + file.toPath() + "!/");
    }

    private FilenameFilter getFilter()
    {
        return ((dir, name) -> name.endsWith(".jar"));
    }

}
