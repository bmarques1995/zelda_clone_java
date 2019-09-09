package Zelda.Java.main.com;

import Zelda.Java.entidades.com.Entidade;
import Zelda.Java.entidades.com.Jogador;
import Zelda.Java.graficos.com.SpriteSheet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Jogo extends Canvas implements Runnable
{
    
    private JFrame frame;
    
    private boolean emExecucao;
    private Thread thread;
    
    
    private final double ns;
    private short frames;
    
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private BufferedImage imagem;
    
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final int WIDTH = 160;
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private final int HEIGHT = 120;
    private final int SCALE = 3;
    
    public List<Entidade> entidades;
    public SpriteSheet spritesheet;
    
    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public Jogo(double FPS) throws IOException
    {
        super();
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.IniciarFrame();
        this.ns = Math.pow(10,9) / FPS;
        
        this.imagem = new BufferedImage(160,120,BufferedImage.TYPE_INT_RGB);
        this.entidades = new ArrayList<>();
        
        this.spritesheet = new SpriteSheet("./src/assets/spritesheet.png");
        
        this.entidades.add(new Jogador(0,0,16,16, spritesheet.getSprite(32, 0, 16, 16)));
        
    }
    
    private void IniciarFrame() 
    {
        this.frame = new JFrame();
        this.frame.add(this);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
    
    public synchronized void Iniciar()
    {
        thread = new Thread(this);
        emExecucao = true;
        thread.start();
    }
    
    public void tique()
    {
        
    }
    
    public void renderizar()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        
        /*Tela inicial*/
        Graphics g = this.imagem.getGraphics();
        g.setColor(new Color(0, 127, 55));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        for (Entidade entidade : entidades) 
            entidade.render(g);
        
        
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(imagem, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }
    
    public synchronized void Encerrar()
    {
        this.emExecucao = false;
        try 
        {
            thread.join();
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private double CalcularTempoDoFrameAtual(long inicio)
    {
        return (System.nanoTime() - inicio)/this.ns;
    }
    
    private void IncrementarFrame()
    {
        this.frames++;
    }
    
    private void AtualizarFrame(short valor)
    {
        this.frames = valor;
    }
    
    private void mensagemFPS()
    {
        System.out.println("FPS: " + this.frames);
    }

    @Override
    public void run() {
        long ultimoTempo = System.nanoTime();
        double temporizador = System.currentTimeMillis();
        this.AtualizarFrame((short) 0);
        while(emExecucao)
        {
            double delta = this.CalcularTempoDoFrameAtual(ultimoTempo);   
            if(delta >= 1)
            {
                this.renderizar();
                ultimoTempo = System.nanoTime();
                this.IncrementarFrame();
            }
            if((System.currentTimeMillis() - temporizador) >= 1000)
            {
                this.mensagemFPS();
                this.AtualizarFrame((short) 0);
                temporizador += 1000;
            }
        }
        this.Encerrar();
    }
    
}
