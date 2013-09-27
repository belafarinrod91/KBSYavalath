package tinf11bc.kbs.yavalath.util;

public class Settings {

	

	
	public static int[] mPlayerInformation;
	public static int mDifficultyUTCAi; 
	
	public Settings(){
		mPlayerInformation = new int[]{1,3,0};
		mDifficultyUTCAi = 1;
	}
	
	
	public void setPlayerInformation(int[] playerInformation){
		mPlayerInformation = playerInformation;		
	}
	
	public int[] getPlayerInformation(){
		return mPlayerInformation;
	}


	public static int getDifficultyUTCAi() {
		int result = 0;
		
		switch(mDifficultyUTCAi){
			case 1:
				result = 1000;
				break;
			case 2:
				result = 5000;
				break;
			case 3:
				result = 50000;
				break;
		}

		return result;
		
		
	}


	public void setDifficultyUTCAi(int difficultyUTCAi) {
		mDifficultyUTCAi = difficultyUTCAi;
	}
	
	
	
	
	
	
	
	
}
