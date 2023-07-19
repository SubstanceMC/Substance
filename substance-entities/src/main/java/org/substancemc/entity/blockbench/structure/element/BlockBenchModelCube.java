package org.substancemc.entity.blockbench.structure.element;


import org.substancemc.core.resourcepack.structure.minecraft.model.ModelCube;
import org.substancemc.core.resourcepack.structure.minecraft.model.ModelElementFacing;

public class BlockBenchModelCube extends ModelCube {
    private String name;
    private double[] from;
    private double[] to;
    private int autouv;
    private int color;
    private double[] origin;
    private ModelElementFacing faces;
    private String uuid;
    private String type;

    private double inflate;

    public BlockBenchModelCube(double[] from, double[] to, ModelElementFacing faces) {
        super(from, to, faces);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getFromOriginal() {
        return from;
    }

    public double[] getFrom()
    {
        double[] withInflate = new double[from.length];
        for(int i = 0; i < withInflate.length; i++)
        {
            withInflate[i] = from[i] - inflate;
        }
        return withInflate;
    }

    public void setFrom(double[] from) {
        this.from = from;
    }

    public double[] getToOriginal() {
        return to;
    }

    public double[] getTo()
    {
        double[] withInflate = new double[from.length];
        for(int i = 0; i < withInflate.length; i++)
        {
            withInflate[i] = from[i] + inflate;
        }
        return withInflate;
    }

    public void setTo(double[] to) {
        this.to = to;
    }

    public int getAutoUV() {
        return autouv;
    }

    public void setAutoUV(int autoUV) {
        this.autouv = autoUV;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double[] getOrigin() {
        return origin;
    }

    public void setOrigin(double[] origin) {
        this.origin = origin;
    }

    public double getInflate() {
        return inflate;
    }

    public void setInflate(double inflate)
    {
        this.inflate = inflate;
    }

    public ModelElementFacing getFaces() {
        return faces;
    }

    public void setFaces(ModelElementFacing faces) {
        this.faces = faces;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
