package com.oop_lab.service;

import com.oop_lab.model.Camera;
import com.oop_lab.model.DoVat;
import com.oop_lab.model.Room;
import com.oop_lab.model.graphic2d.RoomPicture;
import com.oop_lab.model.khong_gian.DoanThang;
import com.oop_lab.model.khong_gian.HinhChop;
import com.oop_lab.model.khong_gian.MatPhang;
import com.oop_lab.model.khong_gian.ToaDo;
import com.oop_lab.model.khong_gian.Vector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    public Room createRoom(float cao, float rong, float dai) {
        if (roomHopLe(cao, rong, dai))
            return new Room(dai, rong, cao);
        return null;
    }

    public Room createRoom(List<ToaDo> danhSachCacDinh) {
        if (roomLaHinhHopChuNhat(danhSachCacDinh))
            return new Room(danhSachCacDinh);

        return null;
    }

    public boolean roomLaHinhHopChuNhat(List<ToaDo> dsCacDinh) {
        // TO DO
        int A = 0, B = 1, C = 2, D = 3,
            E = 4, F = 5, G = 6, H = 7;

        Vector AB = new Vector(dsCacDinh.get(A), dsCacDinh.get(B));
        Vector DC = new Vector(dsCacDinh.get(D), dsCacDinh.get(C));

        Vector EF = new Vector(dsCacDinh.get(E), dsCacDinh.get(F));
        Vector HG = new Vector(dsCacDinh.get(H), dsCacDinh.get(G));

        Vector AE = new Vector(dsCacDinh.get(A), dsCacDinh.get(E));
        Vector DH = new Vector(dsCacDinh.get(D), dsCacDinh.get(H));
        
        Vector AD = new Vector(dsCacDinh.get(A), dsCacDinh.get(D));

        if (!AB.equals(DC))     return false;   // ABCD <> hbh
        if (!EF.equals(HG))     return false;   // EFGH <> hbh
        if (!AE.equals(DH))     return false;   // AEDH <> hbh
    
        // AB ⊥ AD ⊥ AE
        if (AB.tichVoHuong(AD) != 0)    return false;
        if (AE.tichVoHuong(AB) != 0)    return false;
        if (AE.tichVoHuong(AD) != 0)    return false;
        
        return true;
    }

    public boolean roomHopLe(float cao, float rong, float dai) {
        return (cao > 0) && (rong > 0) && (dai > 0);
    }

    public boolean themDoVat(Room room, DoVat doVat) {
        if (!doVatNamTrongPhong(room, doVat)) return false;
        if (!doVatKhongChamTran(room, doVat)) return false;
        if (!doVatKhongBiVuong(room, doVat)) return false;
        if (!doVatNamTrenSanHoacTrenVatKhac(room, doVat)) return false;

        room.themDoVat(doVat);
        return true;
    }

    public boolean doVatNamTrongPhong(Room room, DoVat doVat) {
        // TO DO
        return (room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_A))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_B))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_C))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_D))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_E))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_F))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_G))
                && room.chuaDiem(doVat.getCacDinh().get(DoVat.DINH_H))
        );
    }

    public boolean doVatKhongChamTran(Room room, DoVat doVat) {
        // dinhE of doVat
        ToaDo dinhE = doVat.getCacDinh().get(DoVat.DINH_E);
        MatPhang room_dayTren = room.getCacMat().get(Room.MAT_DAY_TREN_EFGH);

        return !(room_dayTren.chuaDiem(dinhE));
    }

    public boolean doVatKhongBiVuong(Room room, DoVat doVat) {
        
//        List<DoVat> doVats =  room.getDanhSachDoVat();
        for (DoVat dsVat : room.getDanhSachDoVat()) {

            // DoVat them vao` co' dinh nam` trong Vat khac
            for (ToaDo dsDinh : doVat.getCacDinh().values()) {
                if (dsVat.chuaDiem(dsDinh))
                    return false;
            }

            // ton` tai doVat co' dinh nam` trong doVat them vao`
            for (ToaDo dsDinh : dsVat.getCacDinh().values()) {
                if (doVat.chuaDiem(dsDinh))
                    return false;
            }

        }
        return true;
    }

    public boolean doVatNamTrenSanHoacTrenVatKhac(Room room, DoVat doVat) {
        // TO DO
        ToaDo doVat_dinhA = doVat.getCacDinh().get(DoVat.DINH_A);
        MatPhang room_dayDuoi = room.getCacMat().get(Room.MAT_DAY_DUOI_ABCD);

        if (!room_dayDuoi.chuaDiem(doVat_dinhA)) {
            return false;
        }

        for (DoVat dsVat : room.getDanhSachDoVat()) {
            MatPhang dsVat_dayTren = dsVat.getCacMat().get(DoVat.MAT_DAY_TREN_EFGH);

            if (!dsVat_dayTren.chuaDiem(doVat_dinhA))
                return false;
        }

        return true;
    }

    public boolean themCamera(Room room, Camera camera) {
        if (cameraNamTrenTuong(room, camera) == null) return false;
        if (!cameraKhongBiTrung(room, camera)) return false;
        if (!cameraSoiVaoTrongPhong(room, camera)) return false;

        this.setupVungNhinCameraTuongUngVoiPhong(room, camera);
        room.themCamera(camera);
        return true;
    }

    public void setupVungNhinCameraTuongUngVoiPhong(Room room, Camera camera) {
        MatPhang matPhangChuaCam = null;
        for (MatPhang matPhang : room.getDanhSachCacMat()) {
            if (matPhang.chuaDiem(camera.getToaDo())) {
                matPhangChuaCam = matPhang;
                break;
            }
        }

        if (matPhangChuaCam == null)
            return;

        float tanRong = (float) Math.tan(Math.toRadians(camera.getGocRong() / 2));
        float tanCao = (float) Math.tan(Math.toRadians(camera.getGocRong() / 2));

        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_DAY_DUOI_ABCD))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX(),
                    camera.getToaDo().getY(),
                    camera.getToaDo().getZ() + camera.getTamNhin()
            );
            // TO DO
        }
        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_DAY_TREN_EFGH))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX(),
                    camera.getToaDo().getY(),
                    camera.getToaDo().getZ() - camera.getTamNhin()
            );
            // TO DO
        }
        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_ADHE))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX() + camera.getTamNhin(),
                    camera.getToaDo().getY(),
                    camera.getToaDo().getZ()
            );
            float chieuCao = camera.getToaDo().khoangCach(diemH);
            camera.setVungNhin(
                    new HinhChop(
                            camera.getToaDo(),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() + chieuCao * tanCao),
                            new ToaDo(diemH.getX(), diemH.getY() - chieuCao * tanRong, diemH.getZ()),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() - chieuCao * tanCao),
                            new ToaDo(diemH.getX(), diemH.getY() + chieuCao * tanRong, diemH.getZ())
                    )
            );
        }
        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_BCGF))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX() - camera.getTamNhin(),
                    camera.getToaDo().getY(),
                    camera.getToaDo().getZ()
            );
            float chieuCao = camera.getToaDo().khoangCach(diemH);
            camera.setVungNhin(
                    new HinhChop(
                            camera.getToaDo(),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() + chieuCao * tanCao),
                            new ToaDo(diemH.getX(), diemH.getY() - chieuCao * tanRong, diemH.getZ()),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() - chieuCao * tanCao),
                            new ToaDo(diemH.getX(), diemH.getY() + chieuCao * tanRong, diemH.getZ())
                    )
            );
        }
        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_CDHG))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX(),
                    camera.getToaDo().getY() - camera.getTamNhin(),
                    camera.getToaDo().getZ()
            );
            float chieuCao = camera.getToaDo().khoangCach(diemH);
            camera.setVungNhin(
                    new HinhChop(
                            camera.getToaDo(),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() + chieuCao * tanCao),
                            new ToaDo(diemH.getX() - chieuCao * tanRong, diemH.getY(), diemH.getZ()),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() - chieuCao * tanCao),
                            new ToaDo(diemH.getX() + chieuCao * tanRong, diemH.getY(), diemH.getZ())
                    )
            );
        }
        if (matPhangChuaCam.equals(room.getCacMat().get(Room.MAT_AEFB))) {
            ToaDo diemH = new ToaDo(
                    camera.getToaDo().getX(),
                    camera.getToaDo().getY() + camera.getTamNhin(),
                    camera.getToaDo().getZ()
            );
            float chieuCao = camera.getToaDo().khoangCach(diemH);
            camera.setVungNhin(
                    new HinhChop(
                            camera.getToaDo(),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() + chieuCao * tanCao),
                            new ToaDo(diemH.getX() - chieuCao * tanRong, diemH.getY(), diemH.getZ()),
                            new ToaDo(diemH.getX(), diemH.getY(), diemH.getZ() - chieuCao * tanCao),
                            new ToaDo(diemH.getX() + chieuCao * tanRong, diemH.getY(), diemH.getZ())
                    )
            );
        }
    }

    public MatPhang cameraNamTrenTuong(Room room, Camera camera) {
        for (MatPhang matPhang : room.getDanhSachCacMat()) {
            if (matPhang.chuaDiem(camera.getToaDo())) {
                return matPhang;
            }
        }
        return null;
    }

    public boolean cameraKhongBiTrung(Room room, Camera camera) {
        for (Camera cam : room.getDanhSachCamera()) {
            if (cam.getToaDo().equals(camera.getToaDo()))
                return false;
        }
        return true;
    }

    public boolean cameraSoiVaoTrongPhong(Room room, Camera camera) {
        return true;
    }

    public float theTichKhongGianPhong(Room room) {
        float tongTheTichVatTrongPhong = 0;

        for (DoVat doVat : room.getDanhSachDoVat())
            tongTheTichVatTrongPhong += doVat.theTich();

        return room.theTich() - tongTheTichVatTrongPhong;
    }

    public float theTichVungNhinThay(Room room, int x, int y, int z) {
        ToaDo dinhA = room.getCacDinh().get(Room.DINH_A);
        ToaDo dinhB = room.getCacDinh().get(Room.DINH_B);
        ToaDo dinhD = room.getCacDinh().get(Room.DINH_D);
        ToaDo dinhE = room.getCacDinh().get(Room.DINH_E);

        Vector v_AB = new Vector(dinhA, dinhB);
        Vector v_AD = new Vector(dinhA, dinhD);
        Vector v_AE = new Vector(dinhA, dinhE);
        
        Vector stepAB = v_AB.nhanFloat(1.0f / (x-1));
        Vector stepAD = v_AD.nhanFloat(1.0f / (y-1));
        Vector stepAE = v_AE.nhanFloat(1.0f / (z-1));

        int soLuongDiemXetDuyet = 0;
        int soLuongDiemNhinThay = 0;
        ToaDo toaDoDiemDangXet;
        for (int k = 0; k < z; k++) {
            for (int j = 0; j < y; j++) {
                for (int i = 0; i < x; i++) {
                    toaDoDiemDangXet = new ToaDo(dinhA);
                    
                    toaDoDiemDangXet.tinhTien(stepAB.nhanFloat(i));
                    toaDoDiemDangXet.tinhTien(stepAD.nhanFloat(j));
                    toaDoDiemDangXet.tinhTien(stepAE.nhanFloat(k));
                    
                    if (this.diemNamTrongDoVatNaoDo(room, toaDoDiemDangXet)) {
                        continue;
                    }
                    if (this.diemNamTrongVungNhinDuoc(room, toaDoDiemDangXet)) {
                        soLuongDiemNhinThay += 1;
                    }

                    soLuongDiemXetDuyet += 1;
                }
            }
        }

        return theTichKhongGianPhong(room) * (float) soLuongDiemNhinThay / (float) soLuongDiemXetDuyet;
    }

    public boolean diemNamTrongDoVatNaoDo(Room room, ToaDo toaDo) {
        DoVatService doVatService = new DoVatService();

        for (DoVat doVat : room.getDanhSachDoVat()) {
            if (doVatService.diemNamTrongDoVat(doVat, toaDo))
                return true;
        }

        return false;
    }

    public boolean diemNamTrongVungNhinDuoc(Room room, ToaDo toaDo) {
        List<Camera> dsCamera = this.danhSachCameraCoKhaNangNhinDuocDiem(room, toaDo);

        if (dsCamera.isEmpty())
            return false;

        List<DoVat> dsDoVat = room.getDanhSachDoVat();
        for (Camera camera : dsCamera) {
            DoanThang doanThangNoiCameraVoiDiem = new DoanThang(toaDo, camera.getToaDo());
            boolean stop = false;
            int i;
            for (i = 0; i < dsDoVat.size(); i++) {
                if (!doanThangNoiCameraVoiDiem
                        .giaoDiemVoiHinhHopChuNhat(dsDoVat.get(i)).isEmpty()) {
                    break;
                }
            }
            if (i == dsDoVat.size()) {
                return true;
            }
        }

        return false;
    }

    public List<Camera> danhSachCameraCoKhaNangNhinDuocDiem(Room room, ToaDo toaDo) {
        CameraService cameraService = new CameraService();
        List<Camera> result = new ArrayList<Camera>();
        for (Camera camera : room.getDanhSachCamera())
            if (cameraService.diemNamTrongVungNhinDuocCuaCamera(camera, toaDo)) {
                result.add(camera);
            }
        return result;
    }

    public void showRoomPicture(Room room, int phongTo) {
        if (room == null) return;

        RoomPicture roomPicture = new RoomPicture(room, phongTo);
        roomPicture.setVisible(true);
    }

    public void exportToImageFile(Room room, int phongTo, String fileName, String type) {
        // TO DO
    }
}
