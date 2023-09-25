package Connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import model.ChamCong;
import model.ChucVu;
import model.GuiXe;
import model.NhanVien;
import model.PhongBan;
import model.Xe;

/**
 *
 * @author HP
 */
public class ConnectDatabase {

    public static List<PhongBan> lstPhongBan;
    public static List<ChucVu> lstChucVu;
    public static List<ChucVu> lstChucVuInPhongBan;
    public static int guiXe = 0;
    public static int chamCong = 0;
    public static List<GuiXe> lstGuiXe;
    public static List<ChamCong> lstChamCong;
    public static List<Xe> lstXe;

    public static int insertNhanVien(NhanVien nv) {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("insert into NhanVien values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, nv.getHoTen());
            pstm.setString(2, nv.getNgaySinh());
            pstm.setString(3, nv.getGioiTinh());
            pstm.setString(4, nv.getQueQuan());
            pstm.setString(5, nv.getSdt());
            pstm.setString(6, nv.getGmail());
            pstm.setInt(7, nv.getMaPhongBan());
            pstm.setInt(8, nv.getMaChucVu());
            pstm.setString(9, nv.getIdCard());
            pstm.executeUpdate();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idNhanVien = generatedKeys.getInt(1);
                System.out.println("ID của nhân viên: " + idNhanVien);
                nv.setIdCard("" + nv.getMaPhongBan() + nv.getMaChucVu() + idNhanVien);
                pstm = con.prepareStatement("update nhanvien set idcard = ? where id = ?");
                pstm.setString(1, nv.getIdCard());
                pstm.setInt(2, idNhanVien);
                pstm.executeUpdate();
                con.close();
                return idNhanVien;
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }
    
    public static boolean updateNhanVien(NhanVien nv) {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("update NhanVien set hoten = ?, ngaysinh = ?, "
                    + "gioitinh = ?, quequan = ?, sdt = ?, gmail = ? where id = " + nv.getId());
            pstm.setString(1, nv.getHoTen());
            pstm.setString(2, nv.getNgaySinh());
            pstm.setString(3, nv.getGioiTinh());
            pstm.setString(4, nv.getQueQuan());
            pstm.setString(5, nv.getSdt());
            pstm.setString(6, nv.getGmail());
            pstm.executeUpdate();
            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean chamCong(int id) {

        if (chamCong == 1) {
            chamCong = 0;
        } else {
            chamCong = 1;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("insert into chamcong values (null, ?, now(), ?)");
            pstm.setInt(1, id);
            pstm.setInt(2, chamCong);
            pstm.executeUpdate();

            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean guiXe(Xe xe) {

        if (guiXe == 1) {
            guiXe = 0;
        } else {
            guiXe = 1;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("insert into guixe values (null, ?, ?, now(), ?)");
            pstm.setInt(1, Integer.parseInt(xe.getIdNhanVien()));
            pstm.setInt(2, xe.getId());
            pstm.setInt(3, guiXe);
            pstm.executeUpdate();

            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean insertXe(Xe xe) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("insert into xe values (null, ?, ?, ?)");
            pstm.setInt(1, Integer.parseInt(xe.getIdNhanVien()));
            pstm.setString(2, xe.getBienSo());
            pstm.setString(3, xe.getLoaiXe());
            pstm.executeUpdate();

            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    
        public static boolean updateXe(int id, String loaiXe, String bienSo) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("update xe set loaixe = ?, bienSo = ? where id = " + id);
            pstm.setString(1, loaiXe);
            pstm.setString(2, bienSo);
            pstm.executeUpdate();

            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
        
        public static boolean deleteXe(int id) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            PreparedStatement pstm = con.prepareStatement("delete from xe where id = " + id);
            pstm.executeUpdate();

            con.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static void getListPhongBan() {
        if (lstPhongBan == null) {
            lstPhongBan = new ArrayList<>();
        } else {
            lstPhongBan.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select * from phongban");
            while (result.next()) {
                lstPhongBan.add(new PhongBan(result.getInt(1), result.getString(2), result.getInt(3)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

//    public static void main(String[] args) {
//        getListXe(3);
//        for (int i = 0; i < lstXe.size(); i++) {
//            System.out.println(lstXe.get(i).toString());
//        }
//    }

    public static void getListGuiXe(int id) {
        if (lstGuiXe == null) {
            lstGuiXe = new ArrayList<>();
        } else {
            lstGuiXe.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM GuiXe JOIN Xe ON GuiXe.idxe = Xe.id WHERE GuiXe.idnhanvien = " + id);
            while (result.next()) {
                Timestamp timestamp = result.getTimestamp("time");
                //GuiXe(Date time, int status, int idXe, String idNhanVien, String bienSo, String loaiXe)
                lstGuiXe.add(new GuiXe(timestamp, result.getInt(5), result.getInt(3), "" + result.getInt(2),
                        result.getString(8), result.getString(9)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getListXe(int id) {
        if (lstXe == null) {
            lstXe = new ArrayList<>();
        } else {
            lstXe.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM Xe WHERE idnhanvien = " + id);
            while (result.next()) {
                //Xe(int id, String idNhanVien, String bienSo, String loaiXe)
                lstXe.add(new Xe(result.getInt(1), "" + result.getInt(2), result.getString(3), "" + result.getString(4)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getListChamCong(int id) {
        if (lstChamCong == null) {
            lstChamCong = new ArrayList<>();
        } else {
            lstChamCong.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("SELECT * FROM ChamCong WHERE idnhanvien = " + id);
            while (result.next()) {
                Timestamp timestamp = result.getTimestamp("time");
                //ChamCong(Date time, int idNhanVien, int status)
                lstChamCong.add(new ChamCong(timestamp, result.getInt(1), result.getInt(4)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getListChucVu() {
        if (lstChucVu == null) {
            lstChucVu = new ArrayList<>();
        } else {
            lstChucVuInPhongBan.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select * from chucvu");
            while (result.next()) {
                lstChucVu.add(new ChucVu(result.getInt(1), result.getInt(2), result.getString(3), result.getInt(4)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getListChucVuInPhongBan(int id) {
        if (lstChucVuInPhongBan == null) {
            lstChucVuInPhongBan = new ArrayList<>();
        } else {
            lstChucVuInPhongBan.clear();
        }
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select * from chucvu where idphongban = " + id);
            while (result.next()) {
                lstChucVuInPhongBan.add(new ChucVu(result.getInt(1), result.getInt(2), result.getString(3), result.getInt(4)));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getPhongBan(int id) {
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select tenphongban from phongban where id = " + id);
            String str = null;
            if (result.next()) {
                str = result.getString(1);
            }

            con.close();
            return str;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static int getDoUuTienPhongBan(int id) {
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select doUuTien from phongban where id = " + id);
            int doUuTien = -1;
            if (result.next()) {
                doUuTien = result.getInt(1);
            }

            con.close();
            return doUuTien;
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

    public static String getChucVu(int id) {
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select tenchucvu from chucvu where id = " + id);
            String str = null;
            if (result.next()) {
                str = result.getString(1);
            }

            con.close();
            return str;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static int getDoUuTienChucVu(int id) {
        try {
//          Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcard", "root", "");

            Statement stm = con.createStatement();

            ResultSet result = stm.executeQuery("Select doUuTien from chucvu where id = " + id);
            int doUuTien = -1;
            if (result.next()) {
                doUuTien = result.getInt(1);
            }

            con.close();
            return doUuTien;
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }

}
