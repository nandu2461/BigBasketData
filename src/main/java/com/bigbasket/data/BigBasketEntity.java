package com.bigbasket.data;
import javax.persistence.*;

@Entity
public class BigBasketEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private long sku;
    @Column
    private String p_desc;
    @Column
    private String w;
    @Column
    public double sp;
    @Column
    public String date;

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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
