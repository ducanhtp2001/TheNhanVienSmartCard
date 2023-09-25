/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Connection;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JOptionPane;
import model.NhanVien;

public class ConnectCard {

    public static NhanVien nhanVien = new NhanVien();
    public static final byte[] AID_APPLET = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
    private static Card card;
    private static TerminalFactory factory;
    private static CardChannel channel;
    private static CardTerminal terminal;
    private static List<CardTerminal> terminals;
    private static ResponseAPDU responseAPDU;

    public static void connect() {
        try {
            factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();
            terminal = terminals.get(0);
            card = terminal.connect("T=0");
            channel = card.getBasicChannel();
            if (channel == null) {
                System.out.println("channel null");
            }
            responseAPDU = channel.transmit(new CommandAPDU(0x00, (byte) 0xA4, 0x04, 0x00, AID_APPLET));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("Connect success");
            } else if (check.equals("6400")) {
                JOptionPane.showMessageDialog(null, "Thẻ đã bị vô hiệu hóa");
                System.out.println("6400");
            } else {
                System.out.println("false");
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

//    public static void main(String[] args) {
//        // TODO code application logic here
//        ConnectCard.connect();
////        s.test();
//
//        String imagePath = "E:\\img/anh1.jpg"; // Đường dẫn đến ảnh của bạn
//
//        try {
//            // Đọc ảnh từ tệp vào BufferedImage
//            BufferedImage image = ImageIO.read(new File(imagePath));
//
//            // Tạo ByteArrayOutputStream để lưu byte của ảnh
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            // Ghi ảnh vào ByteArrayOutputStream dưới định dạng JPG
//            ImageIO.write(image, "jpg", baos);
//
//            // Chuyển đổi ByteArrayOutputStream thành mảng byte
//            byte[] imageBytes = baos.toByteArray();
//
//            // Đóng ByteArrayOutputStream
//            baos.close();
//
//            // In ra độ dài của mảng byte
//            System.out.println("Độ dài của mảng byte: " + imageBytes.length + " byte");
//            ConnectCard.setAvatar(imageBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("read");
//        byte[] a = ConnectCard.getAvatar();
//        System.out.println("result :" + a.length);
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i] + " ");
//        }
//
//        ConnectCard.setData(2, "Nguyễn Văn Đức Anh");
//        System.out.println(ConnectCard.getData(2));
//
//        ConnectCard.disconnect();
//
////        System.out.println(s.unFormatString(s.formatString("Nguyễn Văn Đức Anh")));
//    }
    
    public static void main(String[] args) {
        connect();
//        String imagePath = "E:\\img/anh1.jpg";
//        try {
//            // Đọc ảnh từ tệp vào BufferedImage
//            BufferedImage image = ImageIO.read(new File(imagePath));
//            NhanVien n = new NhanVien("1", "Nguyễn Văn a", "2/2/2001", "Nam", "na", "033003030", "d@gmail.com", 2, 2, image);
//            setNhanVien(n, 0);
//            NhanVien m = getNhanVien();
//            System.out.println(m.getHoTen() + " " + m.getGioiTinh() + " " +
//                    m.getGmail()+ m.getId()+ " "+ m.getQueQuan()+ " "+ 
//                    m.getSdt()+ " "+ m.getNgaySinh()+ " "+ m.getMaChucVu()+ " " + m.getIdCard());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.println(formatString("Siêu Nhân Gao").toString());

        System.out.println(unFormatString(formatString("Siêu Nhân Gao")));
        disconnect();
        
    }
    public static boolean disconnect() {
        try {
            card.disconnect(false);
            System.out.println("Disconnect");
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi" + e);
        }
        return false;
    }

    public static boolean setNhanVien(NhanVien nv, int id) {
        setData(0, id + "");
        setData(1, nv.getHoTen());
        setData(2, nv.getNgaySinh());
        setData(3, nv.getQueQuan());
        setData(4, nv.getSdt());
        setData(5, nv.getGmail());
        setData(6, nv.getMaChucVu() + "");
        setData(7, nv.getMaPhongBan() + "");
        setData(8, nv.getGioiTinh() + "");
        setData(9, "" + nv.getMaPhongBan() + nv.getMaChucVu() + id);
        setAvatar(nv.getAvatarBytes());
        return true;
    }
    
