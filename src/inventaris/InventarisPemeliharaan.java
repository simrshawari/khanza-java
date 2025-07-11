/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */
package inventaris;

import fungsi.WarnaTable;
import fungsi.WarnaTableKalibrasi;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dosen
 */
public final class InventarisPemeliharaan extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private InventarisKoleksi inventaris = new InventarisKoleksi(null, false);
    private InventarisRuang ruang = new InventarisRuang(null, false);
    private boolean semua;
    private double total;
    private String status;

    /**
     * Creates new form DlgJnsPerawatan
     *
     * @param parent
     * @param modal
     */
    public InventarisPemeliharaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Inventaris", "Kode Barang", "Nama Barang", "Ruang", "NIP", "Penanggung Jawab", "Kegiatan", "Tanggal", "Pelaksana", "Biaya", "Jenis Pemeliharaan", "Tanggal Expire", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(110);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(120);
            } else if (i == 4) {
                column.setPreferredWidth(90);
            } else if (i == 5) {
                column.setPreferredWidth(140);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(105);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(125);
            } else if (i == 11) {
                column.setPreferredWidth(125);
            } else if (i == 12) {
                column.setPreferredWidth(125);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTableKalibrasi());



        UraianKegiatan.setDocument(new batasInput((int) 255).getKata(UraianKegiatan));
        Biaya.setDocument(new batasInput((int) 15).getOnlyAngka(Biaya));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TCari.requestFocus();

        ChkInput.setSelected(false);
        isForm();

        inventaris.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (inventaris.getTable().getSelectedRow() != -1) {
                    NoInventaris.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(), 0).toString());
                    KodeBarang.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(), 1).toString());
                    NamaBarang.setText(inventaris.getTable().getValueAt(inventaris.getTable().getSelectedRow(), 2).toString());
                    UraianKegiatan.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        inventaris.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    inventaris.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (ruang.getTable().getSelectedRow() != -1) {
                    nm_ruangcari.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(), 1).toString());
                    TCari.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

        });

        ruang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    ruang.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        ChkAccor.setSelected(false);
        isPhoto();
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                + ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );

        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                }
                Biaya.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label23 = new widget.Label();
        nm_ruangcari = new widget.TextBox();
        btnRuang1 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label1 = new widget.Label();
        NoInventaris = new widget.TextBox();
        label8 = new widget.Label();
        Tanggal = new widget.Tanggal();
        btnInv = new widget.Button();
        NamaBarang = new widget.TextBox();
        KodeBarang = new widget.TextBox();
        label9 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPtg = new widget.Button();
        label7 = new widget.Label();
        UraianKegiatan = new widget.TextBox();
        Pelaksana = new widget.ComboBox();
        label10 = new widget.Label();
        label11 = new widget.Label();
        Biaya = new widget.TextBox();
        label13 = new widget.Label();
        Status = new widget.ComboBox();
        label12 = new widget.Label();
        TanggalExpire = new widget.Tanggal();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemeliharaan Aset/ Kalibrasi Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record  :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(null);

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(54, 23));
        panelGlass9.add(jLabel19);
        jLabel19.setBounds(6, 10, 54, 23);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-08-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);
        DTPCari1.setBounds(65, 10, 90, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);
        jLabel21.setBounds(160, 10, 23, 23);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-08-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);
        DTPCari2.setBounds(188, 10, 90, 23);

        label23.setText("Ruang :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass9.add(label23);
        label23.setBounds(283, 10, 45, 23);

        nm_ruangcari.setEditable(false);
        nm_ruangcari.setName("nm_ruangcari"); // NOI18N
        nm_ruangcari.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass9.add(nm_ruangcari);
        nm_ruangcari.setBounds(333, 10, 150, 23);

        btnRuang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuang1.setMnemonic('1');
        btnRuang1.setToolTipText("Alt+1");
        btnRuang1.setName("btnRuang1"); // NOI18N
        btnRuang1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnRuang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuang1ActionPerformed(evt);
            }
        });
        panelGlass9.add(btnRuang1);
        btnRuang1.setBounds(488, 10, 28, 23);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);
        jLabel6.setBounds(521, 10, 65, 23);

        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);
        TCari.setBounds(591, 10, 150, 23);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);
        BtnCari.setBounds(746, 10, 28, 23);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);
        BtnAll.setBounds(779, 10, 28, 23);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 180));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 127));
        FormInput.setLayout(null);

        label1.setText("Nomor Inventaris :");
        label1.setName("label1"); // NOI18N
        FormInput.add(label1);
        label1.setBounds(0, 10, 119, 23);

        NoInventaris.setEditable(false);
        NoInventaris.setName("NoInventaris"); // NOI18N
        NoInventaris.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoInventarisKeyPressed(evt);
            }
        });
        FormInput.add(NoInventaris);
        NoInventaris.setBounds(123, 10, 155, 23);

        label8.setText("Tanggal Kalibrasi :");
        label8.setName("label8"); // NOI18N
        FormInput.add(label8);
        label8.setBounds(250, 100, 130, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-08-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(390, 100, 90, 23);

        btnInv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInv.setMnemonic('1');
        btnInv.setToolTipText("Alt+1");
        btnInv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnInv.setName("btnInv"); // NOI18N
        btnInv.setPreferredSize(new java.awt.Dimension(100, 30));
        btnInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvActionPerformed(evt);
            }
        });
        btnInv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnInvKeyPressed(evt);
            }
        });
        FormInput.add(btnInv);
        btnInv.setBounds(779, 10, 25, 23);

        NamaBarang.setEditable(false);
        NamaBarang.setName("NamaBarang"); // NOI18N
        FormInput.add(NamaBarang);
        NamaBarang.setBounds(414, 10, 362, 23);

        KodeBarang.setEditable(false);
        KodeBarang.setName("KodeBarang"); // NOI18N
        FormInput.add(KodeBarang);
        KodeBarang.setBounds(281, 10, 130, 23);

        label9.setText("Penanggung Jawab :");
        label9.setName("label9"); // NOI18N
        FormInput.add(label9);
        label9.setBounds(0, 70, 119, 23);

        NIP.setEditable(false);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(123, 70, 130, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(256, 70, 259, 23);

        btnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPtg.setMnemonic('3');
        btnPtg.setToolTipText("Alt+3");
        btnPtg.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPtg.setName("btnPtg"); // NOI18N
        btnPtg.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPtgActionPerformed(evt);
            }
        });
        btnPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPtgKeyPressed(evt);
            }
        });
        FormInput.add(btnPtg);
        btnPtg.setBounds(518, 70, 25, 23);

        label7.setText("Kegiatan :");
        label7.setName("label7"); // NOI18N
        FormInput.add(label7);
        label7.setBounds(0, 40, 119, 23);

        UraianKegiatan.setName("UraianKegiatan"); // NOI18N
        UraianKegiatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UraianKegiatanKeyPressed(evt);
            }
        });
        FormInput.add(UraianKegiatan);
        UraianKegiatan.setBounds(123, 40, 681, 23);

        Pelaksana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Teknisi Rumah Sakit", "Teknisi Rujukan", "Pihak ke III" }));
        Pelaksana.setName("Pelaksana"); // NOI18N
        Pelaksana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PelaksanaKeyPressed(evt);
            }
        });
        FormInput.add(Pelaksana);
        Pelaksana.setBounds(634, 70, 170, 23);

        label10.setText("Pelaksana :");
        label10.setName("label10"); // NOI18N
        FormInput.add(label10);
        label10.setBounds(560, 70, 70, 23);

        label11.setText("Biaya :");
        label11.setName("label11"); // NOI18N
        FormInput.add(label11);
        label11.setBounds(0, 100, 119, 23);

        Biaya.setName("Biaya"); // NOI18N
        Biaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BiayaKeyPressed(evt);
            }
        });
        FormInput.add(Biaya);
        Biaya.setBounds(123, 100, 90, 23);

        label13.setText("Jenis Pemeliharaan :");
        label13.setName("label13"); // NOI18N
        FormInput.add(label13);
        label13.setBounds(500, 100, 130, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Running Maintenance", "Shut Down Maintenance", "Emergency Maintenance" }));
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(634, 100, 170, 23);

        label12.setText("Expire Kalibrasi:");
        label12.setName("label12"); // NOI18N
        FormInput.add(label12);
        label12.setBounds(250, 130, 130, 23);

        TanggalExpire.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-08-2024" }));
        TanggalExpire.setDisplayFormat("dd-MM-yyyy");
        TanggalExpire.setName("TanggalExpire"); // NOI18N
        TanggalExpire.setOpaque(false);
        TanggalExpire.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalExpireKeyPressed(evt);
            }
        });
        FormInput.add(TanggalExpire);
        TanggalExpire.setBounds(390, 130, 90, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 250));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Dokumen Kalibrasi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPhoto.setMnemonic('U');
        btnAmbilPhoto.setText("Ambil");
        btnAmbilPhoto.setToolTipText("Alt+U");
        btnAmbilPhoto.setName("btnAmbilPhoto"); // NOI18N
        btnAmbilPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(btnAmbilPhoto);

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (NoInventaris.getText().trim().equals("") || KodeBarang.getText().trim().equals("") || NamaBarang.getText().trim().equals("")) {
            Valid.textKosong(btnInv, "No.Permintaan");
        } else if (NIP.getText().trim().equals("")) {
            Valid.textKosong(NIP, "Petugas Penanggung Jawab");
        } else if (UraianKegiatan.getText().trim().equals("")) {
            Valid.textKosong(UraianKegiatan, "Uraian Kegiatan");
        } else if (Biaya.getText().trim().equals("")) {
            Valid.textKosong(Biaya, "Biaya");
        } else {
            if (Sequel.menyimpantf("pemeliharaan_inventaris", "?,?,?,?,?,?,?,?", "Nomor Inventaris", 8, new String[]{
                NoInventaris.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), UraianKegiatan.getText(), NIP.getText(),
                Pelaksana.getSelectedItem().toString(), Biaya.getText(), Status.getSelectedItem().toString(), Valid.SetTgl(TanggalExpire.getSelectedItem() + "")
            }) == true) {
                tampil();
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, Status, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode, NoInventaris, "pemeliharaan_inventaris", "tanggal='" + tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 7).toString() + "' and no_inventaris");
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (NoInventaris.getText().trim().equals("") || KodeBarang.getText().trim().equals("") || NamaBarang.getText().trim().equals("")) {
            Valid.textKosong(btnInv, "No.Permintaan");
        } else if (NIP.getText().trim().equals("")) {
            Valid.textKosong(NIP, "Petugas Penanggung Jawab");
        } else if (UraianKegiatan.getText().trim().equals("")) {
            Valid.textKosong(UraianKegiatan, "Uraian Kegiatan");
        } else if (Biaya.getText().trim().equals("")) {
            Valid.textKosong(Biaya, "Biaya");
        } else {
            if (tbJnsPerawatan.getSelectedRow() > -1) {
                if (Sequel.mengedittf("pemeliharaan_inventaris", "no_inventaris=? and tanggal=?", "no_inventaris=?,tanggal=?,uraian_kegiatan=?,nip=?,pelaksana=?,biaya=?,jenis_pemeliharaan=?,tanggalexpire=?", 10, new String[]{
                    NoInventaris.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), UraianKegiatan.getText(), NIP.getText(),
                    Pelaksana.getSelectedItem().toString(), Biaya.getText(), Status.getSelectedItem().toString(),Valid.SetTgl(TanggalExpire.getSelectedItem() + ""),
                    tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString(), tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 7).toString(),Valid.SetTgl(TanggalExpire.getSelectedItem() + "")
                }) == true) {
                    tampil();
                    emptTeks();
                }
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
            semua = nm_ruangcari.getText().equals("") && TCari.getText().equals("");
            Valid.MyReportqry("rptPemeliharaanInventaris.jasper", "report", "::[ Data Pemeliharaan Inventaris ]::",
                    "select pemeliharaan_inventaris.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,"
                    + "inventaris_ruang.nama_ruang,pemeliharaan_inventaris.nip,petugas.nama,pemeliharaan_inventaris.uraian_kegiatan,"
                    + "pemeliharaan_inventaris.tanggal,pemeliharaan_inventaris.pelaksana,pemeliharaan_inventaris.biaya, "
                    + "pemeliharaan_inventaris.jenis_pemeliharaan from pemeliharaan_inventaris inner join inventaris "
                    + "on pemeliharaan_inventaris.no_inventaris=inventaris.no_inventaris "
                    + "inner join inventaris_barang on inventaris.kode_barang=inventaris_barang.kode_barang "
                    + "inner join inventaris_ruang on inventaris.id_ruang=inventaris_ruang.id_ruang "
                    + "inner join petugas on pemeliharaan_inventaris.nip=petugas.nip where "
                    + "pemeliharaan_inventaris.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' "
                    + (semua ? "" : "and inventaris_ruang.nama_ruang like '%" + nm_ruangcari.getText().trim() + "%' and (pemeliharaan_inventaris.no_inventaris like '%" + TCari.getText().trim() + "%' "
                            + "or inventaris.kode_barang like '%" + TCari.getText().trim() + "%' or inventaris_barang.nama_barang like '%" + TCari.getText().trim() + "%' "
                            + "or pemeliharaan_inventaris.pelaksana like '%" + TCari.getText().trim() + "%' or pemeliharaan_inventaris.jenis_pemeliharaan like '%" + TCari.getText().trim() + "%' "
                            + "or petugas.nama like '%" + TCari.getText().trim() + "%')") + "order by pemeliharaan_inventaris.no_inventaris", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbJnsPerawatan.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        nm_ruangcari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
    isForm();
}//GEN-LAST:event_ChkInputActionPerformed

