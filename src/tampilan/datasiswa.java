/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampilan;

/**
 *
 * @author Asus
 */
import com.toedter.calendar.JDateChooser;
import static com.sun.webkit.perf.WCFontPerfLogger.reset;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.login;

public class datasiswa extends javax.swing.JFrame {

    Connection conn;
    Statement stmt;
    ResultSet rs;

    public datasiswa() {
        initComponents();
        koneksiDatabase();
        showTable();

        jDateChooser.setDateFormatString("dd-MM-yyyy");
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rbLaki);
        genderGroup.add(rbPerempuan);
    }

    private void koneksiDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/beasiswa", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
        }
    }

    private void showTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode alternatif");
        model.addColumn("NISN");
        model.addColumn("Nama");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Kelas");
        model.addColumn("Alamat");

        try {
            String sql = "SELECT * FROM siswa";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kd_alternatif"),
                    rs.getString("NISN"),
                    rs.getString("Nama"),
                    rs.getString("tgllahir"),
                    rs.getString("jk"),
                    rs.getString("Kelas"),
                    rs.getString("Alamat")
                });
            }
            tabelsiswa.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal tampilkan data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpFrame = new javax.swing.JPanel();
        jpAtas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jpKiri = new javax.swing.JPanel();
        jbHome = new javax.swing.JButton();
        jbDataSiswa = new javax.swing.JButton();
        jbDataKriteria = new javax.swing.JButton();
        jbDataPen = new javax.swing.JButton();
        jbDataPer = new javax.swing.JButton();
        jbLogout = new javax.swing.JButton();
        jbLaporan = new javax.swing.JButton();
        jpKanan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tfAlamat = new javax.swing.JTextArea();
        tfNISN = new javax.swing.JTextField();
        tfKdSiswa = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        tfKelas = new javax.swing.JTextField();
        tCari = new javax.swing.JTextField();
        btnReset = new javax.swing.JButton();
        brefresh = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        jbCari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpFrame.setBackground(new java.awt.Color(0, 102, 102));
        jpFrame.setPreferredSize(new java.awt.Dimension(1000, 650));

        jpAtas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("MI Nurul Huda");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/WhatsApp Image 2025-07-20 at 21.22.03.jpeg"))); // NOI18N

        javax.swing.GroupLayout jpAtasLayout = new javax.swing.GroupLayout(jpAtas);
        jpAtas.setLayout(jpAtasLayout);
        jpAtasLayout.setHorizontalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel11)
                .addGap(57, 57, 57)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpAtasLayout.setVerticalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbLogout)
                .addGap(16, 16, 16))
        );

        jpKanan.setBackground(new java.awt.Color(0, 204, 204));
        jpKanan.setPreferredSize(new java.awt.Dimension(600, 390));

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(837, 518));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("~ Data Siswa ~");

        jLabel3.setText("Kode Alternatif");

        jLabel4.setText("NISN");

        jLabel5.setText("Nama");

        jLabel6.setText("Tanggal Lahir");

        jLabel7.setText("Jenis Kelamin");

        jLabel8.setText("Kelas");

        jLabel9.setText("Alamat");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Cari Siswa:");

        buttonGroup1.add(rbLaki);
        rbLaki.setText("Laki - laki");
        rbLaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbLakiActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbPerempuan);
        rbPerempuan.setText("Perempuan");
        rbPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPerempuanActionPerformed(evt);
            }
        });

        tfAlamat.setColumns(20);
        tfAlamat.setRows(5);
        jScrollPane2.setViewportView(tfAlamat);

        tfKdSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKdSiswaActionPerformed(evt);
            }
        });

        tCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tCariActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        brefresh.setText("Refresh");
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelsiswa);

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jbCari.setText("Cari");
        jbCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(69, 69, 69)
                                        .addComponent(rbLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPerempuan)
                                        .addGap(20, 20, 20))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfKdSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(jLabel10)))
                        .addGap(27, 27, 27)
                        .addComponent(tCari, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(brefresh)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnTambah)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnEdit)
                                        .addGap(5, 5, 5))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addGap(69, 69, 69)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                            .addComponent(tfKelas))))
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(108, 108, 108)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfNISN, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                                    .addComponent(tfNama))))
                        .addContainerGap(132, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfKdSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(brefresh)
                        .addComponent(jbCari)))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4))
                    .addComponent(tfNISN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel5))
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rbLaki)
                    .addComponent(rbPerempuan))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnEdit)
                            .addComponent(btnHapus)
                            .addComponent(btnReset))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jpKananLayout = new javax.swing.GroupLayout(jpKanan);
        jpKanan.setLayout(jpKananLayout);
        jpKananLayout.setHorizontalGroup(
            jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpKananLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpKananLayout.setVerticalGroup(
            jpKananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpKananLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    .addComponent(jpKiri, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addComponent(jpKanan, javax.swing.GroupLayout.PREFERRED_SIZE, 530, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            login loginForm = new login();
            loginForm.setLocationRelativeTo(null);
            loginForm.setVisible(true);

            this.dispose();
        }
    }//GEN-LAST:event_jbLogoutActionPerformed

    private void jbLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLaporanActionPerformed
        new laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbLaporanActionPerformed

    private void tCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tCariActionPerformed
        // TODO add your handling code here:
        String keyword = tCari.getText().trim();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kd alternatif");
        model.addColumn("NISN");
        model.addColumn("Nama");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Kelas");
        model.addColumn("Alamat");

        try {
            String sql = "SELECT * FROM siswa WHERE "
                    + "kd_alternatif LIKE ? OR NISN LIKE ? OR nama LIKE ? OR kelas LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 1; i <= 7; i++) {
                pst.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kd_alternatif"),
                    rs.getString("NISN"),
                    rs.getString("Nama"),
                    rs.getString("tgllahir"),
                    rs.getString("jk"),
                    rs.getString("Kelas"),
                    rs.getString("Alamat")
                });
            }

            tabelsiswa.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pencarian gagal: " + e.getMessage());
        }
    }//GEN-LAST:event_tCariActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        tfKdSiswa.setText("");
        tfNISN.setText("");
        tfNama.setText("");
        tfKelas.setText("");
        tfAlamat.setText("");
        jDateChooser.setDate(null);
        buttonGroup1.clearSelection();
        rbLaki.setSelected(false);
        rbPerempuan.setSelected(false);
        tfKdSiswa.setEnabled(true);
        tfNISN.setEnabled(true);
        tfNama.setEnabled(true);
        showTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try {
            String jk = rbLaki.isSelected() ? "Laki-laki" : "Perempuan";
            java.util.Date selectedDate = jDateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            String sql = "INSERT INTO siswa (kd_alternatif, NISN, Nama, tgllahir, jk, Kelas, Alamat) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tfKdSiswa.getText());
            pst.setString(2, tfNISN.getText());
            pst.setString(3, tfNama.getText());
            pst.setDate(4, sqlDate);
            pst.setString(5, jk);
            pst.setString(6, tfKelas.getText());
            pst.setString(7, tfAlamat.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan.");
            showTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal tambah data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM siswa WHERE kd_alternatif=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, tfKdSiswa.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                tfKdSiswa.setText("");
                tfNISN.setText("");
                tfNama.setText("");
                tfKelas.setText("");
                tfAlamat.setText("");
                jDateChooser.setDate(null);
                rbLaki.setSelected(false);
                rbPerempuan.setSelected(false);
                tfKdSiswa.setEnabled(true);
                tfNISN.setEnabled(true);
                tfNama.setEnabled(true);
                showTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal hapus data: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        int baris = tabelsiswa.getSelectedRow();

        tfKdSiswa.setText(tabelsiswa.getValueAt(baris, 0).toString());
        tfNISN.setText(tabelsiswa.getValueAt(baris, 1).toString());
        tfNama.setText(tabelsiswa.getValueAt(baris, 2).toString());

        try {
            String tanggalStr = tabelsiswa.getValueAt(baris, 3).toString();
            java.util.Date tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tanggalStr);
            jDateChooser.setDate(tanggal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jk = tabelsiswa.getValueAt(baris, 4).toString();
        if (jk.equalsIgnoreCase("Laki-laki")) {
            rbLaki.setSelected(true);
        } else {
            rbPerempuan.setSelected(true);
        }

        tfKelas.setText(tabelsiswa.getValueAt(baris, 5).toString());
        tfAlamat.setText(tabelsiswa.getValueAt(baris, 6).toString());
    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        try {
            String jk = rbLaki.isSelected() ? "Laki-laki" : "Perempuan";
            java.util.Date selectedDate = jDateChooser.getDate();
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            String sql = "UPDATE siswa SET NISN=?, Nama=?, tgllahir=?, jk=?, Kelas=?, Alamat=? WHERE kd_alternatif=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, tfNISN.getText());
            pst.setString(2, tfNama.getText());
            pst.setDate(3, sqlDate);
            pst.setString(4, jk);
            pst.setString(5, tfKelas.getText());
            pst.setString(6, tfAlamat.getText());
            pst.setString(7, tfKdSiswa.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil diubah.");
            showTable();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal edit data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jbCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCariActionPerformed
        String keyword = tCari.getText().trim();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Alternatif");
        model.addColumn("NISN");
        model.addColumn("Nama");
        model.addColumn("Tanggal Lahir");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Kelas");
        model.addColumn("Alamat");

        try {
            String sql = "SELECT * FROM siswa WHERE kd_alternatif LIKE ? OR NISN LIKE ? OR nama LIKE ? OR kelas LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 1; i <= 4; i++) {
                pst.setString(i, "%" + keyword + "%");
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kd_alternatif"),
                    rs.getString("NISN"),
                    rs.getString("Nama"),
                    rs.getString("tgllahir"),
                    rs.getString("jk"),
                    rs.getString("Kelas"),
                    rs.getString("Alamat")
                });
            }

            tabelsiswa.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pencarian gagal: " + e.getMessage());
        }
    }//GEN-LAST:event_jbCariActionPerformed

    private void tfKdSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKdSiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKdSiswaActionPerformed

    private void rbLakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbLakiActionPerformed

    private void rbPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbPerempuanActionPerformed

    private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brefreshActionPerformed
        // TODO add your handling code here:
        showTable();
        tCari.setText("");
    }//GEN-LAST:event_brefreshActionPerformed

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
            java.util.logging.Logger.getLogger(datasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datasiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datasiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brefresh;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbCari;
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
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTextField tCari;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTextArea tfAlamat;
    private javax.swing.JTextField tfKdSiswa;
    private javax.swing.JTextField tfKelas;
    private javax.swing.JTextField tfNISN;
    private javax.swing.JTextField tfNama;
    // End of variables declaration//GEN-END:variables
}
