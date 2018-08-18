import java.util.List;

public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("=== Classifier model (full training set) ===");
		Data _data = ImportData.getDataFromFile("example.csv");
		// for (String string : _data.getAttribute()) System.out.println(string);
		// for (String string : _data.getValue()) System.out.println(string);
		
		DecisionTree _deci_tree = Process.createTreeID3(_data);
		_deci_tree.outputID3(_data);
		_data.outputClassifiedInstances();
		System.out.println("===Best attribute criteria===");
		System.out.println("Entropy");
		System.out.println("=== Detailed Accuracy By Class ===");
		Tuples _tuple = new Tuples(_data.getValue(), _deci_tree);
	}

}
