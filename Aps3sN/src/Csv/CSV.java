package Csv;

import java.util.Collection;

public interface CSV<T> {

	Collection<T> carregar();
	void salvar(T obj);

    /**
     *
     * @param obj
     * @return
     */
    boolean validar(T obj);
}