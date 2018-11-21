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

          System.out.print("Você: ");
          aa = digita.readLine();

          switch(aa){
            case ">>":
              server.listaUsuarios(m);
              String selecionado = digita.readLine();
              System.out.print("Msg: ");
              String msgPv = digita.readLine();
              server.enviarPrivado(selecionado,msgPv, m);
              break;

            default :
              server.enviaPraTodos(aa, m);
              break;
          }

          if(aa.equals(">>")){
    				// int[] privateList = list.getSelectedIndices();
            //
    				// for(int i=0; i<privateList.length; i++){
    					// System.out.println("selected index :" + privateList[i]);
    					// System.out.println("Entrou no if aa!");
    				} else {
              // System.out.println("NÃO Entrou no if aa!");

            }
    	  }
     } catch (Exception e) {
        System.out.println("Exceção ao tentar arrancar StartClient: " + e);
        e.printStackTrace();
     }
  }
}
