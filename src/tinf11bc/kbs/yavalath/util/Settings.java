package tinf11bc.kbs.yavalath.util;

public class Settings {

	

	
	public static int[] mPlayerInformation;
	
	public Settings(){
		mPlayerInformation = new int[]{1,3,0};
	}
	
	
	public void setPlayerInformation(int[] playerInformation){
		mPlayerInformation = playerInformation;		
	}
	
	public int[] getPlayerInformation(){
		return mPlayerInformation;
	}
	
	
	
	
	
	
}
