/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 *
 * @author HP
 */
public class ChamCong {
    private Date time;
    private int id;
    private int status;

    public ChamCong() {
    }

    public ChamCong(Date time, int id, int status) {
        this.time = time;
        this.id = id;
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int idNhanVien) {
        this.id = idNhanVien;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.time.toString() + " " + this.id + " " + this.status;
    }

}
