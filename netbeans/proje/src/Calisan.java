public class Calisan {
    private int id;
    private String ad;
    private String soyad;
    private String telNo;

    public Calisan(int id, String ad, String soyad, String telNo) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.telNo = telNo;
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

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    @Override
        public String toString() {
            return ad + " "+ soyad; // JComboBox'ta gösterilecek metni belirtin
        }
}
