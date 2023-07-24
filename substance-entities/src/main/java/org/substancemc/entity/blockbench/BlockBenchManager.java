package org.substancemc.entity.blockbench;

import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.entity.blockbench.convert.BlockBenchConvertManager;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;

import java.util.ArrayList;
import java.util.List;

public class BlockBenchManager implements SubstanceManager {
    private final BlockBenchConvertManager converter = new BlockBenchConvertManager();
    private List<BlockBenchModel> modelList;

    private final List<String> modelLocatorList = new ArrayList<>();

    @Override
    public void load() {
        DataFolderFile models = new DataFolderFile("models");
        if(!models.getFile().exists()) models.getFile().mkdirs();
        modelList = converter.convertAll(models.getFile());
    }

    @Override
    public void unload() {
        if(modelList != null) modelList.clear();
        modelLocatorList.clear();
    }

    public List<String> getModelLocators()
    {
        return modelLocatorList;
    }

    public void addModelLocator(String locator)
    {
        modelLocatorList.add(locator);
    }

    public List<BlockBenchModel> getModels()
    {
        return modelList;
    }

    public BlockBenchModel getModel(String id)
    {
        return modelList.stream().filter(model -> model.getModelIdentifier().equals(id)).findAny().orElse(null);
    }

    public BlockBenchConvertManager getConverter()

    {
        return converter;
    }
}
