package MFEA.TSP;

import static MFEA.TSP.main.*;
import static MFEA.TSP.DanhGiaQuanThe.*;
import static MFEA.TSP.KhoiTao.*;

public class LaiGhep {
    public static int LuaChonChaMe(){
        int c1 = genRandom(maxCaThe)-1;
        int c2 = genRandom(maxCaThe)-1;
        while (c1==c2) c2 = genRandom(maxCaThe)-1;
        if(dsNST[theHe-1][c1].scalar_fitness > dsNST[theHe-1][c2].scalar_fitness){
            return c1;
        } else
            return c2;
    }

    public static int[][] laiGhepOX(int[] cha,int[] me){
        //phương pháp lai ghép thứ tự
        int[][] child = new int[2][totalCities+5];
        //Chọn 2 điểm lai ngẫu nhiên
        int p1,p2;
        p1 = genRandom(totalCities-1);
        p2 = genRandom(totalCities);
        if(p1==totalCities-1) p2=totalCities;
        while (p1>=p2) p2=genRandom(totalCities);
        //Con 1:
        //Sao chép phần giữa 2 điểm lai của cha vào con 1
        for(int i=p1;i<=p2;i++){
            child[0][i]=cha[i];
        }
//        Các gen chưa được sao chép, tiến hành thêm vào con 1 bắt đầu từ điểm lai thứ 2, theo thứ tự xuất hiện ở mẹ
        int contro;
        if(p2==totalCities) contro = 1;
        else contro = p2+1;
        vonglap1:
        for(int i=1;i<=totalCities;i++){
            for(int j=1;j<=totalCities;j++){ //Sàng lọc những gen trong NST mẹ chưa có trong NST con để thêm vào NST con
                if(me[i]==child[0][j]) continue vonglap1;
            }
            child[0][contro] = me[i];
            contro++;
            if(contro>totalCities) contro = 1;
        }
        //Con 2 làm tương tự
        if(p2==totalCities) contro = 1;
        else contro = p2+1;
        for(int i=p1;i<=p2;i++){
            child[1][i]=me[i];
        }
//        Các gen chưa được sao chép, tiến hành thêm vào con 1 bắt đầu từ điểm lai thứ 2, theo thứ tự xuất hiện ở mẹ
        vonglap2:
        for(int i=1;i<=totalCities;i++){
            for(int j=1;j<=totalCities;j++){ //Sàng lọc những gen trong NST mẹ chưa có trong NST con để thêm vào NST con
                if(cha[i]==child[1][j]) continue vonglap2;
            }
            child[1][contro] = cha[i];
            contro++;
            if(contro>totalCities) contro = 1;
        }
//        for(int i=0;i<2;i++){
//            for(int j=1;j<=totalCities;j++){
//                System.out.print(child[i][j]+" ");
//            }
//            System.out.print("\n");
//        }
        return child;
    }

    public static void TheHeTiepTheo(){
        NST[] child = new NST[maxCaThe*2];
        for(int i=0;i<maxCaThe*2;i++){
            child[i] = new NST();
            child[i].khoiTaoDoiTuong();
        }
        for(int i=0;i<maxCaThe;i++){
            System.arraycopy(dsNST[theHe-1][i].Gen, 0, child[i].Gen, 0, dsNST[theHe-1][i].Gen.length);
        }


        int contro = maxCaThe;
        int[][] tempChild = new int[2][totalCities+5];
        while(contro<maxCaThe*2){
            int parentA, parentB;
            parentA = LuaChonChaMe();
            parentB = LuaChonChaMe();
            while (parentA==parentB) parentB = LuaChonChaMe();
            double random = genRandomDouble();
            if(dsNST[theHe-1][parentA].skill_factor==dsNST[theHe-1][parentB].skill_factor || random<tiLeDotBien){
                //Lai ghép
                tempChild = laiGhepOX(dsNST[theHe-1][parentA].Gen,dsNST[theHe-1][parentB].Gen);
                System.arraycopy(tempChild[0], 0, child[contro].Gen, 0, tempChild[0].length);
                System.arraycopy(tempChild[1], 0, child[++contro].Gen, 0, tempChild[1].length);
                contro++;
            } else{
                //Đột biến
                int p1,p2;
                p1=genRandom(totalCities);
                p2=genRandom(totalCities);
                while (p1==p2) p2=genRandom(totalCities);

                System.arraycopy(dsNST[theHe-1][parentA].Gen, 0, tempChild[0], 0, dsNST[theHe-1][parentA].Gen.length);
                System.arraycopy(dsNST[theHe-1][parentB].Gen, 0, tempChild[1], 0, dsNST[theHe-1][parentB].Gen.length);

                int temp;
                temp = tempChild[0][p1];
                tempChild[0][p1] = tempChild[0][p2];
                tempChild[0][p2] = temp;
                temp = tempChild[1][p1];
                tempChild[1][p1] = tempChild[1][p2];
                tempChild[1][p2] = temp;

                System.arraycopy(tempChild[0], 0, child[contro].Gen, 0, tempChild[0].length);
                System.arraycopy(tempChild[1], 0, child[++contro].Gen, 0, tempChild[1].length);
                contro++;
            }
        }
        danhGiaCaThe(child);


        for(int i=0;i<maxCaThe;i++){
            double maxfitness=0;
            int eNST=0;
            for(int j=0;j<child.length;j++){
                if(maxfitness<child[j].scalar_fitness&&child[j].cathemoi==false){
                    maxfitness = child[j].scalar_fitness;
                    eNST = j;
                }
            }
            System.arraycopy(child[eNST].Gen, 0, dsNST[theHe][i].Gen, 0, child[eNST].Gen.length);
            child[eNST].cathemoi=true;
        }
    }
}
