package co.edu.uniandes.csw.pedido.master.persistence.entity;

import co.edu.uniandes.csw.orden.persistence.entity.OrdenEntity;
import co.edu.uniandes.csw.pedido.persistence.entity.PedidoEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn; 
import org.eclipse.persistence.annotations.JoinFetch;

@Entity
@IdClass(PedidoOrdenEntityId.class)
@NamedQueries({
    @NamedQuery(name = "PedidoOrdenEntity.getOrdenListForPedido", query = "SELECT  u FROM PedidoOrdenEntity u WHERE u.pedidoId=:pedidoId"),
    @NamedQuery(name = "PedidoOrdenEntity.deletePedidoOrden", query = "DELETE FROM PedidoOrdenEntity u WHERE u.ordenId=:ordenId and  u.pedidoId=:pedidoId")
})
public class PedidoOrdenEntity implements Serializable {

    @Id
    @Column(name = "pedidoId")
    private Long pedidoId;
    @Id
    @Column(name = "ordenId")
    private Long ordenId;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ordenId", referencedColumnName = "id")
    @JoinFetch
    private OrdenEntity ordenEntity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "pedidoId", referencedColumnName = "id")
    @JoinFetch
    private PedidoEntity pedidoEntity;

    public PedidoOrdenEntity() {
    }

    public PedidoOrdenEntity(Long pedidoId, Long ordenId) {
        this.pedidoId = pedidoId;
        this.ordenId = ordenId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public OrdenEntity getOrdenEntity() {
        return ordenEntity;
    }

    public void setOrdenEntity(OrdenEntity ordenEntity) {
        this.ordenEntity = ordenEntity;
    }

    public PedidoEntity getPedidoEntity() {
        return pedidoEntity;
    }

    public void setPedidoEntity(PedidoEntity pedidoEntity) {
        this.pedidoEntity = pedidoEntity;
    }

}
