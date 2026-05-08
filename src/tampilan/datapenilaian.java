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
public class datapenilaian extends javax.swing.JFrame {

    private Connection conn = new koneksi().connect();
    private DefaultTableModel model;

    /**
     * Creates new form datapenilaian
     */
    public datapenilaian() {
        initComponents();
        this.setLocationRelativeTo(null);
        showTablePenilaian();
        isiComboBoxDariDatabase();
        setupComboBoxListener();
    }

    public String kd_alternatif, NISN, Nama;

    public String getkd_alternatif() {
        return kd_alternatif;
    }

    public String getNISN() {
        return NISN;
    }

    public String getNama() {
        return Nama;
    }

    public void siswaTerpilih() {
        tfKdAlter.setText(kd_alternatif);
        tfNISN.setText(NISN);
        tfNama.setText(Nama);
    }

    private void setupComboBoxListener() {
        setupComboListener(jComboBox1, tfBobotSub1, "C1");
        setupComboListener(jComboBox2, tfBobotSub2, "C2");
        setupComboListener(jComboBox3, tfBobotSub3, "C3");
        setupComboListener(jComboBox4, tfBobotSub4, "C4");
        setupComboListener(jComboBox5, tfBobotSub5, "C5");
    }

    private void setupComboListener(javax.swing.JComboBox<String> comboBox, javax.swing.JTextField bobotField, String kodeKriteria) {
        comboBox.addActionListener(e -> {
            String selectedSub = comboBox.getSelectedItem().toString();
            bobotField.setText(ambilBobotDariCombo(selectedSub, kodeKriteria));
        });
    }

    private void isiComboBoxDariDatabase() {
        isiComboBox(jComboBox1, "C1");
        isiComboBox(jComboBox2, "C2");
        isiComboBox(jComboBox3, "C3");
        isiComboBox(jComboBox4, "C4");
        isiComboBox(jComboBox5, "C5");
    }

