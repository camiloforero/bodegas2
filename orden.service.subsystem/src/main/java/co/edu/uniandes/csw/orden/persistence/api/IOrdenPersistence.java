
package co.edu.uniandes.csw.orden.persistence.api;

public interface IOrdenPersistence extends _IOrdenPersistence {

    /**
     * Este método retorna el máximo valor de la ID de todas las órdenes que existen  
     * @return: Mayor valor de la columna ID de la tabla OrdenEntity
    */
    public long getMaxID();

}