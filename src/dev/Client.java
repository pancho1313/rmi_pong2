package dev;

import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

public class Client {

	static MyUtil U = new MyUtil();
	private Player myPlayer;
	private ISServer sServer;
	private IPongServer server;
	private String serverIp;
	
	public Client(String ipLocalHost, String ipSServer){
		System.setProperty("java.rmi.server.hostname", ipLocalHost);
		U.localMessage("Connecting to PongServer...");
		
		//contactar al SServer y obtener la ip del servidor activo
		try {
			sServer = (ISServer) Naming.lookup("//"+ipSServer+":1099/SServer");
			serverIp = sServer.whoIstheServer();
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
		
		
		//contactar al server
		try {
			server = (IPongServer) Naming.lookup("//"+serverIp+":1099/PongServer");
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
		
		//crear myPlayer
		try {
			myPlayer = new Player();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//pedir jugar pong
		try {
			if(server.iWantToPlay((IPlayer)myPlayer)){
				startPongWindow();
				return;
			}
		} catch (RemoteException e) {}
		
		
		U.localMessage("Not now my friend, try later.");
		System.exit(0);
	}
	
	private void startPongWindow(){
		new Pong(myPlayer, server);
	}
	
	
	
	public static void main(String[] args) {
		String ipLocalHost = U.getArg(args, 0, "ERROR: no se ha especificado LOCALHOST IP!");
		String[] ipSServer = U.getRestOfArgs(args, 1, "WARNING: no se ha especificado SSERVER IP!");
		new Client(ipLocalHost, ipSServer);
	}
	
}
