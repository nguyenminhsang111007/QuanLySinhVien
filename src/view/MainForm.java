/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author DELL
 */
public class MainForm extends javax.swing.JFrame {
    private JTable tblSinhVien;
    private JTextField txtMaSV;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JComboBox<String> cboGioiTinh;
    private JTextField txtDiaChi;
    private JTextField txtMaLop;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainForm.class.getName());

    /**
     * Creates new form MainForm
     */
    public MainForm() {
    initComponents();

    getContentPane().setLayout(new BorderLayout(5, 0));

    jPanel1.setPreferredSize(new java.awt.Dimension(260, 650));

    getContentPane().add(jPanel1, BorderLayout.WEST);
    getContentPane().add(jPanel2, BorderLayout.CENTER);

    setSize(1100, 700);
    setLocationRelativeTo(null);

    taoBangSinhVien();
}
      private void taoBangSinhVien() {

        String[] columns = {
            "Mã SV",
            "Họ tên",
            "Ngày sinh",
            "Giới tính",
            "Địa chỉ",
            "Mã lớp"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        try {
          Connection conn = database.DBConnection.getConnection();

          String sql = "SELECT MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop FROM SinhVien";

          PreparedStatement ps = conn.prepareStatement(sql);

          ResultSet rs = ps.executeQuery();

          while (rs.next()) {
              model.addRow(new Object[]{
                  rs.getString("MaSV"),
                  rs.getString("HoTen"),
                  rs.getDate("NgaySinh"),
                  rs.getString("GioiTinh"),
                  rs.getString("DiaChi"),
                  rs.getString("MaLop")
              });
          }

          rs.close();
          ps.close();
          conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tblSinhVien = new JTable(model);
        // Chỉnh giao diện tiêu đề bảng
        tblSinhVien.getTableHeader().setBackground(new java.awt.Color(220, 232, 245));
        tblSinhVien.getTableHeader().setForeground(new java.awt.Color(45, 55, 70));
        tblSinhVien.getTableHeader().setFont(
        new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
);
        // Khởi tạo các ô nhập thông tin sinh viên
        txtMaSV = new JTextField();
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        txtDiaChi = new JTextField();
        txtMaLop = new JTextField();
        tblSinhVien.getSelectionModel().addListSelectionListener(e -> {
    int row = tblSinhVien.getSelectedRow();

    if (row >= 0) {
        txtMaSV.setText(tblSinhVien.getValueAt(row, 0).toString());
        txtHoTen.setText(tblSinhVien.getValueAt(row, 1).toString());
        txtNgaySinh.setText(tblSinhVien.getValueAt(row, 2).toString());
        cboGioiTinh.setSelectedItem(tblSinhVien.getValueAt(row, 3).toString());
        txtDiaChi.setText(tblSinhVien.getValueAt(row, 4).toString());
        txtMaLop.setText(tblSinhVien.getValueAt(row, 5).toString());
    }
});

        // Tạo thanh cuộn cho bảng
       
// Tạo bảng danh sách sinh viên
        JScrollPane scrollPane = new JScrollPane(tblSinhVien);

        tblSinhVien.setRowHeight(28);
        tblSinhVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblSinhVien.getTableHeader().setFont(
        new Font("Segoe UI", Font.BOLD, 13)
        );

// Tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(jLabel2);

// Khu vực nhập thông tin
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 12));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
        BorderFactory.createTitledBorder("THÔNG TIN SINH VIÊN")
        );

// Ô 1
        JPanel p1 = new JPanel(new BorderLayout(0, 5));
        p1.setBackground(Color.WHITE);
        p1.add(new JLabel("Mã sinh viên"), BorderLayout.NORTH);
        p1.add(txtMaSV, BorderLayout.CENTER);

// Ô 2
        JPanel p2 = new JPanel(new BorderLayout(0, 5));
        p2.setBackground(Color.WHITE);
        p2.add(new JLabel("Họ và tên"), BorderLayout.NORTH);
        p2.add(txtHoTen, BorderLayout.CENTER);

// Ô 3
       JPanel p3 = new JPanel(new BorderLayout(0, 5));
       p3.setBackground(Color.WHITE);
       p3.add(new JLabel("Ngày sinh (yyyy-MM-dd)"), BorderLayout.NORTH);
       p3.add(txtNgaySinh, BorderLayout.CENTER);

// Ô 4
       JPanel p4 = new JPanel(new BorderLayout(0, 5));
       p4.setBackground(Color.WHITE);
       p4.add(new JLabel("Giới tính"), BorderLayout.NORTH);
       p4.add(cboGioiTinh, BorderLayout.CENTER);

