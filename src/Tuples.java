import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

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
		for (int _i_folds = 0; _i_folds < _k_folds; _i_folds++) {
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
					upFalseNegatives();
			}
		}
	}
	
	public void outputDetailedAccuracyByClass() {
		DecimalFormat dcf = new DecimalFormat("#.###");
		System.out.println("Class\tTP Rate\tFP Rate\tPrecision\tRecall\tF-Measure");
		System.out.println("Yes\t" + getTruePositives() + "\t" + getFalsePositives()
			+ "\t" + dcf.format((double)getTruePositives()/(getTruePositives() + getFalsePositives())) 
			+ "\t\t" + dcf.format((double)getTruePositives()/(getTruePositives() + getFalseNegatives()))
			+ "\t" + dcf.format((double)2 * getTruePositives()/(3 * getTruePositives() + 2 * getFalseNegatives() +  getFalsePositives())));
		System.out.println("No\t" + getTrueNegatives() + "\t" + getFalseNegatives() 
			+ "\t" + dcf.format((double)getTrueNegatives()/(getTrueNegatives() + getFalseNegatives())) 
			+ "\t\t" + dcf.format((double)getTrueNegatives()/(getTrueNegatives() + getFalsePositives()))
			+ "\t" + dcf.format((double)2 * getTrueNegatives()/(3 * getTrueNegatives() + 2 * getFalsePositives() +  getFalseNegatives())));
	}
	public void outputDetailedAccuracyByClass(FileWriter fw) {
		try {
			DecimalFormat dcf = new DecimalFormat("#.###");
			fw.write("Class\tTP Rate\tFP Rate\tPrecision\tRecall\tF-Measure\n");
			fw.write("Yes\t" + getTruePositives() + "\t\t" + getFalsePositives()
				+ "\t\t" + dcf.format((double)getTruePositives()/(getTruePositives() + getFalsePositives())) 
				+ "\t\t" + dcf.format((double)getTruePositives()/(getTruePositives() + getFalseNegatives()))
				+ "\t\t" + dcf.format((double)2 * getTruePositives()/(3 * getTruePositives() + 2 * getFalseNegatives() +  getFalsePositives())) + "\n");
			fw.write("No\t" + getTrueNegatives() + "\t\t" + getFalseNegatives() 
				+ "\t\t" + dcf.format((double)getTrueNegatives()/(getTrueNegatives() + getFalseNegatives())) 
				+ "\t\t" + dcf.format((double)getTrueNegatives()/(getTrueNegatives() + getFalsePositives()))
				+ "\t\t" + dcf.format((double)2 * getTrueNegatives()/(3 * getTrueNegatives() + 2 * getFalsePositives() +  getFalseNegatives())) + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
