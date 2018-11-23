// package RIMI_Mensageiro.Servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.*;

public interface GroupChatInterface extends Remote {

	  public boolean login(MessengerInterface msg) throws RemoteException;

	  public void enviaPraTodos(String texto, MessengerInterface deQuem) throws RemoteException;

	  public MessengerInterface getMessenger(String nomeUsuario)  throws RemoteException;

		public void enviarPrivado(String usuarioDestino, String msg, MessengerInterface quemMandou)throws RemoteException;

		public void listaUsuarios(MessengerInterface quemSolicitou)throws RemoteException;

		public void geraArquivo(String texto)throws RemoteException;

}
