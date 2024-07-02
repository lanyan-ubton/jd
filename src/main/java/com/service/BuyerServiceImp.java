package com.service;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import com.bean.Buyer;
import com.bean.Conn;
import com.bean.Goods;

@Service
public class BuyerServiceImp implements BuyerService {

	private Conn conn = new Conn();
	@Autowired
	private GoodsService goodsService;

	@Override
	public List<Buyer> getBuyers() {
		List<Buyer> list = new ArrayList<>();
		String sql = "SELECT * FROM buyer ORDER BY name ASC";
		ResultSet rs;
		try {
			rs = conn.executeQuery(sql);
			while (rs.next()) {
				Buyer info = new Buyer();
				info.setName(rs.getString("name"));
				info.setPwd(rs.getString("pwd"));
				info.setMoney(rs.getDouble("money"));
				info.setSex(rs.getString("sex"));
				info.setMyGoodsIds(rs.getString("myGoodsIds"));
				info.setHeadPortrait(rs.getBytes("headPortrait"));
				list.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean save(Buyer info) {
		// SQL语句，用于插入买家信息
		String sql = "INSERT INTO buyer(name, pwd, money, sex, headPortrait) VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = conn.getConnection();
				// 获取数据库连接
				PreparedStatement ps = connection.prepareStatement(sql)) {
			// 设置SQL语句中的参数
			ps.setString(1, info.getName());
			ps.setString(2, info.getPwd());
			ps.setDouble(3, info.getMoney());
			ps.setString(4, info.getSex());
			// 判断买家头像是否为空
			if (info.getHeadPortrait() != null) {
				// 如果不为空，则将头像设置为SQL语句中的第六个参数
				ps.setBlob(5, new ByteArrayInputStream(info.getHeadPortrait()));
			} else {
				// 如果为空，则将SQL语句中的第六个参数设置为NULL
				ps.setNull(5, Types.BLOB);
			}
			// 执行SQL语句，并返回受影响的行数
			int result = ps.executeUpdate();
			// 如果受影响的行数不为0，则返回true，表示保存成功
			return result != 0;
		} catch (SQLException e) {
			// 如果执行SQL语句时发生异常，则打印异常堆栈信息，并返回false，表示保存失败
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Buyer info) {
		String sql = "UPDATE buyer SET pwd = ?, money = ?, sex = ?, myGoodsIds = ?, headPortrait = ? WHERE name = ?";
		try (Connection connection = conn.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, info.getPwd());
			ps.setDouble(2, info.getMoney());
			ps.setString(3, info.getSex());
			ps.setString(4, info.getMyGoodsIds());
			if (info.getHeadPortrait() != null) {
				ps.setBlob(5, new ByteArrayInputStream(info.getHeadPortrait()));
			} else {
				byte[] existingHeadPortrait = getBuyer(info.getName()).getHeadPortrait();
				if (existingHeadPortrait != null) {
					ps.setBlob(5, new ByteArrayInputStream(existingHeadPortrait));
				} else {
					ps.setNull(5, Types.BLOB);
				}
			}
			ps.setString(6, info.getName());
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
	public Buyer getBuyer(String name) {
		Buyer info = new Buyer();
		String sql = "SELECT * FROM buyer WHERE name = ?";
		try {
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				info.setName(name);
				info.setPwd(rs.getString("pwd"));
				info.setMoney(rs.getDouble("money"));
				info.setSex(rs.getString("sex"));
				info.setMyGoodsIds(rs.getString("myGoodsIds"));
				info.setHeadPortrait(rs.getBytes("headPortrait"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return info;
	}

	@Override
	public boolean login(Buyer loginBuyer) {
		List<Buyer> buyers = getBuyers();
		for (Buyer buyer : buyers) {
			if (buyer.getName().equals(loginBuyer.getName())
					&& buyer.getPwd().equals(loginBuyer.getPwd())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Goods> getGoods(Buyer buyer) {
		// TODO 自动生成的方法存根
		String[] myGoodsIds = {};
		List<Goods> myGoods = new ArrayList<Goods>();
		String sql = "select myGoodsIds from buyer where name ='" + buyer.getName() + "';";
		ResultSet rs;
		try {
			rs = conn.executeQuery(sql);
			if (rs.getString("myGoodsIds") == null) {
				return myGoods;
			}
			while (rs.next()) {
				// 已有的商品id
				myGoodsIds = rs.getString("myGoodsIds").split(",");
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for (Goods goods : goodsService.getGoods()) {
			for (String id : myGoodsIds) {
				if (goods.getId().equals(id)) {
					myGoods.add(goods);
				}
			}
		}
		return myGoods;
	}

	@Override
	public boolean addGoods(Buyer buyer, Goods goods) {
		// TODO 自动生成的方法存根
		String getSql = "select myGoodsIds from buyer where name ='" + buyer.getName() + "';";
		String ids = null;
		ResultSet rs;
		StringBuilder idsBuilder = new StringBuilder();
		try {
			boolean first = true;
			rs = conn.executeQuery(getSql);
			while (rs.next()) {
				ids = rs.getString("myGoodsIds");
				if (ids != null && !ids.isEmpty()) {
					if (!first) {
						idsBuilder.append(",");
					}
					idsBuilder.append(ids);
					first = false;
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ids = idsBuilder.toString();
		if (!ids.isEmpty()) {
			for (String id : ids.split(",")) {
				if (goods.getId().equals(id)) {
					return false;
				}
			}
			ids = ids + "," + goods.getId();
		} else {
			ids = goods.getId();
		}
		String setSql = "update buyer set myGoodsIds = '" + ids + "' where name = '" + buyer.getName() + "';";

		int result = 0;
		try {
			result = conn.executeUpdate(setSql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (result == 0)
			return false;
		else
			return true;
	}

	@Override
	public boolean deleteGoods(Buyer buyer, Goods goods) {
		String getSql = "select myGoodsIds from buyer where name ='" + buyer.getName() + "';";
		List<String> ids = new ArrayList<>();
		ResultSet rs;
		try {
			rs = conn.executeQuery(getSql);
			while (rs.next()) {
				for (String id : rs.getString("myGoodsIds").split(",")) {
					ids.add(id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Iterator<String> iterator = ids.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(goods.getId())) {
				iterator.remove();
			}
		}
		String join = StringUtils.join(ids, ",");
		// SQL语句的修改还未完成，主要问题是ids中无法准确删除对应的数据
		String setSql = "update buyer set myGoodsIds = '" + join + "' where name = '" + buyer.getName() + "';";
		int result = 0;
		try {
			result = conn.executeUpdate(setSql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (result == 0)
			return false;
		else
			return true;
	}

	public boolean sale(Buyer buyer, int money) {
		String getSql = "select money from buyer where name ='" + buyer.getName() + "';";
		int m = 0;
		ResultSet rs;
		try {
			rs = conn.executeQuery(getSql);
			m = rs.getInt("money");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		m -= money;
		String setSql = "update buyer set money = '" + m + "' where name = '" + buyer.getName() + "';";
		int result = 0;
		try {
			result = conn.executeUpdate(setSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (result == 0)
			return false;
		else
			return true;
	}
}