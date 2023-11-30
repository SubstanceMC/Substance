package org.substancemc.entity.blockbench.convert;

import org.substancemc.entity.blockbench.error.BlockBenchParseException;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.core.SubstancePlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class BlockBenchConvertManager {

    private final BlockBenchConvertFixer fixer = new BlockBenchConvertFixer();
    public List<BlockBenchModel> convertAll(File directory)
    {
        List<BlockBenchModel> converted = new ArrayList<>();
        if(!directory.exists() || !directory.isDirectory()) return converted;
        File[] fileList = directory.listFiles(getBlockBenchNameFilter());
        if(fileList == null) return converted;
        for(File file : fileList)
        {
            BlockBenchModel model = new BlockBenchModelConverter(file).convert();
            try {
                fixer.fix(model);
                SubstancePlugin.get().getLogger().warning("Model " + model.getName() + " with id " + model.getModelIdentifier() + " loaded. (Bones: "  + model.getBones().size() + ", Cubes: " + (model.getCubes() != null ? model.getCubes().length : 0) + ")");
                converted.add(model);
            } catch (BlockBenchParseException e) {
                e.printStackTrace();
            }
        }
        return converted;
    }

    public FilenameFilter getBlockBenchNameFilter()
    {
        return (dir, name) -> name.endsWith(".bbmodel");
    }
}
