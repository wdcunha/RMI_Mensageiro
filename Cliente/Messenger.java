import java.rmi.*;
import java.rmi.server.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class Messenger extends UnicastRemoteObject implements MessengerInterface{

	private String nomeUsuario;
	private String passe;
	private GroupChatInterface servidor;

	public Messenger(String u, String p, GroupChatInterface s) throws RemoteException {
		nomeUsuario = u;
		passe = p;
		servidor = s;
	}

	public String getNomeUsuario() throws RemoteException{
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) throws RemoteException{
		this.nomeUsuario = nomeUsuario;
	}

	public String getPasse() throws RemoteException{
		return passe;
	}

	public void setPasse(String passe) throws RemoteException{
		this.passe = passe;
	}

	public GroupChatInterface getServidor() throws RemoteException{
		return servidor;
	}

	public void setServidor(GroupChatInterface servidor) throws RemoteException{
		this.servidor = servidor;
	}

	public void diz(String texto) throws RemoteException{
		System.out.println(texto);
	}

	public boolean lerArquivo(String nomeArquivo, String paraQuem) throws RemoteException{
		/* Sending File...*/
		 try{
			 File arq = new File(nomeArquivo);
			 FileInputStream lido = new FileInputStream(arq);
			 byte [] dados = new byte[1024*1024];
			 int carga = lido.read(dados);
			 String arqDestino = "files/" + arq.getName();
			 while(carga > 0){
				 servidor.enviaArquivo(arqDestino, dados, carga, paraQuem, nomeUsuario);
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
