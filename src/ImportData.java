import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Data{
	private List<String> attribute;
	private List<String> value;
	private Integer correctly_classified_instances;
	private Integer incorrectly_classified_instances;
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
		this.correctly_classified_instances = 0;
		this.incorrectly_classified_instances = 0;
		
		for (String string : value) {
			if(string.split(",")[attribute.size()-1].contains("y")) correctly_classified_instances++;
			else {
				incorrectly_classified_instances++;
			}
		}
	}
	public String convertAttributeName(int index) {
		if(index < 0 || index >= this.attribute.size()) return "";
		return this.attribute.get(index);
	}
	public Integer getCorrectlyClassifiedInstances() {
		return correctly_classified_instances;
	}
	public Integer getInCorrectlyClassifiedInstances() {
		return incorrectly_classified_instances;
	}
	
	public void outputClassifiedInstances() {
		DecimalFormat dcf = new DecimalFormat("#.###");
		System.out.println("Correctly Classified Instances\t\t" + correctly_classified_instances + "\t" + dcf.format((double)correctly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %");
		System.out.println("Incorrectly Classified Instances\t" + incorrectly_classified_instances + "\t" + dcf.format((double)incorrectly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %");
	}
	public void outputClassifiedInstances(FileWriter fw) {
		try {
			DecimalFormat dcf = new DecimalFormat("#.###");
			fw.write("Correctly Classified Instances\t\t" + correctly_classified_instances + "\t" + dcf.format((double)correctly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %\n");
			fw.write("Incorrectly Classified Instances\t" + incorrectly_classified_instances + "\t" + dcf.format((double)incorrectly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	

}
