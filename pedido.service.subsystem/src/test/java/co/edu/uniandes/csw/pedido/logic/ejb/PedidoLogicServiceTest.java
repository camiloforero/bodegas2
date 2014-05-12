
package co.edu.uniandes.csw.pedido.logic.ejb;

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


import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.logic.api.IPedidoLogicService;
import co.edu.uniandes.csw.pedido.persistence.PedidoPersistence;
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;

@RunWith(Arquillian.class)
public class PedidoLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(PedidoLogicService.class.getPackage())
				.addPackage(PedidoPersistence.class.getPackage())
				.addPackage(PedidoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IPedidoLogicService pedidoLogicService;
	
	@Inject
	private IPedidoPersistence pedidoPersistence;	

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
		List<PedidoDTO> dtos=pedidoPersistence.getPedidos();
		for(PedidoDTO dto:dtos){
			pedidoPersistence.deletePedido(dto.getId());
		}
	}

	private List<PedidoDTO> data=new ArrayList<PedidoDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			PedidoDTO pdto=new PedidoDTO();
			pdto.setCantidad(generateRandom(Integer.class));
			pdto.setName(generateRandom(String.class));
			pdto.setProductoId(generateRandom(Long.class));
			pdto=pedidoPersistence.createPedido(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createPedidoTest(){
		PedidoDTO ldto=new PedidoDTO();
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setName(generateRandom(String.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		PedidoDTO result=pedidoLogicService.createPedido(ldto);
		
		Assert.assertNotNull(result);
		
		PedidoDTO pdto=pedidoPersistence.getPedido(result.getId());
		
		Assert.assertEquals(ldto.getCantidad(), pdto.getCantidad());	
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getProductoId(), pdto.getProductoId());	
	}
	
	@Test
	public void getPedidosTest(){
		List<PedidoDTO> list=pedidoLogicService.getPedidos();
		Assert.assertEquals(list.size(), data.size());
        for(PedidoDTO ldto:list){
        	boolean found=false;
            for(PedidoDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getPedidoTest(){
		PedidoDTO pdto=data.get(0);
		PedidoDTO ldto=pedidoLogicService.getPedido(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getCantidad(), ldto.getCantidad());
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getProductoId(), ldto.getProductoId());
        
	}
	
	@Test
	public void deletePedidoTest(){
		PedidoDTO pdto=data.get(0);
		pedidoLogicService.deletePedido(pdto.getId());
        PedidoDTO deleted=pedidoPersistence.getPedido(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updatePedidoTest(){
		PedidoDTO pdto=data.get(0);
		
		PedidoDTO ldto=new PedidoDTO();
		ldto.setId(pdto.getId());
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setName(generateRandom(String.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		pedidoLogicService.updatePedido(ldto);
		
		
		PedidoDTO resp=pedidoPersistence.getPedido(pdto.getId());
		
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