package interfaz;



import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.scene.input.MouseDragEvent;

public class Juego extends JFrame implements Runnable{
	
	private int puntos, dificultad, poder, errores;
	
	private JPanel panel1;
	private JLabel pou,fondo,fruta, puntaje;
	private boolean estadoJuego, frutaPantalla, estadoFruta, poderActivo;
	private ImageIcon frutaBien, frutaMal, frutaPoder, pouComida, pouPoder;
	
	
	
	private static final int ANCHO_VENTANA=500;
	private static final int ALTO_VENTANA=550;	

	private static final int FLECHA_DERECHA=39;
	private static final int FLECHA_IZQUIERDA=37;
	
	//private static final int FLECHA_ARRIBA=38;
	//private static final int FLECHA_ABAJO=40;
	
	Sonido fondoMusica, frutaSonido, perderSonido;
	
	public Juego(){
		setSize(ANCHO_VENTANA, ALTO_VENTANA);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Pou Comida");
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		fondoMusica=new Sonido("fondoMusica");
		
		errores=3;
		puntos=0;
		
		poderActivo=false;
		estadoFruta=true;
		estadoJuego=false;
		frutaPantalla=false;
		
		panel1=new JPanel();	
		this.add(panel1);
		panel1.setLayout(null);	

		
		Componentes();	
	}

	private void Componentes() {
		Imagenes();
		Labels();
		Teclas();
		movimiento();
	}

	private void Imagenes() {
		Image imagen=new ImageIcon("img/frutaBien.png").getImage();
		imagen=imagen.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		frutaBien=new ImageIcon(imagen);
		
		imagen=new ImageIcon("img/popo.png").getImage();
		imagen=imagen.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		frutaMal=new ImageIcon(imagen);
		
		imagen=new ImageIcon("img/frutaPoder.png").getImage();
		imagen=imagen.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		frutaPoder=new ImageIcon(imagen);		
		
		imagen=new ImageIcon("img/pou comida.png").getImage();
		imagen=imagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		
		pouComida=new ImageIcon(imagen);	
		
		imagen=new ImageIcon("img/pouPoder.png").getImage();
		imagen=imagen.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		
		pouPoder=new ImageIcon(imagen);
	}

	private boolean Colision(JComponent componente1,JComponent componente2) {
		Area area1=new Area(componente1.getBounds());
		Area area2=new Area(componente2.getBounds());
		
		return area1.intersects(area2.getBounds2D());
	}

	private void Teclas() {
		KeyListener tecla=new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==FLECHA_DERECHA && estadoJuego){
					pou.setLocation(pou.getLocation().x+20, pou.getLocation().y);
				}
				if(e.getKeyCode()==FLECHA_IZQUIERDA && estadoJuego){
					pou.setLocation(pou.getLocation().x-20, pou.getLocation().y);
				}
				if(pou.getLocation().x<0) {
					pou.setLocation(0,pou.getLocation().y);
				}
				if(pou.getLocation().x+pou.getWidth()>panel1.getWidth()) {
					pou.setLocation(panel1.getWidth()-pou.getWidth(),pou.getLocation().y);
				}
			}
		};	
		
		this.addKeyListener(tecla);		
	}

	private void Labels() {	
		pou=new JLabel(pouComida);
		pou.setBounds(ANCHO_VENTANA/2-(100/2), ALTO_VENTANA-100, 100, 100);
		panel1.add(pou);
		
		puntaje=new JLabel(" Puntaje: 0");
		puntaje.setBounds(0,20,120,20);
		puntaje.setFont(new Font("Arial", Font.BOLD, 18));
		panel1.add(puntaje);
		
		fruta=new JLabel(frutaBien);
		fruta.setBounds(0, -30, 30, 30);
		fruta.setVisible(false);
		panel1.add(fruta);
		
		Image imagen=new ImageIcon("img/fondo.jpg").getImage();
		imagen=imagen.getScaledInstance(ANCHO_VENTANA, ALTO_VENTANA, Image.SCALE_SMOOTH);		
		fondo=new JLabel(new ImageIcon(imagen));
		fondo.setBounds(0, 0, ANCHO_VENTANA, ALTO_VENTANA);
		panel1.add(fondo);
		
	}
	
	private void movimiento() {
		
		MouseAdapter mov=new MouseAdapter() {			
			@Override
			public void mousePressed(MouseEvent e) {
				if(!estadoJuego) {
					estadoJuego=true;
					new Thread(Juego.this).start();
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				pou.setLocation(pou.getLocation().x+e.getX(), pou.getLocation().y);
				if(pou.getLocation().x<0) {
					pou.setLocation(0,pou.getLocation().y);
				}
				if(pou.getLocation().x+pou.getWidth()>panel1.getWidth()) {
					pou.setLocation(panel1.getWidth()-pou.getWidth(),pou.getLocation().y);
				}
			}
		};
		
		pou.addMouseListener(mov);
		pou.addMouseMotionListener(mov);		
	}

	@Override
	public void run() {
		
		fruta.setVisible(true);
		
		while(estadoJuego==true) {	
			try {
				Thread.sleep(18-dificultad);
			} catch (Exception e) {

			}
			
			if(!frutaPantalla) {
				poderActivo=false;
				double random=Math.random();
				int posicion=(int)((ANCHO_VENTANA-30)*Math.random());
				if(random>=0.2) {
					fruta.setIcon(frutaBien);
					estadoFruta=true;
				}
				else if(random<=0.15){
					fruta.setIcon(frutaMal);
					estadoFruta=false;
				}else{
					fruta.setIcon(frutaPoder);
					estadoFruta=true;
					poderActivo=true;
				}
				fruta.setLocation(posicion, -30);
				frutaPantalla=true;
			}
			if(frutaPantalla){
				fruta.setLocation(fruta.getLocation().x,fruta.getLocation().y+5);
				if(Colision(pou, fruta) && estadoFruta) {
					new Sonido("fruta");
					puntos++;
					puntaje.setText(" Puntaje: "+puntos);
					dificultad=puntos/2;
					if(dificultad>13) {
						dificultad=13;
					}
					if(poderActivo) {
						poder=3;
						pou.setIcon(pouPoder);
					}
				}else if(Colision(pou, fruta) && !estadoFruta) {
					if(poder==0) {
						fin();	
					}else {
						poder--;
						if(poder==0) {
							pou.setIcon(pouComida);
						}
					}
				}
				if(Colision(pou, fruta)) {
					frutaPantalla=false;
					errores=3;
				}
				if(fruta.getLocation().y>ALTO_VENTANA) {
					if(estadoFruta) {
						errores--;
						if(errores==0) {
							fin();
						}
					}
					frutaPantalla=false;
				}
			}	
		}
	}
	
	private void fin() {
		new Sonido("fin");
		JOptionPane.showMessageDialog(null, "Se acabó el juego.\nSu puntaje fue de "+puntos+" puntos.");
		puntos=0;
		poder=0;
		dificultad=0;
		errores=3;
		pou.setIcon(pouComida);
		pou.setLocation(ANCHO_VENTANA/2-(100/2),pou.getLocation().y);
		fruta.setLocation(0, -30);
		puntaje.setText(" Puntaje: 0");
		estadoJuego=false;
		fruta.setVisible(false);
	}
}
