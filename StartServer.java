import java.rmi.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.registry.*;
import java.rmi.server.*;

public class StartServer {

    public static void main(String[] args) {

         try {

        	 	System.setSecurityManager(new RMISecurityManager());

            Registry registry = LocateRegistry.createRegistry(1099);

    				GroupChat obj = new GroupChat();

            Naming.rebind("rmi://localhost/ABCD", obj);

            System.out.println("[System] Chat Server iniciado com sucesso!");

      	 } catch (Exception e) {

            System.out.println("Chat Server falhou por problema com: " + e);

      }
    }
}
