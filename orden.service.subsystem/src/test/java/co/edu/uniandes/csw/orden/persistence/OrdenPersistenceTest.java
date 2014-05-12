
package co.edu.uniandes.csw.orden.persistence;

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


import co.edu.uniandes.csw.orden.logic.dto.OrdenDTO;
import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import co.edu.uniandes.csw.orden.persistence.entity.OrdenEntity;

@RunWith(Arquillian.class)
public class OrdenPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(OrdenPersistence.class.getPackage())
				.addPackage(OrdenEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IOrdenPersistence ordenPersistence;

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
		em.createQuery("delete from OrdenEntity").executeUpdate();
	}

	private List<OrdenEntity> data=new ArrayList<OrdenEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			OrdenEntity entity=new OrdenEntity();
			entity.setName(generateRandom(String.class));
			entity.setTipo(generateRandom(String.class));
			entity.setEstado(generateRandom(String.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createOrdenTest(){
		OrdenDTO dto=new OrdenDTO();
		dto.setName(generateRandom(String.class));
		dto.setTipo(generateRandom(String.class));
		dto.setEstado(generateRandom(String.class));
		
		
		OrdenDTO result=ordenPersistence.createOrden(dto);
		
		Assert.assertNotNull(result);
		
		OrdenEntity entity=em.find(OrdenEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getTipo(), entity.getTipo());	
		Assert.assertEquals(dto.getEstado(), entity.getEstado());	
	}
	
	@Test
	public void getOrdensTest(){
		List<OrdenDTO> list=ordenPersistence.getOrdens();
		Assert.assertEquals(list.size(), data.size());
        for(OrdenDTO dto:list){
        	boolean found=false;
            for(OrdenEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getOrdenTest(){
		OrdenEntity entity=data.get(0);
		OrdenDTO dto=ordenPersistence.getOrden(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getTipo(), dto.getTipo());
		Assert.assertEquals(entity.getEstado(), dto.getEstado());
        
	}
	
	@Test
	public void deleteOrdenTest(){
		OrdenEntity entity=data.get(0);
		ordenPersistence.deleteOrden(entity.getId());
        OrdenEntity deleted=em.find(OrdenEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateOrdenTest(){
		OrdenEntity entity=data.get(0);
		
		OrdenDTO dto=new OrdenDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setTipo(generateRandom(String.class));
		dto.setEstado(generateRandom(String.class));
		
		
		ordenPersistence.updateOrden(dto);
		
		
		OrdenEntity resp=em.find(OrdenEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getTipo(), resp.getTipo());	
		Assert.assertEquals(dto.getEstado(), resp.getEstado());	
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