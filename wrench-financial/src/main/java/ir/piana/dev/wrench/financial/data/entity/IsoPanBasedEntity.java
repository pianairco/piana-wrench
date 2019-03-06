package ir.piana.dev.wrench.financial.data.entity;

import org.jpos.iso.ISOMsg;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Mohammad Rahmati, 12/15/2018
 */
@MappedSuperclass
public abstract class IsoPanBasedEntity extends IsoBaseEntity {
    public IsoPanBasedEntity(ISOMsg isoMsg) {
        super(isoMsg);
    }

    @Basic
    @Column(name = "P2", nullable = true, length = 64)
    /**
     * PAN
     */
    public String getP2() {
        return isoMsg.getString(2);
    }

    public void setP2(String value) {
        isoMsg.set(2, value);
    }
}
