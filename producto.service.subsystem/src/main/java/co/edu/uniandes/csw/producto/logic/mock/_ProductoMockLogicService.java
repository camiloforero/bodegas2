
package co.edu.uniandes.csw.producto.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.producto.logic.api._IProductoLogicService;

public abstract class _ProductoMockLogicService implements _IProductoLogicService {

	private Long id= new Long(1);
	protected List<ProductoDTO> data=new ArrayList<ProductoDTO>();

	public ProductoDTO createProducto(ProductoDTO producto){
		id++;
		producto.setId(id);
		return producto;
    }

	public List<ProductoDTO> getProductos(){
		return data; 
	}

	public ProductoDTO getProducto(Long id){
		for(ProductoDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteProducto(Long id){
	    ProductoDTO delete=null;
		for(ProductoDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateProducto(ProductoDTO producto){
	    ProductoDTO delete=null;
		for(ProductoDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(producto);
		} 
	}
        
        public List<ProductoDTO> getProductos(String nombre){
            List<ProductoDTO> dataName=new ArrayList<ProductoDTO>();
		for(ProductoDTO d:data){
			if(d.getName().equals(nombre)){
				dataName.add(d);
			}
		}
             return dataName;
	}
        
        
}