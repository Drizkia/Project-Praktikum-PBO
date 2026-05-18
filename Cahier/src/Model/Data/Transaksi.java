package Model.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Acer
 */
public class Transaksi {
    private int idTransaksi;
    private String noFaktur;
    private Date tanggalTransaksi;
    private int idUser;
    private BigDecimal totalBayar;
    private BigDecimal jumlahUang;
    private BigDecimal kembalian;

    public Transaksi() {
    }

    public Transaksi(int idTransaksi, String noFaktur, Date tanggalTransaksi, int idUser, BigDecimal totalBayar, BigDecimal jumlahUang, BigDecimal kembalian) {
        this.idTransaksi = idTransaksi;
        this.noFaktur = noFaktur;
        this.tanggalTransaksi = tanggalTransaksi;
        this.idUser = idUser;
        this.totalBayar = totalBayar;
        this.jumlahUang = jumlahUang;
        this.kembalian = kembalian;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNoFaktur() {
        return noFaktur;
    }

    public void setNoFaktur(String noFaktur) {
        this.noFaktur = noFaktur;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public BigDecimal getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(BigDecimal totalBayar) {
        this.totalBayar = totalBayar;
    }

    public BigDecimal getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(BigDecimal jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public BigDecimal getKembalian() {
        return kembalian;
    }

    public void setKembalian(BigDecimal kembalian) {
        this.kembalian = kembalian;
    }
}
