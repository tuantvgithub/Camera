package com.oop_lab.model.khong_gian;

import java.util.List;

public class MatPhang {
    private Vector vectorPhapTuyen;
    private ToaDo diemThuoc;

    public MatPhang() {

    }

    public MatPhang(Vector vectorPhapTuyen, ToaDo diemThuoc) {
        this.vectorPhapTuyen = vectorPhapTuyen;
        this.diemThuoc = diemThuoc;
    }

    public MatPhang(ToaDo a, ToaDo b, ToaDo c) {
        Vector ab = new Vector(a, b);
        Vector ac = new Vector(a, c);

        this.vectorPhapTuyen = ab.tichCoHuong(ac);
        this.diemThuoc = a;
    }

    public boolean chuaDiem(ToaDo toaDo) {
        return (
            this.getVectorPhapTuyen().getX() * toaDo.getX() +
            this.getVectorPhapTuyen().getY() * toaDo.getY() +
            this.getVectorPhapTuyen().getZ() * toaDo.getZ() +
            this.giaTriHangSoD() == 0
        );
    }

    public boolean chuaDuong(DuongThang duongThang) {
        if (!this.chuaDiem(duongThang.getDiemThuoc()))
            return false;
        return this.vectorPhapTuyen.tichVoHuong(duongThang.getVectorChiPhuong()) == 0;
    }

    public float giaTriHangSoD() {
        float A = this.vectorPhapTuyen.getX();
        float B = this.vectorPhapTuyen.getY();
        float C = this.vectorPhapTuyen.getZ();

        return - (A * this.diemThuoc.getX() + B * this.diemThuoc.getY() + C * this.diemThuoc.getZ());
    }

    public Vector getVectorPhapTuyen() {
        return vectorPhapTuyen;
    }

    public void setVectorPhapTuyen(Vector vectorPhapTuyen) {
        this.vectorPhapTuyen = vectorPhapTuyen;
    }

    public ToaDo getDiemThuoc() {
        return diemThuoc;
    }

    public void setDiemThuoc(ToaDo diemThuoc) {
        this.diemThuoc = diemThuoc;
    }

    @Override
    public boolean equals(Object obj) {
        MatPhang other = (MatPhang) obj;
        Vector tichCoHuong = this.vectorPhapTuyen.tichCoHuong(other.getVectorPhapTuyen());
        return (
            other.chuaDiem(this.diemThuoc) &&
            tichCoHuong.equals(new Vector(0, 0, 0))
        );
    }

    @Override
    public String toString() {
        // phapTuyen: n = (a, b, c)
        float a = this.vectorPhapTuyen.getX();
        float b = this.vectorPhapTuyen.getY();
        float c = this.vectorPhapTuyen.getZ();

        // ax + by + cz + d = 0
        return "MatPhang: " + a + "x + " + b + "y + " + c + "z + " +
                this.giaTriHangSoD() + " = 0";
    }

    public static boolean laHCN(List<ToaDo> toaDoList)
    {
        //ABCD
        int check = 0;
        Vector AB = new Vector(toaDoList.get(0),toaDoList.get(1));
        Vector DC = new Vector(toaDoList.get(3),toaDoList.get(2));

        if(AB.tichCoHuong(DC).equals(new Vector(0,0,0)) && (AB.doDai() == DC.doDai()))
        {
            check ++;
        }
        Vector AD = new Vector(toaDoList.get(0),toaDoList.get(3));
        if(AB.tichVoHuong(AD) == 0)
        {
            check++;
        }
        if(check == 2){
            return true;
        }
        else{
            return false;
        }

    }
}
