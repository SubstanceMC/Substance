package org.substancemc.entity.blockbench;

import org.bukkit.inventory.ItemStack;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.entity.blockbench.convert.BlockBenchConvertManager;
import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.entity.blockbench.structure.element.BlockBenchModelBone;

import java.util.*;

public class BlockBenchManager implements SubstanceManager {
    private final BlockBenchConvertManager converter = new BlockBenchConvertManager();
    private List<BlockBenchModel> modelList;

    private final HashMap<BlockBenchModel, List<String>> modelLocatorList = new HashMap<>();
    private final HashMap<BlockBenchModelBone, List<String>> boneLocatorList = new HashMap<>();
    private final HashMap<BlockBenchModel, ArrayList<BlockBenchEntityBone>> parsedBones = new HashMap<>();
    public void addBoneLocator(BlockBenchModelBone bone, String locator)
    {
        List<String> locators = boneLocatorList.getOrDefault(bone, new ArrayList<>());
        locators.add(locator);
        boneLocatorList.put(bone, locators);
    }
    public BlockBenchModelBone getBoneByLocator(String locator)
    {
        return boneLocatorList.keySet().stream().filter(key -> boneLocatorList.getOrDefault(key, new ArrayList<>()).contains(locator)).findFirst().orElse(null);
    }

    public BlockBenchModel getModelByLocator(String locator)
    {
        return modelLocatorList.keySet().stream().filter(key -> modelLocatorList.getOrDefault(key, new ArrayList<>()).contains(locator)).findFirst().orElse(null);
    }

    public List<BlockBenchEntityBone> getBones(BlockBenchModel model)
    {
        return parsedBones.getOrDefault(model, new ArrayList<>());
    }
    public void registerParsedBones(BlockBenchModel model, BlockBenchEntityBone... bones)
    {
        ArrayList<BlockBenchEntityBone> existing = parsedBones.getOrDefault(model, new ArrayList<>());
        existing.addAll(Arrays.asList(bones));
        parsedBones.put(model, existing);
    }

    public void registerParsedBones(BlockBenchModel model, Collection<BlockBenchEntityBone> bones)
    {
        ArrayList<BlockBenchEntityBone> existing = parsedBones.getOrDefault(model, new ArrayList<>());
        existing.addAll(bones);
        parsedBones.put(model, existing);
    }

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
        List<String> toReturn = new ArrayList<>();
        modelLocatorList.keySet().forEach(key -> {
            toReturn.addAll(modelLocatorList.get(key));
        });
        return toReturn;
    }

    public void addModelLocator(BlockBenchModel model, String locator)
    {
        List<String> locators = modelLocatorList.getOrDefault(model, new ArrayList<>());
        locators.add(locator);
        modelLocatorList.put(model, locators);
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
