import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;

public class StartClient {
  public static void main(String[] args) {
    try {
	      System.setSecurityManager(new RMISecurityManager());

	      GroupChatInterface server = (GroupChatInterface)Naming.lookup("rmi://localhost/ABCD");

	      Scanner scanner = new Scanner(System.in);

	      System.out.println("[System] Mensageiro Cliente está em execução!");
	      System.out.println("Informe o nome do usuário para login e pressione Enter: ");

	      String nomeUsuario = scanner.nextLine();
	      MessengerInterface m = new Messenger(nomeUsuario, server);

	      server.login(m);
	      server.enviaPraTodos("Acabou de se conectar!", m);

	      for(;;){
	    	  String aa = scanner.nextLine();
  			  server.enviaPraTodos(aa, m);
    	  }
     } catch (Exception e) {
        System.out.println("Exceção ao tentar arrancar StartClient: " + e);
        e.printStackTrace();
     }
  }
}
