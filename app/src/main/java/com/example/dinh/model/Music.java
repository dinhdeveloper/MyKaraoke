package com.example.dinh.model;

public class Music {
    private String maBH;
    private String tenBH;
    private String caSi;
    private boolean thich;

    public Music() {
    }

    public Music(String maBH, String tenBH, String caSi, boolean thich) {
        this.maBH = maBH;
        this.tenBH = tenBH;
        this.caSi = caSi;
        this.thich = thich;
    }

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public boolean isThich() {
        return thich;
    }

    public void setThich(boolean thich) {
        this.thich = thich;
    }
}
