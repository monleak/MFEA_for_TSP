package MFEA.TSP;

import java.io.*;
import java.util.Random;

import static MFEA.TSP.main.*;

class thanhPho{
    public int toaDox;
    public int toaDoy;
}

public class KhoiTao {

    //Các hàm sinh ngẫu nhiên
    public static int genRandom(int gioiHan){
        //gioiHan là giá trị giới hạn, các số được sinh ra nằm trong [1;gioiHan]
        //VD: Nếu muốn sinh ra 1 hoặc 2, hãy đặt gioiHan = 2
        Random output = new Random();
        int number = output.nextInt(gioiHan);
        return number+1;
    }
    public static double genRandomDouble(){
        //Sinh kiểu double, các số được random trong khoảng [0;1)
        Random output = new Random();
        double number = output.nextDouble();
        return number;
    }

    public static double khoangCach(int x1,int y1,int x2,int y2){
        //hàm tính khoảng cách
        double n;
        n = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        return n;
    }

    public static void khoiTao() {
        //Khởi tạo cấu trúc dữ liệu và lưu nó trong mảng 2 chiều
        BufferedReader readBuffer = null;
        try{
            readBuffer = new BufferedReader(new FileReader(linkFile));
            String line;
            do{
                //Đọc từng dòng của file đến phần bắt đầu tọa độ
                line = readBuffer.readLine();
                if(line.equals("NODE_COORD_SECTION")) break; //Thoát khỏi vòng lặp nếu gặp NODE_COORD_SECTION
                if(line.contains("DIMENSION")){
                    //Đọc giá trị của tổng số thành phố
                    String[] result = line.split(" : ");
                    totalCities = Integer.parseInt(result[1]);
                }
                if(print_File) System.out.println(line);
            }while (true);
            thanhPho dsThanhPho[] = new thanhPho[totalCities+5];
            khoangCach = new double[totalCities+5][totalCities+5];
            do{
                //Đọc từng dòng của file và lưu dữ liệu tọa độ vào mảng 2 chiều
                line = readBuffer.readLine();
                if(line.equals("EOF")) break; //Thoát khỏi vòng lặp nếu gặp EOF
                String[] result = line.split(" ");
                dsThanhPho[Integer.parseInt(result[0])] = new thanhPho();
                dsThanhPho[Integer.parseInt(result[0])].toaDox = Integer.parseInt(result[1]);
                dsThanhPho[Integer.parseInt(result[0])].toaDoy = Integer.parseInt(result[2]);
                if(print_toaDo) System.out.println(Integer.parseInt(result[0]) + " " + dsThanhPho[Integer.parseInt(result[0])].toaDox + " " + dsThanhPho[Integer.parseInt(result[0])].toaDoy);
                if(print_File) System.out.println(line);
            }while (true);
            for(int i=1;i<=totalCities;i++){
                for (int j=1;j<=totalCities;j++){
                    double giaTriKhoangCach;
                    giaTriKhoangCach = khoangCach(dsThanhPho[i].toaDox,dsThanhPho[i].toaDoy,dsThanhPho[j].toaDox,dsThanhPho[j].toaDoy);
                    khoangCach[i][j] = giaTriKhoangCach;
                    if(print_khoangCach) System.out.println(i+" "+j+" "+khoangCach[i][j]);
                }
            }
        }catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
    }

    public static void khoiTaoQuanThe_hoanVi(){
        int nhiemSacThe = 0;
        while(nhiemSacThe!=maxCaThe){
            for(int i=1;i<=totalCities;i++){
                dsNST[theHe][nhiemSacThe].Gen[i] = i;
            }
            //khởi tạo số lần hoán vị
            int count = totalCities;
            int p1,p2,temp;
            while (count>0){
                p1 = genRandom(totalCities);
                p2 = genRandom(totalCities);
                while(p1==p2){
                    p2 = genRandom(totalCities);
                }
                temp = dsNST[theHe][nhiemSacThe].Gen[p1];
                dsNST[theHe][nhiemSacThe].Gen[p1] = dsNST[theHe][nhiemSacThe].Gen[p2];
                dsNST[theHe][nhiemSacThe].Gen[p2] = temp;
                count--;
            }
            if(print_first_theHe){
                System.out.print(theHe+"-"+nhiemSacThe+" : ");
                for (int i=1;i<=totalCities;i++){
                    System.out.print(" "+dsNST[theHe][nhiemSacThe].Gen[i]+" ");
                }
                System.out.print("\n");
            }
            nhiemSacThe++;
        }
    }
}
