
package co.edu.uniandes.csw.producto.persistence;

import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.producto.persistence.api.IProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.converter.ProductoConverter;
import java.util.List;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Query;

@Default
@Stateless 
@LocalBean
public class ProductoPersistence extends _ProductoPersistence  implements IProductoPersistence {

    @SuppressWarnings("unchecked")
        public List<ProductoDTO> getProductos(String nombre) {
                Query q = entityManager.createQuery("select u from ProductoEntity u where name = nombre");
                return ProductoConverter.entity2PersistenceDTOList(q.getResultList());
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