package dev;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPongServer extends Remote {
	public boolean iWantToPlay(IPlayer p) throws RemoteException;
	public void iWantToQuit(int playerId) throws RemoteException;
	public void iMovedMyBar(int playerId, double x, double y) throws RemoteException;
	public void refreshBall(int playerId, boolean missedBall, double x, double y, double vx, double vy) throws RemoteException;//TODO: la posicion es deducible
	//public void iWantToPlayAgain(int playerId) throws RemoteException;
}
