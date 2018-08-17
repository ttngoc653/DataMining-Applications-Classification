
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("start");
		Data _data = ImportData.getDataFromFile("example.csv");
		// for (String string : _data.getAttribute()) System.out.println(string);
		// for (String string : _data.getValue()) System.out.println(string);
		
		Process.createTreeID3(_data).outputID3(_data);;
	}

}
