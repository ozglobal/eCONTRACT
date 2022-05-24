package com.oz.unitel.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BlobType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oz.unitel.model.Contracts;

@Transactional
@Component
public class ContractDAOImpl implements ContractDAO{
	@Autowired
    private SessionFactory sessionFactory;
	
	public int save(Contracts contract) {
		Session session = sessionFactory.getCurrentSession();
        
        return (Integer)session.save(contract);
	}
	
	public void update(Contracts contract) {
		sessionFactory.getCurrentSession().update(contract);
		sessionFactory.getCurrentSession().flush();
	}
	
	public void saveOrUpdate(Contracts contract) {
		Session session = sessionFactory.getCurrentSession();
        
        session.saveOrUpdate(contract);
	}
	
	public void delete(Contracts contract) {
		Session session = sessionFactory.getCurrentSession();
        
        session.delete(contract);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contracts> getList(String start, String end) {
		Session session = sessionFactory.getCurrentSession();
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = new Date();
		Date endDate   = new Date();
		try {
			startDate = transFormat.parse(start+" 00:00:00");
			endDate   = transFormat.parse(end+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(">>>>>>>>>> startDate:"+startDate);
		System.out.println(">>>>>>>>>> endDate:"+endDate);
		
        Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.between("updated_date", startDate, endDate));
        criteria.addOrder(Order.desc("updated_date"));
        return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Contracts> getList(String user_name) {
		Session session = sessionFactory.getCurrentSession();
		
        Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("created_id", user_name));
        criteria.addOrder(Order.desc("updated_date"));
        return criteria.list();
	}
	
	public Contracts getContract(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("id", id)); 
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
        
	}

	public Contracts getContract(String prod_no) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prod_no)); 
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
        
	}
	
	public Contracts getContract(String prod_no, String contract_type) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prod_no));
        criteria.add(Restrictions.eq("contract_type", contract_type));
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
        
	}
	
	public Contracts getContract(String prodNo, String contractType, String contractId) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prodNo));
        criteria.add(Restrictions.eq("contract_type", contractType));
        criteria.add(Restrictions.eq("contract_id", contractId));
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
        
	}
	
	public Contracts getContractJ(String prodNo, String contractId) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prodNo));
        criteria.add(Restrictions.eq("contract_id", contractId));
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
        
	}
	
	public Contracts getUncompletedContract(String prod_no, String completed_status) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prod_no)); 
        criteria.add(Restrictions.ne("contract_status", completed_status));
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getUncompletedContract(String prod_no, String completed_status, String user_name) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Contracts.class);
        criteria.add(Restrictions.eq("product_number", prod_no)); 
        criteria.add(Restrictions.ne("contract_status", completed_status));
        criteria.add(Restrictions.eq("created_id", user_name)); 
        if(criteria.list().size() != 0) {
        	return (Contracts) criteria.list().get(0);
        }else {
        	return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Contracts> getLatestContractsGroupByProd(String start, String end) {
		Session session = sessionFactory.getCurrentSession();
		
		String startDate = start + " 00:00:00";
		String endDate   = end + " 23:59:59";
		Query query = session.createSQLQuery("SELECT * FROM econtract.tb_contracts"
				+ " WHERE updated_date IN ("
				+ " 	SELECT MAX(updated_date) FROM econtract.tb_contracts"
				+ "     WHERE updated_date >= :startDate AND updated_date <= :endDate "
				+ " 	GROUP BY product_number"
				+ " ) ORDER BY updated_date desc ")  
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("startDate", 	startDate);
		query.setParameter("endDate", 		endDate);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
        
        return (List<Contracts>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Contracts> getLatestContractsGroupByContractId(String start, String end) {
		Session session = sessionFactory.getCurrentSession();
		
		String startDate = start + " 00:00:00";
		String endDate   = end + " 23:59:59";
		Query query = session.createSQLQuery("SELECT * FROM econtract.tb_contracts"
				+ " WHERE updated_date IN ("
				+ " 	SELECT MAX(updated_date) FROM econtract.tb_contracts"
				+ "     WHERE updated_date >= :startDate AND updated_date <= :endDate "
				+ " 	GROUP BY contract_id"
				+ " ) ORDER BY updated_date desc ")  
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("startDate", 	startDate);
		query.setParameter("endDate", 		endDate);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
        
        return (List<Contracts>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Contracts> getLatestContractsGroupByProd(String user_name, String start, String end) {
		Session session = sessionFactory.getCurrentSession();
		 
		String startDate = start + " 00:00:00";
		String endDate   = end + " 23:59:59";
		
		Query query = session.createSQLQuery("SELECT * FROM econtract.tb_contracts"
				+ " WHERE updated_date IN ("
				+ " 	SELECT MAX(updated_date) FROM econtract.tb_contracts"
				+ "     WHERE created_id = :user_name"
				+ "     AND updated_date >= :startDate AND updated_date <= :endDate "
				+ " 	GROUP BY product_number"
				+ " ) ORDER BY updated_date desc ") 
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("user_name", user_name);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", 	endDate);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
        
        return (List<Contracts>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Contracts> getLatestContractsGroupByContractId(String user_name, String start, String end) {
		Session session = sessionFactory.getCurrentSession();
		 
		String startDate = start + " 00:00:00";
		String endDate   = end + " 23:59:59";
		
		Query query = session.createSQLQuery("SELECT * FROM econtract.tb_contracts"
				+ " WHERE updated_date IN ("
				+ " 	SELECT MAX(updated_date) FROM econtract.tb_contracts"
				+ "     WHERE created_id = :user_name"
				+ "     AND updated_date >= :startDate AND updated_date <= :endDate "
				+ " 	GROUP BY contract_id"
				+ " ) ORDER BY updated_date desc ") 
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("user_name", user_name);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", 	endDate);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
        
        return (List<Contracts>)query.list();
	}

	public Contracts getLatestContract(String prod_no) {
		Session session = sessionFactory.getCurrentSession();
		
		 Query query = session.createSQLQuery("SELECT *"
					+ " FROM econtract.tb_contracts"
					+ " WHERE updated_date = (" 
					+ "				SELECT MAX(j.updated_date) " 
					+ " 			FROM (" 
					+ "					SELECT * " 
					+ "					FROM econtract.tb_contracts" 
					+ "					WHERE product_number = :prod_no" 
					+ "				) j" 
					+ " )")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prod_no", prod_no);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestContractJ(String contractId) {
		Session session = sessionFactory.getCurrentSession();
		
		 Query query = session.createSQLQuery("SELECT *"
					+ " FROM econtract.tb_contracts"
					+ " WHERE updated_date = (" 
					+ "				SELECT MAX(j.updated_date) " 
					+ " 			FROM (" 
					+ "					SELECT * " 
					+ "					FROM econtract.tb_contracts" 
					+ "					WHERE contract_id = :contractId" 
					+ "				) j" 
					+ " )")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("contractId", contractId);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestUncompletedContract(String prod_no, String completed_status, String user_name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT *"
				+ " FROM econtract.tb_contracts"
				+ " WHERE updated_date = (" 
				+ "				SELECT MAX(j.updated_date) " 
				+ " 			FROM (" 
				+ "					SELECT * " 
				+ "					FROM econtract.tb_contracts" 
				+ "					WHERE product_number = :prod_no" 
				+ "					AND contract_status != :completed_status" 
				+ " 				AND created_id = :created_id"
				+ "				) j" 
				+ " )")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prod_no", prod_no);
		query.setParameter("completed_status", completed_status);
		query.setParameter("created_id", user_name);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestUncompletedContractJ(String prodNo, String contractType, String completedStatus, String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("SELECT *"
				+ " FROM econtract.tb_contracts"
				+ " WHERE updated_date = (" 
				+ "				SELECT MAX(j.updated_date) " 
				+ " 			FROM (" 
				+ "					SELECT * " 
				+ "					FROM econtract.tb_contracts" 
				+ "					WHERE product_number = :prodNo" 
				+ "					AND contract_status != :completedStatus"
				+ "					AND contract_type = :contractType" 
				+ " 				AND created_id = :userName"
				+ "				) j" 
				+ " )"
				+ " AND product_number = :prodNo"
				+ " AND contract_type = :contractType")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prodNo", prodNo);
		query.setParameter("contractType", contractType);
		query.setParameter("completedStatus", completedStatus);
		query.setParameter("userName", userName);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestUncompletedContract(String prod_no, String completed_status) {
        Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createSQLQuery("SELECT *"
				+ " FROM econtract.tb_contracts"
				+ " WHERE updated_date = (" 
				+ "				SELECT MAX(j.updated_date) " 
				+ " 			FROM (" 
				+ "					SELECT * " 
				+ "					FROM econtract.tb_contracts" 
				+ "					WHERE product_number = :prod_no" 
				+ "					AND contract_status != :completed_status" 
				+ "				) j" 
				+ " )")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prod_no", prod_no);
		query.setParameter("completed_status", completed_status);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestUncompletedContractJ(String prodNo, String contractType, String completedStatus) {
        Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createSQLQuery("SELECT *"
				+ " FROM econtract.tb_contracts"
				+ " WHERE updated_date = (" 
				+ "				SELECT MAX(j.updated_date) " 
				+ " 			FROM (" 
				+ "					SELECT * " 
				+ "					FROM econtract.tb_contracts" 
				+ "					WHERE product_number = :prodNo" 
				+ "					AND contract_status != :completedStatus"
				+ "					AND contract_type = :contractType" 
				+ "				) j" 
				+ " )"
				+ " AND product_number = :prodNo"
				+ " AND contract_type = :contractType")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prodNo", prodNo);
		query.setParameter("contractType", contractType);
		query.setParameter("completedStatus", completedStatus);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
	
	public Contracts getLatestUncompletedContractJJ(String prodNo, String contractType) {
        Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createSQLQuery("SELECT *"
				+ " FROM econtract.tb_contracts"
				+ " WHERE updated_date = (" 
				+ "				SELECT MAX(j.updated_date) " 
				+ " 			FROM (" 
				+ "					SELECT * " 
				+ "					FROM econtract.tb_contracts" 
				+ "					WHERE product_number = :prodNo" 
				+ "					AND contract_type = :contractType" 
				+ "				) j" 
				+ " )"
				+ " AND product_number = :prodNo"
				+ " AND contract_type = :contractType")
				.addScalar("contract_id", StringType.INSTANCE)
				.addScalar("last_name", StringType.INSTANCE)
				.addScalar("first_name", StringType.INSTANCE)
				.addScalar("ssn", StringType.INSTANCE)
				.addScalar("address", StringType.INSTANCE)
				.addScalar("address1", StringType.INSTANCE)
				.addScalar("email", StringType.INSTANCE)
				.addScalar("contract_package", StringType.INSTANCE)
				.addScalar("contract_type", StringType.INSTANCE)
				.addScalar("product_number", StringType.INSTANCE)
				.addScalar("contact_name", StringType.INSTANCE)
				.addScalar("contact_number", StringType.INSTANCE)
				.addScalar("contact_duration", StringType.INSTANCE)
				.addScalar("contact_phone", StringType.INSTANCE)
				.addScalar("phone_number", StringType.INSTANCE)
				.addScalar("phone_number1", StringType.INSTANCE)
				.addScalar("passport_front", BlobType.INSTANCE)
				.addScalar("passport_back", BlobType.INSTANCE)
				.addScalar("receipt1", BlobType.INSTANCE)
				.addScalar("receipt2", BlobType.INSTANCE)
				.addScalar("receipt3", BlobType.INSTANCE)
				.addScalar("receipt4", BlobType.INSTANCE)
				.addScalar("receipt5", BlobType.INSTANCE)
				.addScalar("receipt6", BlobType.INSTANCE)
				.addScalar("branch_name", StringType.INSTANCE)
				.addScalar("device_name", StringType.INSTANCE)
				.addScalar("serial_no", StringType.INSTANCE)
				.addScalar("teller_name", StringType.INSTANCE)				
				.addScalar("teller_sign", BlobType.INSTANCE)
				.addScalar("customer_sign", BlobType.INSTANCE)
				.addScalar("contract_status", StringType.INSTANCE)
				.addScalar("created_id", StringType.INSTANCE)
				.addScalar("created_date", TimestampType.INSTANCE)
				.addScalar("updated_id", StringType.INSTANCE)			
				.addScalar("updated_date", TimestampType.INSTANCE)
				.addScalar("pdf_file_name", StringType.INSTANCE)
				.addScalar("ozd_file_name", StringType.INSTANCE);
		query.setParameter("prodNo", prodNo);
		query.setParameter("contractType", contractType);
		query.setResultTransformer(Transformers.aliasToBean(Contracts.class));
				
        if(query.list().size() != 0) {
        	return (Contracts) query.list().get(0);
        }else {
        	return null;
        }
	}
}
