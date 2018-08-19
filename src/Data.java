import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

class DataFile{
	private List<String> attribute;
	private List<String> data;
	private Integer correctly_classified_instances;
	private Integer incorrectly_classified_instances;
	public List<String> getAttribute() {
		return attribute;
	}
	public void setAttribute(List<String> attribute) {
		this.attribute = attribute;
	}
	public List<String> getData() {
		return data;
	}
	public void setValue(List<String> value) {
		this.data = value;
	}
	public DataFile(List<String> _attribute,List<String> _data) {
		this.attribute = _attribute;
		this.data = _data;
		this.correctly_classified_instances = 0;
		this.incorrectly_classified_instances = 0;
		
		for (String string : data) {
			if(string.split(",")[attribute.size()-1].contains("y")) correctly_classified_instances++;
			else incorrectly_classified_instances++;
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