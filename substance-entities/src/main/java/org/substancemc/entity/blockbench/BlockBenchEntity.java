package org.substancemc.entity.blockbench;

import org.bukkit.Location;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.entity.ISubstanceEntityType;
import org.substancemc.entity.entity.SubstanceEntity;
import org.substancemc.entity.entity.SubstanceEntityBone;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchEntity implements SubstanceEntity {
    private Location loc;
    private final ISubstanceEntityType type;
    private double health;
    private final List<SubstanceEntityBone> bones = new ArrayList<>();


    public BlockBenchEntity(ISubstanceEntityType type, Location loc)
    {
        this.loc = loc;
        this.type = type;
        this.health = type.getMaxHealth();
        BlockBenchModel model = type.getModel();
        bones.addAll(SubstanceEntityAddon.get().getBlockBenchManager().getBones(model));
    }


    @Override
    public ISubstanceEntityType getType() {
        return type;
    }

    @Override
    public Location getLocation() {
        return loc;
    }

    @Override
    public List<SubstanceEntityBone> getBones() {
        return bones;
    }

    @Override
    public void move(Location loc) {
        this.loc = loc;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
        if(this.health - health <= 0)
        {
            kill();
            return;
        }
        this.health -= health;
    }

    @Override
    public void hit() {

    }

    @Override
    public void damage(double damage) {

    }

    @Override
    public void kill() {
        hit();
    }
}
