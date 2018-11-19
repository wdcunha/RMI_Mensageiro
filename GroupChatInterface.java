import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GroupChatInterface extends Remote {

	  public boolean login(MessengerInterface msg) throws RemoteException;

	  public void enviaPraTodos(String texto, MessengerInterface deQuem) throws RemoteException;

	  public MessengerInterface getMessenger(String nomeUsuario)  throws RemoteException;

}
