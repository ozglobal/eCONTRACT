package com.oz.unitel.dao;

import java.util.List;

import com.oz.unitel.model.Contracts;

public interface ContractDAO {
	public int save(Contracts contract);
	public void update(Contracts contract);
	public void saveOrUpdate(Contracts contract);
	public void delete(Contracts contract);
	public List<Contracts> getList(String start, String end);	// 2016.08.11 : modify by ryu.
	public List<Contracts> getList(String user_name);
	public Contracts getContract(Integer id);
	public Contracts getContract(String prod_no);
	public Contracts getContract(String prod_no, String contract_type);
	public Contracts getContract(String prodNo, String contractType, String contractId);
	public Contracts getContractJ(String prodNo, String contractId);
	public Contracts getUncompletedContract(String prod_no, String completed_status);
	public Contracts getUncompletedContract(String prod_no, String completed_status, String user_name);
	public List<Contracts> getLatestContractsGroupByProd(String start, String end);		// 2016.08.11 : modify by ryu.
	public List<Contracts> getLatestContractsGroupByContractId(String start, String end);
	public List<Contracts> getLatestContractsGroupByProd(String user_name, String start, String end);	// 2016.08.11 : modify by ryu.
	public List<Contracts> getLatestContractsGroupByContractId(String user_name, String start, String end);
	public Contracts getLatestContract(String prod_no);
	public Contracts getLatestContractJ(String contractId);
	public Contracts getLatestUncompletedContract(String prod_no, String completed_status, String user_name);
	public Contracts getLatestUncompletedContractJ(String prodNo, String contractType, String completedStatus, String userName);
	public Contracts getLatestUncompletedContract(String prod_no, String completed_status);
	public Contracts getLatestUncompletedContractJ(String prodNo, String contractType, String completedStatus);
	public Contracts getLatestUncompletedContractJJ(String prodNo, String contractType);
}
