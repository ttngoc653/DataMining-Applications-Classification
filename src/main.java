import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class main {
	static DataFile cau1(String _file_name_in) {
		DataFile _data = ImportData.getDataFromFile(_file_name_in);
		for (int i = 0; i < _data.getAttribute().size(); i++) {
			if(i == _data.getAttribute().size() - 1) System.out.println(_data.getAttribute().get(i));
			System.out.print(_data.getAttribute().get(i)+",");
		}
		for (String _str : _data.getData())
			System.out.println(_str);
		
		return _data;
	}
	static void cau2(String _file_name_in, String _file_name_out) {
		DataFile _data = ImportData.getDataFromFile(_file_name_in);
		System.out.println("=== Classifier model (full training set) ===");
		DecisionTree _deci_tree = Process.createTreeID3(_data);
		try {
			FileWriter fw = new FileWriter(new File(_file_name_out));
			fw.write("=== Classifier model (full training set) ===\n");
			
			_deci_tree.outputID3(_data);
			_deci_tree.outputID3(_data, fw);

			_data.outputClassifiedInstances();
			_data.outputClassifiedInstances(fw);
			
			System.out.println("===Best attribute criteria===");
			fw.write("===Best attribute criteria===\n");
			
			System.out.println("Entropy");
			fw.write("Entropy\n");
			
			System.out.println("=== Detailed Accuracy By Class ===");
			fw.write("=== Detailed Accuracy By Class ===\n");
			
			Tuples _tuple = new Tuples(_data,10);
			_tuple.outputDetailedAccuracyByClass();
			_tuple.outputDetailedAccuracyByClass(fw);
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	static void cau3(String[] _arr_str) {
		if(!(_arr_str.length >= 3 && isFolds(_arr_str[2]))) {
			System.out.println("Wrong parameter");
			return;
		}
		DataFile _data = ImportData.getDataFromFile(_arr_str[0]);
		System.out.println("=== Classifier model (full training set) ===");
		DecisionTree _deci_tree = Process.createTreeID3(_data);
		try {
			FileWriter fw = new FileWriter(new File(_arr_str[1]));
			fw.write("=== Classifier model (full training set) ===\n");
			
			_deci_tree.outputID3(_data);
			_deci_tree.outputID3(_data, fw);
	
			_data.outputClassifiedInstances();
			_data.outputClassifiedInstances(fw);
			
			System.out.println("===Best attribute criteria===");
			fw.write("===Best attribute criteria===\n");
			
			System.out.println("Entropy");
			fw.write("Entropy\n");
			
			System.out.println("=== Detailed Accuracy By Class ===");
			fw.write("=== Detailed Accuracy By Class ===\n");
			
			Tuples _tuple = new Tuples(_data,Integer.valueOf(_arr_str[2]));
			_tuple.outputDetailedAccuracyByClass();
			_tuple.outputDetailedAccuracyByClass(fw);
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	static boolean isFolds(String _str) {
		try {
			if(Integer.parseInt(_str)>0) return true;
		} catch (Exception e) {	}
		return false;
	}
	public static void main(String[] args) {
		// cau1("example.csv");
		// cau2("example.csv", "model.txt");
		// cau3(args);
	}

}