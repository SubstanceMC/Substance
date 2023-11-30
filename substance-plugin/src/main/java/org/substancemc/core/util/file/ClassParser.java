package org.substancemc.core.util.file;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassParser<E> {

    private File sourceDir;
    private final Class<E> parentClass;
    private DefiningClassLoader classLoader;

    public ClassParser(File sourceDir, URL[] sourceURls, Class<E> parentClass)
    {
        this.sourceDir = sourceDir;
        this.parentClass = parentClass;
        this.classLoader = new DefiningClassLoader(sourceURls, getClass().getClassLoader());
    }

    public ClassParser(File sourceDir, DefiningClassLoader classLoader, Class<E> parentClass)
    {
        this.sourceDir = sourceDir;
        this.parentClass = parentClass;
        this.classLoader = classLoader;
    }

    public ClassParser(Class<E> parentClass)
    {
        this.parentClass = parentClass;
    }

    public List<Class<?>> parse(File file) throws IOException, ClassNotFoundException, IllegalAccessException {
        ArrayList<Class<?>> toReturn = new ArrayList<>();
        if(classLoader == null) return toReturn;
        if(sourceDir == null)
        {
            throw new RuntimeException("No jar specified");
        }
        try(JarFile jar = new JarFile(file)){
            Iterator<JarEntry> iterator = jar.entries().asIterator();
            while(iterator.hasNext())
            {
                JarEntry entry = iterator.next();
                if(entry.isDirectory() || !entry.getName().endsWith(".class")) continue;
                String className = entry.getName().substring(0, entry.getName().length() - ".class".length()).replace("/", ".");
                Class<?> clazz = classLoader.loadClass(className);
                classLoader.define(clazz);
                toReturn.add(clazz);
            }
        }catch (Exception ignored)
        {

        }
        return toReturn;
    }

    private FilenameFilter getFilter()
    {
        return ((dir, name) -> name.endsWith(".jar"));
    }
    public List<Class<?>> parse() throws IOException, ClassNotFoundException, IllegalAccessException
    {
        List<Class<?>> parsed = new ArrayList<>();
        if(!sourceDir.isDirectory()) return parsed;
        for(File file : Objects.requireNonNull(sourceDir.listFiles(getFilter())))
        {
            parsed.addAll(parse(file));
        }
        return parsed;
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
