import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Tuples{
	private Integer _true_positives;
	private Integer _true_negatives;
	private Integer _false_positives;
	private Integer _false_negatives;
	public Integer getTruePositives() {
		return _true_positives;
	}
	public void setTruePositives(Integer _true_positives) {
		this._true_positives = _true_positives;
	}
	public Integer getTrueNegatives() {
		return _true_negatives;
	}
	public void setTrueNegatives(Integer _true_negatives) {
		this._true_negatives = _true_negatives;
	}
	public Integer getFalsePositives() {
		return _false_positives;
	}
	public void setFalsePositives(Integer _false_positives) {
		this._false_positives = _false_positives;
	}
	public Integer getFalseNegatives() {
		return _false_negatives;
	}
	public void setFalseNegatives(Integer _false_negatives) {
		this._false_negatives = _false_negatives;
	}
	public void upTruePositives() {
		this._true_positives++;
	}
	public void upTrueNegatives() {
		this._true_negatives++;
	}
	public void upFalsePositives() {
		this._false_positives++;
	}
	public void upFalseNegatives() {
		this._false_negatives++;
	}
	public Tuples() {
		this._false_negatives = 0;
		this._false_positives = 0;
		this._true_negatives = 0;
		this._true_positives = 0;
	}
	public Tuples(Data _data, Integer _k_folds) {
		this._false_negatives = 0;
		this._false_positives = 0;
		this._true_negatives = 0;
		this._true_positives = 0;
	
		String _str_temp;
		DecisionTree _deci_tree = null;
		for (int _i_folds = 3; _i_folds < _k_folds; _i_folds++) {
			_deci_tree = Process.createTreeID3(_data,_i_folds);
			for (int i = Integer.valueOf(_i_folds * _data.getValue().size() / _k_folds); i < Integer.valueOf((_i_folds + 1) * _data.getValue().size() / _k_folds); i++) {
				_str_temp = _deci_tree.result(_data.getValue().get(i));
				if(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1].contains("y") 
						&& _str_temp.equals(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1]))
					upTruePositives();
				else if (_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1].contains("n") 
						&& _str_temp.equals(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1]))
					upTrueNegatives();
				else if (_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1].contains("y") 
						&& !_str_temp.equals(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1]))
					upFalsePositives();
				else if (_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1].contains("n") 
						&& !_str_temp.equals(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length - 1]))
					upTrueNegatives();
			}
			
			System.out.println("_______");
		}
	}
	
	public void outputDetailedAccuracyByClass() {
		System.out.println("Class\tTP Rate\tFP Rate\tPrecision\tRecall\tF-Measure");
		System.out.println("Yes\t" + getTruePositives() + "\t" + getFalsePositives()
			+ "\t" + Double.valueOf((double)getTruePositives()/(getTruePositives() + getFalsePositives())) 
			+ "\t" + Double.valueOf((double)getTruePositives()/(getTruePositives() + getFalseNegatives()))
			+ "\t" + Double.valueOf((double)2 * getTruePositives()/(3 * getTruePositives() + 2 * getFalseNegatives() +  getFalsePositives())));
		System.out.println("No\t" + getTrueNegatives() + "\t" + getFalseNegatives() 
			+ "\t" + Double.valueOf((double)getTrueNegatives()/(getTrueNegatives() + getFalseNegatives())) 
			+ "\t" + Double.valueOf((double)getTrueNegatives()/(getTrueNegatives() + getFalsePositives()))
			+ "\t" + Double.valueOf((double)2 * getTrueNegatives()/(3 * getTrueNegatives() + 2 * getFalsePositives() +  getFalseNegatives())));
	}
}

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
		System.out.println("Correctly Classified Instances\t\t" + correctly_classified_instances + "\t" + Double.valueOf((double)correctly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %");
		System.out.println("Incorrectly Classified Instances\t" + incorrectly_classified_instances + "\t" + Double.valueOf((double)incorrectly_classified_instances / (correctly_classified_instances + incorrectly_classified_instances) * 100) + " %");
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
