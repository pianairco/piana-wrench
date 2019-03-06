package ir.piana.dev.wrench.financial.data.entity;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

import javax.persistence.*;

/**
 * @author Mohammad Rahmati, 12/15/2018
 */
@MappedSuperclass
public abstract class IsoBaseEntity {
    protected ISOMsg isoMsg;
    private Long id;
    private Integer version;
    private Integer partitionKey;

    public IsoBaseEntity(ISOMsg isoMsg) {
        this.isoMsg = isoMsg;
    }

    public IsoBaseEntity() {
    }

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MTI", nullable = false, length = 4)
    public String getMti() {
        try {
            return isoMsg.getMTI();
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMti(String mti) {
        try {
            this.isoMsg.setMTI(mti);
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "PARTITION_KEY")
    public Integer getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(Integer partitionKey) {
        this.partitionKey = partitionKey;
    }
}
