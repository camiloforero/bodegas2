
package co.edu.uniandes.csw.log.logic.ejb;

import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
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
import java.util.List;

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
        System.out.println(tipo);
        if(tipo.equals("Orden de despacho"))
        {
            return crearLogSalida(idProducto, cantidad, tipo);
        }
        
        else if(tipo.equals("Orden de fabricacion"))
        {
            if(darCantidadProducto(53l) >= cantidad && 2*darCantidadProducto(101) >= cantidad)
            {
                crearLogSalida(53l, cantidad, "Orden de fabricacion");
                crearLogSalida(101l, 2*cantidad, "Orden de fabricacion");
                
                LogDTO log = new LogDTO();
                List<BodegaDTO> bodegas = bodegaPersistance.getBodegas();
                log.setBodegaId(bodegas.get((int)(Math.random()*bodegas.size())).getId());
                log.setCantidad(cantidad);
                log.setEntra(true);
                log.setJustificacion(tipo);
                log.setName(new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date()));
                log.setProductoId(idProducto);
                createLog(log);
                return true;
            }
            else return false;
            
        }
        else if(tipo.equals("Orden de reaprovisionamiento"))
        {
            LogDTO log = new LogDTO();
            List<BodegaDTO> bodegas = bodegaPersistance.getBodegas();
            log.setBodegaId(bodegas.get((int)(Math.random()*bodegas.size())).getId());
            log.setCantidad(cantidad);
            log.setEntra(true);
            log.setJustificacion(tipo);
            log.setName(new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date()));
            log.setProductoId(idProducto);
            createLog(log);
            return true;
        }
        else throw new RuntimeException("No entró a ninguno de los tres casos");
        
        
    }
    /**
     * 
     * @param idProducto: Id del producto a sacar
     * @param cantidad Cantidad del producto a sacar
     * @param tipo Tipo de la orden que requiere sacar el producto
     * @return true si hay items suficientes en el inventario para sacar el producto, false de lo contrario.
     * @throws RuntimeException: Cuando detecta productos suficientes 
     */
    private boolean crearLogSalida(long idProducto, int cantidad, String tipo)
    {
        if(cantidad <= darCantidadProducto(idProducto))
            {
                for (ItemInventarioDTO item : itemInventarioPersistance.getItemInventariosProducto(idProducto))
                {
                    if(item.getCantidad() < cantidad)
                    {
                        LogDTO log = new LogDTO();
                        log.setBodegaId(Long.parseLong(item.getName()));
                        log.setCantidad(item.getCantidad());
                        log.setEntra(false);
                        log.setJustificacion(tipo);
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
                        log.setJustificacion(tipo);
                        log.setName(new SimpleDateFormat("dd/MM/YYYY - HH:mm").format(new Date()));
                        log.setProductoId(idProducto);
                        createLog(log);
                        return true;
                    }
                }
                throw new RuntimeException("ERROR FATAL de concurrencia");
            }
            else return false;
        
    }
    
}