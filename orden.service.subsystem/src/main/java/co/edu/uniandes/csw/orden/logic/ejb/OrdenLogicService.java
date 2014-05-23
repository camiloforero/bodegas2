
package co.edu.uniandes.csw.orden.logic.ejb;

import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.enterprise.inject.Default;
import javax.inject.Inject;


@Default
@Stateless
@LocalBean
public class OrdenLogicService extends _OrdenLogicService implements IOrdenLogicService 
{

    public boolean satisfacer(long id) {
        System.out.println("VICTORY " + id);
        OrdenDTO orden = persistance.getOrden(id);
        int cantidad = orden.getCantidad();
        long idProducto = Long.parseLong(orden.getName());
        
        return false;
    }
    
}