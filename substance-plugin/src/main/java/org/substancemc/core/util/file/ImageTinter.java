package org.substancemc.core.util.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageTinter {

    private final DataFolderFile resultFile;
    private final Color tint;

    public ImageTinter(DataFolderFile resultFile, Color tint)
    {
        this.resultFile = resultFile;
        this.tint = tint;
    }

    public void tint(BufferedImage image)
    {
        try {
            if(!resultFile.getFile().getParentFile().exists()) resultFile.getFile().getParentFile().mkdirs();
            if(!resultFile.getFile().exists()) resultFile.getFile().createNewFile();
            BufferedImage tinted = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = tinted.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.setColor(tint);
            boolean[][] alphaMap = new boolean[tinted.getWidth()][tinted.getHeight()];
            for(int x = 0; x < image.getWidth(); x++)
            {
                for(int y = 0; y < image.getHeight(); y++)
                {
                    int alpha = (image.getRGB(x, y) >> 24) & 0xff;
                    alphaMap[x][y] = alpha == 0;
                }
            }
            graphics.fillRect(0, 0, tinted.getWidth(), tinted.getHeight());
            graphics.setColor(new Color(0, 0, 0,0));
            graphics.dispose();
            for(int x = 0; x < image.getWidth(); x++)
            {
                for(int y = 0; y < image.getHeight(); y++)
                {
                    if(alphaMap[x][y])
                    {
                        tinted.getAlphaRaster().setPixel(x, y, new int[]{0, 0, 0, 0});
                    }
                }
            }
            ImageIO.write(tinted, "png", resultFile.getFile());
        }catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

}
