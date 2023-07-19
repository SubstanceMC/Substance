package org.substancemc.core.util.file;


import java.lang.reflect.InvocationTargetException;

public class ClassInstance<E> {

    private final Class<E> clazz;

    public ClassInstance(Class<E> clazz)
    {
        this.clazz = clazz;
    }

    public E newInstance(Object... args)
    {
        Class<?>[] argsClasses = new Class[args.length];
        for(int i = 0; i < args.length; i++) argsClasses[i] = args[i].getClass();
        try {
            return clazz.getDeclaredConstructor(argsClasses).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Exception while creating a new instance for class " + clazz.getName() + " " + e.getMessage());
        }
    }


}
