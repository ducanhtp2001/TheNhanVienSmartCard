/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class ChucVu {
    private int id;
    private int idPhongBan;
    private String tenChucVu;
    private int doUuTienCV;

    public ChucVu(int id, int idPhongBan, String tenChucVu, int doUuTienCV) {
        this.id = id;
        this.idPhongBan = idPhongBan;
        this.tenChucVu = tenChucVu;
        this.doUuTienCV = doUuTienCV;
    }

    

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    
    

    public int getDoUuTienCV() {
        return doUuTienCV;
    }

    public void setDoUuTienCV(int doUuTienCV) {
        this.doUuTienCV = doUuTienCV;
    }
    
    
}
