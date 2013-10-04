package com.affbeastmode.kwassasin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.affbeastmode.kwassasin.dao.IPNInfoDAO;
import com.affbeastmode.kwassasin.model.IPNInfo;

@Service("ipnInfoService")
@Transactional
public class IPNInfoService {
	@Autowired
	private IPNInfoDAO ipnDao;

	@Transactional
	public boolean storeIPNInfo(IPNInfo ipnInfo) throws Exception{
		return ipnDao.saveIPNInfo(ipnInfo);
	}
	
	@Transactional
	public boolean updateIPNInfo(IPNInfo ipnInfo) throws Exception{
		return ipnDao.updateIPNInfo(ipnInfo);
	}	
	
	@Transactional
	public IPNInfo findIPNInfoByTransactionId(String txnId) throws Exception{
		return ipnDao.findIpnInfoByTransactionId(txnId);
	}	
	
	public void setIpnDao(IPNInfoDAO ipnDao) {
		this.ipnDao = ipnDao;
	}
}
