package org.substancemc.core.util.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class GsonElementParser<T> implements GsonParser<T> {
    @Override
    public T parse(Object toParse, Class<T> clazz) {
        if(!(toParse instanceof JsonElement element)) return null;
        return new Gson().fromJson(element, clazz);
    }
}
