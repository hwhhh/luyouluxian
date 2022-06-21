package com.flag.myapplication.car.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name = "Jingdian")
public class Jingdian implements Serializable {
    @Column(name = "id", isId = true,autoGen=true)
    private int id;




    @Column(name = "bookname")
    private String bookname;


    @Column(name = "typename")
    private String typename;

    @Column(name = "luxian")
    private String luxian;

    @Column(name = "zhuoze")
    private String zhuoze;

    @Column(name = "jiege")
    private String jiege;

    @Column(name = "fenshu")
    private int fenshu;

    @Column(name = "imgurl")
    private String imgurl;


    @Column(name = "neirong")
    private String neirong;
    @Column(name = "like")
    private String like="0";
    @Column(name = "collect")
    private String collect="0";

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getLuxian() {
        return luxian;
    }

    public void setLuxian(String luxian) {
        this.luxian = luxian;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }



    public String getZhuoze() {
        return zhuoze;
    }

    public void setZhuoze(String zhuoze) {
        this.zhuoze = zhuoze;
    }

    public String getJiege() {
        return jiege;
    }

    public void setJiege(String jiege) {
        this.jiege = jiege;
    }


    public int getFenshu() {
        return fenshu;
    }

    public void setFenshu(int fenshu) {
        this.fenshu = fenshu;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }
}