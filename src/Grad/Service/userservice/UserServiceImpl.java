package Grad.Service.userservice;

import Grad.Bean.UserInfo;
import Grad.Service.UserSerivice;
import Grad.Service.dataservice.UserDataService;
import Grad.Service.dataservice.impl.UserDataServiceImpl;

public class UserServiceImpl implements UserSerivice{
	
	private UserDataService userDataService ;
	
	public UserServiceImpl(){
		this.userDataService = new UserDataServiceImpl();
	}

	@Override
	public boolean register(UserInfo info) {
		UserInfo u = this.userDataService.getUserInfo(info.getUsername());
		if(u == null){
			return false;
		}
		else{
			this.userDataService.insert(info);
			return true;
		}
	}

	@Override
	public boolean login(UserInfo info) {
		UserInfo userInfo = this.userDataService.getUserInfo(info.getUsername());
		if(userInfo == null){
			return false;
		}
		else{
			if(userInfo.getPassword().equals(info.getPassword())){
				return true;
			}
			else{
				return false;
			}
		}
	}

	@Override
	public UserInfo getInfo(String username) {
		UserInfo userInfo = this.userDataService.getUserInfo(username);
		if(userInfo == null){
			return new UserInfo("default","default","default");
		}
		else{
			return userInfo;
		}
	}

}
