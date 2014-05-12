
package co.edu.uniandes.csw.orden.logic.ejb;

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
import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;
import co.edu.uniandes.csw.orden.persistence.OrdenPersistence;
import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import co.edu.uniandes.csw.orden.persistence.entity.OrdenEntity;

@RunWith(Arquillian.class)
public class OrdenLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(OrdenLogicService.class.getPackage())
				.addPackage(OrdenPersistence.class.getPackage())
				.addPackage(OrdenEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IOrdenLogicService ordenLogicService;
	
	@Inject
	private IOrdenPersistence ordenPersistence;	

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
		List<OrdenDTO> dtos=ordenPersistence.getOrdens();
		for(OrdenDTO dto:dtos){
			ordenPersistence.deleteOrden(dto.getId());
		}
	}

	private List<OrdenDTO> data=new ArrayList<OrdenDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			OrdenDTO pdto=new OrdenDTO();
			pdto.setName(generateRandom(String.class));
			pdto.setTipo(generateRandom(String.class));
			pdto.setEstado(generateRandom(String.class));
			pdto=ordenPersistence.createOrden(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createOrdenTest(){
		OrdenDTO ldto=new OrdenDTO();
		ldto.setName(generateRandom(String.class));
		ldto.setTipo(generateRandom(String.class));
		ldto.setEstado(generateRandom(String.class));
		
		
		OrdenDTO result=ordenLogicService.createOrden(ldto);
		
		Assert.assertNotNull(result);
		
		OrdenDTO pdto=ordenPersistence.getOrden(result.getId());
		
		Assert.assertEquals(ldto.getName(), pdto.getName());	
		Assert.assertEquals(ldto.getTipo(), pdto.getTipo());	
		Assert.assertEquals(ldto.getEstado(), pdto.getEstado());	
	}
	
	@Test
	public void getOrdensTest(){
		List<OrdenDTO> list=ordenLogicService.getOrdens();
		Assert.assertEquals(list.size(), data.size());
        for(OrdenDTO ldto:list){
        	boolean found=false;
            for(OrdenDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getOrdenTest(){
		OrdenDTO pdto=data.get(0);
		OrdenDTO ldto=ordenLogicService.getOrden(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getName(), ldto.getName());
		Assert.assertEquals(pdto.getTipo(), ldto.getTipo());
		Assert.assertEquals(pdto.getEstado(), ldto.getEstado());
        
	}
	
	@Test
	public void deleteOrdenTest(){
		OrdenDTO pdto=data.get(0);
		ordenLogicService.deleteOrden(pdto.getId());
        OrdenDTO deleted=ordenPersistence.getOrden(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateOrdenTest(){
		OrdenDTO pdto=data.get(0);
		
		OrdenDTO ldto=new OrdenDTO();
		ldto.setId(pdto.getId());
		ldto.setName(generateRandom(String.class));
		ldto.setTipo(generateRandom(String.class));
		ldto.setEstado(generateRandom(String.class));
		
		
		ordenLogicService.updateOrden(ldto);
		
		
		OrdenDTO resp=ordenPersistence.getOrden(pdto.getId());
		
		Assert.assertEquals(ldto.getName(), resp.getName());	
		Assert.assertEquals(ldto.getTipo(), resp.getTipo());	
		Assert.assertEquals(ldto.getEstado(), resp.getEstado());	
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