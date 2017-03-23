package Grad.ServiceImpl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import Grad.Service.SearchService;

@Service
@Qualifier("advanced")
public class AdvancedSearchService implements SearchService {

	@Override
	public String search() {
		// TODO Auto-generated method stub
		return "AdvancedSearchService";
	}

}
