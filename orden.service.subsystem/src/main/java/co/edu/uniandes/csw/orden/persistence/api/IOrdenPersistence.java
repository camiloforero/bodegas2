
package co.edu.uniandes.csw.orden.persistence.api;

public interface IOrdenPersistence extends _IOrdenPersistence {

    /**
     * Este m�todo retorna el m�ximo valor de la ID de todas las �rdenes que existen  
     * @return: Mayor valor de la columna ID de la tabla OrdenEntity
    */
    public long getMaxID();

}