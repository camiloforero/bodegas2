/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.log.logic.ejb;

import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;
import co.edu.uniandes.csw.log.logic.api.ILogLogicService;
import co.edu.uniandes.csw.log.logic.dto.LogDTO;
import co.edu.uniandes.csw.log.persistence.LogPersistence;
import co.edu.uniandes.csw.log.persistence.api.ILogPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import javax.inject.Inject;

/**
 *
 * @author admin
 */
@RunWith(Arquillian.class)
public class LogLogicServicesTest {
    public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(LogLogicService.class.getPackage())
				.addPackage(LogPersistence.class.getPackage())
				.addPackage(BodegaEntity.class.getPackage())
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
		List<LogDTO> dtos=logLogicService.getLogs();
		for(LogDTO dto:dtos){
			logLogicService.deleteLog(dto.getId());
		}
	}

	private List<LogDTO> data=new ArrayList<LogDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			LogDTO pdto=new LogDTO();
			pdto.setName(generateRandom(String.class));
                        pdto.setBodegaId(generateRandom(Long.class));
                        pdto.setCantidad(generateRandom(Integer.class));
                        pdto.setEntra(true);
                        pdto.setId(generateRandom(Long.class));
                        pdto.setJustificacion(generateRandom(String.class));
                        pdto.setProductoId(generateRandom(Long.class));
			pdto=logLogicService.createLog(pdto);
			data.add(pdto);
		}
	}
        
       
        @Test
        public void darCantidadProductoTest(){
           Assert.assertNotNull(logLogicService.darCantidadProducto(generateRandom(Long.class)));
        }
                
        @Test
        public void cumplirOrdenDespachoTest(){
            long id = logLogicService.getLogs().get(0).getId();
            try{
                logLogicService.cumplirOrden(id, 1, "Orden de reaprovisionamiento");
            }
            catch(Exception e){
                Assert.fail();
            }
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
