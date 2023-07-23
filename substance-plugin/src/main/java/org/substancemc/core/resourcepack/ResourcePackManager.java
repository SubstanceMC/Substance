package org.substancemc.core.resourcepack;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.substancemc.core.SubstancePlugin;
import org.substancemc.core.resourcepack.generator.atlas.ResourcePackAtlasGenerator;
import org.substancemc.core.resourcepack.listener.ResourcePackListener;
import org.substancemc.core.resourcepack.server.ResourcePackProvider;
import org.substancemc.core.resourcepack.structure.minecraft.atlas.ResourcePackAtlasEntry;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.structure.SubstanceManager;
import org.substancemc.core.util.zip.ZipUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

public class ResourcePackManager implements SubstanceManager {

    private final HashMap<String, List<ResourcePackAtlasEntry>> atlases = new HashMap<>();
    private final List<ResourcePackOperation> operations = new ArrayList<>();
    private final ResourcePackAtlasGenerator atlasGenerator = new ResourcePackAtlasGenerator();

    private ResourcePackProvider packProvider;

    @Override
    public void load() {
        operations.forEach(ResourcePackOperation::operate);
        atlases.entrySet().forEach(atlasGenerator::generate);
        copyPackToResourcePack();
        buildResourcePack();
        upload();
        Bukkit.getPluginManager().registerEvents(new ResourcePackListener(), SubstancePlugin.get());
        Bukkit.getOnlinePlayers().forEach(this::applyPack);
    }

    private void copyPackToResourcePack()
    {
        try {
            FileUtils.copyDirectory(new DataFolderFile("pack").getFile(), new DataFolderFile("resourcePack").getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildResourcePack()
    {
        try {
            File toZip = new DataFolderFile("resourcePack").getFile();
            File zip = new DataFolderFile("resourcePack.zip").getFile();
            if(zip.exists()) zip.delete();
            FileOutputStream fos = new FileOutputStream(zip);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(File file : Objects.requireNonNull(toZip.listFiles()))
            {
                ZipUtils.addDirToZipArchive(zos, file, null);
            }
            zos.flush();
            fos.flush();
            zos.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setPackProvider(ResourcePackProvider provider)
    {
        this.packProvider = provider;
    }

    private void upload()
    {
        if(packProvider != null) packProvider.upload(new DataFolderFile("resourcePack.zip"));
    }

    @Override
    public void unload() {
        packProvider.onUnload();
        atlases.clear();
        DataFolderFile resourcePackFolder = new DataFolderFile("resourcePack");
        if(resourcePackFolder.getFile().exists()) {
            try {
                FileUtils.deleteDirectory(resourcePackFolder.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void applyPack(Player player)
    {
        SubstancePlugin.get().getCompatibilityManager().PROTOCOL_LIB.sendResourcePack(player);
    }

    public void addAtlasEntry(String atlas, ResourcePackAtlasEntry entry)
    {
        List<ResourcePackAtlasEntry> atlasEntryList = atlases.getOrDefault(atlas, new ArrayList<>());
        if(!atlasEntryList.contains(entry)) atlasEntryList.add(entry);
        atlases.put(atlas, atlasEntryList);
    }

    public void addOperation(ResourcePackOperation operation)
    {
        operations.add(operation);
    }

    public Material getModelMaterial()
    {
        return Material.getMaterial(Objects.requireNonNull(SubstancePlugin.get().getConfig().getString("resourcePack.modelMaterial")));
    }

    public ResourcePackProvider getPackProvider()
    {
        return packProvider;
    }

}
