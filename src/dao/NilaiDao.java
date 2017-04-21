/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Nilai;

/**
 *
 * @author gugi
 */
public class NilaiDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getNilaiByNimStatement;
    private PreparedStatement getNilaiByKodemkStatement;

    private final String insertQuery = "insert into nilai(nim,kode_mk,nilai) "
            + " values(?,?,?)";
    private final String updateQuery = "update nilai set nilai=?, "
            + " where nim=? AND kode_mk=?";
    private final String deleteQuery = "delete from nilai where nim=? AND kode_mk=?";
    private final String getByIdQuery = "SELECT * FROM nilai JOIN mahasiswa ON nilai.nim = mahasiswa.nim JOIN matakuliah ON nilai.kode_mk = matakuliah.kode_mk WHERE nilai.nim=? AND nilai.kode_mk=?";
    private final String getNilaiByNimQuery = "SELECT * FROM nilai JOIN mahasiswa ON nilai.nim = mahasiswa.nim JOIN matakuliah ON nilai.kode_mk = matakuliah.kode_mk WHERE nilai.nim=?";
    private final String getNilaiByKodemkQuery = "SELECT * FROM nilai JOIN mahasiswa ON nilai.nim = mahasiswa.nim JOIN matakuliah ON nilai.kode_mk = matakuliah.kode_mk WHERE nilai.kode_mk=?";
    private final String getAllQuery = "select * from nilai";

    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        insertStatement = this.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        updateStatement = this.connection.prepareStatement(updateQuery);
        deleteStatement = this.connection.prepareStatement(deleteQuery);
        getByIdStatement = this.connection.prepareStatement(getByIdQuery);
        getAllStatement = this.connection.prepareStatement(getAllQuery);
        getNilaiByNimStatement = this.connection.prepareStatement(getNilaiByNimQuery);
        getNilaiByKodemkStatement = this.connection.prepareStatement(getNilaiByKodemkQuery);
    }
    
    public Nilai save(Nilai nilai) throws SQLException{
        insertStatement.setInt(1, nilai.getNim());
        insertStatement.setInt(2, nilai.getKode_mk());
        insertStatement.setString(3, nilai.getNilai());
        insertStatement.executeUpdate();
        return nilai;
    }
    
    public Nilai update(Nilai nilai) throws SQLException {
        updateStatement.setString(1, nilai.getNilai());
        updateStatement.setInt(2, nilai.getNim());
        updateStatement.setInt(3, nilai.getKode_mk());
        updateStatement.executeUpdate();
        return nilai;
    }
    
    public Nilai delete(Nilai nilai) throws SQLException {
        deleteStatement.setInt(1, nilai.getNim());
        deleteStatement.executeUpdate();
        return nilai;
    }
    
    public Nilai getByNimKodemk(int nim, int kode_mk) throws SQLException{
        getByIdStatement.setInt(1, nim);
        getByIdStatement.setInt(2, kode_mk);
        ResultSet rs = getByIdStatement.executeQuery();
        //proses mapping dari relational ke object
        if (rs.next()) {
            Nilai nilai = new Nilai();
            nilai.setNim(rs.getInt("nim"));
            nilai.setNama_mhs(rs.getString("nama"));
            nilai.setKode_mk(rs.getInt("kode_mk"));
            nilai.setNama_mk(rs.getString("nama_mk"));
            nilai.setNilai(rs.getString("nilai"));
            return nilai;
        }
        return null;
    }
    
    public List<Nilai> getNilaiByNim() throws SQLException{
        List<Nilai> nilaiR = new ArrayList<>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()){
            Nilai nilai = new Nilai();
            nilai.setNim(rs.getInt("nim"));
            nilai.setNama_mhs(rs.getString("nama"));
            nilai.setKode_mk(rs.getInt("kode_mk"));
            nilai.setNama_mk(rs.getString("nama_mk"));
            nilai.setNilai(rs.getString("nilai"));
            nilaiR.add(nilai);
        }
        return nilaiR;
    }
    
    public List<Nilai> getNilaiByKodemk() throws SQLException{
        List<Nilai> nilaiR = new ArrayList<>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()){
            Nilai nilai = new Nilai();
            nilai.setNim(rs.getInt("nilai.nim"));
            nilai.setNama_mhs(rs.getString("mahasiswa.nama"));
            nilai.setKode_mk(rs.getInt("nilai.kode_mk"));
            nilai.setNama_mk(rs.getString("matakuliah.nama_mk"));
            nilai.setNilai(rs.getString("nilai"));
            nilaiR.add(nilai);
        }
        return nilaiR;
    }
    
    public List<Nilai> getAll(int nim, int kode_mk) throws SQLException{
        List<Nilai> nilaiR = new ArrayList<>();
        ResultSet rs;
        if (nim != 0 && kode_mk == 0) {
            getNilaiByNimStatement.setInt(1, nim);
            rs = getNilaiByNimStatement.executeQuery();
        } else if (nim == 0 && kode_mk != 0) {
            getNilaiByKodemkStatement.setInt(1, kode_mk);
            rs = getNilaiByKodemkStatement.executeQuery();
        } else {
            rs = getAllStatement.executeQuery();
        }
        
        while(rs.next()){
            Nilai nilai = new Nilai();
            nilai.setNim(rs.getInt("nilai.nim"));
            nilai.setNama_mhs(rs.getString("mahasiswa.nama"));
            nilai.setKode_mk(rs.getInt("nilai.kode_mk"));
            nilai.setNama_mk(rs.getString("matakuliah.nama_mk"));
            nilai.setNilai(rs.getString("nilai"));
            nilaiR.add(nilai);
        }
        return nilaiR;
    }
}
