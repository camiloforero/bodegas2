package co.edu.uniandes.csw.bodega.master.persistence;

import javax.ejb.Stateless;

import co.edu.uniandes.csw.bodega.master.persistence.api.IBodegaMasterPersistence;
import javax.ejb.LocalBean;
import javax.enterprise.inject.Default;

@Default
@Stateless 
@LocalBean
public class BodegaMasterPersistence extends _BodegaMasterPersistence  implements IBodegaMasterPersistence {

}