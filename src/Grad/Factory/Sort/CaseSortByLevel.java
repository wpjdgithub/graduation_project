package Grad.Factory.Sort;

import java.util.Comparator;

import Grad.Bean.CaseBrief;

public class CaseSortByLevel implements Comparator<CaseBrief> {

	@Override
	public int compare(CaseBrief arg0, CaseBrief arg1) {
		// TODO Auto-generated method stub
		return getLevel(arg0.getCourtLevel())-getLevel(arg1.getCourtLevel());
	}
	
	private int getLevel(String level){
		if(level==null){
			return 0;
		}else if(level.equals("基层")){
			return 1;
		}else if(level.equals("中层")){
			return 2;
		}else{
			return 3;
		}
	}

}
