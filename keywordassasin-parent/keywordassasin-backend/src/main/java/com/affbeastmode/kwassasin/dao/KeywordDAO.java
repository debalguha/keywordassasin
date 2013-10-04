package com.affbeastmode.kwassasin.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.affbeastmode.kwassasin.model.Plan;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.model.UserActivity;
import com.affbeastmode.kwassasin.model.UserSubscription;

@Repository("keywordDao")
public class KeywordDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveUser(User user) throws Exception {
		Object obj = sessionFactory.getCurrentSession().save(user);
		System.out.println(obj.getClass());
		return true;
	}

	public boolean saveUserWithRole(User user, String role) throws Exception {
		user.setActive(true);
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("insert into user_roles values('" + user.getUserName() + "', 'user')");
			if (query.executeUpdate() <= 0)
				throw new Exception("Unable to update role for user!!");
		} catch (Exception e) {
			//sessionFactory.getCurrentSession().
			throw e;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public User findUserByUserId(String userId) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("findUserById").setString("userId", userId);
		User userToReturn = null;
		List<User> retList = query.list();
		if (retList != null && !retList.isEmpty()) {
			Iterator<User> itr = (Iterator<User>) query.list().iterator();
			userToReturn = itr.next();
			sessionFactory.getCurrentSession().evict(userToReturn);
		}
		return userToReturn;
	}

	@SuppressWarnings("unchecked")
	public User findUserByUUID(String uuid) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("findUserByUUID").setString("uuid", uuid);
		User userToReturn = null;
		List<User> retList = query.list();
		if (retList != null && !retList.isEmpty()) {
			Iterator<User> itr = (Iterator<User>) query.list().iterator();
			userToReturn = itr.next();
			sessionFactory.getCurrentSession().evict(userToReturn);
		}
		return userToReturn;
	}

	@SuppressWarnings("unchecked")
	public Plan findPlanByName(String planName) throws Exception {
		Query query = sessionFactory.getCurrentSession().getNamedQuery("findPlanByName").setString("planName", planName);
		Iterator<Plan> itr = (Iterator<Plan>) query.list().iterator();
		Plan plan = null;
		if (itr != null && itr.hasNext()) {
			plan = itr.next();
			sessionFactory.getCurrentSession().evict(plan);
		}
		return plan;
	}

	@SuppressWarnings("unchecked")
	public List<Plan> findAllPlans() throws Exception {
		return sessionFactory.getCurrentSession().createQuery("from Plan p").list();
	}

	public User findUserById(long id) throws Exception {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		if (user != null)
			sessionFactory.getCurrentSession().evict(user);
		return user;
	}

	public boolean updateUser(User user) throws Exception {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
		return true;
	}

	public boolean createUserActivity(UserActivity activity) throws Exception {
		sessionFactory.getCurrentSession().save(activity);
		return true;
	}

	public boolean createUserSubscription(UserSubscription subscription) throws Exception {
		subscription.setActive(false);
		sessionFactory.getCurrentSession().save(subscription);
		return true;
	}

	public boolean updateSubscription(UserSubscription subscription) throws Exception {
		sessionFactory.getCurrentSession().save(subscription);
		return true;
	}

	public int totalKeywordsProcessedInCurrentSubscription(User user, Plan plan) {
		UserSubscription subscription = user.getSubscriptions().iterator().next();
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select sum(keywordProcessed) from user_activity where user_id=" + user.getID() + " and plan_id=" + plan.getID() + " and activityDate between " + subscription.getPlanStartDate() + " and " + subscription.getPlanEndDate());
		return Integer.parseInt(query.uniqueResult().toString());
	}

}
