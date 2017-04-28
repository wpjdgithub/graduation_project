package Grad.Factory;

public enum Level_court {
	basic(0),//基层
	medium(1),//中级
	high(2);//最高
	
	private int level;
	
	private Level_court(int level){
		this.setLevel(level);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Level_court getLevelByNum(int num){
		switch(num){
		case 1:{
			return Level_court.basic;
		}case 2:{
			return Level_court.medium;
		}case 3:{
			return Level_court.high;
		}default:
			return Level_court.basic;
		}
	}
}
