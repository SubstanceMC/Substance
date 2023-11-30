package org.substancemc.core.util.file;

import java.net.URL;
import java.net.URLClassLoader;

public class DefiningClassLoader extends URLClassLoader {
    public DefiningClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public void define(Class<?> clazz)
    {
        resolveClass(clazz);
    }
}
