
package co.edu.uniandes.csw.log.persistence;

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


import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;
import co.edu.uniandes.csw.log.persistence.entity.LogEntity;

@RunWith(Arquillian.class)
public class LogPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(LogPersistence.class.getPackage())
				.addPackage(LogEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ILogPersistence logPersistence;

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
		em.createQuery("delete from LogEntity").executeUpdate();
	}

	private List<LogEntity> data=new ArrayList<LogEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			LogEntity entity=new LogEntity();
			entity.setName(generateRandom(String.class));
			entity.setCantidad(generateRandom(Integer.class));
			entity.setEntra(generateRandom(Boolean.class));
			entity.setJustificacion(generateRandom(String.class));
			entity.setBodegaId(generateRandom(Long.class));
			entity.setProductoId(generateRandom(Long.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createLogTest(){
		LogDTO dto=new LogDTO();
		dto.setName(generateRandom(String.class));
		dto.setCantidad(generateRandom(Integer.class));
		dto.setEntra(generateRandom(Boolean.class));
		dto.setJustificacion(generateRandom(String.class));
		dto.setBodegaId(generateRandom(Long.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		LogDTO result=logPersistence.createLog(dto);
		
		Assert.assertNotNull(result);
		
		LogEntity entity=em.find(LogEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());	
		Assert.assertEquals(dto.getCantidad(), entity.getCantidad());	
		Assert.assertEquals(dto.getEntra(), entity.getEntra());	
		Assert.assertEquals(dto.getJustificacion(), entity.getJustificacion());	
		Assert.assertEquals(dto.getBodegaId(), entity.getBodegaId());	
		Assert.assertEquals(dto.getProductoId(), entity.getProductoId());	
	}
	
	@Test
	public void getLogsTest(){
		List<LogDTO> list=logPersistence.getLogs();
		Assert.assertEquals(list.size(), data.size());
        for(LogDTO dto:list){
        	boolean found=false;
            for(LogEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getLogTest(){
		LogEntity entity=data.get(0);
		LogDTO dto=logPersistence.getLog(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getCantidad(), dto.getCantidad());
		Assert.assertEquals(entity.getEntra(), dto.getEntra());
		Assert.assertEquals(entity.getJustificacion(), dto.getJustificacion());
		Assert.assertEquals(entity.getBodegaId(), dto.getBodegaId());
		Assert.assertEquals(entity.getProductoId(), dto.getProductoId());
        
	}
	
	@Test
	public void deleteLogTest(){
		LogEntity entity=data.get(0);
		logPersistence.deleteLog(entity.getId());
        LogEntity deleted=em.find(LogEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateLogTest(){
		LogEntity entity=data.get(0);
		
		LogDTO dto=new LogDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setCantidad(generateRandom(Integer.class));
		dto.setEntra(generateRandom(Boolean.class));
		dto.setJustificacion(generateRandom(String.class));
		dto.setBodegaId(generateRandom(Long.class));
		dto.setProductoId(generateRandom(Long.class));
		
		
		logPersistence.updateLog(dto);
		
		
		LogEntity resp=em.find(LogEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getCantidad(), resp.getCantidad());	
		Assert.assertEquals(dto.getEntra(), resp.getEntra());	
		Assert.assertEquals(dto.getJustificacion(), resp.getJustificacion());	
		Assert.assertEquals(dto.getBodegaId(), resp.getBodegaId());	
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