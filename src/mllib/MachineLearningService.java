package mllib;

import java.util.Map;
import java.util.Set;

public interface MachineLearningService {
	public Map<String,Set<String> > cluster(Map<String,Map<String,Double> > wenshus);
}
