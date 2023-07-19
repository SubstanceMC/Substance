package org.substancemc.entity.blockbench.convert;

import com.google.gson.JsonElement;
import org.substancemc.core.util.file.DataFolderFile;
import org.substancemc.core.util.gson.GsonFileParser;

import java.io.File;

public class BlockBenchModelFile extends DataFolderFile {

    private final JsonElement content;


    public BlockBenchModelFile(File file) {
        super(file);
        content = new GsonFileParser<JsonElement>().parse(this, JsonElement.class);
    }

    JsonElement getContent()
    {
        return content;
    }
}
