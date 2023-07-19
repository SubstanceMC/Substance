package org.substancemc.entity.blockbench;

import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.entity.blockbench.convert.BlockBenchConvertManager;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;

import java.util.List;

public class BlockBenchManager implements SubstanceManager {
    private final BlockBenchConvertManager converter = new BlockBenchConvertManager();
    private List<BlockBenchModel> modelList;

    @Override
    public void load() {
        DataFolderFile models = new DataFolderFile("models");
        if(!models.getFile().exists()) models.getFile().mkdirs();
        modelList = converter.convertAll(models.getFile());
    }

    @Override
    public void unload() {
        if(modelList != null) modelList.clear();
    }

    public List<BlockBenchModel> getModels()
    {
        return modelList;
    }

    public BlockBenchConvertManager getConverter()

    {
        return converter;
    }
}
