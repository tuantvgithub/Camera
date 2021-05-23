package com.oop_lab.service;

import com.oop_lab.model.Camera;
import com.oop_lab.model.DoVat;
import com.oop_lab.model.Room;
import com.oop_lab.model.graphic2d.RoomDrawer;
import com.oop_lab.model.graphic2d.RoomPicture;
import com.oop_lab.model.khong_gian.*;

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

    public boolean roomLaHinhHopChuNhat(List<ToaDo> danhSachCacDinh) {
        return HinhHopChuNhat.hopLe(danhSachCacDinh);
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
        for (ToaDo toaDo : doVat.getCacDinh().values()) {
            if (toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_DAY_TREN_EFGH))
                + toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_DAY_DUOI_ABCD))
                    != room.getChieuCao()
            ) return false;
            if (toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_AEFB))
                + toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_CDHG))
                    != room.getChieuRong()
            ) return false;

            if(toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_BCGF))
                    + toaDo.khoangCachDenMatPhang(room.getCacMat().get(HinhHopChuNhat.MAT_ADHE))
                        != room.getChieuDai()
            ) return false;
        }
        return true;
    }

    public boolean doVatKhongChamTran(Room room, DoVat doVat) {
        return !(doVat.getCacMat()
                .get(HinhHopChuNhat.MAT_DAY_TREN_EFGH)
                .equals(room.getCacMat().get(HinhHopChuNhat.MAT_DAY_TREN_EFGH))
        );
    }

    public boolean doVatKhongBiVuong(Room room, DoVat doVat) {
        // TO DO
        return true;
    }

    public boolean doVatNamTrenSanHoacTrenVatKhac(Room room, DoVat doVat) {
        for (DoVat dv : room.getDanhSachDoVat()) {
            if (doVat.getCacMat().get(HinhHopChuNhat.MAT_DAY_DUOI_ABCD)
                .equals(dv.getCacMat().get(HinhHopChuNhat.MAT_DAY_TREN_EFGH))
            ) return new HinhChuNhat(
                    dv.getCacDinh().get(HinhHopChuNhat.DINH_A),
                    dv.getCacDinh().get(HinhHopChuNhat.DINH_B),
                    dv.getCacDinh().get(HinhHopChuNhat.DINH_C),
                    dv.getCacDinh().get(HinhHopChuNhat.DINH_D)
            ).chuaDiem(doVat.getTamDay());
        }

        return doVat.getCacMat().get(HinhHopChuNhat.MAT_DAY_DUOI_ABCD)
                .equals(room.getCacMat().get(HinhHopChuNhat.MAT_DAY_DUOI_ABCD));
    }

    public boolean themCamera(Room room, Camera camera) {
        if (!cameraNamTrenTuong(room, camera)) return false;
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

    public boolean cameraNamTrenTuong(Room room, Camera camera) {
        int c = 0;

        for (MatPhang matPhang : room.getDanhSachCacMat()) {
            if (matPhang.chuaDiem(camera.getToaDo())) {
                c += 1;
            }
        }

        return c == 1;
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

        if (dsCamera.isEmpty()) {
            return false;
        }

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

        RoomPicture roomPicture = new RoomPicture(new RoomDrawer(room, phongTo));
        roomPicture.setVisible(true);
    }

    public void exportToSVGFile(Room room, int phongTo, String fileName) {
        if (room == null) return;

        RoomPicture roomPicture = new RoomPicture(new RoomDrawer(room, phongTo));
        roomPicture.exportSVGFile(fileName);
    }
    
}
