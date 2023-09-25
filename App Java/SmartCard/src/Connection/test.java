/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author HP
 */
public class test {
    public static void main(String[] args) {
        String imagePath = "E:\\img/anh1.jpg"; // Đường dẫn đến ảnh của bạn

        try {
            // Đọc ảnh từ tệp vào BufferedImage
            BufferedImage image = ImageIO.read(new File(imagePath));

            // Tạo ByteArrayOutputStream để lưu byte của ảnh
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Ghi ảnh vào ByteArrayOutputStream dưới định dạng JPG
            ImageIO.write(image, "jpg", baos);

            // Chuyển đổi ByteArrayOutputStream thành mảng byte
            byte[] imageBytes = baos.toByteArray();

            // Đóng ByteArrayOutputStream
            baos.close();

            // In ra độ dài của mảng byte
            System.out.println("Độ dài của mảng byte: " + imageBytes.length + " byte");
            for (byte imageByte : imageBytes) {
                System.out.print(imageByte + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
