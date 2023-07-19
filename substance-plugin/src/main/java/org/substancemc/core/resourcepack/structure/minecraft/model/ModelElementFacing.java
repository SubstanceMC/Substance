package org.substancemc.core.resourcepack.structure.minecraft.model;

public class ModelElementFacing {

    private ModelElementFacingInformation north;
    private ModelElementFacingInformation east;
    private ModelElementFacingInformation south;
    private ModelElementFacingInformation west;
    private ModelElementFacingInformation up;
    private ModelElementFacingInformation down;

    public ModelElementFacingInformation getNorth() {
        return north;
    }

    public void setNorth(ModelElementFacingInformation north) {
        this.north = north;
    }

    public ModelElementFacingInformation getEast() {
        return east;
    }

    public void setEast(ModelElementFacingInformation east) {
        this.east = east;
    }

    public ModelElementFacingInformation getSouth() {
        return south;
    }

    public void setSouth(ModelElementFacingInformation south) {
        this.south = south;
    }

    public ModelElementFacingInformation getWest() {
        return west;
    }

    public void setWest(ModelElementFacingInformation west) {
        this.west = west;
    }

    public ModelElementFacingInformation getUp() {
        return up;
    }

    public void setUp(ModelElementFacingInformation up) {
        this.up = up;
    }

    public ModelElementFacingInformation getDown() {
        return down;
    }

    public void setDown(ModelElementFacingInformation down) {
        this.down = down;
    }

}
