package ir.piana.dev.project.dmlswitch.data.entity;

import ir.piana.dev.wrench.financial.data.entity.IsoPanBasedEntity;
import org.jpos.iso.ISOMsg;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
@Entity
@Table(name = "FINANCIAL_SHETAB_V87")
public class FinancialShetabV87Entity extends IsoPanBasedEntity {
    public FinancialShetabV87Entity() {
        super(new ISOMsg("0200"));
    }

    public FinancialShetabV87Entity(ISOMsg isoMsg) {
        super(isoMsg);
    }

    @Basic
    @Column(name = "P3", nullable = true, length = 64)
    /**
     * PAN
     */
    public String getP3() {
        return isoMsg.getString(3);
    }

    public void setP3(String value) {
        isoMsg.set(3, value);
    }
}
