/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class NhanVien {
    private String id;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String queQuan;
    private String sdt;
    private String gmail;
    private String idCard;
    private int maChucVu;
    private int maPhongBan;
    List<Xe> lstXe;
    BufferedImage avatar;

    public NhanVien() {
    }

    public NhanVien(String id, String hoTen, String ngaySinh, String gioiTinh, String queQuan, String sdt, String gmail, int maChucVu, int maPhongBan, BufferedImage avatar) {
        this.id = id;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.queQuan = queQuan;
        this.sdt = sdt;
        this.gmail = gmail;
        this.maChucVu = maChucVu;
        this.maPhongBan = maPhongBan;
        this.lstXe = new ArrayList<Xe>();
        this.avatar = avatar;
        this.idCard = "" + this.maPhongBan + this.maChucVu + this.id;
    }
    
    public NhanVien(String hoTen, String ngaySinh, String gioiTinh, String queQuan, String sdt, String gmail, int maChucVu, int maPhongBan, BufferedImage avatar) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.queQuan = queQuan;
        this.sdt = sdt;
        this.gmail = gmail;
        this.maChucVu = maChucVu;
        this.maPhongBan = maPhongBan;
        this.lstXe = new ArrayList<Xe>();
        this.avatar = avatar;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    
    
    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public int getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(int maChucVu) {
        this.maChucVu = maChucVu;
    }

    public int getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(int maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public List<Xe> getLstXe() {
        return lstXe;
    }

    public void setLstXe(List<Xe> lstXe) {
        this.lstXe = lstXe;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }
    
    public byte[] getAvatarBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            // Ghi ảnh vào ByteArrayOutputStream dưới định dạng JPG
            ImageIO.write(this.avatar, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();

            // Đóng ByteArrayOutputStream
            baos.close();
            return imageBytes;
        } catch (IOException ex) {
            Logger.getLogger(NhanVien.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void setAvatar(BufferedImage avatar) {
        this.avatar = avatar;
    }

    
    public void setAvatarfromBytes(byte[] data) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(bais);
            bais.close();
            this.avatar = image;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
