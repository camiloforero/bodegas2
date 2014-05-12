
package co.edu.uniandes.csw.iteminventario.persistence;

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


import co.edu.uniandes.csw.iteminventario.logic.dto.ItemInventarioDTO;
import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;
import co.edu.uniandes.csw.iteminventario.persistence.entity.ItemInventarioEntity;

@RunWith(Arquillian.class)
public class ItemInventarioPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ItemInventarioPersistence.class.getPackage())
				.addPackage(ItemInventarioEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IItemInventarioPersistence itemInventarioPersistence;

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
		em.createQuery("delete from ItemInventarioEntity").executeUpdate();
	}

	private List<ItemInventarioEntity> data=new ArrayList<ItemInventarioEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ItemInventarioEntity entity=new ItemInventarioEntity();
			entity.setCantidad(generateRandom(Integer.class));
			entity.setName(generateRandom(String.class));
			entity.setProductoId(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createItemInventarioTest(){
		ItemInventarioDTO dto=new ItemInventarioDTO();
		dto.setCantidad(generateRandom(Integer.class));
		dto.setName(generateRandom(String.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		ItemInventarioDTO result=itemInventarioPersistence.createItemInventario(dto);
		
		Assert.assertNotNull(result);
		
		ItemInventarioEntity entity=em.find(ItemInventarioEntity.class, result.getId());
		
		Assert.assertEquals(dto.getCantidad(), entity.getCantidad());	
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getProductoId(), entity.getProductoId());	
	}
	
	@Test
	public void getItemInventariosTest(){
		List<ItemInventarioDTO> list=itemInventarioPersistence.getItemInventarios();
		Assert.assertEquals(list.size(), data.size());
        for(ItemInventarioDTO dto:list){
        	boolean found=false;
            for(ItemInventarioEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getItemInventarioTest(){
		ItemInventarioEntity entity=data.get(0);
		ItemInventarioDTO dto=itemInventarioPersistence.getItemInventario(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getCantidad(), dto.getCantidad());
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getProductoId(), dto.getProductoId());
        
	}
	
	@Test
	public void deleteItemInventarioTest(){
		ItemInventarioEntity entity=data.get(0);
		itemInventarioPersistence.deleteItemInventario(entity.getId());
        ItemInventarioEntity deleted=em.find(ItemInventarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateItemInventarioTest(){
		ItemInventarioEntity entity=data.get(0);
		
		ItemInventarioDTO dto=new ItemInventarioDTO();
		dto.setId(entity.getId());
		dto.setCantidad(generateRandom(Integer.class));
		dto.setName(generateRandom(String.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		itemInventarioPersistence.updateItemInventario(dto);
		
		
		ItemInventarioEntity resp=em.find(ItemInventarioEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getCantidad(), resp.getCantidad());	
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getProductoId(), resp.getProductoId());	
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