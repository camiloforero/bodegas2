package co.edu.uniandes.csw.bodega.master.persistence.entity;

import co.edu.uniandes.csw.iteminventario.persistence.entity.ItemInventarioEntity;
import co.edu.uniandes.csw.bodega.persistence.entity.BodegaEntity;
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
@IdClass(BodegaItemInventarioEntityId.class)
@NamedQueries({
    @NamedQuery(name = "BodegaItemInventarioEntity.getItemInventarioListForBodega", query = "SELECT  u FROM BodegaItemInventarioEntity u WHERE u.bodegaId=:bodegaId"),
    @NamedQuery(name = "BodegaItemInventarioEntity.deleteBodegaItemInventario", query = "DELETE FROM BodegaItemInventarioEntity u WHERE u.itemInventarioId=:itemInventarioId and  u.bodegaId=:bodegaId")
})
public class BodegaItemInventarioEntity implements Serializable {

    @Id
    @Column(name = "bodegaId")
    private Long bodegaId;
    @Id
    @Column(name = "itemInventarioId")
    private Long itemInventarioId;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "itemInventarioId", referencedColumnName = "id")
    @JoinFetch
    private ItemInventarioEntity itemInventarioEntity;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bodegaId", referencedColumnName = "id")
    @JoinFetch
    private BodegaEntity bodegaEntity;

    public BodegaItemInventarioEntity() {
    }

    public BodegaItemInventarioEntity(Long bodegaId, Long itemInventarioId) {
        this.bodegaId = bodegaId;
        this.itemInventarioId = itemInventarioId;
    }

    public Long getBodegaId() {
        return bodegaId;
    }

    public void setBodegaId(Long bodegaId) {
        this.bodegaId = bodegaId;
    }

    public Long getItemInventarioId() {
        return itemInventarioId;
    }

    public void setItemInventarioId(Long itemInventarioId) {
        this.itemInventarioId = itemInventarioId;
    }

    public ItemInventarioEntity getItemInventarioEntity() {
        return itemInventarioEntity;
    }

    public void setItemInventarioEntity(ItemInventarioEntity itemInventarioEntity) {
        this.itemInventarioEntity = itemInventarioEntity;
    }

    public BodegaEntity getBodegaEntity() {
        return bodegaEntity;
    }

    public void setBodegaEntity(BodegaEntity bodegaEntity) {
        this.bodegaEntity = bodegaEntity;
    }

}
