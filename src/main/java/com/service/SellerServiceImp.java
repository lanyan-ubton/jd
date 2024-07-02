package com.service;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bean.Conn;
import com.bean.Seller;

@Service
public class SellerServiceImp implements SellerService {
    private final Conn conn = new Conn();

    @Override
    public boolean save(Seller info) {
        // 检查用户名是否已经存在
        for (Seller seller : getSellers()) {
            if (seller.getName().equals(info.getName())) {
                // 用户名重复，注册失败
                return false;
            }
        }

        // 用户名不重复，添加用户
        String sql = "INSERT INTO seller(name, pwd, money,headPortrait) VALUES" +
                "('" + info.getName() + "', '" + info.getPwd() + "', " + info.getMoney() + ", '" +
                info.getHeadPortrait() + "')";
        int result = 0;
        try {
            result = conn.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        System.out.println(sql);
        if (result != 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean login(Seller loginUser) {
        // 检查用户名和密码是否匹配
        for (Seller seller : getSellers()) {
            if (seller.getName().equals(loginUser.getName()) && seller.getPwd().equals(loginUser.getPwd())) {
                return true;// 验证通过
            }
        }
        return false;// 验证失败
    }

    @Override
    public List<Seller> getSellers() {
        List<Seller> list = new ArrayList<>();
        String sql = "SELECT * FROM seller ORDER BY name ASC";
        try (ResultSet rs = conn.executeQuery(sql)) {
            while (rs.next()) {
                Seller info = new Seller();
                info.setName(rs.getString("name"));
                info.setPwd(rs.getString("pwd"));
                info.setMoney(rs.getDouble("money"));
                info.setHeadPortrait(rs.getBytes("headPortrait"));
                list.add(info);
            }
        } catch (SQLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } finally {
        }
        return list;
    }

    @Override
    public Seller getSeller(String sellerName) {
        // TODO Auto-generated method stub
        for (Seller seller : getSellers()) {
            if (seller.getName().equals(sellerName)) {
                return seller;
            }
        }
        return new Seller();
    }

    @Override
    public boolean update(Seller info) {
        String sql = "UPDATE seller SET pwd = ?, money = ?, headPortrait = ?  WHERE name = ?";
        try (Connection connection = conn.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, info.getPwd());
            ps.setDouble(2, info.getMoney());
            if (info.getHeadPortrait() != null) {
                ps.setBlob(3, new ByteArrayInputStream(info.getHeadPortrait()));
            } else {
                byte[] existingHeadPortrait = getSeller(info.getName()).getHeadPortrait();
                if (existingHeadPortrait != null) {
                    ps.setBlob(3, new ByteArrayInputStream(existingHeadPortrait));
                } else {
                    ps.setNull(3, Types.BLOB);
                }
            }
            ps.setString(4, info.getName());
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // 可以在这里添加日志记录，以便更详细地了解错误
        }
        return false;
    }

    @Override
    public boolean updateSeller(String sellerName, int num) {
        String sql = "UPDATE seller SET money = money + ? WHERE name = ?";
        try (Connection connection = conn.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, num);
            ps.setString(2, sellerName);
            int result = ps.executeUpdate();
            ps.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // 可以在这里添加日志记录，以便更详细地了解错误
        }
        return false;
    }
}