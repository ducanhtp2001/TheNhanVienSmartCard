/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class Xe {
    private int id;
    private String idNhanVien;
    private String bienSo;
    private String loaiXe;

    public Xe() {
    }

    public Xe(int id, String idNhanVien, String bienSo, String loaiXe) {
        this.id = id;
        this.idNhanVien = idNhanVien;
        this.bienSo = bienSo;
        this.loaiXe = loaiXe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(String inNhanVien) {
        this.idNhanVien = inNhanVien;
    }

    

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
    
    @Override
public String toString() {
    return "GuiXe{" +
            "id=" + id +
            ", idNhanVien='" + idNhanVien + '\'' +
            ", bienSo='" + bienSo + '\'' +
            ", loaiXe='" + loaiXe + '\'' +
            '}';
}

}
