package co.edu.uniandes.csw.pedido.master.persistence.entity;

import java.io.Serializable;

/**
 *
 * @author cadmilo
 */
public class PedidoOrdenEntityId implements Serializable{

    private Long pedidoId;
    private Long ordenId;

    @Override
    public int hashCode() {
        return (int) (pedidoId + ordenId);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PedidoOrdenEntityId) {
            PedidoOrdenEntityId otherId = (PedidoOrdenEntityId) object;
            return (otherId.pedidoId == this.pedidoId) && (otherId.ordenId == this.ordenId);
        }
        return false;
    }

}
