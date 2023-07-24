package org.substancemc.core.util.config;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.substancemc.core.SubstancePlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface SubstanceSerializable extends ConfigurationSerializable {

    SubstanceSerializable getInstance();

    @NotNull
    @Override
    default Map<String, Object> serialize()
    {
        Map<String, Object> serialized = new HashMap<>();
        Field[] fields = getInstance().getClass().getDeclaredFields();
        for(Field field : fields)
        {
            if(Modifier.isTransient(field.getModifiers())) continue;
            boolean fieldAccessible = field.canAccess(getInstance());
            field.setAccessible(true);
            try {
                serialized.put(field.getName(), field.get(getInstance()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(fieldAccessible);
        }
        return serialized;
    }
    default SubstanceSerializable deserialize(Map<String, Object> values) throws IllegalAccessException {
        Field[] fields = getInstance().getClass().getDeclaredFields();
        for(Field field : fields)
        {
            if(Modifier.isTransient(field.getModifiers())) continue;
            boolean fieldAccessible = field.canAccess(getInstance());
            field.setAccessible(true);
            if(values.containsKey(field.getName()))
            {
                field.set(getInstance(), values.get(field.getName()));
            }
            field.setAccessible(fieldAccessible);
        }
        postDeserialization();
        return getInstance();
    }

    void postDeserialization();
}