        public static boolean updateNhanVien(NhanVien nv) {
        setData(1, nv.getHoTen());
        setData(2, nv.getNgaySinh());
        setData(3, nv.getQueQuan());
        setData(4, nv.getSdt());
        setData(5, nv.getGmail());
        setData(8, nv.getGioiTinh() + "");
        setAvatar(nv.getAvatarBytes());
        return true;
    }

    public static NhanVien getNhanVien() {
        
        nhanVien.setId(getData(0));
        nhanVien.setHoTen(getData(1));
        nhanVien.setNgaySinh(getData(2));
        nhanVien.setQueQuan(getData(3));
        nhanVien.setSdt(getData(4));
        nhanVien.setGmail(getData(5));
        nhanVien.setMaChucVu(Integer.parseInt(getData(6)));
        nhanVien.setMaPhongBan(Integer.parseInt(getData(7)));
        nhanVien.setGioiTinh(getData(8));
        nhanVien.setIdCard(getData(9));
        nhanVien.setAvatarfromBytes(getAvatar());

        return nhanVien;
    }

    public static boolean setData(int select, String data) {
        byte p1;

        if (select < 0 || select > 9) {
            return false;
        } else {
            p1 = (byte) select;
        }

        byte[] dataArr = ConnectCard.formatString(data);

        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x02, p1, 0x00, dataArr));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static String getData(int select) {
        byte p1;

        if (select < 0 || select > 9) {
            return null;
        } else {
            p1 = (byte) select;
        }

        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x03, p1, 0x00));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
            } else {
                System.out.println("false");
            }
            byte[] respData = ConnectCard.responseAPDU.getData();
