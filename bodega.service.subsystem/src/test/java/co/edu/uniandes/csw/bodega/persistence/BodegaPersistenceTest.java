
package co.edu.uniandes.csw.bodega.persistence;

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


import co.edu.uniandes.csw.bodega.logic.dto.BodegaDTO;
import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;
import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;

@RunWith(Arquillian.class)
public class BodegaPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(BodegaPersistence.class.getPackage())
				.addPackage(BodegaEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IBodegaPersistence bodegaPersistence;

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
		em.createQuery("delete from BodegaEntity").executeUpdate();
	}

	private List<BodegaEntity> data=new ArrayList<BodegaEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			BodegaEntity entity=new BodegaEntity();
			entity.setName(generateRandom(String.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createBodegaTest(){
		BodegaDTO dto=new BodegaDTO();
		dto.setName(generateRandom(String.class));
		
		
		BodegaDTO result=bodegaPersistence.createBodega(dto);
		
		Assert.assertNotNull(result);
		
		BodegaEntity entity=em.find(BodegaEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());	
	}
	
	@Test
	public void getBodegasTest(){
		List<BodegaDTO> list=bodegaPersistence.getBodegas();
		Assert.assertEquals(list.size(), data.size());
        for(BodegaDTO dto:list){
        	boolean found=false;
            for(BodegaEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getBodegaTest(){
		BodegaEntity entity=data.get(0);
		BodegaDTO dto=bodegaPersistence.getBodega(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
        
	}
	
	@Test
	public void deleteBodegaTest(){
		BodegaEntity entity=data.get(0);
		bodegaPersistence.deleteBodega(entity.getId());
        BodegaEntity deleted=em.find(BodegaEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateBodegaTest(){
		BodegaEntity entity=data.get(0);
		
		BodegaDTO dto=new BodegaDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		
		
		bodegaPersistence.updateBodega(dto);
		
		
		BodegaEntity resp=em.find(BodegaEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
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