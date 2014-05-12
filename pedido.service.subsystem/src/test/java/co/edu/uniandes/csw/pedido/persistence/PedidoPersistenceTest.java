
package co.edu.uniandes.csw.pedido.persistence;

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
import co.edu.uniandes.csw.pedido.persistence.api.IPedidoPersistence;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;

@RunWith(Arquillian.class)
public class PedidoPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(PedidoPersistence.class.getPackage())
				.addPackage(PedidoEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IPedidoPersistence pedidoPersistence;

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
		em.createQuery("delete from PedidoEntity").executeUpdate();
	}

	private List<PedidoEntity> data=new ArrayList<PedidoEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			PedidoEntity entity=new PedidoEntity();
			entity.setCantidad(generateRandom(Integer.class));
			entity.setName(generateRandom(String.class));
			entity.setProductoId(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createPedidoTest(){
		PedidoDTO dto=new PedidoDTO();
		dto.setCantidad(generateRandom(Integer.class));
		dto.setName(generateRandom(String.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		PedidoDTO result=pedidoPersistence.createPedido(dto);
		
		Assert.assertNotNull(result);
		
		PedidoEntity entity=em.find(PedidoEntity.class, result.getId());
		
		Assert.assertEquals(dto.getCantidad(), entity.getCantidad());	
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getProductoId(), entity.getProductoId());	
	}
	
	@Test
	public void getPedidosTest(){
		List<PedidoDTO> list=pedidoPersistence.getPedidos();
		Assert.assertEquals(list.size(), data.size());
        for(PedidoDTO dto:list){
        	boolean found=false;
            for(PedidoEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getPedidoTest(){
		PedidoEntity entity=data.get(0);
		PedidoDTO dto=pedidoPersistence.getPedido(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getCantidad(), dto.getCantidad());
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getProductoId(), dto.getProductoId());
        
	}
	
	@Test
	public void deletePedidoTest(){
		PedidoEntity entity=data.get(0);
		pedidoPersistence.deletePedido(entity.getId());
        PedidoEntity deleted=em.find(PedidoEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updatePedidoTest(){
		PedidoEntity entity=data.get(0);
		
		PedidoDTO dto=new PedidoDTO();
		dto.setId(entity.getId());
		dto.setCantidad(generateRandom(Integer.class));
		dto.setName(generateRandom(String.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		pedidoPersistence.updatePedido(dto);
		
		
		PedidoEntity resp=em.find(PedidoEntity.class, entity.getId());
		
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