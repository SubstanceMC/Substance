package org.substancemc.entity.blockbench.structure;

import org.substancemc.entity.blockbench.error.BlockBenchParseException;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelCube;
import org.substancemc.entity.blockbench.structure.meta.BlockBenchModelMeta;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelResolution;
import org.substancemc.entity.blockbench.structure.texture.BlockBenchModelTexture;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchModel {
    private BlockBenchModelMeta meta;
    private String name;
    private String model_identifier;
    private BlockBenchModelTexture[] textures;
    private BlockBenchModelCube[] elements;

    private BlockBenchModelResolution resolution;

    public BlockBenchModelResolution getResolution()
    {
        return resolution;
    }
    public void setResolution(BlockBenchModelResolution resolution)
    {
        this.resolution = resolution;
    }
    private BlockBenchModelBone[] outliner;

    public BlockBenchModelMeta getMeta() {
        return meta;
    }

    public void setMeta(BlockBenchModelMeta meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelIdentifier() {
        return model_identifier;
    }

    public void setModelIdentifier(String modelIdentifier) {
        this.model_identifier = modelIdentifier;
    }

    public BlockBenchModelTexture[] getTextures() {
        return textures;
    }

    public void setTextures(BlockBenchModelTexture[] textures) {
        this.textures = textures;
    }

    public BlockBenchModelCube[] getCubes() {
        return elements;
    }

    public void setCubes(BlockBenchModelCube[] cubes) {
        this.elements = cubes;
    }

    public List<BlockBenchModelBone> getBones() throws BlockBenchParseException {
        if(outliner.length != 1) throw new BlockBenchParseException("An BlockBench model has to have exactly one origin bone");
        return getBonesRecursively(outliner[0], new ArrayList<>());
    }

    private List<BlockBenchModelBone> getBonesRecursively(BlockBenchModelBone currentBone, List<BlockBenchModelBone> currentList)
    {
        currentList.add(currentBone);
        for(BlockBenchModelBone bone : currentBone.getBoneChildren())
        {
            getBonesRecursively(bone, currentList);
        }
        return currentList;
    }

    public BlockBenchModelBone getOriginBone() throws BlockBenchParseException {
        if(outliner.length != 1) throw new BlockBenchParseException("An BlockBench model has to have exactly one origin bone");
        return outliner[0];
    }

    public void setBones(BlockBenchModelBone[] bones) {
        this.outliner = bones;
    }

}
