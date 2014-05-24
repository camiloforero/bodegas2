
package co.edu.uniandes.csw.orden.logic.ejb;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
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

    @Inject
    protected ILogLogicService logLogicService;
    
    public boolean satisfacer(long id) {
        OrdenDTO orden = persistance.getOrden(id);
        int cantidad = orden.getCantidad();
        long idProducto = Long.parseLong(orden.getName());
        String tipo = orden.getTipo();
        logLogicService.cumplirOrden(idProducto, cantidad, tipo);
        
        
        return false;
    }
    
}