package com.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import com.restapi.dao.RoleDAO;
import com.restapi.dao.UserDAO;
import com.restapi.model.ApiUser;
import com.restapi.model.QueryError;
import com.restapi.model.QueryResult;

@Service
public class UserDetailServiceImpl implements UserDetailsService, UserService {
	
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
			//System.out.println("[DEBUG] : User Not Found!");
			throw new UsernameNotFoundException("User " + userName + " was not found in the Database!");
		}
		
		//System.out.println("[DEBUG] : Found User : " + user);
			
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

	@Override
	public QueryResult addUser(String userName, String password, boolean isAdmin) 
	{
		if(userDao.addUser(userName, password, isAdmin))
		{
			return userDao.findUserAccount(userName);
		}
		else
		{
			return new QueryError("User Cannot be Added!", QueryResult.STATUS_QUERY_ERROR);
		}
	}

	@Override
	public boolean isAdmin()
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails)
		{
			//System.out.println("[DEBUG] : Determining role of : " + principal.toString() + " from principal!");

			Collection<? extends GrantedAuthority> roles = ((UserDetails) principal).getAuthorities();
			
			return roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		else
		{
			//System.out.println("[DEBUG] : Determining role of : " + principal.toString());
			UserDetails details = loadUserByUsername(principal.toString());
			return details.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}
	
}
