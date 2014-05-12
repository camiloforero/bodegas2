
package co.edu.uniandes.csw.iteminventario.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.iteminventario.persistence.api.IItemInventarioPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class ItemInventarioPersistence extends _ItemInventarioPersistence  implements IItemInventarioPersistence {

}