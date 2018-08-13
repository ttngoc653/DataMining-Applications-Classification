import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Attribute{
	private String name;
	private List<String> domain_value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getDomainValue() {
		return domain_value;
	}
	public void setDomainValue(List<String> domain_value) {
		this.domain_value = domain_value;
	}
	public Attribute(String name,String domain_value) {
		this.name = name;
		this.domain_value = new ArrayList<>();
		if(domain_value.indexOf("{")>=0) {
			domain_value = domain_value.substring(1,domain_value.length()-1);
			for (String string : domain_value.split(",")) {
				this.domain_value.add(string);
			}
		}
		System.out.println(name +" - "+domain_value);
	}
}

class Data{
	private List<Attribute> attribute;
	private List<String> value;
	public List<Attribute> getAttribute() {
		return attribute;
	}
	public void setAttribute(List<Attribute> attribute) {
		this.attribute = attribute;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public Data(List<Attribute> _attribute,List<String> _data) {
		this.attribute = _attribute;
		this.value = _data;
	}
}

public class ImportData {

	public static Data getDataFromFile(String file_in_name) {
		try {
			List<Attribute> list_att = new ArrayList<>();
			List<String> list_value = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(file_in_name));
			String receive = reader.readLine();
			Boolean got_data =  false;
			while (receive!=null) {
				if(receive.indexOf("@attribute")>=0) {
					list_att.add(new Attribute(receive.split(" ")[1], receive.split(" ")[2]));
				}
				else if(receive.indexOf("@data")>=0) got_data = true;
				else if(got_data) {
					list_value.add(receive);
					System.out.println(receive);
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
		getDataFromFile("zoo.arff");
	}

}
