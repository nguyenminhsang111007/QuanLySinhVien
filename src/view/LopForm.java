package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LopForm extends JFrame {

    private JTextField txtMaLop, txtTenLop, txtKhoa;
    private JTable tblLop;
    private DefaultTableModel model;

    public LopForm() {
        setTitle("QUẢN LÝ LỚP");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("QUẢN LÝ LỚP", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel topPanel = new JPanel(new BorderLayout(5, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.NORTH);
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

inputPanel.add(new JLabel("Mã lớp:"));
txtMaLop = new JTextField();
inputPanel.add(txtMaLop);

inputPanel.add(new JLabel("Tên lớp:"));
txtTenLop = new JTextField();
inputPanel.add(txtTenLop);

inputPanel.add(new JLabel("Khóa:"));
txtKhoa = new JTextField();
inputPanel.add(txtKhoa);

topPanel.add(inputPanel, BorderLayout.CENTER);
mainPanel.add(topPanel, BorderLayout.NORTH);
String[] columns = {"Mã lớp", "Tên lớp", "Khóa"};

model = new DefaultTableModel(columns, 0);
tblLop = new JTable(model);
// Chỉnh giao diện tiêu đề bảng
tblLop.getTableHeader().setBackground(new Color(220, 232, 245));
tblLop.getTableHeader().setForeground(new Color(45, 55, 70));
tblLop.getTableHeader().setFont(
    new Font("Segoe UI", Font.BOLD, 14)
);

JScrollPane scrollPane = new JScrollPane(tblLop);

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

buttonPanel.add(btnThem);
buttonPanel.add(btnSua);
buttonPanel.add(btnXoa);
buttonPanel.add(btnTimKiem);

btnThem.addActionListener(e -> themLop());
btnSua.addActionListener(e -> suaLop());
btnXoa.addActionListener(e -> xoaLop());
btnTimKiem.addActionListener(e -> timKiemLop());

mainPanel.add(buttonPanel, BorderLayout.PAGE_END);

        add(mainPanel);
        loadLop();
        
        tblLop.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int row = tblLop.getSelectedRow();

        if (row == -1) {
        return;
    }

        txtMaLop.setText(model.getValueAt(row, 0).toString());
        txtTenLop.setText(model.getValueAt(row, 1).toString());
        txtKhoa.setText(model.getValueAt(row, 2).toString());
    }
});
    }
    private void themLop() {
    String maLop = txtMaLop.getText().trim();
    String tenLop = txtTenLop.getText().trim();
    String khoa = txtKhoa.getText().trim();

    if (maLop.isEmpty() || tenLop.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lớp và tên lớp!");
        return;
    }

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "INSERT INTO Lop (MaLop, TenLop, Khoa) VALUES (?, ?, ?)";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maLop);
        ps.setString(2, tenLop);
        ps.setString(3, khoa);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");

        conn.close();

        loadLop();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void xoaLop() {
    int row = tblLop.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!");
        return;
    }

    String maLop = model.getValueAt(row, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn xóa lớp này?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "DELETE FROM Lop WHERE MaLop = ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, maLop);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Xóa lớp thành công!");

        conn.close();
        loadLop();

        txtMaLop.setText("");
        txtTenLop.setText("");
        txtKhoa.setText("");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Không thể xóa lớp này! Có thể lớp vẫn đang có sinh viên.\n"
            + e.getMessage());
    }
}
    private void suaLop() {
    String maLop = txtMaLop.getText().trim();
    String tenLop = txtTenLop.getText().trim();
    String khoa = txtKhoa.getText().trim();

    if (maLop.isEmpty() || tenLop.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần sửa!");
        return;
    }

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "UPDATE Lop SET TenLop = ?, Khoa = ? WHERE MaLop = ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tenLop);
        ps.setString(2, khoa);
        ps.setString(3, maLop);

        ps.executeUpdate();

        JOptionPane.showMessageDialog(this, "Sửa lớp thành công!");

        conn.close();
        loadLop();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void timKiemLop() {
    String tuKhoa = JOptionPane.showInputDialog(
        this, "Nhập mã lớp hoặc tên lớp cần tìm:"
    );

    if (tuKhoa == null || tuKhoa.trim().isEmpty()) {
        return;
    }

    model.setRowCount(0);

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaLop, TenLop, Khoa FROM Lop "
                   + "WHERE MaLop LIKE ? OR TenLop LIKE ?";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, "%" + tuKhoa.trim() + "%");
        ps.setString(2, "%" + tuKhoa.trim() + "%");

        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaLop"),
                rs.getString("TenLop"),
                rs.getString("Khoa")
            });
        }

        conn.close();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp!");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    private void loadLop() {
    model.setRowCount(0);

    try {
        java.sql.Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT MaLop, TenLop, Khoa FROM Lop";

        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("MaLop"),
                rs.getString("TenLop"),
                rs.getString("Khoa")
            });
        }

        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LopForm().setVisible(true);
        });
    }
}