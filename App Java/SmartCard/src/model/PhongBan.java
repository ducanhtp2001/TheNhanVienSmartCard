/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class PhongBan {

    private int id;
    private String tenPhongBan;
    private int doUuTienPB;
    List<NhanVien> lstNhanVien;
    List<ChucVu> lstChucVu;

    public PhongBan(int id, String tenPhongBan, int doUuTienPB) {
        this.id = id;
        this.tenPhongBan = tenPhongBan;
        this.doUuTienPB = doUuTienPB;
        this.lstNhanVien = new ArrayList<NhanVien>();
        this.lstChucVu = new ArrayList<ChucVu>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public int getDoUuTienPB() {
        return doUuTienPB;
    }

    public void setDoUuTienPB(int doUuTienPB) {
        this.doUuTienPB = doUuTienPB;
    }

    public List<NhanVien> getLstNhanVien() {
        return lstNhanVien;
    }

    public void setLstNhanVien(List<NhanVien> lstNhanVien) {
        this.lstNhanVien = lstNhanVien;
    }

    public List<ChucVu> getLstChucVu() {
        return lstChucVu;
    }

    public void setLstChucVu(List<ChucVu> lstChucVu) {
        this.lstChucVu = lstChucVu;
    }

    public boolean addChucVu(ChucVu cv) {
        for (int i = 0; i < this.lstChucVu.size(); i++) {
            if (cv.getId()== this.lstChucVu.get(i).getId()) {
                return false;
            } else {
                if (this.lstChucVu.add(cv)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean addNhanVien(NhanVien nv) {
        for (int i = 0; i < this.lstChucVu.size(); i++) {
            if (nv.getMaChucVu() == this.lstChucVu.get(i).getId()) {
                if (this.lstNhanVien.add(nv)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