//            for (int i = 0; i < respData.length; i++) {
////               System.out.println(""+(char)respData[i]);
//                System.out.println((char) respData[i]);
//            }
            return ConnectCard.unFormatString(respData);

        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public static boolean updatePin(byte[] data) {
        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x00, 0x00, 0x00, data));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
                return true;
            } else {
                System.out.println("false");
                return false;
            }

        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static int checkPin(byte[] data) {
        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x01, 0x00, 0x00, data));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
                return 1;
            } else if (check.equals("9201")){
                System.out.println("false");
                return -1;
            }

        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static boolean rstPin() {
        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x05, 0x00, 0x00));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
                return true;
            } else{
                System.out.println("false");
                return false;
            }

        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean checkRstPin(byte[] data) {
        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x06, 0x00, 0x00, data));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
                return true;
            } else {
                System.out.println("false");
                return false;
            }

        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static byte[] formatString(String str) {
        byte[] result, temp;
        int len = 0;
        temp = new byte[3 * str.length()];
        for (int i = 0; i < str.length(); i++) {
            int intChar = (int) str.charAt(i);

            if (intChar <= 127) {
//                System.out.println(intChar);
                temp[len++] = (byte) str.charAt(i);
            } else {
//                System.out.println(intChar);
                int fistByte = (int) intChar / 16, secondByte = (int) intChar % 16;
//                System.out.println(fistByte + " " + secondByte);
                if (fistByte <= 127) {
                    temp[len++] = (byte) (-fistByte);
                    temp[len++] = (byte) secondByte;
                } else {
                    int thirtByte = secondByte;
                    secondByte = (int) fistByte % 16;
                    fistByte = (int) fistByte / 16;
                    temp[len++] = (byte) (-fistByte);
                    temp[len++] = (byte) (-secondByte);
                    temp[len++] = (byte) thirtByte;
//                    System.out.println((-fistByte) + "" + (-secondByte) + " " + thirtByte);
                }
            }
        }
        result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = temp[i];
            System.out.print(" " + result[i]);
        }
        return result;
    }

    public static String unFormatString(byte[] bytes) {
        char[] result, temp;
        temp = new char[bytes.length];
        int len = 0;
        for (int i = 0; i < temp.length; i++) {
            if (len >= bytes.length) {
                break;
            }
            if (bytes[len] >= 0) {
                temp[i] = (char) bytes[len++];
            } else {
                byte a = bytes[len++];
                if (bytes[len] >= 0) {
                    int re = (int) ((int) (-a * 16) + (int) (bytes[len++]));
                    temp[i] = (char) re;
                } else {
                    byte b = bytes[len++];
                    int re = (int) ((int) (((int) (-a * 16) + (int) (-b)) * 16) + bytes[len++]);
                    temp[i] = (char) re;
                }
            }
        }
        result = new char[len];
        for (int i = 0; i < len; i++) {
            result[i] = temp[i];
//            System.out.print("" + result[i]);
        }
        String result2 = "";
        for (char chr : result) {
            result2 += chr;
        }
        result2 = result2.trim();
        System.out.println(result2);
        System.out.printf("result len = %d%n", result2.length());
        return result2;
    }

    public static boolean sendArrAvatar(byte p2, byte[] data) {
        try {
            ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x04, 0x00, p2, data));
            String check = Integer.toHexString(responseAPDU.getSW());
            if (check.equals("9000")) {
                System.out.println("success");
            } else {
                System.out.println("false");
            }
        } catch (CardException ex) {
            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static boolean setAvatar(byte[] data) {
        int maxSendSize = 255;
        int len = data.length, time = 0;
        byte p2 = (byte) 0;
        int sendLen;
        if (len <= maxSendSize) {
            if (sendArrAvatar(p2, data)) {
                return true;
            }
            return false;

//            System.out.println("Gửi luôn");
//            for (int i = 0; i < data.length; i++) {
//                System.out.print(data[i] + " ");
//            }
        } else {
            byte[] sendArr;
            int sendPosition = 0;
            while (len > 0) {
                p2 = time == 0 ? (byte) 0 : (byte) 1;
                sendArr = new byte[0];
                if (len <= maxSendSize) {

                    sendArr = Arrays.copyOfRange(data, sendPosition, sendPosition + len);
//                    System.out.println("Gửi luôn " + time + " " + len + " " + p2);
                    len = 0;
                } else {
//                    System.out.println("gửi 255 " + p2);
                    sendArr = Arrays.copyOfRange(data, sendPosition, sendPosition + maxSendSize);
                    len -= maxSendSize;
                    sendPosition += maxSendSize;
                    time++;
                }
                for (int i = 0; i < sendArr.length; i++) {
                    System.out.print(sendArr[i] + " ");
                }
                System.out.println(" ");
                if (!ConnectCard.sendArrAvatar(p2, sendArr)) {
                    return false;
                }
            }
        }

        return false;
    }

    public static byte[] getAvatar() {
        byte[] result, temp;
        temp = new byte[3072];
        int len = 0;
        byte p2 = (byte) 0;
        while (true) {
            try {
                ConnectCard.responseAPDU = ConnectCard.channel.transmit(new CommandAPDU(0x00, (byte) 0x04, 0x01, p2));
                p2 += 1;
                String check = Integer.toHexString(responseAPDU.getSW());
                if (check.equals("9000")) {
                    System.out.println("success");
                } else if (check.equals("9101")) {
                    System.out.println("9101");
                    break;
                } else {
                    System.out.println("false");
                }
                byte[] respData = ConnectCard.responseAPDU.getData();
                System.out.println(respData.length);
                for (int i = 0; i < respData.length; i++) {
                    temp[len++] = respData[i];
                    System.out.print(respData[i] + " ");
                }

            } catch (CardException ex) {
                Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = temp[i];
        }
        return result;
    }
}

//public void test() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Nhap ten: ");
//        String data = sc.nextLine();
//        System.out.println("" + data);
//        byte[] dataArr = data.getBytes();
////        byte[] dataArr = {100, 30, -50, 60};
////        for (int i = 0; i < dataArr.length; i++) {
////            System.out.println(dataArr[i]);
////        }
////        System.out.println("" + dataArr);
//        try {
//            this.responseAPDU = this.channel.transmit(new CommandAPDU(0x00, (byte) 0x02, 0x01, 0x00, dataArr));
//            String check = Integer.toHexString(responseAPDU.getSW());
//            if (check.equals("9000")) {
//                System.out.println("success");
//            } else if (check.equals("6400")) {
//                JOptionPane.showMessageDialog(null, "Thẻ đã bị vô hiệu hóa");
//                System.out.println("6400");
//            } else {
//                System.out.println("false");
//            }
//            this.responseAPDU = this.channel.transmit(new CommandAPDU(0x00, (byte) 0x03, 0x01, 0x00));
//            byte[] respData = this.responseAPDU.getData();
//            for (int i = 0; i < respData.length; i++) {
////               System.out.println(""+(char)respData[i]);
//                System.out.println((char) respData[i]);
//            }
//        } catch (CardException ex) {
//            Logger.getLogger(ConnectCard.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
