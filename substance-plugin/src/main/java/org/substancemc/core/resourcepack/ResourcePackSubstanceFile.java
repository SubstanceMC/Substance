package org.substancemc.core.resourcepack;

public class ResourcePackSubstanceFile extends ResourcePackFile {

    private final String resourceLocator;
    public ResourcePackSubstanceFile(String relativePath) {
        super("substance/" + relativePath);
        String relativeResourcePath = relativePath.replace(relativePath.split("/")[0] + "/", "");
        String relativeResourcePathNoJsonOrPng = relativeResourcePath.endsWith(".png") ? relativeResourcePath.substring(0, relativeResourcePath.length() - 4) : relativeResourcePath.substring(0, relativeResourcePath.length() - 5);
        this.resourceLocator = "substance:" + relativeResourcePathNoJsonOrPng;
    }

    public String getResourceLocator()
    {
        return resourceLocator;
    }


}
