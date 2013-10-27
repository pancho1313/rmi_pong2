package dev;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JFrame;

public class SSLoop implements KeyListener {

	public final static String TITLE = "Pong/SServer - CC5303";
	public final static int WIDTH = 100, HEIGHT = 300;
	public final static int UPDATE_RATE = 1;

	public JFrame frame;
	public SSCanvas canvas;

	

	public boolean[] keysPressed;
	public boolean[] keysReleased;
	
	private SServer sServer;
	private boolean suicide;
	private HashMap<String,IPongServer> servers;

	private void reset(){
		keysPressed = new boolean[KeyEvent.KEY_LAST];
		keysReleased = new boolean[KeyEvent.KEY_LAST];
		suicide = false;
		canvas.reset();
	}
	
	public SSLoop(SServer _sServer) {
		this.sServer = _sServer;
		this.servers = new HashMap<String,IPongServer>();
		for(String key : sServer.serversIp){
			servers.put(key, null);
		}
		
		keysPressed = new boolean[KeyEvent.KEY_LAST];
		keysReleased = new boolean[KeyEvent.KEY_LAST];
		suicide = false;//babies dont die
		init();

	}

	/* Initializes window frame and set it visible */
	public void init() {

		frame = new JFrame(TITLE);
		frame.setLayout(new BorderLayout());
		
		canvas = new SSCanvas(WIDTH, HEIGHT);
		frame.add(canvas);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		
		reset();//no mover de aqui!

		

		
		
		frame.addKeyListener(this);
		frame.addWindowListener(
			new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			    	exitSSLoop();
			    }
		    }
		);

		Thread sSLoop = new Thread(new Runnable(){

			@Override
			public void run(){
				while (!suicide){
					Iterator<String> keySetIterator = servers.keySet().iterator();

					while(keySetIterator.hasNext()){
					  String ip = keySetIterator.next();
					  if(servers.get(ip) != null){
						  //TODO: pedirles la carga academica
						  // si no responde (hizo ctrl+c) --> informar su fallecimiento a sServer
						  String[] str = {"0.0%","0.1%","0.6%","0.7%"};
						  canvas.loadInfo = str;
					  }else{
						  IPongServer server;
						  
							try {
								server = (IPongServer) Naming.lookup("//"+ip+":1099/PongServer");
								servers.put(ip, server);
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NotBoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						  
					  }
					}
					
			        //procesar el input del usuario
			        userKeys();
			        
					//repintar el canvas
					canvas.repaintSSCanvas();

					//regular los fps
					try {
						Thread.sleep(1000 / UPDATE_RATE); // milliseconds
					} catch (InterruptedException ex) {
					}
				}
				
				frame.dispose();//para matar la ventana del player
			}
		});
		sSLoop.start();
		try {
			sSLoop.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	/*------------------------------------------*/
	
	@Override
	public void keyPressed(KeyEvent event) {
		keysPressed[event.getKeyCode()] = true;
		keysReleased[event.getKeyCode()] = false;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		keysPressed[event.getKeyCode()] = false;
		keysReleased[event.getKeyCode()] = true;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	/*----------------------------------------*/
	
	/**
	 * avisa al servidor que el player desea retirarse del juego.
	 * */
	private void exitSSLoop(){
		suicide = true;
	}
	
	/**
	 * procesa las teclas presionadas por el usuario segun el estado del juego.
	 * */
	 private void userKeys(){
		if(keysPressed[KeyEvent.VK_Q]){
			exitSSLoop();
		}else if(keysPressed[KeyEvent.VK_M]){
			//TODO: migrate
			
			System.out.println("Migrate!");
			
			/* AL FINALLLLL
			 * cambiar ip del server activo...
			 * 
			*/
		}
	 }
	 


}
