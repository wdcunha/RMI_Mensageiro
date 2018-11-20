import java.rmi.*;
import java.rmi.server.*;

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

	public void msgPv(String userRemoto, String msg, String quemMandou) throws RemoteException{

		System.out.println("userRemoto é: " + userRemoto);
		System.out.println("msg é: " + msg);
		System.out.println("quemMandou é: " + quemMandou);
	}

}
