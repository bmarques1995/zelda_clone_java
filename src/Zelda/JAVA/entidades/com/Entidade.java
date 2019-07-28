package Zelda.JAVA.entidades.com;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Entidade 
{
    protected int x;
    protected int y;
    protected int largura;
    protected int altura;
    protected BufferedImage sprite;

    public Entidade(int x, int y, int largura, int altura, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.sprite = sprite;
    }
    
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public int getLargura()
    {
        return this.largura;
    }
    
    public int getAltura()
    {
        return this.altura;
    }
    
    public BufferedImage getSprite() {
        return sprite;
    }

    public void render(Graphics g)
    {
        g.drawImage(sprite, x, y, null);
    }
    
}
