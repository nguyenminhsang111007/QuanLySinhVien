package view;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnThoat;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);

        JLabel lblTitle = new JLabel("QUẢN LÝ SINH VIÊN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 23));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(75, 35, 300, 35);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lblTitle);

        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setBounds(55, 110, 100, 30);
        mainPanel.add(lblTaiKhoan);

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(155, 110, 230, 30);
        mainPanel.add(txtTaiKhoan);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(55, 160, 100, 30);
        mainPanel.add(lblMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(155, 160, 230, 30);
        mainPanel.add(txtMatKhau);

        btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setBounds(85, 225, 130, 35);
        btnDangNhap.setBackground(new Color(0, 123, 255));
        btnDangNhap.setForeground(Color.WHITE);
        mainPanel.add(btnDangNhap);

        btnThoat = new JButton("THOÁT");
        btnThoat.setBounds(235, 225, 110, 35);
        btnThoat.setBackground(new Color(220, 53, 69));
        btnThoat.setForeground(Color.WHITE);
        mainPanel.add(btnThoat);

        add(mainPanel);
        btnDangNhap.addActionListener(e -> dangNhap());
        btnThoat.addActionListener(e -> System.exit(0));
    }
    private void dangNhap() {
    String taiKhoan = txtTaiKhoan.getText().trim();
    String matKhau = new String(txtMatKhau.getPassword());

    if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
        return;
    }

    try {
        Connection conn = database.DBConnection.getConnection();

        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, taiKhoan);
        ps.setString(2, matKhau);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }

        rs.close();
        ps.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}