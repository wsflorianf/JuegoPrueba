package interfaz;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Sonido{
	
	private Player mp3Player;
	
	
	public Sonido(String nombreAudio) {        
        if(nombreAudio.equals("fondoMusica")){
                new Thread() {
                	@Override
                	public void run() {
                		while(true) {
	                        try {
	                            FileInputStream fileInputStream = new FileInputStream("aud/"+nombreAudio+".mp3");
	                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
	                            mp3Player = new Player(bufferedInputStream);
	                        } catch (Exception e) {
	                            System.out.println("Problema reproduciendo " + nombreAudio);
	                            System.out.println(e.getMessage());
	                        }
        
	                		try {
	        					mp3Player.play();
	        				} catch (JavaLayerException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}
                		}
                	}
                }.start();	
        }else {
            new Thread() {
            	@Override
            	public void run() {
                    try {
                        FileInputStream fileInputStream = new FileInputStream("aud/"+nombreAudio+".mp3");
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        mp3Player = new Player(bufferedInputStream);
                    } catch (Exception e) {
                        System.out.println("Problema reproduciendo " + nombreAudio);
                        System.out.println(e.getMessage());
                    }
            		try {
    					mp3Player.play();
    				} catch (JavaLayerException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}
            }.start();
        }		
        
	}
	

}
