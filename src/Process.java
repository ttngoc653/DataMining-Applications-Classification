import java.util.ArrayList;
import java.util.List;

class Decision{
	private Integer index_attribute;
	private String value;
	private String result;
	private DecisionTree next;
	public Integer getIndexAttribute() {
		return index_attribute;
	}
	public void setIndexAttribute(Integer index_attribute) {
		this.index_attribute = index_attribute;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public DecisionTree getNext() {
		return next;
	}
	public void setNext(DecisionTree next) {
		this.next = next;
	}
	public Decision() {
		this.index_attribute = -1;
		this.value = null;
		this.result = null;
		this.next = null;
	}
	public Decision(Integer _index_attribute, String _value, String _result, DecisionTree _next){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.next = _next;
	}
}

class DecisionTree{
	private List<Decision> field;
	public List<Decision> getField() {
		return field;
	}
	public void setField(List<Decision> field) {
		this.field = field;
	}
	public DecisionTree(List<Decision> _field) {
		this.field = _field;
	}
	public DecisionTree() {
		this.field = new ArrayList<>();
	}
}

class H {
	private String value;
	private Integer count_class;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getCountClass() {
		return count_class;
	}
	public void setCountClass(Integer count_class) {
		this.count_class = count_class;
	}
	public H(String _value) {
		this.value = _value;
		this.count_class = 1;
	}
	public void upCountClass() {
		this.count_class++;
	}
}

class AE {
	private String value;
	private List<H> list_class;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<H> getListClass() {
		return list_class;
	}
	public void setListClass(List<H> list_class) {
		this.list_class = list_class;
	}
	public AE(String _value) {
		this.value = _value;
		this.list_class = new ArrayList<>();
	}
	public void addListClass(String value) {
		this.list_class.add(new H(value));
	}
	public H searchH(String str_class) {
		for (int i = 0; i < this.getListClass().size(); i++) {
			if(this.getListClass().get(i).getValue().equals(str_class)) return this.getListClass().get(i);
		}
		return null;
	}
	public void addH(String str_class) {
		this.list_class.add(new H(str_class));
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
		for (H h : list_class)
			sum += h.getCountClass();
		return sum;
	}
	public double calculatorH() {
		Integer sum = countElement();
		Double calcu=0D;
		for (H h : list_class)
			calcu = -((double)h.getCountClass()/sum)*log2(h.getCountClass()/sum);
		
		return calcu;
	}
	public static Double calculatorAE(List<AE> list) {
		Double calcu = 0D;
		for (AE ae : list)
			calcu += Double.valueOf(ae.getValue()) / ae.countElement() * ae.calculatorH(); 
		
		return calcu;
	}
}

public class Process {
	private static DecisionTree head;

	@SuppressWarnings("unused")
	private static DecisionTree searchResultBefore(DecisionTree _item) {
		return searchResultBefore(_item, head);
	}
	private static DecisionTree searchResultBefore(DecisionTree _item, DecisionTree _current) {
		if(_item == head)	return null;
		for(int i=0;i<_current.getField().size();i++) {
			if(_current.getField().get(i).getNext() == _item)
				return _current;
			DecisionTree temp = searchResultBefore(_item, _current.getField().get(i).getNext());
			if(temp!=null) return temp;
		}
		return null;
	}
	
	static Boolean linkConsider(DecisionTree _deci,DecisionTree _current, String _value_consider) {
		for(Decision _de : _current.getField()) {
			if(_de.getNext() == _deci) return _value_consider.split(",")[_de.getIndexAttribute()] == _de.getValue();
			Boolean temp = linkConsider(_deci, _de.getNext(),_value_consider);
			if(temp != null) return !temp ? false : _value_consider.split(",")[_de.getIndexAttribute()] == _de.getValue();
		}
		return false;
	}
	
	static boolean linkConsider(DecisionTree _deci,String _value_consider) {
		if(head == null) return true;
		return linkConsider(_deci, head, _value_consider);
	}

	static List<AE> initCountValue(Data _data, Integer _index, DecisionTree _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getValue().size(); i++) {
			b_temp = false;
			for (int j = 0; j < temp.size(); j++) {
				if(temp.get(j).getValue().equals(_data.getValue().get(i).split(",")[_index])) {
					if(!temp.get(j).getListClass().contains(temp.get(j).searchH(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1])))
						temp.get(j).addListClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					else temp.get(j).upCountClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					b_temp = true;
				}
			}
			if(!b_temp && linkConsider(_decision, _data.getValue().get(i))) temp.add(new AE(_data.getValue().get(i).split(",")[_index]));
		}
		return temp;
	}
	
	public static DecisionTree createTreeID3(Data _data, DecisionTree _current) {
		Double min = Double.MAX_VALUE;
		Integer minValue = 0;
		for (int i = 0; i < _data.getAttribute().size(); i++) {
			initCountValue(_data, i, null);
			if(min > AE.calculatorAE(initCountValue(_data, i, null))){
				min = AE.calculatorAE(initCountValue(_data, i, null));
				minValue = i;
			}
		}
		
		List<AE> list_xet = initCountValue(_data, minValue, _current);
		if(head == null) _current = new DecisionTree();
		List<Decision> field = new ArrayList<>();
		for (AE ae : list_xet) {
			if(ae.getListClass().size()==1) {
				field.add(new Decision(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				DecisionTree tree = new DecisionTree();
				field.add(new Decision(minValue, ae.getValue(), null, createTreeID3(_data, tree)));
			}
		}
		/*for (AE ae : list_xet) {
			System.out.println(ae.getValue()+" + "+ae.getListClass().size());
			for (H h : ae.getListClass()) {
				System.out.println("  "+h.getValue()+" - "+h.getCountClass());
			}
		}*/
		
		return new DecisionTree(field);
	}
	
	public static DecisionTree createTreeID3(Data _data) {
		return createTreeID3(_data, null);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
