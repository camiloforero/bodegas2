
package co.edu.uniandes.csw.log.logic.ejb;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Default
@Stateless
@LocalBean
public class LogLogicService extends _LogLogicService implements ILogLogicService {

    public int darCantidadProducto(long idProducto) 
    {
        int ans = 0;
        for(ItemInventarioDTO dto : itemInventarioPersistance.getItemInventariosProducto(idProducto))
        {
            ans += dto.getCantidad();
        }
        return ans;
        
    }
    
    public boolean cumplirOrden(long idProducto, int cantidad, String tipo)
    {
        if(tipo.equals("Örden de despacho"))
        {
            for (ItemInventarioDTO item : itemInventarioPersistance.getItemInventariosProducto(idProducto))
            {
                if(item.getCantidad() < cantidad)
                {
                    LogDTO log = new LogDTO();
                    log.setBodegaId(Long.parseLong(item.getName()));
                    log.setCantidad(item.getCantidad());
                    log.setEntra(false);
                    log.setJustificacion("Orden de despacho");
                    log.setName(new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date()));
                    log.setProductoId(idProducto);
                    createLog(log);
                }
                else
                {
                    LogDTO log = new LogDTO();
                    log.setBodegaId(Long.parseLong(item.getName()));
                    log.setCantidad(cantidad);
                    log.setEntra(false);
                    log.setJustificacion("Orden de despacho");
                    log.setName(new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date()));
                    log.setProductoId(idProducto);
                    createLog(log);
                    return true;
                }
            }
            
        }
        
        return false;
        
       
    }

}