/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author HP
 */
public class GuiXe extends Xe{
    private Date time;
    private int status;

    public GuiXe() {
    }

    public GuiXe(Date time, int status) {
        this.time = time;
        this.status = status;
    }

    public GuiXe(Date time, int status, int idXe, String idNhanVien, String bienSo, String loaiXe) {
        super(idXe, idNhanVien, bienSo, loaiXe);
        this.time = time;
        this.status = status;
    }

    @Override
    public String toString() {
        return "" + this.time + " " + this.status + " " + this.getId() + " " + this.getIdNhanVien() + " " + this.getBienSo() + " " + this.getLoaiXe();

    }

    

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
