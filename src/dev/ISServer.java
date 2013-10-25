package dev;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISServer extends Remote {
	public void iWantToServe(String ip) throws RemoteException;
	public String whoIstheServer() throws RemoteException;
}
