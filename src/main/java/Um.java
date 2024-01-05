import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Um {
	public static void main(String[] args) {
		
		String[] players = {"mumu", "soe", "poe", "kai", "mine"};
		String[] callings = {"kai", "kai", "mine", "mine"};
		
		List<String> list = new ArrayList<>(Arrays.asList(players));
		List<String> list2 = new ArrayList<>(Arrays.asList(callings));
		
		for(int i = 0; i < list2.size(); i++) {
			if(list2.get(i).equals("kai")) {
				Collections.swap(list, i, i);
			}
		}
		
		
		
		
	}

}
