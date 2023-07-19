package org.substancemc.entity.blockbench.structure.meta;

public class BlockBenchModelMeta {
    private String format_version;
    private String model_format;
    private boolean box_uv;

    public String getFormatVersion() {
        return format_version;
    }

    public void setFormatVersion(String formatVersion) {
        this.format_version = formatVersion;
    }

    public String getModelFormat() {
        return model_format;
    }

    public void setModelFormat(String modelFormat) {
        this.model_format = modelFormat;
    }

    public boolean isBoxUV() {
        return box_uv;
    }

    public void setBoxUV(boolean boxUV) {
        this.box_uv = boxUV;
    }


}
