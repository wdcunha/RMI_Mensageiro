
import java.rmi.*;
import java.rmi.Naming;
import java.rmi.server.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GroupChat extends UnicastRemoteObject implements GroupChatInterface{

	private Hashtable<String, MessengerInterface> lista = new Hashtable();

	public GroupChat() throws RemoteException{ }


	public boolean registro(MessengerInterface dadosUsuario) throws RemoteException{
		if (!lista.containsKey(dadosUsuario.getNomeUsuario())) {

			System.out.println("\n[Sistema] Usuário criado com Sucesso!");
			lista.put(dadosUsuario.getNomeUsuario(), dadosUsuario);

			return true;
		}
		return false;

	}

	// public boolean login(String nomeUsuario, String passe,  MessengerInterface dadosUsuario) throws RemoteException{
	public boolean login(MessengerInterface dadosUsuario) throws RemoteException{
		try {
			String usuario = "";
			if (this.lista.containsKey(dadosUsuario.getNomeUsuario())) {
				usuario = dadosUsuario.getNomeUsuario();
			} else {
				dadosUsuario.diz("[Sistema] Nome não cadastrado " + usuario + "!");
				return false;
			}

			MessengerInterface dados = null;
			if (this.lista.get(usuario) != null) {
				dados = this.lista.get(usuario);
			} else {
				return false;
			}

			String passLista = "";
			if (!dados.getPasse().toString().equals("")) {
				passLista = dados.getPasse().toString();
			}

			if (this.lista.get(usuario).equals(null)) {
				return false;
			}

			if (dadosUsuario.getPasse().equals(passLista)){

				dadosUsuario.diz("[Servidor] Bem-vindo " + usuario+ "!");
				System.out.println("Usuário: |"+ usuario+ "| logado com sucesso!");

				// carrega histórico msg a todos
				String nomeArquivo = "historicoConversas.txt";
				Path path = Paths.get(nomeArquivo);
				if (new File(nomeArquivo).exists()) {
						List<String> allLines = Files.readAllLines(path);
						for (String line : allLines) {
							dadosUsuario.diz(line);
						}
				}

				// verifica se há ficheiro de histórico msg priv e carrega em tela
				nomeArquivo = usuario+ ".txt";
				path = Paths.get(nomeArquivo);
				if (new File(nomeArquivo).exists()) {
						List<String> allLines = Files.readAllLines(path);
						for (String line : allLines) {
							dadosUsuario.diz(line);
						}
				}
			} else {
				//elimina os dados para não serem usados para login incorreto
				dadosUsuario.setServidor(null);
				dadosUsuario.setPasse("");
				dadosUsuario.setNomeUsuario("");
				return false;

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void enviaPraTodos(String texto, MessengerInterface deQuem) throws RemoteException{

		System.out.println("\n[" + deQuem.getNomeUsuario() + "] " + texto);

		Enumeration usuarios = lista.keys();
		String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/uu HH:mm"));
		String msgTratada = "\n |" + agora + "| [" + deQuem.getNomeUsuario() + "] " + texto;

		// SALVA CONVERSA A TODOS PARA UM FICHEIRO CADA VEZ QUE ALGUÉM ENVIA MSG
		geraArquivo(msgTratada, "historicoConversas.txt");

    while(usuarios.hasMoreElements()){
       String usuario = (String) usuarios.nextElement();
       MessengerInterface mensagem = (MessengerInterface) lista.get(usuario);
       if (usuario.equals(deQuem.getNomeUsuario())){
       	continue;
       }
       try{
    	   mensagem.diz(msgTratada);
       } catch(Exception e){
	       e.printStackTrace();
       }
    }
	}

	public void enviarPrivado(String usuarioDestino, String msg, MessengerInterface quemMandou) throws RemoteException{
		MessengerInterface destino = (MessengerInterface) lista.get(usuarioDestino);
		String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/uu HH:mm"));
		String msgTratada = "\n |" + agora + "| *MP* [" + quemMandou.getNomeUsuario() + "] " + msg;
		String nomeArquivoDeQuem = quemMandou.getNomeUsuario() + ".txt";
		String nomeArquivoParaQuem = destino.getNomeUsuario() + ".txt";

		// SALVA CONVERSA PRIVADA PARA REMETENTE E DISTINATÁRIO PARA UM FICHEIRO
		geraArquivo(msgTratada, nomeArquivoDeQuem);
		geraArquivo(msgTratada, nomeArquivoParaQuem);

		try{
			destino.diz(msgTratada);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	// 'quemSolicitou' serve para indicar a quem devolver a lista (somente para ele)
	public void listaUsuarios(MessengerInterface quemSolicitou)throws RemoteException{
		MessengerInterface mensagem = (MessengerInterface) lista.get(quemSolicitou.getNomeUsuario());
		Set<String> keys = lista.keySet();
		try {
			for(String key: keys){
				if (key.equals(quemSolicitou.getNomeUsuario())){
					key = key + " (You)";
				}
				mensagem.diz(key);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void geraArquivo(String texto, String nomeArquivo)throws RemoteException{
		Path path = Paths.get(nomeArquivo);
		byte[] strToBytes = texto.getBytes();
		try {
			Files.write(path, strToBytes,
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean enviaArquivo(String arqDestino, byte[] dados, int carga, String paraQuem, String deQuem) throws RemoteException{
		MessengerInterface origem = (MessengerInterface) lista.get(deQuem);
		MessengerInterface destino = (MessengerInterface) lista.get(paraQuem);
		String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/uu HH:mm"));
		String ref = "\n |" + agora + "| *MP* ";
		if (destino.gravaArquivo(arqDestino, dados, carga)) {
			destino.diz(ref + "Acaba de receber um arquivo de [" + origem.getNomeUsuario() + "] na pasta: " + arqDestino);
			origem.diz("Arquivo |" + arqDestino + "| enviado com sucesso para ["+ destino.getNomeUsuario() + "]");
		}
		return true;
	}
}
