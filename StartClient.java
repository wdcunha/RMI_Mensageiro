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

        DataInputStream digita = new DataInputStream (System.in);
        String aa = "//";

	      for(;;){

          switch(aa){
            case "//":
            System.out.println("\n***************************************\n"
                                + "**                                   **\n"
                                + "** Menu: (a qualquer momento digite)" + " **\n"
                                + "** Ver Menu: //" + "                      **\n"
                                + "** Usuários online: ??" + "               **\n"
                                + "** Msg Privada (MP): >>" + "              **\n"
                                + "**                                   **\n"
                                + "***************************************\n");
              break;

            case "??":
              server.listaUsuarios(m);
              break;

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

          System.out.print("Você: ");
          aa = digita.readLine();
    	  }
     } catch (Exception e) {
        System.out.println("Erro ocorrido em StartClient: " + e);
        e.printStackTrace();
     }
  }
}
