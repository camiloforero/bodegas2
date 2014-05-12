
package co.edu.uniandes.csw.iteminventario.logic.ejb;

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
import co.edu.uniandes.csw.iteminventario.logic.api.IItemInventarioLogicService;
import co.edu.uniandes.csw.iteminventario.persistence.ItemInventarioPersistence;
import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;
import co.edu.uniandes.csw.iteminventario.persistence.entity.ItemInventarioEntity;

@RunWith(Arquillian.class)
public class ItemInventarioLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(ItemInventarioLogicService.class.getPackage())
				.addPackage(ItemInventarioPersistence.class.getPackage())
				.addPackage(ItemInventarioEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IItemInventarioLogicService itemInventarioLogicService;
	
	@Inject
	private IItemInventarioPersistence itemInventarioPersistence;	

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<ItemInventarioDTO> dtos=itemInventarioPersistence.getItemInventarios();
		for(ItemInventarioDTO dto:dtos){
			itemInventarioPersistence.deleteItemInventario(dto.getId());
		}
	}

	private List<ItemInventarioDTO> data=new ArrayList<ItemInventarioDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			ItemInventarioDTO pdto=new ItemInventarioDTO();
			pdto.setCantidad(generateRandom(Integer.class));
			pdto.setName(generateRandom(String.class));
			pdto.setProductoId(generateRandom(Long.class));
			pdto=itemInventarioPersistence.createItemInventario(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createItemInventarioTest(){
		ItemInventarioDTO ldto=new ItemInventarioDTO();
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setName(generateRandom(String.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		ItemInventarioDTO result=itemInventarioLogicService.createItemInventario(ldto);
		
		Assert.assertNotNull(result);
		
		ItemInventarioDTO pdto=itemInventarioPersistence.getItemInventario(result.getId());
		
		Assert.assertEquals(ldto.getCantidad(), pdto.getCantidad());	
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getProductoId(), pdto.getProductoId());	
	}
	
	@Test
	public void getItemInventariosTest(){
		List<ItemInventarioDTO> list=itemInventarioLogicService.getItemInventarios();
		Assert.assertEquals(list.size(), data.size());
        for(ItemInventarioDTO ldto:list){
        	boolean found=false;
            for(ItemInventarioDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getItemInventarioTest(){
		ItemInventarioDTO pdto=data.get(0);
		ItemInventarioDTO ldto=itemInventarioLogicService.getItemInventario(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getCantidad(), ldto.getCantidad());
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getProductoId(), ldto.getProductoId());
        
	}
	
	@Test
	public void deleteItemInventarioTest(){
		ItemInventarioDTO pdto=data.get(0);
		itemInventarioLogicService.deleteItemInventario(pdto.getId());
        ItemInventarioDTO deleted=itemInventarioPersistence.getItemInventario(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateItemInventarioTest(){
		ItemInventarioDTO pdto=data.get(0);
		
		ItemInventarioDTO ldto=new ItemInventarioDTO();
		ldto.setId(pdto.getId());
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setName(generateRandom(String.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		itemInventarioLogicService.updateItemInventario(ldto);
		
		
		ItemInventarioDTO resp=itemInventarioPersistence.getItemInventario(pdto.getId());
		
		Assert.assertEquals(ldto.getCantidad(), resp.getCantidad());	
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getProductoId(), resp.getProductoId());	
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