package co.edu.uniandes.csw.pedido.master.logic.ejb;

import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.master.logic.api._IPedidoMasterLogicService;
import co.edu.uniandes.csw.pedido.master.logic.dto.PedidoMasterDTO;
import co.edu.uniandes.csw.pedido.master.persistence.api.IPedidoMasterPersistence;
import co.edu.uniandes.csw.pedido.master.persistence.entity.PedidoOrdenEntity;
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import javax.inject.Inject;

public abstract class _PedidoMasterLogicService implements _IPedidoMasterLogicService {
    
    @Inject
    protected IPedidoPersistence pedidoPersistance;
    @Inject
    protected IPedidoMasterPersistence pedidoMasterPersistance;
    @Inject
    protected IOrdenPersistence ordenPersistance;
    
    @Inject
    protected ILogLogicService apiLog;
    
    public PedidoMasterDTO createMasterPedido(PedidoMasterDTO pedido) {
        PedidoDTO persistedPedidoDTO = pedidoPersistance.createPedido(pedido.getPedidoEntity());
        int cantidadDisponible = apiLog.darCantidadProducto(persistedPedidoDTO.getProductoId());
        System.out.println(cantidadDisponible);
        
        int cantidadPedida = persistedPedidoDTO.getCantidad();
        
        int diferencia = cantidadDisponible - cantidadPedida;
        
        int cantidadPosible = diferencia >= 0 ? cantidadPedida : cantidadDisponible;
        
        if(cantidadPosible > 0)
        {
            //Crea un DTO para guardar una orden de despacho
            OrdenDTO ordenDespacho = new OrdenDTO();
            //Guarda los atributos
            ordenDespacho.setEstado("Por entregar");
            ordenDespacho.setId(ordenPersistance.getMaxID() + 1l);
            ordenDespacho.setName(persistedPedidoDTO.getProductoId() + "");
            ordenDespacho.setTipo("Orden de despacho");
            ordenDespacho.setCantidad(cantidadPosible);
            
            //Guarda la orden creada
            OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenDespacho);
            PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
            pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
        }
        
        
        //Crea una orden de producción, si aplica
        if(diferencia < 0)
        {
            //representa los productos que faltan en el inventario
            int productosFaltantes = -diferencia;
            int cueros = apiLog.darCantidadProducto(101l);
            int hierro = apiLog.darCantidadProducto(53l);
            
            //Representa la cantidad de productos que se pueden hacer con la materia prima en inventario
            int productosPosibles = Math.min(cueros/2, hierro);
            
            //Quiere decir que se pueden crear productos suficientes
            if(productosPosibles > productosFaltantes)
            {
                //Crea un DTO para guardar una orden de despacho
                OrdenDTO ordenProd = new OrdenDTO();
                //Guarda los atributos
                ordenProd.setEstado("Por entregar");
                ordenProd.setId(ordenPersistance.getMaxID() + 1l);
                ordenProd.setName(persistedPedidoDTO.getProductoId() + "");
                ordenProd.setTipo("Orden de produccion");
                ordenProd.setCantidad(productosFaltantes);
                
                //Guarda la orden creada
                OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenProd);
                PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
                pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
            }
            else
            {
                //Representa los productos que no pueden ser fabricados
                int prdsNoFabricables = productosFaltantes - productosPosibles;
                if(prdsNoFabricables > 0)
                {
                    //Crea un DTO para guardar una orden de producción, que no puede ser satisfecha en este momento
                    OrdenDTO ordenProd = new OrdenDTO();
                    //Guarda los atributos
                    ordenProd.setEstado("Por entregar");
                    ordenProd.setId(ordenPersistance.getMaxID() + 1l);
                    ordenProd.setName(persistedPedidoDTO.getProductoId() + "");
                    ordenProd.setTipo("Orden de produccion");
                    ordenProd.setCantidad(productosFaltantes);
                    
                    //Guarda la orden creada
                    OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenProd);
                    PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
                    pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
                }
                
                
                
                int cuerosFaltantes = 2*prdsNoFabricables - cueros;
                int hierroFaltante = prdsNoFabricables - hierro;
                if(cuerosFaltantes > 0)
                {
                    //Crea un DTO para guardar una orden de despacho
                    OrdenDTO ordenProd = new OrdenDTO();
                    //Guarda los atributos
                    ordenProd.setEstado("Por entregar");
                    ordenProd.setId(ordenPersistance.getMaxID() + 1l);
                    ordenProd.setName(101l + "");
                    ordenProd.setTipo("Orden de reaprovisionamiento");
                    ordenProd.setCantidad(cuerosFaltantes);
                    
                    //Guarda la orden creada
                    OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenProd);
                    PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
                    pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
                }
                if(hierroFaltante > 0)
                {
                    //Crea un DTO para guardar una orden de despacho
                    OrdenDTO ordenProd = new OrdenDTO();
                    //Guarda los atributos
                    ordenProd.setEstado("Por entregar");
                    ordenProd.setId(ordenPersistance.getMaxID() + 1l);
                    ordenProd.setName(53l + "");
                    ordenProd.setTipo("Orden de reaprovisionamiento");
                    ordenProd.setCantidad(hierroFaltante);
                    
                    //Guarda la orden creada
                    OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenProd);
                    PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(persistedPedidoDTO.getId(), persistedOrdenDTO.getId());
                    pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
                }
                
            }
            
        }
        
        
        return pedido;
    }
    
    public PedidoMasterDTO getMasterPedido(Long id) {
        return pedidoMasterPersistance.getPedido(id);
    }
    
    public void deleteMasterPedido(Long id) {
        pedidoPersistance.deletePedido(id);
    }
    
    public void updateMasterPedido(PedidoMasterDTO pedido) {
        pedidoPersistance.updatePedido(pedido.getPedidoEntity());
        
        //---- FOR RELATIONSHIP
        // persist new orden
        if (pedido.getCreateOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getCreateOrden()) {
                OrdenDTO persistedOrdenDTO = ordenPersistance.createOrden(ordenDTO);
                PedidoOrdenEntity pedidoOrdenEntity = new PedidoOrdenEntity(pedido.getPedidoEntity().getId(), persistedOrdenDTO.getId());
                pedidoMasterPersistance.createPedidoOrden(pedidoOrdenEntity);
            }
        }
        // update orden
        if (pedido.getUpdateOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getUpdateOrden()) {
                ordenPersistance.updateOrden(ordenDTO);
            }
        }
        // delete orden
        if (pedido.getDeleteOrden() != null) {
            for (OrdenDTO ordenDTO : pedido.getDeleteOrden()) {
                pedidoMasterPersistance.deletePedidoOrden(pedido.getPedidoEntity().getId(), ordenDTO.getId());
                ordenPersistance.deleteOrden(ordenDTO.getId());
            }
        }
    }
}
