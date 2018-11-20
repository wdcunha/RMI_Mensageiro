import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessengerInterface  extends Remote{

	public String getNomeUsuario() throws RemoteException;

	public void diz(String texto) throws RemoteException;

	public void msgPv(String userRemoto, String msg, String quemMandou)throws RemoteException;

}