// Ô 5
       JPanel p5 = new JPanel(new BorderLayout(0, 5));
       p5.setBackground(Color.WHITE);
       p5.add(new JLabel("Địa chỉ"), BorderLayout.NORTH);
       p5.add(txtDiaChi, BorderLayout.CENTER);

// Ô 6
       JPanel p6 = new JPanel(new BorderLayout(0, 5));
       p6.setBackground(Color.WHITE);
       p6.add(new JLabel("Mã lớp"), BorderLayout.NORTH);
       p6.add(txtMaLop, BorderLayout.CENTER);

       formPanel.add(p1);
       formPanel.add(p2);
       formPanel.add(p3);
       formPanel.add(p4);
       formPanel.add(p5);
       formPanel.add(p6);

// Khu vực phía trên
       JPanel topPanel = new JPanel(new BorderLayout(10, 12));
       topPanel.setBackground(Color.WHITE);
       topPanel.add(titlePanel, BorderLayout.NORTH);
       topPanel.add(formPanel, BorderLayout.CENTER);
       JButton btnThem = new JButton("THÊM");
       JButton btnSua = new JButton("SỬA");
       JButton btnXoa = new JButton("XÓA");
       JButton btnTimKiem = new JButton("TÌM KIẾM");
       JButton btnLamMoi = new JButton("LÀM MỚI");

// Tạo kiểu cho các nút
       btnThem.setBackground(new Color(40, 167, 69));
       btnSua.setBackground(new Color(0, 123, 255));
       btnXoa.setBackground(new Color(220, 53, 69));
       btnTimKiem.setBackground(new Color(255, 193, 7));
       btnLamMoi.setBackground(new Color(108, 117, 125));

       btnThem.setForeground(Color.WHITE);
       btnSua.setForeground(Color.WHITE);
       btnXoa.setForeground(Color.WHITE);
       btnTimKiem.setForeground(Color.BLACK);
       btnLamMoi.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnLamMoi);
        
        btnThem.addActionListener(e -> themSinhVien());
        btnSua.addActionListener(e -> suaSinhVien());
        btnXoa.addActionListener(e -> xoaSinhVien());
        btnTimKiem.addActionListener(e -> timKiemSinhVien());
        btnLamMoi.addActionListener(e -> lamMoiSinhVien());
        jButton4.addActionListener(e -> {
        new DiemForm().setVisible(true);
    });
        jButton3.addActionListener(e -> {
        new MonHocForm().setVisible(true);
    });
        jButton2.addActionListener(e -> {
        new LopForm().setVisible(true);
    });
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

