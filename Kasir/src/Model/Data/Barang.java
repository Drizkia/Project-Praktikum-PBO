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
public class Barang {
    private int id_barang;
    private String barcode;
    private String nama_barang;
    private int id_kategori;
    private BigDecimal harga_beli;
    private BigDecimal harga_jual;
    private int stok;

    public Barang() {
    }

    public Barang(int id_barang, String barcode, String nama_barang, int id_kategori, BigDecimal harga_beli, BigDecimal harga_jual, int stok) {
        this.id_barang = id_barang;
        this.barcode = barcode;
        this.nama_barang = nama_barang;
        this.id_kategori = id_kategori;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.stok = stok;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public BigDecimal getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(BigDecimal harga_beli) {
        this.harga_beli = harga_beli;
    }

    public BigDecimal getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(BigDecimal harga_jual) {
        this.harga_jual = harga_jual;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
