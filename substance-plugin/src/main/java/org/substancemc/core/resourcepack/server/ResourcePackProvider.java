package org.substancemc.core.resourcepack.server;

import org.substancemc.core.util.file.DataFolderFile;

import java.io.File;

public interface ResourcePackProvider {
    String getURL();
    byte[] getHash();
    String getChecksum();
    void upload(DataFolderFile resourcePack);

    default void onUnload() {

    }
}
