package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MonHocForm extends JFrame {

    private JTextField txtMaMon, txtTenMon, txtSoTinChi;
    private JTable tblMonHoc;
    private DefaultTableModel model;

    public MonHocForm() {
        setTitle("QUẢN LÝ MÔN HỌC");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("QUẢN LÝ MÔN HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel topPanel = new JPanel(new BorderLayout(5, 10));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Mã môn:"));
        txtMaMon = new JTextField();
        inputPanel.add(txtMaMon);

        inputPanel.add(new JLabel("Tên môn:"));
        txtTenMon = new JTextField();
        inputPanel.add(txtTenMon);

        inputPanel.add(new JLabel("Số tín chỉ:"));
        txtSoTinChi = new JTextField();
        inputPanel.add(txtSoTinChi);

        topPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Mã môn", "Tên môn", "Số tín chỉ"};

        model = new DefaultTableModel(columns, 0);
        tblMonHoc = new JTable(model);
        // Chỉnh giao diện tiêu đề bảng
        tblMonHoc.getTableHeader().setBackground(new Color(220, 232, 245));
        tblMonHoc.getTableHeader().setForeground(new Color(45, 55, 70));
        tblMonHoc.getTableHeader().setFont(
    new Font("Segoe UI", Font.BOLD, 14)
);

        JScrollPane scrollPane = new JScrollPane(tblMonHoc);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnThem = new JButton("THÊM");
        JButton btnSua = new JButton("SỬA");
        JButton btnXoa = new JButton("XÓA");
        JButton btnTimKiem = new JButton("TÌM KIẾM");
        // Chỉnh màu các nút chức năng
        btnThem.setBackground(new Color(40, 167, 69));
        btnThem.setForeground(Color.WHITE);

        btnSua.setBackground(new Color(0, 123, 255));
        btnSua.setForeground(Color.WHITE);

        btnXoa.setBackground(new Color(220, 53, 69));
        btnXoa.setForeground(Color.WHITE);

        btnTimKiem.setBackground(new Color(255, 193, 7));
        btnTimKiem.setForeground(Color.BLACK);
        
        btnThem.addActionListener(e -> themMonHoc());
        btnSua.addActionListener(e -> suaMonHoc());
        btnXoa.addActionListener(e -> xoaMonHoc());
        btnTimKiem.addActionListener(e -> timKiemMonHoc());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);

        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        loadMonHoc();
        tblMonHoc.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = tblMonHoc.getSelectedRow();

        if (row == -1) {
            return;
        }

        txtMaMon.setText(model.getValueAt(row, 0).toString());
        txtTenMon.setText(model.getValueAt(row, 1).toString());
        txtSoTinChi.setText(model.getValueAt(row, 2).toString());
    }
});
    }
private void loadMonHoc() {
    model.setRowCount(0);

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaMon, TenMon, SoTinChi FROM MonHoc";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaMon"),
                rs.getString("TenMon"),
                rs.getInt("SoTinChi")
            });
        }

        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
public static void main(String[] args) {
    new MonHocForm().setVisible(true);
}

private void themMonHoc() {
    String maMon = txtMaMon.getText().trim();
    String tenMon = txtTenMon.getText().trim();
    String soTinChi = txtSoTinChi.getText().trim();

    if (maMon.isEmpty() || tenMon.isEmpty() || soTinChi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        int tinChi = Integer.parseInt(soTinChi);

        if (tinChi <= 0) {
            JOptionPane.showMessageDialog(this, "Số tín chỉ phải lớn hơn 0!");
            return;
        }

        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "INSERT INTO MonHoc (MaMon, TenMon, SoTinChi) VALUES (?, ?, ?)";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maMon);
        ps.setString(2, tenMon);
        ps.setInt(3, tinChi);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Thêm môn học thành công!");

        conn.close();

        loadMonHoc();

        txtMaMon.setText("");
        txtTenMon.setText("");
        txtSoTinChi.setText("");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
private void suaMonHoc() {
    String maMon = txtMaMon.getText().trim();
    String tenMon = txtTenMon.getText().trim();
    String soTinChi = txtSoTinChi.getText().trim();

    if (maMon.isEmpty() || tenMon.isEmpty() || soTinChi.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        int tinChi = Integer.parseInt(soTinChi);

        if (tinChi <= 0) {
            JOptionPane.showMessageDialog(this, "Số tín chỉ phải lớn hơn 0!");
            return;
        }

        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "UPDATE MonHoc SET TenMon = ?, SoTinChi = ? WHERE MaMon = ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tenMon);
        ps.setInt(2, tinChi);
        ps.setString(3, maMon);

        int result = ps.executeUpdate();

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Sửa môn học thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã môn học!");
        }

        conn.close();
        loadMonHoc();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
private void xoaMonHoc() {
    int row = tblMonHoc.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học cần xóa!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa môn học này?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String maMon = model.getValueAt(row, 0).toString();

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "DELETE FROM MonHoc WHERE MaMon = ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maMon);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Xóa môn học thành công!");

        conn.close();
        loadMonHoc();

        txtMaMon.setText("");
        txtTenMon.setText("");
        txtSoTinChi.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            this,
            "Không thể xóa môn học này! Có thể môn học đang có điểm liên quan.\n"
            + "Chi tiết: " + e.getMessage()
        );
    }
}
private void timKiemMonHoc() {
    String tuKhoa = JOptionPane.showInputDialog(
        this,
        "Nhập mã môn hoặc tên môn cần tìm:"
    );

    if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
        return;
    }

    model.setRowCount(0);

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaMon, TenMon, SoTinChi FROM MonHoc "
                   + "WHERE MaMon LIKE ? OR TenMon LIKE ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + tuKhoa.trim() + "%");
        ps.setString(2, "%" + tuKhoa.trim() + "%");

        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaMon"),
                rs.getString("TenMon"),
                rs.getInt("SoTinChi")
            });
        }

        conn.close();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy môn học!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
}