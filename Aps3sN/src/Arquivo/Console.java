package Arquivo;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
	
	private static Scanner scan;

    /**
     *
     * @return
     */
    public static String getValorDigitado() {
		scan = new Scanner(System.in);
		String valor = "";
		
		try{
			valor = scan.nextLine();
		} catch(NoSuchElementException ex) {
			ex.getMessage();
		}
		
		return valor;
	}
	
    /**
     *
     * @param value
     * @return
     */
    public static int tryParseToInt(String value) {
		int valor = 0;
		
		try {
			valor = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			Console.error("O valor digitado não parece ser um número inteiro.. verifique e tente novamente!");
		}
		
		return valor;
	}
	
    /**
     *
     * @param value
     * @return
     */
    public static double tryParseToDouble(String value) {
		double valor = 0;
		
		try {
			valor = Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			Console.error("O valor digitado não parece ser um número decimal.. verifique e tente novamente!");
		}
		
		return valor;
	}
	
    /**
     *
     * @param errorMessage
     */
    public static void error(String errorMessage) {
		System.err.println(String.format("\n\n[ERRO] => %s", errorMessage));
		System.err.println("Precione qualquer tecla para continuar.");
		
		Console.getValorDigitado();
	}
	
    /**
     *
     * @param message
     */
    public static void info(String message) {
		System.out.println(String.format("\n[INFO] => %s", message));
		System.out.println("Precione qualquer tecla para continuar.");
		
		Console.getValorDigitado();
	}
	
	
}

