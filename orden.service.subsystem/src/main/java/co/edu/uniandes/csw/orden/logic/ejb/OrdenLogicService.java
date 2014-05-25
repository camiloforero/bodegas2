
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
        if(!orden.getEstado().equals("Satisfecha"))
        {
            int cantidad = orden.getCantidad();
            long idProducto = Long.parseLong(orden.getName());
            String tipo = orden.getTipo();
            boolean seCumple = logLogicService.cumplirOrden(idProducto, cantidad, tipo);
            if(seCumple)
            {
                if(tipo.equals("Orden de fabricacion"))
                {
                    orden.setTipo("Orden de despacho");
                }
                else orden.setEstado("Satisfecha");
                updateOrden(orden);
                
                
            }
            
            return false;
        }
        
        return false;
        
    }
    
}