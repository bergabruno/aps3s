package Csv;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;	
import java.util.stream.Collectors;

import Amazonia.Curso;
import Exception.ExisteEx;
import Exception.NaoExisteEx;
import Exception.ValidoEx;
import Arquivo.Arquivos;

public class CursoCSV implements CSV<Curso> {

  private final Arquivos fileUtils = new Arquivos("cursos.csv");

    /**
     *
     * @return
     */
    @Override
  public Set<Curso> carregar() {
      
    Set<Curso> dados = new HashSet<Curso>();

    for(String valor : fileUtils.pegarArquivo()) {
      String[] linha = valor.split(",");
      dados.add(new Curso(linha[0], linha[1], Integer.parseInt(linha[2])));
    }

    return dados;
  }

  
  public Set<Curso> pegarNome(String nome) {
      
    return carregar().stream().filter(a -> a.getNome().equals(nome)).collect(Collectors.toSet());
  }
  
  
  public Curso pegarNomeNivelAno(String nome, String nivel, int ano) {
      
	  return carregar().stream().filter(c -> c.getNome().equals(nome) && c.getNivel().equals(nivel) && c.getAno() == ano).findFirst()
		.orElseThrow(() -> new NaoExisteEx("Nenhum curso encontrado."));
  }
    
    /**
     *
     * @param curso
     */
    @Override
  public void salvar(Curso curso) {
      
    if (validar(curso)) {
      if (!existe(curso.getNome(), curso.getNivel(), curso.getAno())) {
    	curso.setAno(curso.getAno() == 0 ? Calendar.getInstance().get(Calendar.YEAR) : curso.getAno());  
    	  
        fileUtils.scannerConteudo(curso.toCSV());
      } else {
        throw new ExisteEx("Já existe um curso cadastrado com estes dados.");
      }
    } else {
      throw new ValidoEx("Nem todos os campos foram preenchidos corretamente. Verifique novamente.");
    }
  }

    /**
     *
     * @param obj
     * @return
     */
    @Override
  public boolean validar(Curso obj) {
	Set<String> niveisDisponiveis = Arrays.asList("Graduacao", "Pos graduacao").stream().collect(Collectors.toSet());
			
    
        if (obj.getNome().isBlank() || obj.getNivel().isBlank() || !niveisDisponiveis.contains(obj.getNivel())) {
    	return false;
    }
    
    return true;
  }

  
  private boolean existe(String nome, String nivel, int ano) {
      
	 try {
		 pegarNomeNivelAno(nome, nivel, ano);
		 return true;
	 } catch (NaoExisteEx ex) {
		 return false;
	 }
  }
}
