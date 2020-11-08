package com.e.doan3;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.Serializable;

public class tintuc implements Serializable {
    public String tieude,link,anh,thoigiancn;

    public tintuc() {
        this.tieude = tieude;
        this.link = link;
        this.anh = anh;
        this.thoigiancn = thoigiancn;

    }

    public tintuc(String tieude, String link, String anh, String thoigiancn) {
        this.tieude = tieude;
        this.link = link;
        this.anh = anh;
        this.thoigiancn = thoigiancn;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getThoigiancn() {
        return thoigiancn;
    }

    public void setThoigiancn(String thoigiancn) {
        this.thoigiancn = thoigiancn;
    }
}
