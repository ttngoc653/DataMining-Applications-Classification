import java.util.ArrayList;
import java.util.List;

class Decision{
	private Integer index_attribute;
	private String value;
	private String result;
	private List<Decision> next;
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
	public List<Decision> getNext() {
		return next;
	}
	public void setNext(List<Decision> next) {
		this.next = next;
	}
	public Decision() {
		this.index_attribute = -1;
		this.value = null;
		this.result = null;
		this.next = null;
	}
	public Decision(Integer _index_attribute, String _value, String _result, List<Decision> _next){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.next = _next;
	}
	public Decision(Integer _index_attribute, String _value, String _result){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.next = new ArrayList<>();
	}
	public Decision(Integer _index_attribute, String _value, List<Decision> _next){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = null;
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
	public static void outputID3(Decision _deci_current, String str, Data _data) {
		for (Decision _deci : _deci_current.getNext()) {
			System.out.println(str + _data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci == null ? "" : (": " + _deci.getResult())));
			outputID3(_deci, str + "| ", _data);
		}
	}
	public void outputID3(Data _data) {
		for (Decision _deci : field) {
			System.out.println(_data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci == null ? "" : (": " + _deci.getResult())));
			outputID3(_deci, "| ", _data);
		}
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
	public Double calculatorH() {
		Integer sum = countElement();
		Double calcu=0D;
		for (H h : list_class) {
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

public class Process {
	private static DecisionTree head;

	@SuppressWarnings("unused")
	private static Decision searchResultBefore(Decision _item) {
		for (Decision _deci : head.getField()) {
			if(_deci == _item) return null;
			return searchResultBefore(_item, _deci);	
		}
		return null;
	}
	private static Decision searchResultBefore(Decision _item, Decision _current) {
		for(int i=0;i<_current.getNext().size();i++) {
			if(_current.getNext().get(i) == _item)
				return _current;
			Decision temp = searchResultBefore(_item, _current.getNext().get(i));
			if(temp!=null) return temp;
		}
		return null;
	}
	
	static Boolean linkConsider(Decision _deci,Decision _current, String _value_consider) {
		for(Decision _de : _current.getNext()) {
			if(_de.getNext() == _deci) return _value_consider.split(",")[_de.getIndexAttribute()] == _de.getValue();
			Boolean temp = linkConsider(_deci, _de,_value_consider);
			return !temp ? false : _value_consider.split(",")[_de.getIndexAttribute()] == _de.getValue();
		}
		return false;
	}
	
	static boolean linkConsider(Decision _deci,String _value_consider) {
		if(head == null) return true;
		for (Decision _i_deci : head.getField()) {
			if(_i_deci == _deci) return true;
			return linkConsider(_deci, _i_deci, _value_consider);	
		}
		return true;
	}
	
	static Integer countStep(Decision _deci, Decision _current) {
		for (Decision _i_deci : _deci.getNext()) {
			if(_i_deci == _deci) return 1;
			Integer _i_count = countStep(_deci,_i_deci);
			return _i_count > 0 ? _i_count++ : 0;
		}
		
		return 0;
	}
	
	static Integer countStep(Decision _deci) {
		if(head == null) return 0;
		for (Decision _i_deci : head.getField()) {
			return countStep(_deci, _i_deci);	
		} 
		return 0;
	}

	static List<AE> initCountValue(Data _data, Integer _index, Decision _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getValue().size(); i++) {
			b_temp = false;
			for (int j = 0; j < temp.size(); j++) {
				if(temp.get(j).getValue().equals(_data.getValue().get(i).split(",")[_index])  && linkConsider(_decision, _data.getValue().get(i))) {
					if(!temp.get(j).getListClass().contains(temp.get(j).searchH(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1])))
						temp.get(j).addListClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					else temp.get(j).upCountClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					b_temp = true;
				}
			}
			if(!b_temp && linkConsider(_decision, _data.getValue().get(i))) {
				//System.out.println(_data.getValue().get(i) +" "+ _index +" "+ i);
				temp.add(new AE(_data.getValue().get(i).split(",")[_index]));
			}
		}
		return temp;
	}
	
	public static List<Decision> createTreeID3(Data _data, Decision _current, String _str) {
		Double min = AE.calculatorAE(initCountValue(_data, 0, _current)), _d_temp;
		Integer minValue = 0;
		for (int i = 1; i < (_data.getAttribute().size() - 1); i++) {
			_d_temp = AE.calculatorAE(initCountValue(_data, i, _current));
			//System.out.println(_d_temp);
			if(min > _d_temp){
				min = _d_temp;
				minValue = i;
			}
		}
		
		List<AE> list_xet = initCountValue(_data, minValue, _current);
		List<Decision> field = new ArrayList<>();
		if(head == null) head = new DecisionTree(field);
		for (AE ae : list_xet) {
			if(ae.getListClass().size()==1) {
				System.out.println(_str+minValue+" = "+ae.getValue()+" : "+ae.getListClass().get(0).getValue());
				field.add(new Decision(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				System.out.println(_str+minValue+" = "+ae.getValue());
				Decision tree = new Decision(minValue, ae.getValue(),null,null);
				field.add(tree);
				tree.setNext(createTreeID3(_data, tree, _str + "| "));
			}
		}

		return field;
	}
	
	public static DecisionTree createTreeID3(Data _data) {
		return new DecisionTree(createTreeID3(_data, null, ""));
	}
}
