package Grad.Factory.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import Grad.Bean.CaseBrief;

public class CaseSortByDate implements Comparator<CaseBrief>{

	@Override
	public int compare(CaseBrief o1, CaseBrief o2) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); 
		try {
			Date d1 = sdf.parse(o1.getDate());
			Date d2 = sdf.parse(o2.getDate());
			return d1.compareTo(d2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
