// package RIMI_Mensageiro.Servidor;

import java.rmi.*;
import java.rmi.Naming;
import java.rmi.server.*;
import java.util.*;

public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{

	private Hashtable lista = new Hashtable();
	private Enumeration usuarios;


	public GroupChat() throws RemoteException{ }

	public boolean login(MessengerInterface dadosUsuario) throws RemoteException{

  		lista.put(dadosUsuario.getNomeUsuario(), dadosUsuario);
  		dadosUsuario.diz("[Servidor] Bem-vindo " + dadosUsuario.getNomeUsuario());
  		//
		return true;
	}

	public void enviaPraTodos(String texto, MessengerInterface deQuem) throws RemoteException{

		System.out.println("\n[" + deQuem.getNomeUsuario() + "] " + texto);
		// Enumeration usuarios = lista.keys();
		listaUsuarios();

    while(usuarios.hasMoreElements()){
       String usuario = (String) usuarios.nextElement();
       MessengerInterface mensagem = (MessengerInterface) lista.get(usuario);
       if (usuario.equals(deQuem.getNomeUsuario())){
       	continue;
       }
       try{
    	   mensagem.diz("\n[" + deQuem.getNomeUsuario() + "] " + texto);
				// OUTPUT PARA O FICHEIRO HS
       } catch(Exception e){
	       e.printStackTrace();
       }
    }
	}

	public MessengerInterface getMessenger(String nomeUsuario)  throws RemoteException{
		MessengerInterface msg = (MessengerInterface) lista.get(nomeUsuario);
		return msg;
	}

	public void enviarPrivado(String usuarioDestino, String msg, MessengerInterface quemMandou) throws RemoteException{
		System.out.println("usuarioDestino é: " + usuarioDestino);
		System.out.println("msg é: " + msg);
		System.out.println("quemMandou é: " + quemMandou.getNomeUsuario());
		// System.out.println("lista: " + lista.toString());
		listaUsuarios();
		while (usuarios.hasMoreElements()){
      // System.out.println("Elements: ");
      System.out.println(usuarios.nextElement());
    }

	}

	public Enumeration listaUsuarios()throws RemoteException{
		usuarios = lista.keys();
		return usuarios;
	}
}
