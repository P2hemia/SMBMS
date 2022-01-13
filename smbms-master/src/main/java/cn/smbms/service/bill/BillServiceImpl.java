package cn.smbms.service.bill;

import cn.smbms.dao.BaseDao;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.bill.BillDaoImpl;
import cn.smbms.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService {
	
	private BillDao billDao;
	public BillServiceImpl(){
		billDao = new BillDaoImpl();
	}
	//增加订单
	@Override
	public boolean add(Bill bill) {
		boolean flag = false;
		Connection connection = null;
		try {
			connection = BaseDao.getConnection();
			connection.setAutoCommit(false);//开启JDBC事务管理
			if(billDao.add(connection,bill) > 0) {
				flag = true;
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("rollback==================");
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			//在service层进行connection连接的关闭
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	//通过条件获取订单列表-模糊查询-billList
	@Override
	public List<Bill> getBillList(Bill bill) {
		Connection connection = null;
		List<Bill> billList = null;
		System.out.println("query productName ---- > " + bill.getProductName());
		System.out.println("query providerId ---- > " + bill.getProviderId());
		System.out.println("query isPayment ---- > " + bill.getIsPayment());
		
		try {
			connection = BaseDao.getConnection();
			billList = billDao.getBillList(connection, bill);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return billList;
	}

	//通过billId删除Bill
	@Override
	public boolean deleteBillById(String delId) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			if(billDao.deleteBillById(connection, delId) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

	//通过billId获取Bill
	@Override
	public Bill getBillById(String id) {
		Bill bill = null;
		Connection connection = null;
		try{
			connection = BaseDao.getConnection();
			bill = billDao.getBillById(connection, id);
		}catch (Exception e) {
			e.printStackTrace();
			bill = null;
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return bill;
	}

	//修改订单信息
	@Override
	public boolean modify(Bill bill) {
		Connection connection = null;
		boolean flag = false;
		try {
			connection = BaseDao.getConnection();
			if(billDao.modify(connection,bill) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return flag;
	}

}
