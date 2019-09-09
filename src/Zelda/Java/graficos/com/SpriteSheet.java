package Zelda.Java.graficos.com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheet 
{

    private BufferedImage spritesheet;

    
    public SpriteSheet(String dir) throws IOException 
    {
        spritesheet = ImageIO.read(new File(dir));
    }
    
    public BufferedImage getSprite(int x, int y, int largura, int altura)
    {
        return spritesheet.getSubimage(x, y, largura, altura);
    }
}
