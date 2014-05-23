
package co.edu.uniandes.csw.producto.persistence.api;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import java.util.List;

public interface IProductoPersistence extends _IProductoPersistence {

    public List<ProductoDTO> getProductos(String nombre);

}