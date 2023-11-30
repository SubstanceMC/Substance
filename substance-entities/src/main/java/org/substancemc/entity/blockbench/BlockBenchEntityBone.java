package org.substancemc.entity.blockbench;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;
import org.substancemc.entity.entity.SubstanceEntityBone;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchEntityBone implements SubstanceEntityBone {

    private final ItemStack visuals;
    private final BlockBenchModelBone bone;
    private final List<String> children = new ArrayList<>();
    private Transformation transformation;

    public BlockBenchEntityBone(BlockBenchModelBone bone, ItemStack visuals)
    {
        this.bone = bone;
        this.visuals = visuals;
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
