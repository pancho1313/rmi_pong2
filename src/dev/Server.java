package dev;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Server {

	static MyUtil U = new MyUtil();
	
	public static void main(String[] args) {
		String ipHost = U.getArg(args, 0, "192.168.2.14", "WARNING: no se ha especificado HOST.");
		String nPlayers = U.getArg(args, 1, "2", "WARNING: no se ha especificado la cantidad de jugadores!");
		//String wScore = U.getArg(args, 2, "10", "WARNING: no se ha especificado el puntaje para ganar! 2/5/10");
		
		int numPlayers;
		if(nPlayers.equals("2")){
			numPlayers = 2;
		}else if(nPlayers.equals("3")){
			numPlayers = 3;
		}else if(nPlayers.equals("4")){
			numPlayers = 4;
		}else{
			numPlayers = 2;
		}
		////////////////////////////////
		
		
		try {
			System.setProperty("java.rmi.server.hostname", ipHost);
			IPongServer pongServer = new PongServer(numPlayers, 10, ipHost);
			Naming.rebind("rmi://localhost:1099/PongServer", pongServer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
