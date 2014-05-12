
package co.edu.uniandes.csw.bodega.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.bodega.persistence.api.IBodegaPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class BodegaPersistence extends _BodegaPersistence  implements IBodegaPersistence {

}