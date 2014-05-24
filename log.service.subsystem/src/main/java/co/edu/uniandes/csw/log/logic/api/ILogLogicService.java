
package co.edu.uniandes.csw.log.logic.api;

public interface ILogLogicService extends _ILogLogicService 
{
    public int darCantidadProducto (long idProducto);
    
    public boolean cumplirOrden(long idProducto, int cantidad, String tipo);

}