package org.substancemc.entity.blockbench;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.substancemc.entity.SubstanceEntityAddon;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.entity.ISubstanceEntityType;
import org.substancemc.entity.entity.SubstanceEntity;
import org.substancemc.entity.entity.SubstanceEntityBone;
import org.substancemc.entity.entity.SubstanceEntityType;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchEntity implements SubstanceEntity {
    private Location loc;
    private final SubstanceEntityType type;
    private double health;
    private final List<SubstanceEntityBone> bones = new ArrayList<>();
    private final List<ItemDisplay> visuals = new ArrayList<>();

    public static SubstanceEntity spawn(SubstanceEntityType type, Location loc)
    {
        BlockBenchEntity entity = new BlockBenchEntity(type, loc);
        SubstanceEntityAddon.get().getEntityManager().registerEntities(entity);
        entity.spawn(loc);
        return entity;
    }

    public BlockBenchEntity(SubstanceEntityType type, Location loc)
    {
        this.loc = loc;
        this.type = type;
        this.health = type.getMaxHealth();
        BlockBenchModel model = type.getModel();
        bones.addAll(SubstanceEntityAddon.get().getBlockBenchManager().getBones(model));
    }

    protected void spawn(Location loc)
    {
        bones.forEach(bone -> {
            ItemDisplay display = (ItemDisplay) loc.getWorld().spawnEntity(loc, EntityType.ITEM_DISPLAY);
            display.setTransformation(bone.getTransformation());
            display.setItemStack(bone.getVisuals());
            display.customName(Component.text(bone.getID()));
            visuals.add(display);
        });
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

    public List<ItemDisplay> getVisuals()
    {
        return visuals;
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