private void NoInventarisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoInventarisKeyPressed
    Valid.pindah(evt, TCari, Tanggal);
}//GEN-LAST:event_NoInventarisKeyPressed

private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
    Valid.pindah(evt, NoInventaris, KodeBarang);
}//GEN-LAST:event_TanggalKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void btnRuang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuang1ActionPerformed
        ruang.isCek();
        ruang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setAlwaysOnTop(false);
        ruang.setVisible(true);
    }//GEN-LAST:event_btnRuang1ActionPerformed

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void btnInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvActionPerformed
        inventaris.tampil();
        inventaris.isCek();
        inventaris.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        inventaris.setLocationRelativeTo(internalFrame1);
        inventaris.setAlwaysOnTop(false);
        inventaris.setVisible(true);
    }//GEN-LAST:event_btnInvActionPerformed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        Valid.pindah(evt, UraianKegiatan, Biaya);
    }//GEN-LAST:event_NIPKeyPressed

    private void btnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPtgActionPerformed
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPtgActionPerformed

    private void UraianKegiatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UraianKegiatanKeyPressed
        Valid.pindah(evt, btnInv, btnPtg);
    }//GEN-LAST:event_UraianKegiatanKeyPressed

    private void PelaksanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PelaksanaKeyPressed
        Valid.pindah(evt, Tanggal, Status);
    }//GEN-LAST:event_PelaksanaKeyPressed

    private void BiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BiayaKeyPressed
        Valid.pindah(evt, btnPtg, Tanggal);
    }//GEN-LAST:event_BiayaKeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        Valid.pindah(evt, Pelaksana, BtnSimpan);
    }//GEN-LAST:event_StatusKeyPressed

    private void btnInvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnInvKeyPressed
        Valid.pindah(evt, TCari, UraianKegiatan);
    }//GEN-LAST:event_btnInvKeyPressed

    private void btnPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPtgKeyPressed
        Valid.pindah(evt, UraianKegiatan, Biaya);
    }//GEN-LAST:event_btnPtgKeyPressed

    private void TanggalExpireKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalExpireKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalExpireKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if (tbJnsPerawatan.getSelectedRow() != -1) {
            isPhoto();
            panggilPhoto();
        } else {
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null, "Silahkan pilih No.Inventaris...!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        if (tbJnsPerawatan.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.panggilUrl("inventarispemeliharaan/login.php?act=login&usere=" + koneksiDB.USERHYBRIDWEB() + "&passwordte=" + koneksiDB.PASHYBRIDWEB() + "&no_inventaris=" + tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString().replaceAll(" ", "_"));
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventarisPemeliharaan dialog = new InventarisPemeliharaan(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Biaya;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.TextBox KodeBarang;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NIP;
    private widget.TextBox NamaBarang;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoInventaris;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Pelaksana;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalExpire;
    private widget.TextBox UraianKegiatan;
    private widget.Button btnAmbilPhoto;
    private widget.Button btnInv;
    private widget.Button btnPtg;
    private widget.Button btnRuang1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label23;
    private widget.Label label7;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nm_ruangcari;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            semua = nm_ruangcari.getText().equals("") && TCari.getText().equals("");
            ps = koneksi.prepareStatement(
                    "select pemeliharaan_inventaris.no_inventaris,inventaris.kode_barang,inventaris_barang.nama_barang,"
                    + "inventaris_ruang.nama_ruang,pemeliharaan_inventaris.nip,petugas.nama,pemeliharaan_inventaris.uraian_kegiatan,"
                    + "pemeliharaan_inventaris.tanggal,pemeliharaan_inventaris.pelaksana,pemeliharaan_inventaris.biaya, "
                    + "pemeliharaan_inventaris.jenis_pemeliharaan,pemeliharaan_inventaris.tanggalexpire from pemeliharaan_inventaris inner join inventaris "
                    + "on pemeliharaan_inventaris.no_inventaris=inventaris.no_inventaris "
                    + "inner join inventaris_barang on inventaris.kode_barang=inventaris_barang.kode_barang "
                    + "inner join inventaris_ruang on inventaris.id_ruang=inventaris_ruang.id_ruang "
                    + "inner join petugas on pemeliharaan_inventaris.nip=petugas.nip where "
                    + "pemeliharaan_inventaris.tanggal between ? and ? " + (semua ? "" : "and inventaris_ruang.nama_ruang like ? and "
                            + "(pemeliharaan_inventaris.no_inventaris like ? or inventaris.kode_barang like ? or inventaris_barang.nama_barang like ? "
                            + "or pemeliharaan_inventaris.pelaksana like ? or pemeliharaan_inventaris.jenis_pemeliharaan like ? "
                            + "or petugas.nama like ?)")
                    + "order by pemeliharaan_inventaris.no_inventaris"
            );

            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                if (!semua) {
                    ps.setString(3, "%" + nm_ruangcari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");
                }
                rs = ps.executeQuery();
                total = 0;
                while (rs.next()) {
                    total = total + rs.getDouble("biaya");
                    String tanggalString = rs.getString("tanggalexpire");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate tanggalDariTabel = LocalDate.parse(tanggalString, formatter);
                    LocalDate hariIni = LocalDate.now();
                    status = "";

                    if (tanggalDariTabel.isBefore(hariIni)) {
                        status = "Kadaluarsa";
                    } else if (tanggalDariTabel.isAfter(hariIni)) {
                        status = "Belum Kadaluarsa";
                    } else {
                        status = "Belum di Kalibrasi";
                    }
                    tabMode.addRow(new String[]{
                        rs.getString("no_inventaris"), rs.getString("kode_barang"), rs.getString("nama_barang"),
                        rs.getString("nama_ruang"), rs.getString("nip"), rs.getString("nama"), rs.getString("uraian_kegiatan"), rs.getString("tanggal"),
                        rs.getString("pelaksana"), Valid.SetAngka(rs.getDouble("biaya")), rs.getString("jenis_pemeliharaan"), rs.getString("tanggalexpire"), status
                    });
                }
                if (total > 0) {
                    tabMode.addRow(new String[]{
                        "", "", "", "", "", "", "Total Biaya : ", "", "", Valid.SetAngka(total), ""
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            LCount.setText(tabMode.getRowCount() + "");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void emptTeks() {
        ChkInput.setSelected(true);
        isForm();
        NoInventaris.setText("");
        KodeBarang.setText("");
        NamaBarang.setText("");
        UraianKegiatan.setText("");
        Biaya.setText("0");
        Pelaksana.setSelectedIndex(0);
        Status.setSelectedIndex(0);
        Tanggal.setDate(new Date());
        UraianKegiatan.requestFocus();
        TanggalExpire.setDate(new Date());
    }

    private void getData() {
        if (tbJnsPerawatan.getSelectedRow() != -1) {
//"No.Inventaris", "Kode Barang", "Nama Barang", "Ruang", "NIP", "Penanggung Jawab", "Kegiatan", "Tanggal", "Pelaksana", "Biaya", "Jenis Pemeliharaan", "Tanggal Expire", "Status"
            NoInventaris.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString());
            KodeBarang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 1).toString());
            NamaBarang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 2).toString());
            NIP.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 4).toString());
            NamaPetugas.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 5).toString());
            UraianKegiatan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 6).toString());
            Pelaksana.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 8).toString());
            Biaya.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 9).toString());
            Status.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 10).toString());
            Valid.SetTgl(Tanggal, tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 7).toString());
            Valid.SetTgl(TanggalExpire, tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 11).toString());

