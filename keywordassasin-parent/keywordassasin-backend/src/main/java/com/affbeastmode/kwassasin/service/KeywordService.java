package com.affbeastmode.kwassasin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.affbeastmode.kwassasin.dao.KeywordDAO;
import com.affbeastmode.kwassasin.model.Plan;
import com.affbeastmode.kwassasin.model.User;
import com.affbeastmode.kwassasin.model.UserActivity;
import com.affbeastmode.kwassasin.model.UserSubscription;

@Service("keywordservice")
@Transactional
public class KeywordService {
	@Autowired
	private KeywordDAO keywordDao;
	
	@Transactional
	public boolean registerUser(User user) throws Exception{
		keywordDao.saveUser(user);
		return true;
	}
	
	@Transactional
	public boolean registerUserWithRole(User user) throws Exception{
		keywordDao.saveUserWithRole(user, "user");
		return true;
	}	
	
	@Transactional
	public boolean updateUser(User user) throws Exception{
		keywordDao.updateUser(user);
		return true;
	}	
	
	@Transactional
	public Plan findPlanByName(String planName) throws Exception{
		return keywordDao.findPlanByName(planName);
	}
	
	@Transactional
	public List<Plan> findAllPlans() throws Exception{
		return keywordDao.findAllPlans();
	}
	
	@Transactional
	public User findUserByUUID(String uuid) throws Exception{
		return keywordDao.findUserByUUID(uuid);
	}
	
	@Transactional
	public User findUserByUserId(String userId) throws Exception{
		return keywordDao.findUserByUserId(userId);
	}	
	
	@Transactional
	public boolean activateUser(User user) throws Exception{
		user.setActive(true);
		UserSubscription subscription = user.getSubscriptions().iterator().next();
		subscription.setActive(true);
		int duration = subscription.getPlan().getDurationInDays();
		Calendar currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		endDate.setTimeInMillis(currentDate.getTimeInMillis());
		endDate.add(Calendar.DAY_OF_YEAR, duration);
		subscription.setPlanStartDate(currentDate.getTime());
		subscription.setPlanEndDate(endDate.getTime());
		keywordDao.updateUser(user);
		return true;
	}
	
	@Transactional
	public boolean logUserActivity(UserActivity activity) throws Exception{
		keywordDao.createUserActivity(activity);
		return true;
	}
	
	@Transactional
	public boolean checkSubscriptionExpiration(User user){
		UserSubscription subscription = user.getSubscriptions().iterator().next();
		return subscription.getPlanEndDate().before(new Date());
	}
	
	@Transactional
	public int keywordRemaining(User user){
		Plan plan = user.getSubscriptions().iterator().next().getPlan();
		int totalKeywordProcessed = keywordDao.totalKeywordsProcessedInCurrentSubscription(user, plan);
		return plan.getTotalKeywordLimit()-totalKeywordProcessed;
	}
}
