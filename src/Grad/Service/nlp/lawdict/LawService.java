package Grad.Service.nlp.lawdict;

import java.util.Set;

public interface LawService {
	public String getLawContent(String lawname,String lawitem);
	public Set<String> getAllLawNames();
}
