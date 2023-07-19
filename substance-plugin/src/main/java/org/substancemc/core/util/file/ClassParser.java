package org.substancemc.core.util.file;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassParser<E> {

    private final File source;
    private final Class<E> parentClass;

    public ClassParser(File source, Class<E> parentClass)
    {
        this.source = source;
        this.parentClass = parentClass;
    }

    public ClassParser()
    {
        this.source = null;
        this.parentClass = null;
    }


    public Class<? extends E> forName(String classPath)
    {
        try {
            return (Class<? extends E>) Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error parsing classpath " + classPath + " " + e.getMessage());
        }
    }

    public List<Class<?>> parse()
    {
        ArrayList<Class<?>> toReturn = new ArrayList<>();
        if(source == null)
        {
            throw new RuntimeException("No jar specified");
        }
        try(JarFile jar = new JarFile(source))
        {
            URL[] jarUrl = {new URL("jar:file:" + source.getPath() + "!/")};
            URLClassLoader loader = URLClassLoader.newInstance(jarUrl, getClass().getClassLoader());
            Iterator<JarEntry> iterator = jar.entries().asIterator();
            while(iterator.hasNext())
            {
                JarEntry entry = iterator.next();
                if(entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
                String className = entry.getName().substring(0, entry.getName().length() - ".class".length()).replace("/", ".");
                Class<?> clazz = loader.loadClass(className);
                toReturn.add(clazz);
            }
        }catch (Exception e)
        {
            assert source != null;
            throw new RuntimeException("Error parsing file " + source.getPath() + " " + e.getMessage());
        }
        return toReturn;
    }

    public Class<? extends E> findFitting(List<Class<?>> classes)
    {
        if(parentClass == null)
        {
            throw new RuntimeException("No parent specified");
        }

        return (Class<? extends E>) classes.stream().filter(clazz -> clazz.getInterfaces().length > 0 && clazz.getInterfaces()[0].equals(parentClass)).findAny().orElse(null);

    }

}
