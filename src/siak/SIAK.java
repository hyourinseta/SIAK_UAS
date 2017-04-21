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
            System.out.println("3. Input Nilai Mahasiswa");
            System.out.print("\nMasukkan pilihan : ");
            String pilihanutama = in.nextLine();
            switch(Integer.parseInt(pilihanutama)) {
                case 1:
                    getMenuMahasiswa(service);
                    break;
                case 2:
                    getMenuMatkul();
                    break;
                case 3:
                    getMenuNilai();
                    break;
                default:
                    break;
            }
            
        }
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
                default:
                    break;
            }

        }
    }
    
    public static void getMenuMatkul() {
        
    }
    
    public static void getMenuNilai() {
        
    }
    
}
