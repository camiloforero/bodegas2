
package co.edu.uniandes.csw.iteminventario.persistence.api;

import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import java.util.List;

public interface IItemInventarioPersistence extends _IItemInventarioPersistence {
    
    public List<ItemInventarioDTO> getItemInventariosProducto(long idProducto);

}