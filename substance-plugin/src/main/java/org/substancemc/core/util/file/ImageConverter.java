package org.substancemc.core.util.file;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageConverter {

    private final String base64Image;

    public ImageConverter(String base64Image)
    {
        this.base64Image = base64Image;
    }

    public BufferedImage convert(DataFolderFile file)
    {
        if(!file.getFile().getParentFile().exists()) file.getFile().getParentFile().mkdirs();
        String[] parts = base64Image.split(",");
        byte[] imageBytes = Base64.getDecoder().decode(parts[1]);
        try {
            if(!file.getFile().exists()) file.getFile().createNewFile();
            BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ImageIO.write(original, "png", file.getFile());
            return original;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
