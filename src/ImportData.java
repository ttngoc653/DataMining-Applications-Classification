import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class ImportData {

	public static DataFile getDataFromFile(String file_in_name) {
		try {
			List<String> list_att = new ArrayList<>();
			List<String> list_value = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(file_in_name));
			
			String receive = reader.readLine();
			
			for (String string : receive.split(",")) list_att.add(string);
			
			receive = reader.readLine();
			while (receive != null) {
				if(receive.length()> 1) {
					list_value.add(receive);
				}
				receive = reader.readLine();
			}
			
			reader.close();
			return new DataFile(list_att, list_value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
