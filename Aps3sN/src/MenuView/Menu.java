package MenuView;

import Arquivo.Console;

public class Menu {
	
	public static final int LISTAR_OPCAO = 1;
	public static final int CADASTRAR_OPCAO = 2;
	public static final int SAIR_OPCAO = 3;
	
	public Menu() {

	}
	
	public void show()
	{
		int opcaoEscolhida = 0;
		
		while (opcaoEscolhida != SAIR_OPCAO)
		{
			System.out.println("\n");
			
			Submenu submenu = null;
			StringBuilder menu = new StringBuilder();
			
			menu.append("BEM VINDO A UNIVERSIDADE AMAZONIA \n\n");
			
			// Definição do menu
			menu.append(String.format("[%d] Listar \n", LISTAR_OPCAO));
			menu.append(String.format("[%d] Cadastrar \n", CADASTRAR_OPCAO));
			menu.append(String.format("[%d] Sair \n\n", SAIR_OPCAO));
			
			menu.append("Escolha uma opção: ");
			
			System.out.print(menu.toString());
			
			opcaoEscolhida = Console.tryParseToInt(Console.getValorDigitado());
			
			switch(opcaoEscolhida) {
				case LISTAR_OPCAO:
					submenu = new MenuListar();
					break;
				case CADASTRAR_OPCAO:
					submenu = new MenuCadastrar();
					break;
			}
			
			if (submenu != null) {
				submenu.showSubmenu();
			}
		}
		
		System.out.println("\n\nEncerrando.");
	}
}
