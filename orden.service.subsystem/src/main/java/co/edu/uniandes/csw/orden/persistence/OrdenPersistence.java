
package co.edu.uniandes.csw.orden.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.orden.persistence.api.IOrdenPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class OrdenPersistence extends _OrdenPersistence  implements IOrdenPersistence {

}