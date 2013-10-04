package com.affbeastmode.kwassasin.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.affbeastmode.kwassasin.model.IPNInfo;

@Repository("ipnDao")
public class IPNInfoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveIPNInfo(IPNInfo ipn) throws Exception {
		sessionFactory.getCurrentSession().save(ipn);
		return true;
	}

	public boolean updateIPNInfo(IPNInfo ipn) throws Exception {
		sessionFactory.getCurrentSession().saveOrUpdate(ipn);
		return true;
	}

	@SuppressWarnings("unchecked")
	public IPNInfo findIpnInfoByTransactionId(String txnId) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("findIpnInfoByTransactionId").setString("txnId", txnId);
		IPNInfo ipnInfoToReturn = null;
		List<IPNInfo> retList = query.list();
		if (retList != null && !retList.isEmpty()) {
			Iterator<IPNInfo> itr = (Iterator<IPNInfo>) query.list().iterator();
			ipnInfoToReturn = itr.next();
			sessionFactory.getCurrentSession().evict(ipnInfoToReturn);
		}
		return ipnInfoToReturn;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
