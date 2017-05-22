package tfidf;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public class DistanceCalc {
	public double calculateCosDistance(WenshuVector vec1,WenshuVector vec2){
		Map<String,Double> map1 = vec1.getVector();
		Map<String,Double> map2 = vec2.getVector(); 
		return this.calculateCosDistance(map1, map2);
	}
	public double calculateCosDistance(Map<String,Double> map1,Map<String,Double> map2){
		Set<String> set = new HashSet<String>();
		set.addAll(map1.keySet());
		set.addAll(map2.keySet());
		double sum0 = 0;
		double sum1 = 0;
		double sum2 = 0;
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			double tfidf1 = map1.containsKey(word)?map1.get(word):0.0;
			double tfidf2 = map2.containsKey(word)?map2.get(word):0.0;
			sum0 += tfidf1*tfidf2;
			sum1 += tfidf1*tfidf1;
			sum2 += tfidf2*tfidf2;
		}
		double mul = Math.sqrt(sum1)*Math.sqrt(sum2);
		double result = sum0/mul;
		return result;
	}
}
