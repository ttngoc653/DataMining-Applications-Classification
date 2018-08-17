
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Data _data = ImportData.getDataFromFile("letter-train.arff");
		Process.createTreeID3(_data).outputID3(_data);;
	}

}
