package co.edu.uniandes.csw.producto.service;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import javax.ws.rs.GET;

@Path("/Producto")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductoService extends _ProductoService {

     @GET
        @Path("busqueda/{nombre}")
        public List<ProductoDTO> getProductos(String nombre)
        {
            return productoLogicService.getProductos(nombre);
        }

}