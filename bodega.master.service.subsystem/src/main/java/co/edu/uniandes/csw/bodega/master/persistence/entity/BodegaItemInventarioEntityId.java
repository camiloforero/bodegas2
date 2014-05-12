package co.edu.uniandes.csw.bodega.master.persistence.entity;

import java.io.Serializable;

/**
 *
 * @author cadmilo
 */
public class BodegaItemInventarioEntityId implements Serializable{

    private Long bodegaId;
    private Long itemInventarioId;

    @Override
    public int hashCode() {
        return (int) (bodegaId + itemInventarioId);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BodegaItemInventarioEntityId) {
            BodegaItemInventarioEntityId otherId = (BodegaItemInventarioEntityId) object;
            return (otherId.bodegaId == this.bodegaId) && (otherId.itemInventarioId == this.itemInventarioId);
        }
        return false;
    }

}
