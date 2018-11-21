// package RIMI_Mensageiro.Cliente;

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

        DataInputStream digita = new DataInputStream (System.in);
        String aa = "";

	      for(;;){
	    	  // aa = scanner.nextLine();

          aa = digita.readLine();

          switch(aa){
            case ">>":
              // System.out.println("Entrou no switch with aa!");
              server.enviarPrivado("Paul","Hi Paul", m);
              break;
          }

          if(aa == ":pv"){
    				// int[] privateList = list.getSelectedIndices();
            //
    				// for(int i=0; i<privateList.length; i++){
    					// System.out.println("selected index :" + privateList[i]);
    					System.out.println("Entrou no if aa!");
    				} else {
              server.enviaPraTodos(aa, m);
            }
    	  }
     } catch (Exception e) {
        System.out.println("Exceção ao tentar arrancar StartClient: " + e);
        e.printStackTrace();
     }
  }
}
