package Csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import Amazonia.Curso;
import Amazonia.Nota;
import Amazonia.Rendimento;
import AmazoniaNota.TipoNota;
import Exception.ValidoEx;
import Arquivo.Arquivos;

public class RendimentoCSV implements CSV<Rendimento> {

	private final Arquivos fileUtils;
	private final Curso curso;
	
	private final AlunoCSV alunoCsv = new AlunoCSV();
		
    /**
     *
     * @param curso
     */
    public RendimentoCSV(Curso curso) {
        
		String nomeArquivo = String.format("%s_%s_%d", curso.getNome(), curso.getNivel(), curso.getAno()).toUpperCase();
		
		this.fileUtils = new Arquivos(nomeArquivo + ".csv");
		this.curso = curso;
	}
	
    /**
     *
     * @return
     */
    @Override
	public Collection<Rendimento> carregar() {
		return null;
	}
	
	
	public List<Rendimento> getByCurso() {
            
		List<Rendimento> dados = new ArrayList<Rendimento>();
		
		for(String linha : fileUtils.pegarArquivo()) {
			String[] linhaSplit = linha.split(",");
			
			dados.add(new Rendimento(alunoCsv.getById(linhaSplit[0]), this.curso,
				Arrays.asList(
					new Nota(Double.parseDouble(linhaSplit[1]), TipoNota.NP1),
					new Nota(Double.parseDouble(linhaSplit[2]), TipoNota.NP2),
					new Nota(Double.parseDouble(linhaSplit[3]), TipoNota.SUB),
					new Nota(Double.parseDouble(linhaSplit[4]), TipoNota.EXAME)
				)
			));
		}
		
		return dados;
	}
	
	
	public List<Rendimento> getByCursoAndAlunoId(String alunoId) {
		return getByCurso().stream().filter(r -> r.getAluno().getId().equals(alunoId)).collect(Collectors.toList());
	}

    /**
     *
     * @param obj
     */
    @Override
	public void salvar(Rendimento obj) {
            
		if (validar(obj)) {
			fileUtils.scannerConteudo(obj.toCSV());
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
	public boolean validar(Rendimento obj) {
            
		if (obj.getAluno() == null || obj.getCurso() == null || obj.getNotas().size() != 4) {
			return false;
		}
		
		return true;
	}
}
