
package co.edu.uniandes.csw.log.logic.ejb;

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
import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.log.persistence.LogPersistence;
import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;
import co.edu.uniandes.csw.log.persistence.entity.LogEntity;

@RunWith(Arquillian.class)
public class LogLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(LogLogicService.class.getPackage())
				.addPackage(LogPersistence.class.getPackage())
				.addPackage(LogEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private ILogLogicService logLogicService;
	
	@Inject
	private ILogPersistence logPersistence;	

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
		List<LogDTO> dtos=logPersistence.getLogs();
		for(LogDTO dto:dtos){
			logPersistence.deleteLog(dto.getId());
		}
	}

	private List<LogDTO> data=new ArrayList<LogDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			LogDTO pdto=new LogDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setCantidad(generateRandom(Integer.class));
			pdto.setEntra(generateRandom(Boolean.class));
			pdto.setJustificacion(generateRandom(String.class));
			pdto.setBodegaId(generateRandom(Long.class));
			pdto.setProductoId(generateRandom(Long.class));
			pdto=logPersistence.createLog(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createLogTest(){
		LogDTO ldto=new LogDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setEntra(generateRandom(Boolean.class));
		ldto.setJustificacion(generateRandom(String.class));
		ldto.setBodegaId(generateRandom(Long.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		LogDTO result=logLogicService.createLog(ldto);
		
		Assert.assertNotNull(result);
		
		LogDTO pdto=logPersistence.getLog(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getCantidad(), pdto.getCantidad());	
		Assert.assertEquals(ldto.getEntra(), pdto.getEntra());	
		Assert.assertEquals(ldto.getJustificacion(), pdto.getJustificacion());	
		Assert.assertEquals(ldto.getBodegaId(), pdto.getBodegaId());	
		Assert.assertEquals(ldto.getProductoId(), pdto.getProductoId());	
	}
	
	@Test
	public void getLogsTest(){
		List<LogDTO> list=logLogicService.getLogs();
		Assert.assertEquals(list.size(), data.size());
        for(LogDTO ldto:list){
        	boolean found=false;
            for(LogDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getLogTest(){
		LogDTO pdto=data.get(0);
		LogDTO ldto=logLogicService.getLog(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getCantidad(), ldto.getCantidad());
		Assert.assertEquals(pdto.getEntra(), ldto.getEntra());
		Assert.assertEquals(pdto.getJustificacion(), ldto.getJustificacion());
		Assert.assertEquals(pdto.getBodegaId(), ldto.getBodegaId());
		Assert.assertEquals(pdto.getProductoId(), ldto.getProductoId());
        
	}
	
	@Test
	public void deleteLogTest(){
		LogDTO pdto=data.get(0);
		logLogicService.deleteLog(pdto.getId());
        LogDTO deleted=logPersistence.getLog(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateLogTest(){
		LogDTO pdto=data.get(0);
		
		LogDTO ldto=new LogDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setCantidad(generateRandom(Integer.class));
		ldto.setEntra(generateRandom(Boolean.class));
		ldto.setJustificacion(generateRandom(String.class));
		ldto.setBodegaId(generateRandom(Long.class));
		ldto.setProductoId(generateRandom(Long.class));
		
		
		logLogicService.updateLog(ldto);
		
		
		LogDTO resp=logPersistence.getLog(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getCantidad(), resp.getCantidad());	
		Assert.assertEquals(ldto.getEntra(), resp.getEntra());	
		Assert.assertEquals(ldto.getJustificacion(), resp.getJustificacion());	
		Assert.assertEquals(ldto.getBodegaId(), resp.getBodegaId());	
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