//                Valid.SetTgl2(Tanggal,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
//                Valid.SetTgl2(TanggalExpire,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString());   
        }
    }

    public JTable getTable() {
        return tbJnsPerawatan;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 180));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        if (akses.getjml2() >= 1) {
            NIP.setEditable(false);
            btnPtg.setEnabled(false);
            BtnSimpan.setEnabled(akses.getpemeliharaan_inventaris());
            BtnHapus.setEnabled(akses.getpemeliharaan_inventaris());
            BtnEdit.setEnabled(akses.getpemeliharaan_inventaris());
            BtnPrint.setEnabled(akses.getpemeliharaan_inventaris());
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        }
        TCari.requestFocus();
    }

    private void isPhoto() {
        if (ChkAccor.isSelected() == true) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth() - 300, HEIGHT));
            FormPhoto.setVisible(true);
            ChkAccor.setVisible(true);
        } else if (ChkAccor.isSelected() == false) {
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
            FormPhoto.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if (FormPhoto.isVisible() == true) {
            try {
                ps = koneksi.prepareStatement("select inventaris_pemeliharaan_gambar.photo from inventaris_pemeliharaan_gambar where inventaris_pemeliharaan_gambar.no_inventaris=?");
                try {
                    ps.setString(1, tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        if (rs.getString("photo").equals("") || rs.getString("photo").equals("-")) {
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        } else {
                            LoadHTML.setText("<html><body><center><img src='http://" + koneksiDB.HOSTHYBRIDWEB() + ":" + koneksiDB.PORTWEB() + "/" + koneksiDB.HYBRIDWEB() + "/inventarispemeliharaan/" + rs.getString("photo") + "' alt='photo' width='" + (internalFrame1.getWidth() - 330) + "' height='" + (internalFrame1.getHeight() - 260) + "'/></center></body></html>");
                        }
                    } else {
                        LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
    }

}
