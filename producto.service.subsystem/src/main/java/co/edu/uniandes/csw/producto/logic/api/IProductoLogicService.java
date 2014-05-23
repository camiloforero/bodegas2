
package co.edu.uniandes.csw.producto.logic.api;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import java.util.List;

public interface IProductoLogicService extends _IProductoLogicService {

    public List<ProductoDTO> getProductos(String nombre);

}