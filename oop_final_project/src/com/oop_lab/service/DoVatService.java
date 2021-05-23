package com.oop_lab.service;

import com.oop_lab.model.DoVat;
import com.oop_lab.model.khong_gian.MatPhang;
import com.oop_lab.model.khong_gian.ToaDo;
import com.oop_lab.model.khong_gian.Vector;

import java.util.ArrayList;
import java.util.List;

public class DoVatService {

    public DoVat createDoVat(List<ToaDo> cacDinh) {
        if (doVatLaHinhHopChuNhat(cacDinh))
            return new DoVat(cacDinh);

        return null;
    }

    public boolean doVatLaHinhHopChuNhat(List<ToaDo> cacDinh) {
        // TO DO
        List<ToaDo> matTren = new ArrayList<>();
        matTren.add(cacDinh.get(0));
        matTren.add(cacDinh.get(1));
        matTren.add(cacDinh.get(2));
        matTren.add(cacDinh.get(3));

        List<ToaDo> matDuoi = new ArrayList<>();
        matDuoi.add(cacDinh.get(4));
        matDuoi.add(cacDinh.get(5));
        matDuoi.add(cacDinh.get(6));
        matDuoi.add(cacDinh.get(7));

        if(!MatPhang.laHCN(matTren) && !MatPhang.laHCN(matDuoi))
        {
            return false;
        }
        Vector AB = new Vector(cacDinh.get(0),cacDinh.get(1));
        Vector EF = new Vector(cacDinh.get(4),cacDinh.get(5));
        if(AB.doDai() != EF.doDai()) return false;

        Vector AD = new Vector(cacDinh.get(0),cacDinh.get(3));
        Vector EH = new Vector(cacDinh.get(4),cacDinh.get(7));
        if(AD.doDai() != EH.doDai()) return false;

        Vector AE = new Vector(cacDinh.get(0),cacDinh.get(4));
        if(AE.tichVoHuong(EF) != 0) return false;
        return true;
    }

    public boolean diemNamTrongDoVat(DoVat doVat, ToaDo toaDo) {
        return doVat.chuaDiem(toaDo);
    }
}
