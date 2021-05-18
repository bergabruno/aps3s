
package MenuView;

import Amazonia.Aluno;

import Amazonia.Curso;
import Amazonia.Nota;
import Amazonia.Rendimento;
import AmazoniaNota.TipoNota;
import Csv.CursoCSV;
import Csv.RendimentoCSV;
import Exception.ExisteEx;
import Exception.NaoExisteEx;
import Exception.ValidoEx;
import Arquivo.Console;
import Csv.AlunoCSV;
import java.util.Arrays;

public class MenuCadastrar extends Submenu {
	private final CursoCSV cursoCsv = new CursoCSV();
        private final AlunoCSV alunoCsv = new AlunoCSV();

	private final int CADASTRARALUNO_OPTION = 1;
	private final int CADASTRARCURSO_OPTION = 2;
	private final int CADASTRARRENDIMENTO_OPTION = 3;
	private final int VOLTAR_OPTION = 4;
  
	@Override
	public void showSubmenu() {
		int opcaoEscolhida = 0;

		while (opcaoEscolhida != VOLTAR_OPTION) {
			System.out.println("\n\n");
			StringBuilder menu = new StringBuilder();

			
			menu.append(String.format("[%d] Cadastrar Aluno \n", CADASTRARALUNO_OPTION));
			menu.append(String.format("[%d] Cadastrar curso \n", CADASTRARCURSO_OPTION));
			menu.append(String.format("[%d] Cadastrar rendimento \n", CADASTRARRENDIMENTO_OPTION));
			menu.append(String.format("[%d] VOLTAR \n\n", VOLTAR_OPTION));

			menu.append("Escolha uma opção: ");

			System.out.print(menu.toString());

			opcaoEscolhida = Console.tryParseToInt(Console.getValorDigitado());

			switch(opcaoEscolhida) {
				case CADASTRARALUNO_OPTION:
					cadastrarAluno();
					break;
		        case CADASTRARCURSO_OPTION:
		        	cadastrarCurso();
		        	break;
		        case CADASTRARRENDIMENTO_OPTION:
		        	cadastrarRendimento();
		        	break;
			}
		}
	}

        private void cadastrarAluno() {
		System.out.println();
				
		String id;
		String nome;
		
		try {
			System.out.print("Id: ");
			id = Console.getValorDigitado().trim();
			
			System.out.print("Nome: ");
			nome = Console.getValorDigitado().trim();
			
			
			alunoCsv.salvar(new Aluno(id, nome));
			
			Console.info(String.format("Aluno %s cadastrado com sucesso!", nome));
		} catch (ExisteEx | ValidoEx ex) {
			Console.error(ex.getMessage());
		}
	}

	private void cadastrarCurso() {
	    System.out.println();
	
	    String nome;
	    String nivel;
	    int ano;
	
	    try {
	    	System.out.print("Nome: ");
	    	nome = Console.getValorDigitado().trim();
	
	    	System.out.print("Nível (Graduacao / Posgraduacao): ");
	    	nivel = Console.getValorDigitado().trim();
	
	    	System.out.print("Ano: ");
	    	ano = Console.tryParseToInt(Console.getValorDigitado().trim());
	
	    	
	    	cursoCsv.salvar(new Curso(nome, nivel, ano));
	
	    	Console.info(String.format("Curso %s cadastrado com sucesso!", nome));
	    } catch (ExisteEx | ValidoEx ex) {
	    	Console.error(ex.getMessage());
	    }
	}
        private void cadastrarRendimento() {
		System.out.println();
				
		Aluno aluno;
		Curso curso;
		Rendimento rendimento;
		
		try {
			String nomeCurso, nivelCurso;
			int anoCurso;
			Nota notaNP1, notaNP2, notaSUB, notaExame;
			
			System.out.print("Digite o Id do aluno: ");
			aluno = alunoCsv.getById(Console.getValorDigitado());
			
			System.out.print("Digite o nome do curso: ");
			nomeCurso = Console.getValorDigitado();
			
			System.out.print("Digite o nível do curso: ");
			nivelCurso = Console.getValorDigitado();
			
			System.out.print("Digite o ano do curso: ");
			anoCurso = Console.tryParseToInt(Console.getValorDigitado());
			
			System.out.print("Digite a nota da NP1: ");
			notaNP1 = new Nota(Console.tryParseToDouble(Console.getValorDigitado()), TipoNota.NP1);
			
			System.out.print("Digite a nota da NP2: ");
			notaNP2 = new Nota(Console.tryParseToDouble(Console.getValorDigitado()), TipoNota.NP2);
			
			System.out.print("Digite a nota da SUB: ");
			notaSUB = new Nota(Console.tryParseToDouble(Console.getValorDigitado()), TipoNota.SUB);
			
			System.out.print("Digite a nota do EXAME: ");
			notaExame = new Nota(Console.tryParseToDouble(Console.getValorDigitado()), TipoNota.EXAME);
			
			curso = cursoCsv.pegarNomeNivelAno(nomeCurso, nivelCurso, anoCurso);
			
			rendimento = new Rendimento(aluno, curso, Arrays.asList(notaNP1, notaNP2, notaSUB, notaExame));
			
			new RendimentoCSV(rendimento.getCurso()).salvar(rendimento);
			
			Console.info(String.format("O rendimento do aluno %s foi gravado com sucesso!", rendimento.getAluno().getNome()));
		} catch (NaoExisteEx | ValidoEx ex) {
			Console.error(ex.getMessage());
		}
	}
	
}