    private void isiComboBox(javax.swing.JComboBox<String> comboBox, String kodeKriteria) {
        try {
            comboBox.removeAllItems();
            String sql = "SELECT nama_subkriteria FROM subkriteria WHERE kode_kriteria = ? ORDER BY bobot_subkriteria DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeKriteria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comboBox.addItem(rs.getString("nama_subkriteria"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(datapenilaian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String ambilBobotDariCombo(String namaSub, String kodeKriteria) {
        String bobot = "";
        String sql = "SELECT bobot_subkriteria FROM subkriteria WHERE nama_subkriteria=? AND kode_kriteria=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaSub);
            ps.setString(2, kodeKriteria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bobot = rs.getString("bobot_subkriteria");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil bobot: " + e.getMessage());
        }
        return bobot;
    }

    private void ambilBobotJikaKosong() {
        if (tfBobotSub1.getText().isEmpty()) {
            tfBobotSub1.setText(ambilBobotDariCombo(jComboBox1.getSelectedItem().toString(), "C1"));
        }
        if (tfBobotSub2.getText().isEmpty()) {
            tfBobotSub2.setText(ambilBobotDariCombo(jComboBox2.getSelectedItem().toString(), "C2"));
        }
        if (tfBobotSub3.getText().isEmpty()) {
            tfBobotSub3.setText(ambilBobotDariCombo(jComboBox3.getSelectedItem().toString(), "C3"));
        }
        if (tfBobotSub4.getText().isEmpty()) {
            tfBobotSub4.setText(ambilBobotDariCombo(jComboBox4.getSelectedItem().toString(), "C4"));
        }
        if (tfBobotSub5.getText().isEmpty()) {
            tfBobotSub5.setText(ambilBobotDariCombo(jComboBox5.getSelectedItem().toString(), "C5"));
        }
    }

    private void cekKosong() throws exceptionData {
        if (tfKdAlter.getText().isEmpty() || tfBobotSub1.getText().isEmpty() || tfBobotSub2.getText().isEmpty()
                || tfBobotSub3.getText().isEmpty() || tfBobotSub4.getText().isEmpty() || tfBobotSub5.getText().isEmpty()) {
            throw new exceptionData();
        }
    }

    public void showComboBox() {
        try {
            String query;
            query = "SELECT nama_subkriteria FROM subkriteria WHERE kode_kriteria LIKE '%C5%'";
            java.sql.Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                jComboBox5.addItem(rs.getString("nama_subkriteria"));
            }
            rs.last();
            int jumlah_data = rs.getRow();
            rs.first();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void edit() {
        try {
            String query;
            query = "UPDATE penilaian SET c1=?, c2=?, c3=?, c4=?, c5=? WHERE kd_alternatif=?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tfBobotSub1.getText());
            st.setString(2, tfBobotSub2.getText());
            st.setString(3, tfBobotSub3.getText());
            st.setString(4, tfBobotSub4.getText());
            st.setString(5, tfBobotSub5.getText());
            st.setString(6, tfKdAlter.getText());
            st.executeUpdate();
            tfKdAlter.requestFocus();
            JOptionPane.showMessageDialog(this, "data berhasi diedit");
            showTablePenilaian();
        } catch (Exception ex) {
            Logger.getLogger(dataperhitungan.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "data gagal diedit" + ex.getMessage());
        }
    }

    public void hapus() {
        try {
            String query = "DELETE FROM penilaian WHERE kd_alternatif = ?";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tfKdAlter.getText());
            st.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            tfKdAlter.requestFocus();
            showTablePenilaian();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Data gagal dihapus: " + ex.getMessage());
        }
        showTablePenilaian();
    }

    public void tambah() {
        ambilBobotJikaKosong();

        if (tfKdAlter.getText().isEmpty() || tfNISN.getText().isEmpty() || tfNama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode Alternatif, NISN, dan Nama harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String queryCheck = "SELECT COUNT(*) FROM penilaian WHERE kd_alternatif = ?";
            PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
            checkStatement.setString(1, tfKdAlter.getText());
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int existingCount = checkResult.getInt(1);
            checkResult.close();
            checkStatement.close();

            if (existingCount > 0) {
                JOptionPane.showMessageDialog(this, "Data dengan kode alternatif ini sudah ada.", "Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String queryInsert = "INSERT INTO penilaian (kd_alternatif, c1, c2, c3, c4, c5) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = conn.prepareStatement(queryInsert);
            insertStatement.setString(1, tfKdAlter.getText());
            insertStatement.setString(2, tfBobotSub1.getText());
            insertStatement.setString(3, tfBobotSub2.getText());
            insertStatement.setString(4, tfBobotSub3.getText());
            insertStatement.setString(5, tfBobotSub4.getText());
            insertStatement.setString(6, tfBobotSub5.getText());

            int hasil = insertStatement.executeUpdate();
            insertStatement.close();

            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data penilaian berhasil ditambahkan.");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data penilaian.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        showTablePenilaian();
    }

    public void reset() {
        tfKdAlter.setText("");
        tfNISN.setText("");
        tfNama.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        jComboBox5.setSelectedIndex(0);
        tfBobotSub1.setText("");
        tfBobotSub2.setText("");
        tfBobotSub3.setText("");
        tfBobotSub4.setText("");
        tfBobotSub5.setText("");
    }

    protected void showTablePenilaian() {
        Object[] baris = {"No", "Kode Alternatif", "NISN", "Nama", "C1", "C2", "C3", "C4", "C5"};
        model = new DefaultTableModel(null, baris);
        tabelpenilaian.setModel(model);

        String query = "SELECT siswa.kd_alternatif, siswa.NISN, siswa.Nama, "
                + "penilaian.C1, penilaian.C2, penilaian.C3, penilaian.C4, penilaian.C5 "
                + "FROM penilaian "
                + "LEFT JOIN siswa ON penilaian.kd_alternatif = siswa.kd_alternatif "
                + "ORDER BY penilaian.kd_alternatif";
        try {
            Statement st = conn.createStatement();
            ResultSet hasil = st.executeQuery(query);
            int no = 1;
            while (hasil.next()) {
                String[] data = {
                    String.valueOf(no++),
                    hasil.getString("kd_alternatif"),
                    hasil.getString("NISN"),
                    hasil.getString("Nama"),
                    hasil.getString("C1"),
                    hasil.getString("C2"),
                    hasil.getString("C3"),
                    hasil.getString("C4"),
                    hasil.getString("C5")
                };
                model.addRow(data);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data penilaian: " + e.getMessage());
            Logger.getLogger(datapenilaian.class.getName()).log(Level.SEVERE, null, e);
        }
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
        jLabel17 = new javax.swing.JLabel();
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
        btnHapus = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpenilaian = new javax.swing.JTable();
        btnReset = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfKdAlter = new javax.swing.JTextField();
        tfNISN = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        tfBobotSub1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        tfBobotSub2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        tfBobotSub3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        tfBobotSub4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        tfBobotSub5 = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));

        jpFrame.setBackground(new java.awt.Color(0, 102, 102));
        jpFrame.setPreferredSize(new java.awt.Dimension(1000, 650));

        jpAtas.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("MI Nurul Huda");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/WhatsApp Image 2025-07-20 at 21.22.03.jpeg"))); // NOI18N

        javax.swing.GroupLayout jpAtasLayout = new javax.swing.GroupLayout(jpAtas);
        jpAtas.setLayout(jpAtasLayout);
        jpAtasLayout.setHorizontalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel17)
                .addGap(62, 62, 62)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpAtasLayout.setVerticalGroup(
            jpAtasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAtasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
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
        jLabel2.setText("~ Data Penilaian ~");

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        tabelpenilaian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tabelpenilaian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenilaianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpenilaian);

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel3.setText("NISN");

        jLabel4.setText("Kode Alternatif");

        tfKdAlter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKdAlterActionPerformed(evt);
            }
        });

