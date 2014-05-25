
package co.edu.uniandes.csw.pedido.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.pedido.logic.dto.PedidoDTO;
import co.edu.uniandes.csw.pedido.persistence.api._IPedidoPersistence;
import co.edu.uniandes.csw.pedido.persistence.converter.PedidoConverter;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class _PedidoPersistence implements _IPedidoPersistence {

	@PersistenceContext(unitName="PedidoPU")
	protected EntityManager entityManager;
	
	public PedidoDTO createPedido(PedidoDTO pedido) {
		PedidoEntity entity=PedidoConverter.persistenceDTO2Entity(pedido);
		entityManager.persist(entity);
                 enviarCorreo(pedido.getId(), pedido.getName(), pedido.getCantidad());
		return PedidoConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<PedidoDTO> getPedidos() {
		Query q = entityManager.createQuery("select u from PedidoEntity u");
		return PedidoConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public PedidoDTO getPedido(Long id) {
		return PedidoConverter.entity2PersistenceDTO(entityManager.find(PedidoEntity.class, id));
	}

	public void deletePedido(Long id) {
		PedidoEntity entity=entityManager.find(PedidoEntity.class, id);
		entityManager.remove(entity);
	}

	public void updatePedido(PedidoDTO detail) {
		PedidoEntity entity=entityManager.merge(PedidoConverter.persistenceDTO2Entity(detail));
		PedidoConverter.entity2PersistenceDTO(entity);
	}

        public void enviarCorreo(Long id, String nombrePedido, Integer cantidad ) 
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
                        message.setSubject("Nuevo pedido agregado al sistema");
                        message.setText("Se ha agregado el pedido con id "+id+",\n "
                                + "con nombre "+nombrePedido+",\n "
                                + "de cantidad "+cantidad+",\n "
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