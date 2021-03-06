package com.coorp.rob.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coorp.rob.model.ShopList;
import com.coorp.rob.model.User;
import com.coorp.rob.repository.ShopListRepository;
import com.coorp.rob.repository.UserRepository;


/**
 * @author Roberto
 * 
 * */
@Service
public class UserService {
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ShopListRepository shopListRepository;
	
	/**
	 * 
	 * 
	 * */
	public Optional<User> getUserByEmail(String email) {
		log.info("getUserByEmail - START - receiving{ email: " + email);
		 Optional<User> userRetrieved = this.userRepository.findById(email);
		 return userRetrieved;
	}
	
	/**
	 * 
	 * 
	 * */
	public User getUserByEmailAndPassword(String email, String password) {
		 log.info("getUserByEmailAndPassword - START - receiving{ email: " + email + "  e password: " + password + "}");
//		 Optional<User> userRetrievedByEmail = this.userRepository.findByUserEmail(email);
		 Optional<User> userRetrievedByEmail = getUserByEmail(email);
		 log.info("getUserByEmailAndPassword - DEBUG - found by email isEmpty: " + userRetrievedByEmail.isEmpty());
		 if (userRetrievedByEmail.isEmpty()) return null;
		 if (!userRetrievedByEmail.get().getPassword().equals(password)) return null; //in this case I decide to do not return any results about email not found, 
		 return userRetrievedByEmail.get() ;
	}
	
	/**
	 * 
	 * 
	 * */
	public Optional<ShopList> getShopListUser(String email) {
		 log.info("method getAllShopListUser(String email) - START\n\n" + 
				  "\t PARAMS: {\n" + 
				  					"\t  1:" + email +
				  			  "\n\t}\n\n");
		 Optional<ShopList> shopListRetrieved = null;
		 Optional<User> user = this.userRepository.findByUserEmail(email);
		 if(user.get() != null) {
			shopListRetrieved = this.shopListRepository.findById(user.get().getUserInfo().getIdListItem());
		 }
//		 log.error("method  getAllShopListUser(String email) - DEBUG\n\n" + 
//				  "\t {\n" + 
//						 "\t  shopList:" + shopList.toString() +
//				  "\n\t}\n\n"); 
//		 log.info("method getAllShopListUser(String email) - RETURN\n\n" +
//				 "\t {\n" + 
//				 	"\t  shopList:" + shopListRetrieved.isPresent() != null?shopListRetrieved.get().toString():"null" +
//				 "\n\t}\n\n"); 
//		 log.info("method getAllShopListUser(String email) - END");
		 return shopListRetrieved;
	}
	
	
	/**
	 * 
	 * 
	 * */
	public boolean saveUser(User usrParam) {
		 log.info("method  saveUser(User user) - START\n\n" + 
				  "\t PARAMS: {\n" + 
				  					"\t  1:" + usrParam.toString() +
				  			  "\n\t}\n\n"); 
		
		 boolean saved = true;
		 User user = null;
		 
		 try {
			 user = this.userRepository.save(usrParam);
			 log.error("method  saveUser(User user) - DEBUG\n\n" + 
					  "\t item-saved:{\n" + 
	  						"\t  user:" + user.toString() +
					  "\n\t}\n\n"); 
		 }catch(IllegalArgumentException ex) {
			 saved = false;
			 log.error("method  saveUser(User user) - ERROR\n\n" + 
					  "\t {\n" + 
	  						"\t  error-message:" + ex.getMessage() +
	  						"\t  error-cause:" + ex.getCause() +
	  						"\t  error-stackTrace:" + ex.getStackTrace() +
					  "\n\t}\n\n"); 
			 ex.printStackTrace();
		 }
		 log.info("method saveUser(User user) - RETURN\n\n" +
				 "\t {\n" + 
					"\t  saved: " + saved +
				 "\n\t}\n\n"); 
		 log.info("method  saveUser(User user) - END");
		 return saved;
	}
	
}
