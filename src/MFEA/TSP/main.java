package MFEA.TSP;

import static MFEA.TSP.KhoiTao.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.LaiGhep.*;

public class main {
    static int totalCities;
    static double[][] khoangCach;    //[start][end] trả về khoảng cách giữa 2 thành phố

    static String linkFile = "G:\\IdeaProjects\\GAforTSP\\eil51.tsp";  //Để đường dẫn của file chứa thông tin các đỉnh
    static int soTacVu = 2; //Số lượng tác vụ chạy đồng thời (Không động vào)
    static int theHe = 0;
    static int maxTheHe = 10000;
    static int maxCaThe = 100; //tối thiếu là 100
    static double tiLeDotBien = 0.3; //Đảm bảo tính đa dạng của quần thể

    static boolean print_File = false; //có hay không in ra file .tsp
    static boolean print_toaDo = false; //có hay không in ra tọa độ
    static boolean print_khoangCach = false; //có hay không in ra khoảng cách giữa các thành phố
    static boolean print_first_theHe = false; //Có hay không in ra các NST trong thế hệ đầu tiên
    static boolean print_first_theHe_FULL = false; //In ra full thông tin
    static boolean print_eNST = true;


    static NST[][] dsNST = new NST[maxTheHe][maxCaThe];

    public static void main(String[] args){
        //Bài toán MFEA chạy đồng thời 2 tác vụ 25 đỉnh và 51 đỉnh
        System.out.println("Chương trình đã chạy!");
        khoiTao();
        for(int i=0;i<maxTheHe;i++){
            for(int j=0;j<maxCaThe;j++){
                dsNST[i][j] = new NST();
                dsNST[i][j].khoiTaoDoiTuong();
            }
        }
        khoiTaoQuanThe_hoanVi();
        danhGiaCaThe(dsNST[theHe]);
        if(print_first_theHe_FULL){
            int nhiemSacThe=0;
            while (nhiemSacThe!=dsNST[theHe].length){
                System.out.print(theHe+"-"+nhiemSacThe+" : ");
                for (int i=1;i<=totalCities;i++){
                    System.out.print(" "+dsNST[theHe][nhiemSacThe].Gen[i]+" ");
                }
                System.out.print("|");
                for(int i=0;i<soTacVu;i++){
                    System.out.print("\tF"+i+":"+dsNST[theHe][nhiemSacThe].f_cost[i]+"\tR"+i+":"+dsNST[theHe][nhiemSacThe].rank[i]+" ");
                }
                System.out.print("|");
                System.out.print("\tskill-factor:"+dsNST[theHe][nhiemSacThe].skill_factor+"\tscalar-fitness:"+dsNST[theHe][nhiemSacThe].scalar_fitness+" ");
                System.out.print("\n");
                nhiemSacThe++;
            }
        }
        theHe++;
        for (int nst=1;nst<maxTheHe;nst++){
            TheHeTiepTheo();
            danhGiaCaThe(dsNST[theHe]);
            if(print_first_theHe_FULL){
                int nhiemSacThe=0;
                while (nhiemSacThe!=dsNST[theHe].length){
                    System.out.print(theHe+"-"+nhiemSacThe+" : ");
                    for (int i=1;i<=totalCities;i++){
                        System.out.print(" "+dsNST[theHe][nhiemSacThe].Gen[i]+" ");
                    }
                    System.out.print("|");
                    for(int i=0;i<soTacVu;i++){
                        System.out.print("\tF"+i+":"+dsNST[theHe][nhiemSacThe].f_cost[i]+"\tR"+i+":"+dsNST[theHe][nhiemSacThe].rank[i]+" ");
                    }
                    System.out.print("|");
                    System.out.print("\tskill-factor:"+dsNST[theHe][nhiemSacThe].skill_factor+"\tscalar-fitness:"+dsNST[theHe][nhiemSacThe].scalar_fitness+" ");
                    System.out.print("\n");
                    nhiemSacThe++;
                }
            }
            if(print_eNST){
                for (int nhiemSacThe=0;nhiemSacThe<maxCaThe;nhiemSacThe++){
                    if(dsNST[theHe][nhiemSacThe].rank[1]==1){
                        System.out.println(theHe+"-"+nhiemSacThe+" :\tF:"+dsNST[theHe][nhiemSacThe].f_cost[1]);
                    }
                }
            }
            theHe++;
        }
        for (int nhiemSacThe=0;nhiemSacThe<maxCaThe;nhiemSacThe++){
            if(dsNST[maxTheHe-1][nhiemSacThe].rank[1]==1){
                System.out.print(maxTheHe-1+"-"+nhiemSacThe+" : ");
                for (int i=1;i<=totalCities;i++){
                    System.out.print(" "+dsNST[maxTheHe-1][nhiemSacThe].Gen[i]+" ");
                }
                System.out.print("|");
                for(int i=0;i<soTacVu;i++){
                    System.out.print("\tF"+i+":"+dsNST[maxTheHe-1][nhiemSacThe].f_cost[i]+"\tR"+i+":"+dsNST[maxTheHe-1][nhiemSacThe].rank[i]+" ");
                }
                System.out.print("|");
                System.out.print("\tskill-factor:"+dsNST[maxTheHe-1][nhiemSacThe].skill_factor+"\tscalar-fitness:"+dsNST[maxTheHe-1][nhiemSacThe].scalar_fitness+" ");
                System.out.print("\n");
            }
        }
    }
}
