import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Data{
	private List<String> attribute;
	private List<String> value;
	public List<String> getAttribute() {
		return attribute;
	}
	public void setAttribute(List<String> attribute) {
		this.attribute = attribute;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public Data(List<String> _attribute,List<String> _data) {
		this.attribute = _attribute;
		this.value = _data;
	}
	public String convertAttributeName(int index) {
		if(index < 0 || index >= this.attribute.size()) return "";
		return this.attribute.get(index);
	}
}

public class ImportData {

	public static Data getDataFromFile(String file_in_name) {
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
			return new Data(list_att, list_value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
