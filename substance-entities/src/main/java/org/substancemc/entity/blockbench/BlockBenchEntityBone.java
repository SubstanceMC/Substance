package org.substancemc.entity.blockbench;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.entity.blockbench.convert.BlockBenchVector;
import org.substancemc.entity.blockbench.convert.util.BlockBenchRotationVector;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.entity.SubstanceEntityBone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockBenchEntityBone implements SubstanceEntityBone {

    private final ItemStack visuals;
    private final BlockBenchModelBone bone;
    private final List<String> children = new ArrayList<>();
    private Transformation transformation = null;

    public BlockBenchEntityBone(BlockBenchModelBone bone, ItemStack visuals)
    {
        this.bone = bone;
        this.visuals = visuals;
        BlockBenchRotationVector rotation = new BlockBenchRotationVector(bone.getRotation());
        addTransformation(new Transformation(new BlockBenchVector(bone.getOrigin()).mul(new Vector3d(-1, 1, -1)).mul(0.0625).get(new Vector3f(0, 0, 0)), rotation.getLeftRotation(), new Vector3d(1, 1, 1).get(new Vector3f()), rotation.getRightRotation()));
        bone.getBoneChildren().forEach(child -> children.add(child.getUUID()));
    }

    @Override
    public ItemStack getVisuals() {
        return visuals;
    }

    @Override
    public Transformation getTransformation() {
        return transformation;
    }

    @Override
    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void addTransformation(Transformation transformation)
    {
        if(this.transformation == null)
        {
            this.transformation = transformation;
            return;
        }
        this.transformation = new Transformation(
                this.transformation.getTranslation().add(transformation.getTranslation()),
                this.transformation.getLeftRotation().add(transformation.getLeftRotation()),
                this.transformation.getScale().add(transformation.getScale()),
                this.transformation.getRightRotation().add(transformation.getRightRotation())
                );
    }

    @Override
    public String getID() {
        return bone.getUUID();
    }

    @Override
    public List<String> getChildren() {
        return children;
    }
}
