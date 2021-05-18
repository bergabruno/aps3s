package Exception;

public class NaoExisteEx extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NaoExisteEx(String message) {
		super(message);
	}
}
