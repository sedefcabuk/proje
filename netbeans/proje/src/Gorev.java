
import java.util.Date;

public class Gorev {
    private int id;
    private String ad;
    private Date baslangicTarihi;
    private int adamGunDegeri;
    private Date bitisTarihi;
    private String gorevDurumu;
    private int calisanId;
    private String calisanAdi;
    private String calisanSoyadi;
    
    public Gorev(int id, String ad, Date baslangicTarihi, int adamGunDegeri, Date bitisTarihi, String gorevDurumu,int calisanId, String calisanAdi, String calisanSoyadi) {
        this.id = id;
        this.ad = ad;
        this.baslangicTarihi = baslangicTarihi;
        this.adamGunDegeri = adamGunDegeri;
        this.bitisTarihi = bitisTarihi;
        this.gorevDurumu = gorevDurumu;
        this.calisanId =calisanId;
        this.calisanAdi = calisanAdi;
        this.calisanSoyadi = calisanSoyadi;
    }

    public String getGorevDurumu() {
        return gorevDurumu;
    }

    public void setGorevDurumu(String gorevDurumu) {
        this.gorevDurumu = gorevDurumu;
    }

    public String getCalisanAdi() {
        return calisanAdi;
    }

    public void setCalisanAdi(String calisanAdi) {
        this.calisanAdi = calisanAdi;
    }

    public String getCalisanSoyadi() {
        return calisanSoyadi;
    }

    public void setCalisanSoyadi(String calisanSoyadi) {
        this.calisanSoyadi = calisanSoyadi;
    }

    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Date getBaslangicTarihi() {
        return baslangicTarihi;
    }

    public void setBaslangicTarihi(Date baslangicTarihi) {
        this.baslangicTarihi = baslangicTarihi;
    }

    public int getAdamGunDegeri() {
        return adamGunDegeri;
    }

    public void setAdamGunDegeri(int adamGunDegeri) {
        this.adamGunDegeri = adamGunDegeri;
    }

    public Date getBitisTarihi() {
        return bitisTarihi;
    }

    public void setBitisTarihi(Date bitisTarihi) {
        this.bitisTarihi = bitisTarihi;
    }
    
}
