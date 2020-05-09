package com.bigbasket.data;
import javax.persistence.*;

@Table
@Entity(name="BigBasketData")
public class BigBasketEntity
{
    @Column
    @Id
    @GeneratedValue
    private long sku;
    @Column
    private String p_desc;
    @Column
    private String w;
    @Column
    public double sp;

    public BigBasketEntity()
    {
    }

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public String getP_desc() {
        return p_desc;
    }

    public void setP_desc(String p_desc) {
        this.p_desc = p_desc;
    }

    public String  getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public double getSp() {
        return sp;
    }

    public void setSp(double sp) {
        this.sp = sp;
    }
}
