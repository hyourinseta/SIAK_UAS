/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siak;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.List;
import java.util.Scanner;
import model.Mahasiswa;
import model.Matkul;
import model.Nilai;
import service.ServiceJdbc;

/**
 *
 * @author gugi
 */
public class SIAK {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setDatabaseName("siak?serverTimezone=UTC");
        dataSource.setServerName("localhost");

        dataSource.setPortNumber(3306);

        ServiceJdbc service = new ServiceJdbc();
        service.setDataSource(dataSource);
        
        Scanner in = new Scanner(System.in);
        System.out.println("Selamat datang di Sistem Informasi Akademik");
        Boolean mainmenu = true;
        while (mainmenu) {
            System.out.println("\nMenu Utama");
            System.out.println("\n1. Pengelolaan Mahasiswa");
            System.out.println("2. Pengelolaan Mata Kuliah");
            System.out.println("3. Pengelolaan Nilai Mahasiswa");
            System.out.println("\n0. Keluar");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanutama = in.nextLine();
            switch(Integer.parseInt(pilihanutama)) {
                case 1:
                    getMenuMahasiswa(service);
                    break;
                case 2:
                    getMenuMatkul(service);
                    break;
                case 3:
                    getMenuNilai(service);
                    break;
                case 0:
                    mainmenu = false;
                    break;
                default:
                    break;
            }
            
        }
        System.out.println("\nTerima kasih telah menggunakan Sistem Informasi Akademik");
    }
    
    public static void getMenuMahasiswa(ServiceJdbc service) {
        Boolean mhsmenu = true;
        Scanner in = new Scanner(System.in);
        while (mhsmenu) {
            System.out.println("\nMenu Pengelolaan Mahasiswa");
            System.out.println("\n1. Lihat Daftar Mahasiswa");
            System.out.println("2. Tambah Data Mahasiswa");
            System.out.println("3. Ubah Data Mahasiswa");
            System.out.println("4. Hapus Data Mahasiswa");
            System.out.println("\n0. Kembali ke Menu Utama");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanmhs = in.nextLine();
            switch (Integer.parseInt(pilihanmhs)) {
                case 1:
                    List<Mahasiswa> mhsR = service.getAllMahasiswa();
                    if (mhsR.isEmpty()) {
                        System.out.println("\nBelum ada mahasiswa yang terdaftar!");
                        break;
                    }
                    System.out.println("NIM \t | Nama \t\t\t | Tanggal Lahir | Alamat \t\t\t | No. HP");
                    System.out.println("=============================================================================================");
                    for (Mahasiswa mhs : mhsR) {
                        System.out.println(mhs.getNim() + "\t | " + mhs.getNama() + " \t | " + mhs.getTgllahir() + " | "+ mhs.getAlamat() + "\t\t | " + mhs.getNohp());
                    }
                    break;
                case 2:
                    System.out.print("NIM : ");
                    String npm = in.nextLine();
                    System.out.print("Nama : ");                    
                    String nama = in.nextLine();
                    System.out.print("Tanggal Lahir (YYYY-MM-DD) : ");                    
                    String tgllahir = in.nextLine();
                    System.out.print("Alamat : ");
                    String alamat = in.nextLine();
                    System.out.print("No. HP : ");
                    String nohp = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah = in.nextLine();
                    if (tambah.toLowerCase().equals("y")) {
                        Mahasiswa mhs = new Mahasiswa();
                        mhs.setNim(Integer.parseInt(npm));
                        mhs.setNama(nama);
                        mhs.setTgllahir(tgllahir);
                        mhs.setAlamat(alamat);
                        mhs.setNohp(nohp);
                        service.save(mhs);
                    }
                    break;
                case 3:
                    System.out.print("Masukkan NIM : ");
                    String nim_x = in.nextLine();
                    Mahasiswa mhs_x = service.getMahasiswa(Integer.parseInt(nim_x));
                    if (mhs_x == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM " + nim_x);
                        break;
                    }
                    System.out.print("Nama : ");                    
                    String nama_x = in.nextLine();
                    System.out.print("Tanggal Lahir (YYYY-MM-DD) : ");                    
                    String tgllahir_x = in.nextLine();
                    System.out.print("Alamat : ");
                    String alamat_x = in.nextLine();
                    System.out.print("No. HP : ");
                    String nohp_x = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah_x = in.nextLine();
                    if (tambah_x.toLowerCase().equals("y")) {
                        mhs_x.setNim(Integer.parseInt(nim_x));
                        mhs_x.setNama(nama_x);
                        mhs_x.setTgllahir(tgllahir_x);
                        mhs_x.setAlamat(alamat_x);
                        mhs_x.setNohp(nohp_x);
                        service.update(mhs_x);
                    }
                    break;
                case 4:
                    System.out.print("Masukkan NIM : ");
                    String npm_d = in.nextLine();
                    Mahasiswa mhs_d = service.getMahasiswa(Integer.parseInt(npm_d));
                    if (mhs_d == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM "+npm_d);
                        break;
                    }
                    System.out.print("Hapus? (Y/N) : ");
                    String hapus = in.nextLine();
                    if (hapus.toLowerCase().equals("y")) {
                        service.delete(mhs_d);
                    }
                    break;
                case 0:
                    mhsmenu = false;
                default:
                    break;
            }

        }
    }
    
    public static void getMenuMatkul(ServiceJdbc service) {
        Scanner in = new Scanner(System.in);
        Boolean active = true;
        while (active) {
            System.out.println("\nMenu Pengelolaan Mata Kuliah : \n");
            System.out.println("1. Lihat Daftar Mata Kuliah");
            System.out.println("2. Tambah Data Mata Kuliah");
            System.out.println("3. Ubah Data Mata Kuliah");
            System.out.println("4. Hapus Data Mata Kuliah");
            System.out.println("\n0. Kembali ke Menu Utama");

            System.out.print("\nPilihan : ");
            String pilih = in.nextLine();
            switch (pilih) {
                case "1":
                    List<Matkul> matkulR = service.getAllMatkul();
                    if (matkulR.isEmpty()) {
                        System.out.println("\nBelum ada mata kuliah yang terdaftar. Silakan tambahkan mata kuliah terlebih dahulu!");
                    } else {
                        System.out.println("\nKode Mata Kuliah\t | Nama Mata Kuliah\t | Jumlah SKS");
                        System.out.println("--------------------------------------------------------------------------------------");
                        for (Matkul matkul : matkulR) {
                            System.out.println(matkul.getKd_mk()+ "\t\t | " + matkul.getNama_mk()+ "\t| " + matkul.getSks());
                        }
                    }
                    break;
                case "2":
                    System.out.print("Nama Mata Kuliah : ");
                    String nama_mk = in.nextLine();
                    System.out.print("Jumlah SKS : ");
                    String sks = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah = in.nextLine();
                    if (tambah.toLowerCase().equals("y")) {
                        Matkul buku = new Matkul();
                        buku.setNama_mk(nama_mk);
                        buku.setSks(Integer.parseInt(sks));
                        service.save(buku);
                    }
                    break;
                case "3":
                    System.out.print("Masukkan ID Mata Kuliah : ");
                    String id_matkul_x = in.nextLine();
                    Matkul matkul_x = service.getMatkul(Integer.parseInt(id_matkul_x));
                    if (matkul_x == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan ID " + id_matkul_x);
                        break;
                    }
                    System.out.print("Nama Mata Kuliah : ");
                    String nama_mk_x = in.nextLine();
                    System.out.print("Jumlah SKS : ");
                    String sks_x = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String ubah_x = in.nextLine();
                    if (ubah_x.toLowerCase().equals("y")) {
                        matkul_x.setKd_mk(Integer.parseInt(id_matkul_x));
                        matkul_x.setNama_mk(nama_mk_x);
                        matkul_x.setSks(Integer.parseInt(sks_x));
                        service.save(matkul_x);
                    }
                    break;
                case "4":
                    System.out.print("Masukkan ID Mata Kuliah : ");
                    String id_matkul_d = in.nextLine();
                    Matkul matkul_d = service.getMatkul(Integer.parseInt(id_matkul_d));
                    if (matkul_d == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan ID " + id_matkul_d);
                        break;
                    }
                    System.out.print("Hapus? (Y/N) : ");
                    String hapus = in.nextLine();
                    if (hapus.toLowerCase().equals("y")) {
                        service.delete(matkul_d);
                    }
                    break;
                case "0":
                    active = false;
                    break;
                default:
                    break;
            }
        }
    }
    
    public static void getMenuNilai(ServiceJdbc service) {
        Boolean nilaimenu = true;
        Scanner in = new Scanner(System.in);
        while (nilaimenu) {
            System.out.println("\nMenu Pengelolaan Nilai");
            System.out.println("\n1. Lihat Nilai Berdasarkan Mahasiswa");
            System.out.println("2. Lihat Nilai Berdasarkan Mata Kuliah");
            System.out.println("3. Input Nilai");
            System.out.println("4. Ubah Nilai");
            System.out.println("5. Hapus Nilai");
            System.out.println("\n0. Kembali ke Menu Utama");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanmhs = in.nextLine();
            switch (Integer.parseInt(pilihanmhs)) {
                case 1:
                    System.out.print("\nSilakan masukkan NIM : ");
                    String nim_x = in.nextLine();
                    Mahasiswa mhs_x = service.getMahasiswa(Integer.parseInt(nim_x));
                    if (mhs_x == null) {
                        System.out.println("\nTidak ada Mahasiswa dengan NIM tersebut!");
                        break;
                    }
                    System.out.println("\nNIM \t\t\t : " + mhs_x.getNim());
                    System.out.println("Nama Mahasiswa \t : " + mhs_x.getNama());
                    List<Nilai> nilaiR = service.getAllNilai(Integer.parseInt(nim_x), 0);
                    System.out.println("\nMata Kuliah \t\t\t | Nilai");
                    System.out.println("====================================================");
                    for (Nilai nilai : nilaiR) {
                        System.out.println(nilai.getNama_mk()+ " \t | "+ nilai.getNilai());
                    }
                    break;
                case 2:
                    System.out.print("\nSilakan masukkan Kode Mata Kuliah : ");
                    String kode_mk_x = in.nextLine();
                    Matkul matkul_x = service.getMatkul(Integer.parseInt(kode_mk_x));
                    if (matkul_x == null) {
                        System.out.println("\nTidak ada Mahasiswa dengan NIM tersebut!");
                        break;
                    }
                    System.out.println("\nKode Mata Kuliah \t\t\t : " + matkul_x.getKd_mk());
                    System.out.println("Nama Mata Kuliah \t : " + matkul_x.getNama_mk());
                    List<Nilai> nilai_M = service.getAllNilai(0, Integer.parseInt(kode_mk_x));
                    System.out.println("\nNIM\t | Nama Mahasiswa \t\t | Nilai");
                    System.out.println("============================================================================");
                    for (Nilai nilai : nilai_M) {
                        System.out.println(nilai.getNim() + "\t | " + nilai.getNama_mhs()+ " \t\t | "+ nilai.getNilai());
                    }
                    break;
                case 3:
                    System.out.print("\nMasukkan NIM : ");
                    String nim_n = in.nextLine();
                    Mahasiswa mhs_n = service.getMahasiswa(Integer.parseInt(nim_n));
                    if (mhs_n == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM " + nim_n);
                        break;
                    }
                    System.out.println("Nama Mahasiswa : "+mhs_n.getNama());
                    System.out.print("Masukkan Kode Mata Kuliah : ");
                    String kode_mk_n = in.nextLine();
                    Matkul matkul_n = service.getMatkul(Integer.parseInt(kode_mk_n));
                    if (matkul_n == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan kode mata kuliah " + kode_mk_n);
                        break;
                    }
                    System.out.println("Nama Mata Kuliah : "+matkul_n.getNama_mk());
                    System.out.print("Nilai : ");                    
                    String nilai_b = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah_x = in.nextLine();
                    if (tambah_x.toLowerCase().equals("y")) {
                        Nilai nilai_n = new Nilai();
                        nilai_n.setNim(mhs_n.getNim());
                        nilai_n.setKode_mk(matkul_n.getKd_mk());

                        if (Integer.parseInt(nilai_b) > 80) {
                            nilai_n.setNilai("A");
                        } else if (Integer.parseInt(nilai_b) >= 70) {
                            nilai_n.setNilai("B");
                        } else if (Integer.parseInt(nilai_b) >= 60) {
                            nilai_n.setNilai("C");
                        } else if (Integer.parseInt(nilai_b) >= 50) {
                            nilai_n.setNilai("D");
                        } else {
                            nilai_n.setNilai("E");
                        }
                        service.save(nilai_n);
                    }
                    break;
                case 4:
                    System.out.print("\nMasukkan NIM : ");
                    String nim_u = in.nextLine();
                    Mahasiswa mhs_u = service.getMahasiswa(Integer.parseInt(nim_u));
                    if (mhs_u == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM " + nim_u);
                        break;
                    }
                    System.out.println("Nama Mahasiswa : "+mhs_u.getNama());
                    System.out.print("Masukkan Kode Mata Kuliah : ");
                    String kode_mk_u = in.nextLine();
                    Matkul matkul_u = service.getMatkul(Integer.parseInt(kode_mk_u));
                    if (matkul_u == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan kode mata kuliah " + kode_mk_u);
                        break;
                    }
                    System.out.println("Nama Mata Kuliah : "+matkul_u.getNama_mk());
                    Nilai nilai_u = service.getNilai(mhs_u.getNim(), matkul_u.getKd_mk());
                    System.out.print("Nilai : ");                    
                    String nilai_c = in.nextLine();
                    System.out.print("Simpan? (Y/N) : ");
                    String tambah_u = in.nextLine();
                    if (tambah_u.toLowerCase().equals("y")) {
                        if (Integer.parseInt(nilai_c) > 80) {
                            nilai_u.setNilai("A");
                        } else if (Integer.parseInt(nilai_c) >= 70) {
                            nilai_u.setNilai("B");
                        } else if (Integer.parseInt(nilai_c) >= 60) {
                            nilai_u.setNilai("C");
                        } else if (Integer.parseInt(nilai_c) >= 50) {
                            nilai_u.setNilai("D");
                        } else {
                            nilai_u.setNilai("E");
                        }
                        service.update(nilai_u);
                    }
                    break;
                case 5:
                    System.out.print("\nMasukkan NIM : ");
                    String nim_d = in.nextLine();
                    Mahasiswa mhs_d = service.getMahasiswa(Integer.parseInt(nim_d));
                    if (mhs_d == null) {
                        System.out.println("Tidak ditemukan mahasiswa dengan NIM " + nim_d);
                        break;
                    }
                    System.out.println("Nama Mahasiswa : "+mhs_d.getNama());
                    System.out.print("Masukkan Kode Mata Kuliah : ");
                    String kode_mk_d = in.nextLine();
                    Matkul matkul_d = service.getMatkul(Integer.parseInt(kode_mk_d));
                    if (matkul_d == null) {
                        System.out.println("Tidak ditemukan mata kuliah dengan kode mata kuliah " + kode_mk_d);
                        break;
                    }
                    System.out.println("Nama Mata Kuliah : "+matkul_d.getNama_mk());
                    Nilai nilai_d = service.getNilai(mhs_d.getNim(), matkul_d.getKd_mk());
                    if (nilai_d == null) {
                        System.out.println("Tidak ditemukan nilai mata kuliah "+matkul_d.getNama_mk()+" untuk "+mhs_d.getNama());
                        break;
                    }
                    System.out.print("Hapus? (Y/N) : ");
                    String hapus = in.nextLine();
                    if (hapus.toLowerCase().equals("y")) {
                        service.delete(nilai_d);
                    }
                    break;
                case 0:
                    nilaimenu = false;
                    break;
                default:
                    break;
            }

        }
    }
    
}
