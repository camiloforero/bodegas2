
package co.edu.uniandes.csw.producto.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.producto.persistence.api.IProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.entity.ProductoEntity;

@RunWith(Arquillian.class)
public class ProductoPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ProductoPersistence.class.getPackage())
				.addPackage(ProductoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IProductoPersistence productoPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from ProductoEntity").executeUpdate();
	}

	private List<ProductoEntity> data=new ArrayList<ProductoEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ProductoEntity entity=new ProductoEntity();
			entity.setName(generateRandom(String.class));
			entity.setTipo(generateRandom(String.class));
			entity.setTalla(generateRandom(String.class));
			entity.setPrecio(generateRandom(String.class));
			entity.setImagen(generateRandom(String.class));
			entity.setCalificacion(generateRandom(Integer.class));
			entity.setNumCalificaciones(generateRandom(Integer.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createProductoTest(){
		ProductoDTO dto=new ProductoDTO();
		dto.setName(generateRandom(String.class));
		dto.setTipo(generateRandom(String.class));
		dto.setTalla(generateRandom(String.class));
		dto.setPrecio(generateRandom(String.class));
		dto.setImagen(generateRandom(String.class));
		dto.setCalificacion(generateRandom(Integer.class));
		dto.setNumCalificaciones(generateRandom(Integer.class));
		
		
		ProductoDTO result=productoPersistence.createProducto(dto);
		
		Assert.assertNotNull(result);
		
		ProductoEntity entity=em.find(ProductoEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getTipo(), entity.getTipo());	
		Assert.assertEquals(dto.getTalla(), entity.getTalla());	
		Assert.assertEquals(dto.getPrecio(), entity.getPrecio());	
		Assert.assertEquals(dto.getImagen(), entity.getImagen());	
		Assert.assertEquals(dto.getCalificacion(), entity.getCalificacion());	
		Assert.assertEquals(dto.getNumCalificaciones(), entity.getNumCalificaciones());	
	}
	
	@Test
	public void getProductosTest(){
		List<ProductoDTO> list=productoPersistence.getProductos();
		Assert.assertEquals(list.size(), data.size());
        for(ProductoDTO dto:list){
        	boolean found=false;
            for(ProductoEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getProductoTest(){
		ProductoEntity entity=data.get(0);
		ProductoDTO dto=productoPersistence.getProducto(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getTipo(), dto.getTipo());
		Assert.assertEquals(entity.getTalla(), dto.getTalla());
		Assert.assertEquals(entity.getPrecio(), dto.getPrecio());
		Assert.assertEquals(entity.getImagen(), dto.getImagen());
		Assert.assertEquals(entity.getCalificacion(), dto.getCalificacion());
		Assert.assertEquals(entity.getNumCalificaciones(), dto.getNumCalificaciones());
        
	}
	
	@Test
	public void deleteProductoTest(){
		ProductoEntity entity=data.get(0);
		productoPersistence.deleteProducto(entity.getId());
        ProductoEntity deleted=em.find(ProductoEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateProductoTest(){
		ProductoEntity entity=data.get(0);
		
		ProductoDTO dto=new ProductoDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setTipo(generateRandom(String.class));
		dto.setTalla(generateRandom(String.class));
		dto.setPrecio(generateRandom(String.class));
		dto.setImagen(generateRandom(String.class));
		dto.setCalificacion(generateRandom(Integer.class));
		dto.setNumCalificaciones(generateRandom(Integer.class));
		
		
		productoPersistence.updateProducto(dto);
		
		
		ProductoEntity resp=em.find(ProductoEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getTipo(), resp.getTipo());	
		Assert.assertEquals(dto.getTalla(), resp.getTalla());	
		Assert.assertEquals(dto.getPrecio(), resp.getPrecio());	
		Assert.assertEquals(dto.getImagen(), resp.getImagen());	
		Assert.assertEquals(dto.getCalificacion(), resp.getCalificacion());	
		Assert.assertEquals(dto.getNumCalificaciones(), resp.getNumCalificaciones());	
	}
	
	public <T> T generateRandom(Class<T> objectClass){
		Random r=new Random();
		if(objectClass.isInstance(String.class)){
			String s="";
			for(int i=0;i<10;i++){
				char c=(char)(r.nextInt()/('Z'-'A')+'A');
				s=s+c;
			}
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Integer.class)){
			Integer s=r.nextInt();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Long.class)){
			Long s=r.nextLong();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(java.util.Date.class)){
			java.util.Calendar c=java.util.Calendar.getInstance();
			c.set(java.util.Calendar.MONTH, r.nextInt()/12);
			c.set(java.util.Calendar.DAY_OF_MONTH,r.nextInt()/30);
			c.setLenient(false);
			return objectClass.cast(c.getTime());
		} 
		return null;
	}
	
}