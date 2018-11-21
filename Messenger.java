// package RIMI_Mensageiro.Cliente;

import java.rmi.*;
import java.rmi.server.*;
// import Servidor.GroupChatInterface;

public class Messenger extends UnicastRemoteObject implements MessengerInterface{

	private String nomeUsuario;
	private GroupChatInterface server;

	public Messenger(String u, GroupChatInterface s) throws RemoteException {
		nomeUsuario = u;
		server = s;
	}

	public String getNomeUsuario() throws RemoteException{
		return nomeUsuario;
	}

	public void diz(String s) throws RemoteException{
		System.out.println(s);
	}

}
