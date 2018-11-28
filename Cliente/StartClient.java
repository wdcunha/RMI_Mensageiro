// package RIMI_Mensageiro.Cliente;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
import java.io.Console.*;

public class StartClient {
  public static void main(String[] args) {
    try {
      System.setSecurityManager(new RMISecurityManager());

      GroupChatInterface server = (GroupChatInterface)Naming.lookup("rmi://localhost/ABCD");

      Scanner scanner = new Scanner(System.in);
      Console console = System.console();

      // System.out.print("\033[H\033[2J");
      // System.out.flush();
      System.out.println("[Sistema] Mensageiro Cliente está em execução!");

      System.out.print("Escolha uma das opções: \n1 - Registar\n2 - Login\n--> ");
      String aa = scanner.nextLine();
      boolean cond = false;
      String nomeUsuario;
      String passe;
      MessengerInterface dadosUsuario = null;

      while (!cond) {
        switch(aa){
          case "1":
            System.out.print("\n ********************\n Regista novo usuário\n ********************\n");
            System.out.print("Novo nome de usuário: ");
            nomeUsuario = scanner.nextLine();
            passe = new String(console.readPassword("Palavra passe: "));
            if (nomeUsuario.equals("") || passe.equals("")) {
              System.out.println("[Sistema] Nome de usuário e/ou palavra passe devem ser preenchidos!");
              aa = "1";
              break;
            }
            dadosUsuario = new Messenger(nomeUsuario, passe, server);
            if (server.registro(dadosUsuario)) {
              System.out.println("[Sistema] Usuário criado com Sucesso!\n\n[Sistema] Informe a seguir os dados para aceder ao sistema.");
              aa = "2";
            } else if (!server.registro(dadosUsuario)) {
              System.out.println("\nNome de usuário [" +  dadosUsuario.getNomeUsuario() + "] já está cadastrado!");
            }
            break;

          case "2":
            dadosUsuario = null;
            nomeUsuario = "";
            passe = "";
            System.out.print("\n ********\n  Login\n ********\n");
            System.out.print("Nome do usuário: ");
            nomeUsuario = scanner.nextLine();
            passe = new String(console.readPassword("Palavra passe: "));
            dadosUsuario = new Messenger(nomeUsuario, passe, server);
            if (server.login(dadosUsuario)) {
              server.enviaPraTodos("Acabou de se conectar!", dadosUsuario);
            } else {
              System.out.println("[Sistema] Nome de usuário e/ou palavra inválido(s)!");
              cond = false;
              break;
            }
            cond = true;
            break;

          default:
            System.out.print("Opção inválida!");
          break;
        }
      }



      DataInputStream digita = new DataInputStream (System.in);
      aa = "//";

      for(;;){

        switch(aa){
          case "//":
            System.out.println("\n****************************************\n"
                                + "**                                    **\n"
                                + "**  Menu: (a qualquer momento digite)" + " **\n"
                                + "**  Ver Menu:           //" + "            **\n"
                                + "**  Usuários online:    ??" + "            **\n"
                                + "**  Msg Privada (MP):   >>" + "            **\n"
                                + "**  Enviar arquivo:     ++" + "            **\n"
                                + "**                                    **\n"
                                + "****************************************\n");
            break;

          case "??":
            server.listaUsuarios(dadosUsuario);
            break;

          case ">>":
            System.out.println("\nPara Mensagem Privada, informe qual dos\n" + "Usuários online:");
            server.listaUsuarios(dadosUsuario);
            String selecionado = digita.readLine();
            System.out.print("Msg: ");
            String msgPv = digita.readLine();
            System.out.println("\nPara finalizar MP, digite << \n"+ "a qualquer momento!" );
            server.enviarPrivado(selecionado,msgPv, dadosUsuario);
            do {
              System.out.print("*MP* Você: ");
              msgPv = digita.readLine();
              server.enviarPrivado(selecionado,msgPv, dadosUsuario);
            } while(!msgPv.equals("<<"));
            break;

          case "++":
            server.listaUsuarios(dadosUsuario);
            System.out.print("Informe o destinatário do conteúdo: ");
            String destinatario = digita.readLine();
            System.out.print("Indique o nome do ficheiro: ");
            String nomeArquivo = digita.readLine();
            dadosUsuario.lerArquivo(nomeArquivo, destinatario);
          break;

          default:
            server.enviaPraTodos(aa, dadosUsuario);
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