        tfNISN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNISNActionPerformed(evt);
            }
        });

        jLabel5.setText("Nama");

        tfNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamaActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfKdAlter)
                            .addComponent(jLabel3)
                            .addComponent(tfNISN)
                            .addComponent(jLabel5)
                            .addComponent(tfNama, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addComponent(btnCari)
                        .addGap(17, 17, 17))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfKdAlter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNISN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Kriteria Penilaian:");

        jLabel7.setText("Pendapatan Orang Tua");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel8.setText("C1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Rp. 1.000.000", ">Rp. 1.000.000 - <Rp. 2.000.000", ">Rp. 2.000.000 - <Rp. 3.000.000", ">Rp. 3.000.000 - <Rp. 4.000.000", ">Rp. 4.000.000" }));

        jLabel9.setText("Pekerjaan Orang Tua");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel10.setText("C2");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Petani", "Buruh Harian", "Wiraswasta", "Karyawan Swasta", "PNS" }));

        jLabel11.setText("Kondisi Tempat Tinggal");
        jLabel11.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel12.setText("C3");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kontrakan sangat sederhana", "Kontrakan biasa", "Rumah semi permanen", "Rumah semi sederhana", "Rumah permanen mewah" }));

        jLabel13.setText("Kepemilikan Aset");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel14.setText("C4");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak punya aset", "Sepeda", "Motor", "Mobil", "Tanah" }));

        jLabel15.setText("Jumlah Tanggungan");
        jLabel15.setBorder(javax.swing.BorderFactory.createBevelBorder(null, null, new java.awt.Color(0, 0, 0), null, null));

        jLabel16.setText("C5");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "> 4 Orang anak", "4 Orang anak", "3 Orang anak", "2 Orang anak", "1 Orang anak" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfBobotSub3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfBobotSub1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, 152, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfBobotSub2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox5, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfBobotSub5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox4, 0, 1, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfBobotSub4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBobotSub1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBobotSub4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBobotSub2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBobotSub5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfBobotSub3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnTambah)
                                .addGap(65, 65, 65)
                                .addComponent(btnEdit)
                                .addGap(57, 57, 57)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnReset))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(32, 32, 32))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnHapus)
                    .addComponent(btnReset)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
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
                .addGap(0, 4, Short.MAX_VALUE))
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

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapus();
        reset();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        try {
            cekKosong();
        } catch (exceptionData ex) {
            JOptionPane.showMessageDialog(null, ex.showMessageError());
        }
        edit();
        reset();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        ambilBobotJikaKosong();
        if (tfKdAlter.getText().isEmpty() || tfNISN.getText().isEmpty() || tfNama.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode Alternatif, NISN, dan Nama harus diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String queryCheck = "SELECT COUNT(*) FROM penilaian WHERE kd_alternatif = ?";
            PreparedStatement checkStatement = conn.prepareStatement(queryCheck);
            checkStatement.setString(1, tfKdAlter.getText());
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int existingCount = checkResult.getInt(1);
            checkResult.close();
            checkStatement.close();

            if (existingCount > 0) {
                JOptionPane.showMessageDialog(this, "Data dengan kode alternatif ini sudah ada.", "Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String queryInsert = "INSERT INTO penilaian (kd_alternatif, NISN, Nama, C1, C2, C3, C4, C5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = conn.prepareStatement(queryInsert);
            insertStatement.setString(1, tfKdAlter.getText());
            insertStatement.setString(2, tfNISN.getText());
            insertStatement.setString(3, tfNama.getText());
            insertStatement.setString(4, tfBobotSub1.getText());
            insertStatement.setString(5, tfBobotSub2.getText());
            insertStatement.setString(6, tfBobotSub3.getText());
            insertStatement.setString(7, tfBobotSub4.getText());
            insertStatement.setString(8, tfBobotSub5.getText());

            int hasil = insertStatement.executeUpdate();
            insertStatement.close();

            if (hasil == 1) {
                JOptionPane.showMessageDialog(this, "Data penilaian berhasil ditambahkan.");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data penilaian.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan SQL: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        showTablePenilaian();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String query = "SELECT * FROM siswa WHERE kd_alternatif = '" + tfKdAlter.getText() + "'";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(query);
            while (hasil.next()) {
                String a = hasil.getString("NISN");
                String b = hasil.getString("Nama");

                tfNISN.setText(a);
                tfNama.setText(b);

                tfNISN.setEnabled(false);
                tfNama.setEnabled(false);
            }
        } catch (Exception ex) {
        }

        cariSiswa cs = new cariSiswa();
        cs.penilaian = this;
        cs.setVisible(true);
        cs.setResizable(false);
        tfKdAlter.setEnabled(false);
        tfNISN.setEnabled(false);
        tfNama.setEnabled(false);
    }//GEN-LAST:event_btnCariActionPerformed

    private void tabelpenilaianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpenilaianMouseClicked
        // TODO add your handling code here:
        int row = tabelpenilaian.getSelectedRow();
        if (row >= 0) {
            tfKdAlter.setText(tabelpenilaian.getValueAt(row, 1).toString());
            tfNISN.setText(tabelpenilaian.getValueAt(row, 2).toString());
            tfNama.setText(tabelpenilaian.getValueAt(row, 3).toString());
            tfBobotSub1.setText(tabelpenilaian.getValueAt(row, 4).toString());
            tfBobotSub2.setText(tabelpenilaian.getValueAt(row, 5).toString());
            tfBobotSub3.setText(tabelpenilaian.getValueAt(row, 6).toString());
            tfBobotSub4.setText(tabelpenilaian.getValueAt(row, 7).toString());
            tfBobotSub5.setText(tabelpenilaian.getValueAt(row, 8).toString());

            tfKdAlter.setEnabled(false);
            tfNISN.setEnabled(false);
            tfNama.setEnabled(false);
        }
    }//GEN-LAST:event_tabelpenilaianMouseClicked

    private void tfKdAlterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKdAlterActionPerformed
        // TODO add your handling code here:
        btnCari.doClick();
    }//GEN-LAST:event_tfKdAlterActionPerformed

    private void tfNISNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNISNActionPerformed
        // TODO add your handling code here:
        tfNISN.setEnabled(false);
    }//GEN-LAST:event_tfNISNActionPerformed

    private void tfNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamaActionPerformed
        // TODO add your handling code here:
        tfNama.setEnabled(false);
    }//GEN-LAST:event_tfNamaActionPerformed

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
            java.util.logging.Logger.getLogger(datapenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datapenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datapenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datapenilaian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datapenilaian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTable tabelpenilaian;
    private javax.swing.JTextField tfBobotSub1;
    private javax.swing.JTextField tfBobotSub2;
    private javax.swing.JTextField tfBobotSub3;
    private javax.swing.JTextField tfBobotSub4;
    private javax.swing.JTextField tfBobotSub5;
    private javax.swing.JTextField tfKdAlter;
    private javax.swing.JTextField tfNISN;
    private javax.swing.JTextField tfNama;
    // End of variables declaration//GEN-END:variables
}
