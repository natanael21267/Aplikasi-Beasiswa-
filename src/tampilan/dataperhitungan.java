/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package tampilan;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import login.login;

/**
 *
 * @author Asus
 */
public class dataperhitungan extends javax.swing.JFrame {

    Connection conn = new koneksi().connect();

    DefaultTableModel modelShowVektorS;
    DefaultTableModel modelShowVektorV;
    DefaultTableModel modelShowRangking;
    DefaultTableModel modelShowNilai;

    public dataperhitungan() {
        initComponents();
        this.setLocationRelativeTo(null);
        showTableSiswa();
        showTableBobotKriteria();
        showTableVektorS();
        showTableVektorV();
        showTableRanking();
    }

    protected void showTableSiswa() {
        Object[] baris = {"No", "Kode Alternatif", "NISN", "Nama", "C1", "C2", "C3", "C4", "C5"};
        modelShowNilai = new DefaultTableModel(null, baris);
        jTable1.setModel(modelShowNilai);
        String query = "SELECT siswa.kd_alternatif, siswa.NISN, siswa.Nama, penilaian.C1, penilaian.C2, penilaian.C3, penilaian.C4, penilaian.C5 FROM penilaian LEFT JOIN siswa ON penilaian.kd_alternatif = siswa.kd_alternatif ORDER BY penilaian.kd_alternatif";
        try (Statement st = conn.createStatement(); ResultSet hasil = st.executeQuery(query)) {
            int no = 1;
            while (hasil.next()) {
                modelShowNilai.addRow(new Object[]{
                    no++,
                    hasil.getString("kd_alternatif"),
                    hasil.getString("NISN"),
                    hasil.getString("Nama"),
                    hasil.getFloat("C1"),
                    hasil.getFloat("C2"),
                    hasil.getFloat("C3"),
                    hasil.getFloat("C4"),
                    hasil.getFloat("C5")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data siswa: " + e.getMessage());
        }
    }

    protected void showTableBobotKriteria() {
        Object[] kolom = {"Kode Kriteria", "Nama Kriteria", "Bobot"};
        DefaultTableModel modelBobot = new DefaultTableModel(null, kolom);
        jTable2.setModel(modelBobot);

        String sql = "SELECT kode_kriteria, nama_kriteria, bobot_kriteria FROM kriteria ORDER BY kode_kriteria";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] data = {
                    rs.getString("kode_kriteria"),
                    rs.getString("nama_kriteria"),
                    rs.getDouble("bobot_kriteria")
                };
                modelBobot.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data bobot kriteria: " + e.getMessage());
        }
    }

    protected void showTableVektorS() {
        Object[] baris = {"Kode Alternatif", "Total", "NC1", "NC2", "NC3", "NC4", "NC5"};
        modelShowVektorS = new DefaultTableModel(null, baris);
        jTable3.setModel(modelShowVektorS);
    }

    protected void showTableVektorV() {
        Object[] baris = {"Kode Alternatif", "Vektor V"};
        modelShowVektorV = new DefaultTableModel(null, baris);
        jTable4.setModel(modelShowVektorV);
    }

    protected void showTableRanking() {
        Object[] baris = {"Kode Alternatif", "Vektor V", "Ranking"};
        modelShowRangking = new DefaultTableModel(null, baris);
        jTable5.setModel(modelShowRangking);
    }

    public void normalisasiVektorS() {
        try {
            if (jTable2.getRowCount() < 5) {
                JOptionPane.showMessageDialog(this, "Data bobot tidak lengkap (kurang dari 5 baris).");
                return;
            }

            float[] bobot = new float[5];

            for (int i = 0; i < 5; i++) {
                Object val = jTable2.getValueAt(i, 2);
                if (val == null || val.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nilai bobot pada baris ke-" + (i + 1) + " kosong.");
                    return;
                }

                try {
                    bobot[i] = Float.parseFloat(val.toString());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Bobot di baris ke-" + (i + 1) + " tidak valid (bukan angka).");
                    return;
                }
            }

            modelShowVektorS.setRowCount(0);

            for (int i = 0; i < modelShowNilai.getRowCount(); i++) {
                String kd = modelShowNilai.getValueAt(i, 1).toString();

                float[] nilai = new float[5];
                for (int j = 0; j < 5; j++) {
                    try {
                        nilai[j] = Float.parseFloat(modelShowNilai.getValueAt(i, 4 + j).toString());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Nilai C" + (j + 1) + " pada baris ke-" + (i + 1) + " tidak valid.");
                        return;
                    }
                }

                float[] norm = new float[5];
                float total = 1f;
                for (int j = 0; j < 5; j++) {
                    norm[j] = (float) Math.pow(nilai[j], bobot[j]);
                    total *= norm[j];
                }

                modelShowVektorS.addRow(new Object[]{kd, total, norm[0], norm[1], norm[2], norm[3], norm[4]});
            }

            JOptionPane.showMessageDialog(this, "Normalisasi vektor S berhasil dihitung.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error normalisasi vektor S: " + e.getMessage());
        }
    }

    public void HitungVektorV() {
        try {
            if (modelShowVektorS.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Silakan hitung vektor S terlebih dahulu.");
                return;
            }

            float totalS = 0f;
            for (int i = 0; i < modelShowVektorS.getRowCount(); i++) {
                totalS += Float.parseFloat(modelShowVektorS.getValueAt(i, 1).toString());
            }

            modelShowVektorV.setRowCount(0);

            for (int i = 0; i < modelShowVektorS.getRowCount(); i++) {
                String kd = modelShowVektorS.getValueAt(i, 0).toString();
                float s = Float.parseFloat(modelShowVektorS.getValueAt(i, 1).toString());
                float v = s / totalS;
                modelShowVektorV.addRow(new Object[]{kd, v});
            }

            JOptionPane.showMessageDialog(this, "Vektor V berhasil dihitung.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Hitung Vektor V: " + e.getMessage());
        }
    }

    public void Ranking() {
        try {
            if (modelShowVektorV.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Silakan hitung vektor V terlebih dahulu.");
                return;
            }

            Map<String, Float> vektorV = new HashMap<>();
            for (int i = 0; i < modelShowVektorV.getRowCount(); i++) {
                String kd = modelShowVektorV.getValueAt(i, 0).toString();
                float v = Float.parseFloat(modelShowVektorV.getValueAt(i, 1).toString());
                vektorV.put(kd, v);
            }

            List<Map.Entry<String, Float>> list = new ArrayList<>(vektorV.entrySet());
            list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            modelShowRangking.setRowCount(0);
            int rank = 1;
            for (Map.Entry<String, Float> entry : list) {
                modelShowRangking.addRow(new Object[]{entry.getKey(), entry.getValue(), rank++});
            }

            JOptionPane.showMessageDialog(this, "Proses ranking berhasil.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saat proses ranking: " + e.getMessage());
        }
    }

    public void simpanVektor() {
        try {
        // Hapus isi tabel sebelumnya agar tidak dobel
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM vektors");
        stmt.executeUpdate("DELETE FROM rangking");

        // Simpan ke tabel 'vektors'
        for (int i = 0; i < modelShowVektorV.getRowCount(); i++) {
            String kd = modelShowVektorV.getValueAt(i, 0).toString();
            double s = Double.parseDouble(modelShowVektorS.getValueAt(i, 1).toString());
            double v = Double.parseDouble(modelShowVektorV.getValueAt(i, 1).toString());

            String sql = "INSERT INTO vektors (kd_alternatif, vektor_s, vektor_v) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kd);
            pst.setDouble(2, s);
            pst.setDouble(3, v);
            pst.executeUpdate();
        }

        // Simpan ke tabel 'rangking'
        for (int i = 0; i < modelShowRangking.getRowCount(); i++) {
    String kd = modelShowRangking.getValueAt(i, 0).toString();
    String nama = cariNamaDariKode(kd); // ambil nama dari tabel siswa
    double v = Double.parseDouble(modelShowRangking.getValueAt(i, 1).toString());
    int rank = Integer.parseInt(modelShowRangking.getValueAt(i, 2).toString());

    String sql = "INSERT INTO rangking (kd_alternatif, nama, vektor_v, ranking) VALUES (?, ?, ?, ?)";
    PreparedStatement pst = conn.prepareStatement(sql);
    pst.setString(1, kd);
    pst.setString(2, nama);
    pst.setDouble(3, v);
    pst.setInt(4, rank);
    pst.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Data vektor dan ranking berhasil disimpan.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal simpan data: " + e.getMessage());
    }    }
  public String cariNamaDariKode(String kd) {
        for (int i = 0; i < modelShowNilai.getRowCount(); i++) {
            if (modelShowNilai.getValueAt(i, 1).toString().equals(kd)) {
                return modelShowNilai.getValueAt(i, 3).toString();
            }
        }
        return "";
    }
    public void reset() {
        modelShowVektorS.setRowCount(0);
        modelShowVektorV.setRowCount(0);
        modelShowRangking.setRowCount(0);
        modelShowNilai.setRowCount(0);
        showTableSiswa();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jpFrame = new javax.swing.JPanel();
        jpAtas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jpKiri = new javax.swing.JPanel();
        jbHome = new javax.swing.JButton();
        jbDataSiswa = new javax.swing.JButton();
        jbDataKriteria = new javax.swing.JButton();
        jbDataPen = new javax.swing.JButton();
        jbDataPer = new javax.swing.JButton();
        jbLogout = new javax.swing.JButton();
        jbLaporan = new javax.swing.JButton();
        jpKanan = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnCariVektorS = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        btnCariRanking = new javax.swing.JButton();
        btnCariVektorV = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));

        jpFrame.setBackground(new java.awt.Color(0, 102, 102));
        jpFrame.setPreferredSize(new java.awt.Dimension(1000, 650));

        jpAtas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MI Nurul Huda");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/WhatsApp Image 2025-07-20 at 21.22.03.jpeg"))); // NOI18N

        javax.swing.GroupLayout jpAtasLayout = new javax.swing.GroupLayout(jpAtas);
        jpAtas.setLayout(jpAtasLayout);
        jpAtasLayout.setHorizontalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addGap(63, 63, 63)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpAtasLayout.setVerticalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(14, 14, 14))
        );

        jpKiri.setBackground(new java.awt.Color(0, 153, 153));
        jpKiri.setPreferredSize(new java.awt.Dimension(100, 400));

        jbHome.setText("Home");
        jbHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHomeActionPerformed(evt);
            }
        });

        jbDataSiswa.setText("Data Siswa");
        jbDataSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDataSiswaActionPerformed(evt);
            }
        });

        jbDataKriteria.setText("Data Kriteria");
        jbDataKriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDataKriteriaActionPerformed(evt);
            }
        });

        jbDataPen.setText("Data Penilaian");
        jbDataPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDataPenActionPerformed(evt);
            }
        });

        jbDataPer.setText("Data Perhitungan");
        jbDataPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDataPerActionPerformed(evt);
            }
        });

        jbLogout.setText("Logout");
        jbLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLogoutActionPerformed(evt);
            }
        });

        jbLaporan.setText("Laporan");
        jbLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpKiriLayout = new javax.swing.GroupLayout(jpKiri);
        jpKiri.setLayout(jpKiriLayout);
        jpKiriLayout.setHorizontalGroup(
            jpKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpKiriLayout.createSequentialGroup()
                .addGroup(jpKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpKiriLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbDataSiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbDataKriteria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbDataPen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jpKiriLayout.createSequentialGroup()
                                .addComponent(jbDataPer)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jpKiriLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jbLogout)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpKiriLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpKiriLayout.setVerticalGroup(
            jpKiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpKiriLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jbHome)
                .addGap(18, 18, 18)
                .addComponent(jbDataSiswa)
                .addGap(18, 18, 18)
                .addComponent(jbDataKriteria)
                .addGap(18, 18, 18)
                .addComponent(jbDataPen)
                .addGap(18, 18, 18)
                .addComponent(jbDataPer)
                .addGap(18, 18, 18)
                .addComponent(jbLaporan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addComponent(jbLogout)
                .addGap(16, 16, 16))
        );

        jpKanan.setBackground(new java.awt.Color(0, 204, 204));
        jpKanan.setPreferredSize(new java.awt.Dimension(600, 390));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(837, 518));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("~ Data Perhitungan ~");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        btnCariVektorS.setText("Hitung Vektor S");
        btnCariVektorS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariVektorSActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Bobot");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        btnCariRanking.setText("Ranking");
        btnCariRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariRankingActionPerformed(evt);
            }
        });

        btnCariVektorV.setText("Hitung Vektor V");
        btnCariVektorV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariVektorVActionPerformed(evt);
            }
        });

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        btnSimpan.setText("Simpan Perhitungan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan)
                            .addComponent(btnReset)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariVektorS)
                            .addComponent(btnCariRanking))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .addComponent(btnCariVektorV)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(344, 344, 344)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCariVektorS)
                    .addComponent(btnCariVektorV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSimpan)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCariRanking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))))
        );

        javax.swing.GroupLayout jpKananLayout = new javax.swing.GroupLayout(jpKanan);
        jpKanan.setLayout(jpKananLayout);
        jpKananLayout.setHorizontalGroup(
            jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 837, Short.MAX_VALUE)
            .addGroup(jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpKananLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jpKananLayout.setVerticalGroup(
            jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 518, Short.MAX_VALUE)
            .addGroup(jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpKananLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jpFrameLayout = new javax.swing.GroupLayout(jpFrame);
        jpFrame.setLayout(jpFrameLayout);
        jpFrameLayout.setHorizontalGroup(
            jpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jpFrameLayout.createSequentialGroup()
                        .addComponent(jpKiri, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpKanan, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpAtas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpFrameLayout.setVerticalGroup(
            jpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFrameLayout.createSequentialGroup()
                .addComponent(jpAtas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jpFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpKiri, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(jpKanan, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jpFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jpFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHomeActionPerformed
        // TODO add your handling code here:
        new menu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbHomeActionPerformed

    private void jbDataSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDataSiswaActionPerformed
        // TODO add your handling code here:
        new datasiswa().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbDataSiswaActionPerformed

    private void jbDataKriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDataKriteriaActionPerformed
        // TODO add your handling code here:
        new datakriteria().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbDataKriteriaActionPerformed

    private void jbDataPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDataPenActionPerformed
        // TODO add your handling code here:
        new datapenilaian().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbDataPenActionPerformed

    private void jbDataPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDataPerActionPerformed
        // TODO add your handling code here:
        new dataperhitungan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbDataPerActionPerformed

    private void jbLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLogoutActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin Logout?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            login login = new login();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jbLogoutActionPerformed

    private void jbLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLaporanActionPerformed
        // TODO add your handling code here:
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbLaporanActionPerformed

    private void btnCariVektorSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariVektorSActionPerformed
        // TODO add your handling code here:
        normalisasiVektorS();
    }//GEN-LAST:event_btnCariVektorSActionPerformed

    private void btnCariVektorVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariVektorVActionPerformed
        // TODO add your handling code here:
        HitungVektorV();
    }//GEN-LAST:event_btnCariVektorVActionPerformed

    private void btnCariRankingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariRankingActionPerformed
        // TODO add your handling code here:
        Ranking();
    }//GEN-LAST:event_btnCariRankingActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpanVektor();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dataperhitungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dataperhitungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dataperhitungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dataperhitungan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dataperhitungan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariRanking;
    private javax.swing.JButton btnCariVektorS;
    private javax.swing.JButton btnCariVektorV;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JButton jbDataKriteria;
    private javax.swing.JButton jbDataPen;
    private javax.swing.JButton jbDataPer;
    private javax.swing.JButton jbDataSiswa;
    private javax.swing.JButton jbHome;
    private javax.swing.JButton jbLaporan;
    private javax.swing.JButton jbLogout;
    private javax.swing.JPanel jpAtas;
    private javax.swing.JPanel jpFrame;
    private javax.swing.JPanel jpKanan;
    private javax.swing.JPanel jpKiri;
    // End of variables declaration//GEN-END:variables
}
