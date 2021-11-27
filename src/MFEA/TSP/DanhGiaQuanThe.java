package MFEA.TSP;

import static MFEA.TSP.main.*;

public class DanhGiaQuanThe {
    public static void danhGiaCaThe(NST[] dsNST){
        //Đánh giá các cá thể và tính điểm fitness
        double[] totalDist = new double[soTacVu];
        int cityA,cityB;
        int nhiemSacThe = 0;
        int[] luuDanhSach25Dinh = new int[26];
        while (nhiemSacThe!=dsNST.length){
            //Tính khoảng cách của tác vụ 1
            int dem=1;
            for(int gene=1;gene<totalCities;gene++){
                if(dsNST[nhiemSacThe].Gen[gene]<=25){
                    luuDanhSach25Dinh[dem] = dsNST[nhiemSacThe].Gen[gene];
                    dem++;
                }
            }
            for(int gene=1;gene<25;gene++){
                cityA = luuDanhSach25Dinh[gene];
                cityB = luuDanhSach25Dinh[gene+1];
                totalDist[0] += khoangCach[cityA][cityB];
            }
            //Tính khoảng cách của tác vụ 2
            for(int gene=1;gene<totalCities;gene++){
                cityA = dsNST[nhiemSacThe].Gen[gene];
                cityB = dsNST[nhiemSacThe].Gen[gene+1];
                totalDist[1] += khoangCach[cityA][cityB];
            }
            //Lưu giá trị
            dsNST[nhiemSacThe].f_cost[0] = totalDist[0];
            dsNST[nhiemSacThe].f_cost[1] = totalDist[1];

            for(int i=0;i<soTacVu;i++){
                dsNST[nhiemSacThe].rank[i]=nhiemSacThe;
            }
            //NST tiếp theo
            nhiemSacThe++;
            for(int i=0;i<soTacVu;i++){
                totalDist[i]=0;
            }
        }
        //Xếp rank cho các cá thể
        double[] minDist = new double[soTacVu];
        minDist[0] = 10000;
        minDist[1] = 10000;
        int[] eNST = new int[soTacVu];
        for (int i=0;i<dsNST.length;i++){
            for (int j=0;j<dsNST.length;j++){
                //Tìm NST tốt nhất ở mỗi tác vụ
                if(minDist[0]>dsNST[j].f_cost[0] && dsNST[j].rank0==false){
                    minDist[0]=dsNST[j].f_cost[0];
                    eNST[0] = j;
                }
                if(minDist[1]>dsNST[j].f_cost[1] && dsNST[j].rank1==false){
                    minDist[1]=dsNST[j].f_cost[1];
                    eNST[1] = j;
                }
            }
            dsNST[eNST[0]].rank[0] = i+1;
            dsNST[eNST[0]].rank0=true;
            dsNST[eNST[1]].rank[1] = i+1;
            dsNST[eNST[1]].rank1=true;
//            System.out.println(i+" "+eNST[0]+" "+eNST[1]);
            minDist[0] = 10000;
            minDist[1] = 10000;
        }

        //Tính skill-factor và scalar-fitness
        nhiemSacThe=0;
        while (nhiemSacThe!=dsNST.length){
            if(dsNST[nhiemSacThe].rank[0] < dsNST[nhiemSacThe].rank[1]){
                dsNST[nhiemSacThe].skill_factor=1;
                dsNST[nhiemSacThe].scalar_fitness = 1.0/dsNST[nhiemSacThe].rank[0];
            }else {
                dsNST[nhiemSacThe].skill_factor=2;
                dsNST[nhiemSacThe].scalar_fitness = 1.0/dsNST[nhiemSacThe].rank[1];
            }
            nhiemSacThe++;
        }
    }
}
