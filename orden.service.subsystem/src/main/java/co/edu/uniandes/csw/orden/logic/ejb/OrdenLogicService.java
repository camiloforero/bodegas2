
package co.edu.uniandes.csw.orden.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.orden.logic.api.IOrdenLogicService;

@Default
@Stateless
@LocalBean
public class OrdenLogicService extends _OrdenLogicService implements IOrdenLogicService 
{

    public boolean satisfacer(long id) {
        System.out.println("VICTORY " + id);
        return false;
    }
    
}