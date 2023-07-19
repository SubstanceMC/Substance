package org.substancemc.entity.resourcepack.operations;

import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.ResourcePackOperation;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;

public class EntityAddonResourcePackOperation implements ResourcePackOperation {


    @Override
    public void operate() {
        new EntityAddonResourcePackOperation().operate();
        SubstancePlugin.get().getResourcePackManager().addAtlasEntry("blocks", new ResourcePackAtlasEntry("entity", "directory"));
    }
}
