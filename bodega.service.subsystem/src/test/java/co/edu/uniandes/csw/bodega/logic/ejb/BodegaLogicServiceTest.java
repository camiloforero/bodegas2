
package co.edu.uniandes.csw.bodega.logic.ejb;

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
import co.edu.uniandes.csw.bodega.logic.api.IBodegaLogicService;
import co.edu.uniandes.csw.bodega.persistence.BodegaPersistence;
import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;
import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;

@RunWith(Arquillian.class)
public class BodegaLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(BodegaLogicService.class.getPackage())
				.addPackage(BodegaPersistence.class.getPackage())
				.addPackage(BodegaEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IBodegaLogicService bodegaLogicService;
	
	@Inject
	private IBodegaPersistence bodegaPersistence;	

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
		List<BodegaDTO> dtos=bodegaPersistence.getBodegas();
		for(BodegaDTO dto:dtos){
			bodegaPersistence.deleteBodega(dto.getId());
		}
	}

	private List<BodegaDTO> data=new ArrayList<BodegaDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			BodegaDTO pdto=new BodegaDTO();
			pdto.setName(generateRandom(String.class));
			pdto=bodegaPersistence.createBodega(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createBodegaTest(){
		BodegaDTO ldto=new BodegaDTO();
		ldto.setName(generateRandom(String.class));
		
		
		BodegaDTO result=bodegaLogicService.createBodega(ldto);
		
		Assert.assertNotNull(result);
		
		BodegaDTO pdto=bodegaPersistence.getBodega(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
	}
	
	@Test
	public void getBodegasTest(){
		List<BodegaDTO> list=bodegaLogicService.getBodegas();
		Assert.assertEquals(list.size(), data.size());
        for(BodegaDTO ldto:list){
        	boolean found=false;
            for(BodegaDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getBodegaTest(){
		BodegaDTO pdto=data.get(0);
		BodegaDTO ldto=bodegaLogicService.getBodega(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
        
	}
	
	@Test
	public void deleteBodegaTest(){
		BodegaDTO pdto=data.get(0);
		bodegaLogicService.deleteBodega(pdto.getId());
        BodegaDTO deleted=bodegaPersistence.getBodega(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateBodegaTest(){
		BodegaDTO pdto=data.get(0);
		
		BodegaDTO ldto=new BodegaDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		
		
		bodegaLogicService.updateBodega(ldto);
		
		
		BodegaDTO resp=bodegaPersistence.getBodega(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
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