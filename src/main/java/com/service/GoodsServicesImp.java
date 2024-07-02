package com.service;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bean.Conn;
import com.bean.Goods;
@Service
public class GoodsServicesImp implements GoodsService
{
    	private final Conn conn = new Conn();
	    
    	public static String encode(byte[] input) {
	        return Base64.getEncoder().encodeToString(input);
	    }
	    // 从数据库中检索商品列表
	    @Override
	    public List<Goods> getGoods() {
	        List<Goods> goodsList = new ArrayList<>();
	        String sql = "SELECT * FROM goods";

	        try (ResultSet resultSet = conn.executeQuery(sql)) {
	            while (resultSet.next()) {
	                Goods goods = new Goods();
	                // 设置商品属性
	                goods.setId(resultSet.getString("id"));
	                goods.setName(resultSet.getString("name"));
	                goods.setPrice(resultSet.getDouble("price"));
	                goods.setCount(resultSet.getInt("count"));
	                goods.setDescription(resultSet.getString("description"));
	                goods.setSellerName(resultSet.getString("sellerName"));
	                // 获取图片数据
	                byte[] pictureData = resultSet.getBytes("picture");
	                goods.setPicture(pictureData);
	                goodsList.add(goods); // 将商品添加到列表
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        System.out.println(goodsList.size());
	        return goodsList;
	    }

	    // 根据商品ID获取单个商品的详细信息
	    @Override
	    public Goods getGoods(String id) {
	        String sql = "SELECT * FROM goods WHERE id = " + id;
	        Goods goods = null;
	        ResultSet resultSet;
			try {
				resultSet = conn.executeQuery(sql);
					if (resultSet.next()) 
					{
					    goods = new Goods();
					    // 设置商品属性
					    goods.setId(resultSet.getString("id"));
					    goods.setName(resultSet.getString("name"));
					    goods.setPrice(resultSet.getDouble("price"));
					    goods.setCount(resultSet.getInt("count"));
					    goods.setDescription(resultSet.getString("description"));
		                goods.setSellerName(resultSet.getString("sellerName"));
					    // 获取图片数据
					    byte[] pictureData = resultSet.getBytes("picture");
					    goods.setPicture(pictureData);
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
	        return goods;
	    }
		
	    @Override
		public boolean update(Goods goods) {
		    String sql = "UPDATE goods SET name = ?, picture = ?, price = ?, count = ?, description = ?, sellerName = ? WHERE id = ?";
		    
		    try (Connection connection = conn.getConnection();
		         PreparedStatement ps = connection.prepareStatement(sql)) {
		        
		        ps.setString(1, goods.getName());
		        
		        if (goods.getPicture() != null) {
		            ps.setBlob(2, new ByteArrayInputStream(goods.getPicture()));
		        } else {
		            ps.setNull(2, Types.BLOB);
		        }
		        ps.setDouble(3, goods.getPrice());
		        ps.setInt(4, goods.getCount());
		        ps.setString(5, goods.getDescription());
		        ps.setString(6, goods.getSellerName());
		        ps.setString(7, goods.getId());
		        System.out.println("id:"+goods.getId());
		        System.out.println(ps.toString());
		        int rowsUpdated = ps.executeUpdate();
		        
		        if (rowsUpdated > 0) {
		            System.out.println("更新成功。");
		            return true;
		        } else {
		            System.out.println("更新失败。没有影响到任何行。");
		            return false;
		        }
		        
		    } catch (SQLException e) {
		        System.err.println("更新商品时出错: " + e.getMessage());
		        return false;
		    }
		}

		//添加商品
		@Override
		public String addGoods(Goods goods)
		{
			//先存入商品表中
			String id="";
			List<Goods> list = getGoods();
			while(true)
			{
				id =  Integer.toString((int)(Math.random()*1000));
				for(Goods exitGoods:list)
				{
					if(exitGoods.getId().equals(id))
					{
						continue;
					}
				}
				break;
			}
			String name = goods.getName();
			byte[] picture = goods.getPicture();
			double price = goods.getPrice();
			int count = goods.getCount();
			String sellerName = goods.getSellerName();
			String description = goods.getDescription();
			String sql = "INSERT INTO goods (id, name, picture, price, count, description, sellerName) "
			                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
			try (Connection connection = conn.getConnection()) {
				PreparedStatement ps = connection.prepareStatement(sql);
			    ps.setString(1, id);
			    ps.setString(2, name);
			    if(picture!=null)
			    {
			    	ps.setBlob(3,new ByteArrayInputStream(picture));
			    }
			    	else
			        ps.setNull(3, Types.BLOB);
			       	ps.setDouble(4, price);
			        ps.setInt(5, count);
			        ps.setString(6, description);
			        ps.setString(7, sellerName);			        	
			        // 执行插入操作
			        ps.executeUpdate();
			        ps.close();
			 } catch (SQLException e) {
				 	// 处理异常
			        e.printStackTrace();
			   }
			return id;
		}
			
		@Override
		public boolean deleteGoods(String goodsId) {
			String sql ="delete from goods where id = "+goodsId;
			try (Connection connection = conn.getConnection()) {
				PreparedStatement ps = connection.prepareStatement(sql);
				int rowsAffected = ps.executeUpdate();
				// 处理删除后的逻辑，比如确认是否成功删除了记录
				if (rowsAffected > 0) {
					System.out.println("删除成功");
				} else {
				    System.out.println("未找到符合条件的记录");
				}
			} catch (SQLException e) {
				 // 处理 SQL 异常
				 	e.printStackTrace();
			}

			return false;
			}
}
