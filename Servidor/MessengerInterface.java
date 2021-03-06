
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessengerInterface  extends Remote{

	public String getNomeUsuario() throws RemoteException;

	public void setNomeUsuario(String nomeUsuario) throws RemoteException;

	public String getPasse() throws RemoteException;

	public void setPasse(String passe) throws RemoteException;

	public GroupChatInterface getServidor() throws RemoteException;

	public void setServidor(GroupChatInterface servidor) throws RemoteException;

	public void diz(String texto) throws RemoteException;

	public boolean lerArquivo(String nomeArquivo, String paraQuem) throws RemoteException;

	public boolean gravaArquivo(String nomeArquivo, byte[] dado, int carga) throws RemoteException;

}