// Hoàn thiện giao diện
       jPanel2.removeAll();
       jPanel2.setLayout(new BorderLayout(10, 15));
       jPanel2.setBackground(Color.WHITE);
       jPanel2.setBorder(
     BorderFactory.createEmptyBorder(10, 10, 10, 10)
    );

       jPanel2.add(topPanel, BorderLayout.NORTH);
       jPanel2.add(scrollPane, BorderLayout.CENTER);

       jPanel2.revalidate();
       jPanel2.repaint();

    }
      private void themSinhVien() {
    String maSV = txtMaSV.getText().trim();
    String hoTen = txtHoTen.getText().trim();
    String ngaySinh = txtNgaySinh.getText().trim();
    String gioiTinh = cboGioiTinh.getSelectedItem().toString();
    String diaChi = txtDiaChi.getText().trim();
    String maLop = txtMaLop.getText().trim();

    if (maSV.isEmpty() || hoTen.isEmpty() || ngaySinh.isEmpty() || maLop.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        java.sql.Date ns = java.sql.Date.valueOf(ngaySinh);

        Connection conn = database.DBConnection.getConnection();

        String sql = "INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, maSV);
        ps.setString(2, hoTen);
        ps.setDate(3, ns);
        ps.setString(4, gioiTinh);
        ps.setString(5, diaChi);
        ps.setString(6, maLop);

        ps.executeUpdate();

        DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
        model.addRow(new Object[]{maSV, hoTen, ns, gioiTinh, diaChi, maLop});

        ps.close();
        conn.close();

        JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
      private void suaSinhVien() {
    int row = tblSinhVien.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!");
        return;
    }

    String maSV = txtMaSV.getText().trim();
    String hoTen = txtHoTen.getText().trim();
    String ngaySinh = txtNgaySinh.getText().trim();
    String gioiTinh = cboGioiTinh.getSelectedItem().toString();
    String diaChi = txtDiaChi.getText().trim();
    String maLop = txtMaLop.getText().trim();

    if (hoTen.isEmpty() || ngaySinh.isEmpty() || maLop.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        java.sql.Date ns = java.sql.Date.valueOf(ngaySinh);

        Connection conn = database.DBConnection.getConnection();

        String sql = "UPDATE SinhVien SET HoTen=?, NgaySinh=?, GioiTinh=?, DiaChi=?, MaLop=? WHERE MaSV=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, hoTen);
        ps.setDate(2, ns);
        ps.setString(3, gioiTinh);
        ps.setString(4, diaChi);
        ps.setString(5, maLop);
        ps.setString(6, maSV);

        ps.executeUpdate();

        tblSinhVien.setValueAt(hoTen, row, 1);
        tblSinhVien.setValueAt(ns, row, 2);
        tblSinhVien.setValueAt(gioiTinh, row, 3);
        tblSinhVien.setValueAt(diaChi, row, 4);
        tblSinhVien.setValueAt(maLop, row, 5);

        ps.close();
        conn.close();

        JOptionPane.showMessageDialog(this, "Sửa sinh viên thành công!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
      

private void xoaSinhVien() {
    int row = tblSinhVien.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa sinh viên này và toàn bộ điểm của sinh viên?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String maSV = tblSinhVien.getValueAt(row, 0).toString();

    try {
        Connection conn = database.DBConnection.getConnection();
        conn.setAutoCommit(false);

        String sqlDiem = "DELETE FROM Diem WHERE MaSV = ?";
        PreparedStatement ps1 = conn.prepareStatement(sqlDiem);
        ps1.setString(1, maSV);
        ps1.executeUpdate();
        ps1.close();

        String sqlSV = "DELETE FROM SinhVien WHERE MaSV = ?";
        PreparedStatement ps2 = conn.prepareStatement(sqlSV);
        ps2.setString(1, maSV);
        ps2.executeUpdate();
        ps2.close();

        conn.commit();
        conn.close();

        DefaultTableModel model =
            (DefaultTableModel) tblSinhVien.getModel();
        model.removeRow(row);

        JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}


      private void timKiemSinhVien() {
    String tuKhoa = JOptionPane.showInputDialog(
        this,
        "Nhập mã sinh viên hoặc họ tên cần tìm:"
    );

    if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
    model.setRowCount(0);

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop "
                   + "FROM SinhVien WHERE MaSV LIKE ? OR HoTen LIKE ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + tuKhoa.trim() + "%");
        ps.setString(2, "%" + tuKhoa.trim() + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaSV"),
                rs.getString("HoTen"),
                rs.getDate("NgaySinh"),
                rs.getString("GioiTinh"),
                rs.getString("DiaChi"),
                rs.getString("MaLop")
            });
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
      private void lamMoiSinhVien() {
    DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
    model.setRowCount(0);

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaSV, HoTen, NgaySinh, GioiTinh, DiaChi, MaLop FROM SinhVien";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaSV"),
                rs.getString("HoTen"),
                rs.getDate("NgaySinh"),
                rs.getString("GioiTinh"),
                rs.getString("DiaChi"),
                rs.getString("MaLop")
            });
        }

        rs.close();
        ps.close();
        conn.close();

        txtMaSV.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtMaLop.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ SINH VIÊN");

        jPanel1.setBackground(new java.awt.Color(245, 247, 250));

        jButton1.setBackground(new java.awt.Color(230, 239, 250));
        jButton1.setForeground(new java.awt.Color(70, 80, 90));
        jButton1.setText("QUẢN LÝ SINH VIÊN");
        jButton1.setPreferredSize(new java.awt.Dimension(180, 40));

        jButton2.setBackground(new java.awt.Color(230, 239, 250));
        jButton2.setForeground(new java.awt.Color(70, 80, 90));
        jButton2.setText("QUẢN LÝ LỚP");
        jButton2.setPreferredSize(new java.awt.Dimension(180, 40));

        jButton3.setBackground(new java.awt.Color(230, 239, 250));
        jButton3.setForeground(new java.awt.Color(70, 80, 90));
        jButton3.setText("QUẢN LÝ MÔN HỌC");
        jButton3.setPreferredSize(new java.awt.Dimension(180, 40));
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setBackground(new java.awt.Color(230, 239, 250));
        jButton4.setForeground(new java.awt.Color(70, 80, 90));
        jButton4.setText("QUẢN LÝ ĐIỂM");
        jButton4.setPreferredSize(new java.awt.Dimension(180, 40));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(45, 65, 85));

        jLabel2.setBackground(new java.awt.Color(220, 232, 245));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 55, 70));
        jLabel2.setText("DANH SÁCH SINH VIÊN");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ReflectiveOperationException |
                javax.swing.UnsupportedLookAndFeelException ex) {

            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> new MainForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
