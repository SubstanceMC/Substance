package org.substancemc.core.util.file;

import org.substancemc.core.SubstancePlugin;

import java.io.File;

public class DataFolderFile {

    private final File file;
    private final String relativePath;

    public String getRelativePath()
    {
        return relativePath;
    }
    public DataFolderFile(String relativePath)
    {
        this.relativePath = relativePath;
        this.file = new File(SubstancePlugin.get().getDataFolder(), relativePath);
    }

    public DataFolderFile(File file)
    {
        this.relativePath = file.getPath();
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }


}
