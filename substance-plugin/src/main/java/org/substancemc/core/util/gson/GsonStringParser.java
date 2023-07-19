package org.substancemc.core.util.gson;

import com.google.gson.Gson;

public class GsonStringParser<T> implements GsonParser<T> {
    @Override
    public T parse(Object toParse, Class<T> clazz) {
        if(!(toParse instanceof String gsonString)) return null;
        return new Gson().fromJson(gsonString, clazz);
    }
}
