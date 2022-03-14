package interfaz;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Musica{
	
	Player mp3Player;
	
	
	public Musica(String Audio) {
		
        try {
            FileInputStream fileInputStream = new FileInputStream("aud/"+Audio+".mp3");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            mp3Player = new Player(bufferedInputStream);
        } catch (Exception e) {
            System.out.println("Problema reproduciendo " + Audio);
            System.out.println(e.getMessage());
        }
        
        if(Audio.equals("fondoMusica")){
                new Thread() {
                	@Override
                	public void run() {
                		while(true) {
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
