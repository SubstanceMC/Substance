package org.substancemc.core.resourcepack;

import org.substancemc.core.util.file.DataFolderFile;

public class ResourcePackFile extends DataFolderFile {
    public ResourcePackFile(String relativePath) {
        super("resourcePack/assets/" + relativePath);
    }
}
