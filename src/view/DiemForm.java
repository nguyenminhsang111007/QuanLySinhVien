
package view;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DiemForm extends JFrame {

    private JTextField txtMaSV;
    private JTextField txtMaMon;
    private JTextField txtDiem;
    private JTable tblDiem;

    public DiemForm() {
        setTitle("QUẢN LÝ ĐIỂM SINH VIÊN");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("QUẢN LÝ ĐIỂM SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);

        txtMaSV = new JTextField();
        txtMaMon = new JTextField();
        txtDiem = new JTextField();

        formPanel.add(new JLabel("Mã sinh viên:"));
        formPanel.add(txtMaSV);

        formPanel.add(new JLabel("Mã môn học:"));
        formPanel.add(txtMaMon);

        formPanel.add(new JLabel("Điểm:"));
        formPanel.add(txtDiem);

        String[] columns = {"Mã sinh viên", "Mã môn học", "Điểm"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        tblDiem = new JTable(model);
        // Chỉnh giao diện tiêu đề bảng
        tblDiem.getTableHeader().setBackground(new Color(220, 232, 245));
        tblDiem.getTableHeader().setForeground(new Color(45, 55, 70));
        tblDiem.getTableHeader().setFont(
        new Font("Segoe UI", Font.BOLD, 14)
     );

        JPanel centerPanel = new JPanel(new BorderLayout(10, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(tblDiem), BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

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
        
        btnThem.addActionListener(e -> themDiem());
        btnSua.addActionListener(e -> suaDiem());
        btnXoa.addActionListener(e -> xoaDiem());
        btnTimKiem.addActionListener(e -> timKiemDiem());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadDiem();
    }
    private void xoaDiem() {
    int row = tblDiem.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn điểm cần xóa!");
        return;
    }

    String maSV = tblDiem.getValueAt(row, 0).toString();
    String maMon = tblDiem.getValueAt(row, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa điểm này?",
        "Xác nhận xóa",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "DELETE FROM Diem WHERE MaSV = ? AND MaMon = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maSV);
        ps.setString(2, maMon);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Xóa điểm thành công!");

        ps.close();
        conn.close();

        loadDiem();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void timKiemDiem() {
    String maSV = JOptionPane.showInputDialog(
        this, "Nhập mã sinh viên cần tìm:"
    );

    if (maSV == null || maSV.trim().isEmpty()) {
        return;
    }

    DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
    model.setRowCount(0);

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaSV, MaMon, Diem FROM Diem WHERE MaSV LIKE ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + maSV.trim() + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaSV"),
                rs.getString("MaMon"),
                rs.getDouble("Diem")
            });
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy điểm!");
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void suaDiem() {
    int row = tblDiem.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn điểm cần sửa!");
        return;
    }

    String maSV = txtMaSV.getText().trim();
    String maMon = txtMaMon.getText().trim();
    String diem = txtDiem.getText().trim();

    if (maSV.isEmpty() || maMon.isEmpty() || diem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        double diemSo = Double.parseDouble(diem);

        if (diemSo < 0 || diemSo > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải từ 0 đến 10!");
            return;
        }

        Connection conn = database.DBConnection.getConnection();

        String sql = "UPDATE Diem SET Diem = ? WHERE MaSV = ? AND MaMon = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, diemSo);
        ps.setString(2, maSV);
        ps.setString(3, maMon);

        int result = ps.executeUpdate();

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Sửa điểm thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy điểm cần sửa!");
        }

        ps.close();
        conn.close();

        loadDiem();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Điểm phải là số!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void themDiem() {
    String maSV = txtMaSV.getText().trim();
    String maMon = txtMaMon.getText().trim();
    String diem = txtDiem.getText().trim();

    if (maSV.isEmpty() || maMon.isEmpty() || diem.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    try {
        double diemSo = Double.parseDouble(diem);

        if (diemSo < 0 || diemSo > 10) {
            JOptionPane.showMessageDialog(this, "Điểm phải từ 0 đến 10!");
            return;
        }

        Connection conn = database.DBConnection.getConnection();

        String sql = "INSERT INTO Diem (MaSV, MaMon, Diem) VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maSV);
        ps.setString(2, maMon);
        ps.setDouble(3, diemSo);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Thêm điểm thành công!");

        ps.close();
        conn.close();

        loadDiem();

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Điểm phải là số!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void loadDiem() {
    DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
    model.setRowCount(0);

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaSV, MaMon, Diem FROM Diem";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaSV"),
                rs.getString("MaMon"),
                rs.getDouble("Diem")
            });
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi tải điểm: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DiemForm().setVisible(true);
        });
    }
}
