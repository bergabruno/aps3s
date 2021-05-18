package MenuView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import Amazonia.Aluno;
import Amazonia.Curso;
import Amazonia.Rendimento;
import Csv.AlunoCSV;
import Csv.CursoCSV;
import Csv.RendimentoCSV;
import Exception.NaoExisteEx;
import Arquivo.Console;

public class MenuListar extends Submenu {
	
	private AlunoCSV alunoCsv = new AlunoCSV();
	private CursoCSV cursoCsv = new CursoCSV();
	
	private final int LISTARALUNO_OPTION = 1;
	private final int LISTARCURSOS_OPTION = 2;
        private final int LISTARRENDIMENTO_OPTION = 3;
	private final int LISTARHISTORICO_OPTION = 4;
	private final int VOLTAR_OPTION = 5;
	
	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();
			
			menu.append(String.format("[%d] Listar Alunos \n", LISTARALUNO_OPTION));
			menu.append(String.format("[%d] Listar Cursos \n", LISTARCURSOS_OPTION));
                        menu.append(String.format("[%d] Listar Rendimento \n", LISTARRENDIMENTO_OPTION));
			menu.append(String.format("[%d] listar Historico \n", LISTARHISTORICO_OPTION));
			menu.append(String.format("[%d] VOLTAR \n\n", VOLTAR_OPTION));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = Console.tryParseToInt(Console.getValorDigitado());
			
			switch(opcaoEscolhida) {
				case LISTARALUNO_OPTION:
					listarAlunos();
					break;
				case LISTARCURSOS_OPTION:
					listarCursos();
					break;
				case LISTARRENDIMENTO_OPTION:
					exibirRelatorioRendimentoAluno();
					break;
                                case LISTARHISTORICO_OPTION:
					exibirRelatorioCursoRendimento();
					break;     
                                        
			}
		}
	}
	
	
	private void listarAlunos() {
		System.out.println();
		
		Collection<Aluno> alunos = alunoCsv.carregar();
		
		for(Aluno aluno : alunos) {
			System.out.println(aluno);
		}
	}
        
        private void listarCursos() {
		Collection<Curso> cursos = cursoCsv.carregar();
	
	    System.out.println("-----------------------------");
	    for(Curso curso : cursos) {
	    	System.out.println(curso);
	    }
	} 
        
        
	private void exibirRelatorioRendimentoAluno() {
		List<Rendimento> rendimentosAluno = new ArrayList<Rendimento>();
		Set<Curso> cursos = cursoCsv.carregar();
		
		Aluno aluno;
		
		try {
			System.out.print("Digite o id do aluno para o histórico: ");
			aluno = alunoCsv.getById(Console.getValorDigitado());

			cursos.forEach(curso -> {
				rendimentosAluno.addAll(new RendimentoCSV(curso).getByCursoAndAlunoId(aluno.getId()));
			});
			
			rendimentosAluno.forEach((rendimento) -> {
				System.out.println();
				System.out.println(rendimento.toString(true, false));
			});
		} catch (NaoExisteEx ex) {
			Console.error(ex.getMessage());
		}
	}
        private void exibirRelatorioCursoRendimento() {
		try {
			String nomeCurso, nivelCurso;
			int anoCurso;
			Curso curso;
						
			System.out.print("Digite o nome do curso: ");
			nomeCurso = Console.getValorDigitado();
			
			System.out.print("Digite o nível do curso: ");
			nivelCurso = Console.getValorDigitado();
			
			System.out.print("Digite o ano do curso: ");
			anoCurso = Console.tryParseToInt(Console.getValorDigitado());
			
			curso = cursoCsv.pegarNomeNivelAno(nomeCurso, nivelCurso, anoCurso);
			
			List<Rendimento> rendimentosCurso = new RendimentoCSV(curso).getByCurso();
			
			rendimentosCurso.forEach(rendimento -> {
				System.out.println();
				System.out.println(rendimento.toString(false, true));
			});
			
		} catch (NaoExisteEx ex) {
			Console.error(ex.getMessage());
		}
	}
}
