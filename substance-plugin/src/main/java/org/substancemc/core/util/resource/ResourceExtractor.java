package org.substancemc.core.util.resource;

import org.substancemc.core.SubstancePlugin;

public class ResourceExtractor {

    public ResourceExtractor(String... resourceLocations)
    {
        SubstancePlugin.get().saveDefaultConfig();
        for(String resourceLocation : resourceLocations)
        {
            extract(resourceLocation);
        }
    }

    public void extract(String resourceLocation)
    {
        SubstancePlugin.get().saveResource(resourceLocation, false);
    }

}
