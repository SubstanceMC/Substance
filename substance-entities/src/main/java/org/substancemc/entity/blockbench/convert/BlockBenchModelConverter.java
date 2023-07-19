package org.substancemc.entity.blockbench.convert;

import org.substancemc.entity.blockbench.structure.BlockBenchModel;
import org.substancemc.core.util.gson.GsonElementParser;

import java.io.File;

public class BlockBenchModelConverter {

    private final BlockBenchModelFile file;

    public BlockBenchModelConverter(File file)
    {
        this.file = new BlockBenchModelFile(file);
    }

    public BlockBenchModel convert()
    {
        return new GsonElementParser<BlockBenchModel>().parse(file.getContent(), BlockBenchModel.class);
    }

    public BlockBenchModelFile getBlockBenchFile()
    {
        return file;
    }

}
