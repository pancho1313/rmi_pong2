package dev;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class SServer extends UnicastRemoteObject implements ISServer{

	private ArrayList<String> serversIp;
	private int activeServer;
	
	static MyUtil U = new MyUtil();
	
	public SServer(int numPlayers, int winScore) throws RemoteException{
		serversIp = new ArrayList<String>();
		activeServer = -1;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void iWantToServe(String ip) throws RemoteException{
		serversIp.add(ip);
		if(serversIp.size() == 1){
			activeServer = 0;
		}
	}
	
	public String whoIstheServer() throws RemoteException{
		if(activeServer >= 0){
			return serversIp.get(activeServer);
		}else{
			return "blablablablabla";//TODO: esto es muy feo!
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		String ipLocalHost = U.getArg(args, 0, "ERROR: no se ha especificado LOCALHOST IP!");
		String nPlayers = U.getArg(args, 1, "ERROR: no se ha especificado la cantidad de jugadores!");
		//String winScore = U.getArg(args, 2, "ERROR: no se ha especificado el puntaje de termino!");
		
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
		
		int winScore = 10;
		////////////////////////////////
		
		System.setProperty("java.rmi.server.hostname", ipLocalHost);
		try {
			ISServer sServer = new SServer(numPlayers, winScore);
			Naming.rebind("rmi://localhost:1099/SServer", sServer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
