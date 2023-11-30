package org.substancemc.entity.entity;

import org.bukkit.Location;

import java.util.List;

public interface SubstanceEntity {
    ISubstanceEntityType getType();
    Location getLocation();

    List<SubstanceEntityBone> getBones();
    void move(Location loc);
    double getHealth();
    void setHealth(double health);
    void hit();
    void damage(double damage);

    void kill();
}
