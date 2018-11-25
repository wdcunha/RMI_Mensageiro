import java.rmi.*;
import java.rmi.server.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

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

	public void diz(String texto) throws RemoteException{
		System.out.println(texto);
	}

	public boolean lerArquivo(String nomeArquivo, String paraQuem) throws RemoteException{
		/* Sending The File...*/
		 try{
			 File arq = new File(nomeArquivo);
			 FileInputStream lido = new FileInputStream(arq);
			 byte [] dados = new byte[1024*1024];
			 int carga = lido.read(dados);
			 String arqDestino = "files/" + arq.getName();
			 while(carga > 0){
				 server.enviaArquivo(arqDestino, dados, carga, paraQuem, nomeUsuario);
				 carga = lido.read(dados);
		 		}

		 } catch(Exception e){
			 e.printStackTrace();
		 }
		return true;
	}

	public boolean gravaArquivo(String nomeArquivo, byte[] dado, int carga) throws RemoteException{
		try{
    	File arq = new File(nomeArquivo);
    	arq.createNewFile();
    	FileOutputStream ficheiro = new FileOutputStream(arq,true);
    	ficheiro.write(dado, 0, carga);
    	ficheiro.flush();
    	ficheiro.close();
    } catch(Exception e){
    	e.printStackTrace();
    }
		return true;
	}
}
