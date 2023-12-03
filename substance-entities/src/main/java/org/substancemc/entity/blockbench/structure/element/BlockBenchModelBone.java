package org.substancemc.entity.blockbench.structure.element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockBenchModelBone {

    private String name;
    private double[] origin;

    private double[] rotation;

    public double[] getRotation()
    {
        return rotation;
    }
    private transient double[] offset;

    public double[] getOffset()
    {
        return offset;
    }

    public void setOffset(double[] offset)
    {
        this.offset = offset;
    }
    private int color;
    private String uuid;

    //Shows if the bone got shrunk during generation period
    private transient boolean small;
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

    @ApiStatus.Experimental
    public boolean isSmall()
    {
        return small;
    }
    public void setSmall(boolean small)
    {
        this.small = small;
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
