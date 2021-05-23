package com.oop_lab.service;

import com.oop_lab.model.DoVat;
import com.oop_lab.model.khong_gian.ToaDo;
import com.oop_lab.model.khong_gian.Vector;

import java.util.List;

public class DoVatService {

    public DoVat createDoVat(List<ToaDo> cacDinh) {
        if (doVatLaHinhHopChuNhat(cacDinh))
            return new DoVat(cacDinh);

        return null;
    }

    public boolean doVatLaHinhHopChuNhat(List<ToaDo> dsCacDinh) {
        // TO DO
        int A = 0, B = 1, C = 2, D = 3,
            E = 4, F = 5, G = 6, H = 7;

        Vector AB = new Vector(dsCacDinh.get(A), dsCacDinh.get(B));
        Vector DC = new Vector(dsCacDinh.get(D), dsCacDinh.get(C));

        Vector EF = new Vector(dsCacDinh.get(E), dsCacDinh.get(F));
        Vector HG = new Vector(dsCacDinh.get(H), dsCacDinh.get(G));

        Vector AE = new Vector(dsCacDinh.get(A), dsCacDinh.get(E));
        Vector CG = new Vector(dsCacDinh.get(C), dsCacDinh.get(G));
        
        Vector AD = new Vector(dsCacDinh.get(A), dsCacDinh.get(D));

        if (!AB.equals(DC) || Math.round(AB.doDai() *1000)/1000 == 0)     return false;   // ABCD <> hbh
        if (!EF.equals(HG) || Math.round(EF.doDai() *1000)/1000 == 0)     return false;   // EFGH <> hbh
        if (!AE.equals(CG) || Math.round(AE.doDai() *1000)/1000 == 0)     return false;   // AEDH <> hbh
    
        // AB ⊥ AD ⊥ AE
        if (AB.tichVoHuong(AD) != 0)    return false;
        if (AE.tichVoHuong(AB) != 0)    return false;
        if (AE.tichVoHuong(AD) != 0)    return false;
        
        return true;
    }

    public boolean diemNamTrongDoVat(DoVat doVat, ToaDo toaDo) {
        return doVat.chuaDiem(toaDo);
    }
}
