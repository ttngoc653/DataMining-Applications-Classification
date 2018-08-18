import java.util.ArrayList;
import java.util.List;

class AE {
	private String value;
	private List<HOfAE> list_class;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<HOfAE> getListClass() {
		return list_class;
	}
	public void setListClass(List<HOfAE> list_class) {
		this.list_class = list_class;
	}
	public AE(String _value) {
		this.value = _value;
		this.list_class = new ArrayList<>();
	}
	public AE(String _value, String _result_first) {
		this.value = _value;
		this.list_class = new ArrayList<>();
		this.list_class.add(new HOfAE(_result_first));
	}
	public void addListClass(String value) {
		this.list_class.add(new HOfAE(value));
	}
	public HOfAE searchH(String str_class) {
		for (int i = 0; i < this.getListClass().size(); i++) {
			if(this.getListClass().get(i).getValue().equals(str_class)) return this.getListClass().get(i);
		}
		return null;
	}
	public void addH(String str_class) {
		this.list_class.add(new HOfAE(str_class));
	}
	public void upCountClass(String str_class) {
		for (int i = 0; i < this.list_class.size(); i++) {
			if(this.list_class.get(i).getValue().equals(str_class)) this.list_class.get(i).upCountClass();
		}
	}
	public static double log2(double x) {
        return Math.log(x)/Math.log(2.0);
    }
	public Integer countElement() {
		Integer sum = 0;
		for (HOfAE h : list_class)
			sum += h.getCountClass();
		return sum;
	}
	public Double calculatorH() {
		Integer sum = countElement();
		Double calcu=0D;
		for (HOfAE h : list_class) {
			calcu = -((double)h.getCountClass()/sum)*log2((double)h.getCountClass()/sum);
		}
		return calcu;
	}
	static Integer sumValueAE(List<AE> list) {
		Integer sum = 0;
		for (AE ae : list) {
			sum += ae.countElement();
		}
		return sum;
	}
	public static Double calculatorAE(List<AE> list) {
		Double calcu = 0D;
		for (AE ae : list) {
			// System.out.println(ae.countElement() +" - "+ sumValueAE(list) +" - "+ ae.calculatorH());
			
			calcu += (double)ae.countElement() / sumValueAE(list) * ae.calculatorH(); 
		}
		return calcu;
	}
}
