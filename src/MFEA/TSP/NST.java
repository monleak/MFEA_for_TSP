package MFEA.TSP;

import static MFEA.TSP.main.*;

public class NST {
    public int[] Gen; //[i] đưa ra giá trị của gen ở vị trí i
    public double[] f_cost; //[tác vụ] đưa ra factorial-cost cho tác vụ (trong bài toán TSP là tổng khoảng cách)
    public int[] rank; //[tác vụ] đưa ra xếp hạng của cá thể trong tác vụ (Xếp theo factorial-cost)
    public int skill_factor; // = tác vụ có rank cao nhất
    public double scalar_fitness; // = 1/(min(rank[]))
    public boolean rank0 = false; //Thể hiện xem đối tượng này đã được xếp rank ở tác vụ 1 hay chưa
    public boolean rank1 = false; //Thể hiện xem đối tượng này đã được xếp rank ở tác vụ 2 hay chưa
    public boolean cathemoi = false;

    public void khoiTaoDoiTuong(){
        Gen = new int[totalCities+5];
        f_cost = new double[soTacVu];
        rank = new int[soTacVu];
    }
}
