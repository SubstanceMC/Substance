package org.substancemc.core.resourcepack.structure.minecraft.model;

public class Override {
    private String model;
    private Predicate predicate;

    public Override(String model, int modelData)
    {
        this.model = model;
        this.predicate = new Predicate(modelData);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }


}
