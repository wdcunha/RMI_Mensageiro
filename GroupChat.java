// package RIMI_Mensageiro.Servidor;

import java.rmi.*;
import java.rmi.Naming;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{

	private Hashtable lista = new Hashtable();

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
		Enumeration usuarios = lista.keys();
		String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/uu HH:mm"));
		String msgTratada = "\n |" + agora + "| [" + deQuem.getNomeUsuario() + "] " + texto;
		// OUTPUT PARA O FICHEIRO HS
		geraArquivo(msgTratada);

    while(usuarios.hasMoreElements()){
       String usuario = (String) usuarios.nextElement();
       MessengerInterface mensagem = (MessengerInterface) lista.get(usuario);
       if (usuario.equals(deQuem.getNomeUsuario())){
       	continue;
       }
       try{
    	   mensagem.diz(msgTratada);
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
		MessengerInterface destino = (MessengerInterface) lista.get(usuarioDestino);
		try{
			destino.diz("*MP* [" + quemMandou.getNomeUsuario() + "] " + msg);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	// quemSolicitou serve para indicar a quem devolver a lista (somente para ele)
	public void listaUsuarios(MessengerInterface quemSolicitou)throws RemoteException{
		MessengerInterface mensagem = (MessengerInterface) lista.get(quemSolicitou.getNomeUsuario());
		Set<String> keys = lista.keySet();
		try {
			for(String key: keys){
				if (key.equals(quemSolicitou.getNomeUsuario())){
					key = key + " (You)";
				}
				mensagem.diz(key);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void geraArquivo(String texto)throws RemoteException{
		System.out.println("Entrou em geraArquivo chamado em GroupChat");

		Path path = Paths.get("historicoConversas.txt");
		byte[] strToBytes = texto.getBytes();
		try {
			Files.write(path, strToBytes,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			String read = Files.readAllLines(path).get(0);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
