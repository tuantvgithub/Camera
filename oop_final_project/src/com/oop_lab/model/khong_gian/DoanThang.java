package com.oop_lab.model.khong_gian;

import java.util.ArrayList;
import java.util.List;

public class DoanThang {
    private ToaDo a;
    private ToaDo b;

    public DoanThang(ToaDo a, ToaDo b) {
        this.a = a;
        this.b = b;
    }

    public ToaDo giaoDiemVoiMatPhang(MatPhang matPhang) {
        ToaDo giaoDiem = this.toDuongThang().giaoDiemVoiMatPhang(matPhang);

        if (giaoDiem == null || !this.chuaDiem(giaoDiem))
            return null;

        return giaoDiem;
    }

    public List<ToaDo> giaoDiemVoiHinhHopChuNhat(HinhHopChuNhat hinhHopChuNhat) {
        List<ToaDo> dsGiaoDiem = new ArrayList<ToaDo>();
        List<ToaDo> dsUngCuVien = this.toDuongThang().giaoDiemVoiHinhHopChuNhat(hinhHopChuNhat);

        for (ToaDo giaoDiem : dsUngCuVien) {
            if (this.chuaDiem(giaoDiem)) {
                dsGiaoDiem.add(giaoDiem.clone());
            }
        }

        return dsGiaoDiem;
    }

    public boolean chuaDiem(ToaDo toaDo) {
        float doDai = (float) (Math.round(this.doDai() * 1000000.0) / 1000000.0);
        float tongKhoangCach = (float) (Math.round((toaDo.khoangCach(this.a)
                + toaDo.khoangCach(this.b)) * 1000000.0) / 1000000.0);

        return (!toaDo.equals(this.a) && !toaDo.equals(this.b) &&
                doDai == tongKhoangCach);
    }

    public DuongThang toDuongThang() {
        return new DuongThang(this.a, new Vector(this.a, this.b), true);
    }

    public float doDai() {
        return this.a.khoangCach(this.b);
    }

    public ToaDo getA() {
        return a;
    }

    public void setA(ToaDo a) {
        this.a = a;
    }

    public ToaDo getB() {
        return b;
    }

    public void setB(ToaDo b) {
        this.b = b;
    }
}
