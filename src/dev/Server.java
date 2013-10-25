package dev;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Server {

	static MyUtil U = new MyUtil();
	
	public static void main(String[] args) {
		String ipHost = U.getArg(args, 1, "ERROR: no se ha especificado LOCALHOST IP!");
		String nPlayers = U.getArg(args, 0, "ERROR: no se ha especificado la cantidad de jugadores!");
		String[] otherIpHosts = U.getRestOfArgs(args, 2, "WARNING: no se han especificado las IP de los SERVERs de contingencia!");//TODO: verificar que existan por lo menos 2 argumentos! 
		
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
			IPongServer pongServer = new PongServer(numPlayers, 10, otherIpHosts);
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
