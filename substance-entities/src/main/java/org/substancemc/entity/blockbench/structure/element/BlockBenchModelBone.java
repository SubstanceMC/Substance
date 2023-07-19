package org.substancemc.entity.blockbench.structure.element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockBenchModelBone {

    private String name;
    private double[] origin;
    private int color;
    private String uuid;
    private boolean visibility;
    private int autouv;
    private JsonElement[] children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getOrigin() {
        return origin;
    }

    public void setOrigin(double[] origin) {
        this.origin = origin;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisible(boolean visibility) {
        this.visibility = visibility;
    }

    public int getAutoUV() {
        return autouv;
    }

    public void setAutoUV(int autoUV) {
        this.autouv = autoUV;
    }

    public List<JsonElement> getCubeChildren() {
        return Arrays.stream(children).filter(JsonElement::isJsonPrimitive).toList();
    }

    public List<BlockBenchModelBone> getBoneChildren() {
        Gson gson = new Gson();
        List<JsonElement> values = Arrays.stream(children).filter(JsonElement::isJsonObject).toList();
        List<BlockBenchModelBone> bones = new ArrayList<>();
        for(JsonElement value : values)
        {
            bones.add(gson.fromJson(value, BlockBenchModelBone.class));
        }
        return bones;
    }

    public void setChildren(JsonElement[] children) {
        this.children = children;
    }

}
