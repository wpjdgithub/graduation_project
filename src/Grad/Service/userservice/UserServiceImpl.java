package Grad.Service.userservice;
import java.util.ArrayList;
import java.util.List;
import Grad.Bean.CaseMinMes;
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
		if(u != null){
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
	@Override
	public List<CaseMinMes> getMinMes(String username) {
		List<CaseMinMes> result = new ArrayList<CaseMinMes>();
		List<String> list = this.userDataService.getCaseMinMes(username);
		int size = list.size();
		for(int i = 0;i < size;i++){
			String line = list.get(i);
			String[] s = line.split(" ");
			CaseMinMes mes = new CaseMinMes();
			StringBuilder sb = new StringBuilder();
			for(int j = 0;j < s.length-1;j++){
				sb.append(s[j]).append(" ");
			}
			String title = sb.toString().trim();
			mes.setId(title);
			mes.setTitle(title);
			mes.setUploadDate(s[s.length-1]);
			result.add(mes);
		}
		return result;
	}
}