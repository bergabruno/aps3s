package Arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Arquivos {

	private final String filePath;
	private final File file;
	private final Charset charset = Charset.forName("UTF-8");
	
	public Arquivos(String filePath) {
		this.filePath = filePath;
		this.file = new File(this.filePath);
		
		
		criarArquivo(this.file);
	}

	
	public List<String> pegarArquivo() {
		List<String> content = new ArrayList<String>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file, charset))) {
			String linha = reader.readLine();
			
			while (linha != null) {
				content.add(linha);
				linha = reader.readLine();
			}
			
		} catch (IOException ex) {
			Console.error("Ocorreu um erro ao realizar a leitura do arquivo.'");
		}
		
		return content;
	}
	
	
	public void scanner(Collection<String> lines) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, charset, false))){
			for(String linha : lines) {
				writer.write(linha + "\n");
			}
		} catch (IOException ex) {
			Console.error("Ocorreu um erro ao realizar a escrita no arquivo.'");
		}
	}
	
	
	public void scannerConteudo(String content) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, charset, true))) {
			writer.write(content + "\n");
		} catch (IOException ex) {
			Console.error("Ocorreu um erro ao realizar a escrita no arquivo. ");
		}
	}
	
	public String arquivoCaminho() {
		return filePath;
	}
	
	
	private void criarArquivo(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ex) {
			Console.error("Ocorreu um erro ao criar o arquivo.");
		}
	}
}
