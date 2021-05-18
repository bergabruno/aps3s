
package Csv;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import Amazonia.Aluno;
import Exception.ExisteEx;
import Exception.NaoExisteEx;
import Exception.ValidoEx;
import Arquivo.Arquivos;


public class AlunoCSV implements CSV<Aluno> {
	
	private final Arquivos fileUtils = new Arquivos("alunos.csv");
	
    /**
     *
     * @return
     */
        @Override
	public Set<Aluno> carregar() {
            
		Set<Aluno> dados = new HashSet<Aluno>();
		
		for(String valor : fileUtils.pegarArquivo()) {
			String[] linha = valor.split(",");
			dados.add(new Aluno(linha[0], linha[1]));
		}
		
		return dados;
	}
	
    /**
     *
     * @param id
     * @return
     */
    public Aluno getById(String id) {
		return carregar().stream().filter(a -> a.getId().equals(id)).findFirst()
			.orElseThrow(() -> new NaoExisteEx(String.format("Nenhum aluno encontrado com o id  %s!", id)));
	}
	
    /**
     *
     * @param aluno
     */
    @Override
	public void salvar(Aluno aluno) {
		if (validar(aluno)) {
			Set<String> idExistentes = carregar().stream().map(a -> a.getId()).collect(Collectors.toSet());
			
			if (!idExistentes.contains(aluno.getId())) {
				fileUtils.scannerConteudo(aluno.toCSV());
			} else {
				throw new ExisteEx(String.format("Já existe um aluno cadastrado com o id  %s!", aluno.getId()));
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
	public boolean validar(Aluno obj) {
		return !obj.getId().trim().isEmpty() && !obj.getNome().trim().isEmpty(); 
	}
}
