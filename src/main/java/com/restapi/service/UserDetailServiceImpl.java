package com.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import com.restapi.dao.RoleDAO;
import com.restapi.dao.UserDAO;
import com.restapi.model.ApiUser;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private RoleDAO roleDao;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		ApiUser user = this.userDao.findUserAccount(userName);
		
		if(user == null)
		{
			System.out.println("[DEBUG] : User Not Found!");
			throw new UsernameNotFoundException("User " + userName + " was not found in the Database!");
		}
		
		System.out.println("[DEBUG] : Found User : " + user);
			
		List<String> roleNames = this.roleDao.getRoleNames(user.getUserId());
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if(roleNames != null)
		{
			for(String role: roleNames)
			{
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		
		UserDetails userDetails = (UserDetails) new User(user.getUserName(), user.getEncryptedPasword(), grantList);
		
		return userDetails;
	}

	
}
