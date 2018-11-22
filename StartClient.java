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
	      System.out.print("Informe o nome do usuário para login e pressione Enter: ");

	      String nomeUsuario = scanner.nextLine();
	      MessengerInterface m = new Messenger(nomeUsuario, server);

	      server.login(m);
	      server.enviaPraTodos("Acabou de se conectar!", m);
        System.out.println("Para Msg Privada (MP) \n" + "digite >> a qualquer momento");

        DataInputStream digita = new DataInputStream (System.in);
        String aa = "";

	      for(;;){

          System.out.print("Você: ");
          aa = digita.readLine();

          switch(aa){
            case ">>":
              System.out.println("\nPara Mensagem Privada, informe qual dos\n" + "Usuários online:");
              server.listaUsuarios(m);
              String selecionado = digita.readLine();
              System.out.print("Msg: ");
              String msgPv = digita.readLine();
              System.out.println("\nPara finalizar MP, digite << \n"+ "a qualquer momento!" );
              server.enviarPrivado(selecionado,msgPv, m);
              do {
                System.out.print("*MP* Você: ");
                msgPv = digita.readLine();
                server.enviarPrivado(selecionado,msgPv, m);
              } while(!msgPv.equals("<<"));
              break;

            default:
              server.enviaPraTodos(aa, m);
              break;
          }
    	  }
     } catch (Exception e) {
        System.out.println("Erro ocorrido em StartClient: " + e);
        e.printStackTrace();
     }
  }
}
