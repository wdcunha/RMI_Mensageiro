import java.rmi.*;
import java.rmi.Naming;
import java.rmi.server.*;
import java.util.*;

public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{

	private Hashtable lista = new Hashtable();

	public GroupChat() throws RemoteException{ }

	public boolean login(MessengerInterface msg) throws RemoteException{

  		lista.put(msg.getNomeUsuario(), msg);
  		msg.diz("[Servidor] Bemvindo " + msg.getNomeUsuario());

		return true;
	}

	public void enviaPraTodos(String texto, MessengerInterface deQuem) throws RemoteException{

		System.out.println("\n[" + deQuem.getNomeUsuario() + "] " + texto);
		Enumeration usuarios = lista.keys();

        while(usuarios.hasMoreElements()){

		       String usuario = (String) usuarios.nextElement();
		       MessengerInterface mensagem = (MessengerInterface) lista.get(usuario);

		       if (usuario.equals(deQuem.getNomeUsuario())){
		       	continue;
		       }

		       try{

		    	   mensagem.diz("\n[" + deQuem.getNomeUsuario() + "] " + texto);

		       } catch(Exception e){

  		       e.printStackTrace();

		       }
        }
	}

	public MessengerInterface getMessenger(String nomeUsuario)  throws RemoteException{

  		MessengerInterface msg = (MessengerInterface) lista.get(nomeUsuario);

		return msg;
	}

  public void msgPv(int[] grupoPrivado, String msgPrivada) throws RemoteException{
		Chatter pc;
		for(int i : grupoPrivado){
			pc= chatters.elementAt(i);
			pc.getClient().messageFromServer(msgPrivada);
		}
	}

}
