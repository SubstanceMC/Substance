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

    public ModelElementFacingInformation[] getAllFacings()
    {
        return new ModelElementFacingInformation[] {north, east, south, west, up, down};
    }

    public void setAllFacings(ModelElementFacingInformation[] facings)
    {
        this.north = facings[0];
        this.east = facings[1];
        this.south = facings[2];
        this.west = facings[3];
        this.up = facings[4];
        this.down = facings[5];
    }

}
