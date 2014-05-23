
package co.edu.uniandes.csw.producto.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Query;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.producto.persistence.api._IProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.converter.ProductoConverter;
import co.edu.uniandes.csw.producto.persistence.entity.ProductoEntity;
import java.util.Properties;

public abstract class _ProductoPersistence implements _IProductoPersistence {

	@PersistenceContext(unitName="ProductoPU")
	protected EntityManager entityManager;
	
	public ProductoDTO createProducto(ProductoDTO producto) {
		ProductoEntity entity=ProductoConverter.persistenceDTO2Entity(producto);
		entityManager.persist(entity);
                enviarCorreo(producto.getName(), producto.getTipo());
		return ProductoConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ProductoDTO> getProductos() {
		Query q = entityManager.createQuery("select u from ProductoEntity u");
		return ProductoConverter.entity2PersistenceDTOList(q.getResultList());
                
        }

	public ProductoDTO getProducto(Long id) {
		return ProductoConverter.entity2PersistenceDTO(entityManager.find(ProductoEntity.class, id));
	}

	public void deleteProducto(Long id) {
		ProductoEntity entity=entityManager.find(ProductoEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateProducto(ProductoDTO detail) {
		ProductoEntity entity=entityManager.merge(ProductoConverter.persistenceDTO2Entity(detail));
		ProductoConverter.entity2PersistenceDTO(entity);
	}
        
        public void enviarCorreo(String nameProducto, String tipoProducto ) 
        {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
 
                Session session = Session.getInstance(props, new javax.mail.Authenticator() 
                {
                        protected PasswordAuthentication getPasswordAuthentication()
                        {
                                return new PasswordAuthentication("equiporocket2014@gmail.com", "E2014rocket");
                        }
                  });
 
                try {
 
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("equiporocket2014@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("equiporocket2014@gmail.com"));
                        message.setSubject("Nuevo producto agregado al sistema");
                        message.setText("Se ha agregado el producto "+nameProducto+",\n "
                                + "de tipo "+tipoProducto+",\n "
                                + "Gracias por su atencion.\n "
                                + "Equipo Rocket 2014  ");
 
                        Transport.send(message);
 
                        System.out.println("Done");
 
                } 
                catch (MessagingException e) 
                {
                        throw new RuntimeException(e);
                }
        }


}