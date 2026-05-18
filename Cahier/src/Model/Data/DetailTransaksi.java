/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Data;
import java.math.BigDecimal;

/**
 *
 * @author Acer
 */
public class DetailTransaksi {
    private int idDetail;
    private int idTransaksi;
    private int idBarang;
    private int qty;
    private BigDecimal hargaSatuan;
    private BigDecimal subtotal;

    public DetailTransaksi() {
    }

    public DetailTransaksi(int idDetail, int idTransaksi, int idBarang, int qty, BigDecimal hargaSatuan, BigDecimal subtotal) {
        this.idDetail = idDetail;
        this.idTransaksi = idTransaksi;
        this.idBarang = idBarang;
        this.qty = qty;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = subtotal;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(BigDecimal hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
