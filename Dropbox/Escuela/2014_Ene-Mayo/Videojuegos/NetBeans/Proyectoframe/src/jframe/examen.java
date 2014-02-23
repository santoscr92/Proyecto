/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jframe;

/**
 *
 * @author santoscr92
 */

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class examen extends JFrame implements Runnable, KeyListener,MouseListener,MouseMotionListener{
     private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int direccion;    // Direccion del elefante
    private int velocidad,aceleracion; // determinado por vidas
    private final int MIN = -6;    //Minimo al generar un numero al azar.
    private final int MAX = 7;    //Maximo al generar un numero al azar.
    private Image dbImage;      // Imagen a proyectar  
    private Image gameover;
    private Graphics dbg;       // Objeto grafico
    private SoundClip sonido;    // Objeto AudioClip
    private SoundClip rat;    // Objeto AudioClip
    private SoundClip bomb;    //Objeto AudioClip
    private SoundClip teleport;
    private Bueno dumbo;    // Objeto de la clase Bueno
    private Malo raton;    //Objeto de la clase Malo
    private int ancho;  //Ancho del elefante
    private int alto;   //Alto del elefante
    private ImageIcon elefante; // Imagen del elefante.
    private int x1; // posicion del mouse en x
    private int y1; // posicion del mouse en y
    private int x_pos;
    private int y_pos;
    private int vidas = 6;
    private int score = 0;
    private boolean pausa = false;
    
    public examen() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void init() {
        direccion = 0;
        this.setSize(900, 700);
        URL eURL = this.getClass().getResource("Imagenes/bola.png");
        int dposy = getHeight() / 2;
        dumbo = new Bueno(15, dposy , Toolkit.getDefaultToolkit().getImage(eURL));
        //dumbo.setPosX((int) (getWidth()/2));
        //dumbo.setPosY(getHeight());
        int posrX =  getWidth()/2 ;    //posision x es tres cuartos del applet
        int posrY =   getHeight() ;  //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("Imagenes/caja.png");
        raton = new Malo(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
        raton.setPosX(raton.getPosX() - raton.getAncho());
        raton.setPosY(raton.getPosY() - raton.getAlto());
        setBackground(Color.white);
        addKeyListener(this);
        URL goURL = this.getClass().getResource("Imagenes/perder.png");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);
       
        //Se cargan los sonidos.
        sonido = new SoundClip("Sonidos/mice.wav");
        bomb = new SoundClip("Sonidos/Explosion.wav");
        teleport = new SoundClip("Sonidos/teleport.wav");
 
        
        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL));
        ancho = elefante.getIconWidth();
        alto = elefante.getIconHeight();
        //ancho2 = raton.getIconWidth();
        // alto2 = raton.getIconHeight();
        addMouseListener(this);
        addMouseMotionListener(this);  
        
  
    }
 
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }
    
    public void run() {
        while (vidas > 0) {
            actualiza();
            checaColision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
 
        }
    }
    
    public void actualiza() {
        
           
        }
        
        
    
 
    public void checaColision() {
        
        
        if (dumbo.getPosX() + dumbo.getAncho() > getWidth()) {
            dumbo.setPosX(getWidth() - dumbo.getAncho());
        }
        if (dumbo.getPosX() < 0) {
            dumbo.setPosX(0);
        }
        if (dumbo.getPosY() + dumbo.getAlto() > getHeight()) {
            dumbo.setPosY(getHeight() - dumbo.getAlto());
        }
        if (dumbo.getPosY() < 0) {
            dumbo.setPosY(0);
        }
    }
 
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) 
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
 
        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
 
        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);
 
        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paint1(Graphics g) {
        if (vidas>0){
        if (dumbo != null && raton != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(dumbo.getImagenI(), dumbo.getPosX(), dumbo.getPosY(), this);
            g.drawImage(raton.getImagenI(), raton.getPosX(), raton.getPosY(), this);
 
        } else {
            //Da un mensaje mientras se carga el dibujo
            g.drawString("No se cargo la imagen..", 20, 20);
        }
        }
        else{
            g.drawImage(gameover, 400, 150, this);
        }
        
        //
        g.drawImage(raton.getImagenI(), raton.getPosX(), raton.getPosY(), this);
        
        
        g.setColor(Color.black);
        //g.drawString("Vidas: " + vidas, 10, 20);
        g.drawString("Score: " + score, 70, 50); 
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) //Presiono flecha arriba
        {    
            pausa = !pausa;
        }
       
        /*
        if (e.getKeyCode() == KeyEvent.VK_UP) //Presiono flecha arriba
        {    
            up = true;
	} 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Presiono flecha abajo
                {    
                    down = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Presiono flecha izquierda
                {    
			left = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Presiono flecha derecha
                {    
			right = true;
		}
        */
         
    }
 
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
     public void mouseClicked(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
    
}


