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
			System.out.println(str + _data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci.getResult() == null ? "" : (": " + _deci.getResult())));
			if(_deci.getNext()!=null) outputID3(_deci, str + "| ", _data);
		}
	}
	public void outputID3(Data _data) {
		for (Decision _deci : field) {
			System.out.println(_data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci.getResult() == null ? "" : (": " + _deci.getResult())));
			if(_deci.getNext()!=null) outputID3(_deci, "| ", _data);
		}
	}
	private String result(String _str, Decision _current) {
		for (Decision decision : _current.getNext()) {
			if (decision.getValue().equals(_str.split(",")[decision.getIndexAttribute()])) {
				return decision.getResult() == null ? result(_str, decision) : decision.getResult();
			}
		}
		return "";
	}
	public String result(String _str) {
		for (Decision decision : field) {
			if (decision.getValue().equals(_str.split(",")[decision.getIndexAttribute()])) {
				return decision.getResult() == null ? result(_str, decision) : decision.getResult();
			}
		}
		return "";
	}
	public List<Boolean> resultFull(List<String> _list_value) {
		List<Boolean> _b_result = new ArrayList<>();
		for (String _i_str : _list_value) {
			_b_result.add(_i_str.split(",")[_i_str.split(",").length - 1].equals(result(_i_str)));
		}
		return _b_result;
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
	public AE(String _value, String _result_first) {
		this.value = _value;
		this.list_class = new ArrayList<>();
		this.list_class.add(new H(_result_first));
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

	static Boolean linkConsider(Decision _deci,Decision _current, String _value_consider) {
		if(_current.getNext() == null) return null;
		for(Decision _i_deci : _current.getNext()) {
			if(_i_deci == _deci) 
				return _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
			Boolean temp = linkConsider(_deci, _i_deci,_value_consider);
			if(temp != null) return !temp ? false : _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
		}
		return null;
	}
	
	static Boolean linkConsider(DecisionTree _head, Decision _deci, String _value_consider) {
		if(_head == null) return true;
		for (Decision _i_deci : _head.getField()) {
			if(_i_deci == _deci) {
				return _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
			}
			Boolean _b_temp = linkConsider(_deci, _i_deci, _value_consider);
			if(_b_temp != null)	return _b_temp;	
		}
		return true;
	}
	
	static List<AE> initCountValue(Data _data, DecisionTree _deci_tree, Integer _index, Decision _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getValue().size(); i++) {
			b_temp = false;
			for (int j = 0; j < temp.size(); j++) {
				if(temp.get(j).getValue().equals(_data.getValue().get(i).split(",")[_index]) && linkConsider(_deci_tree, _decision, _data.getValue().get(i))) {
					if(!temp.get(j).getListClass().contains(temp.get(j).searchH(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1])))
						temp.get(j).addListClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					else temp.get(j).upCountClass(_data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]);
					b_temp = true;
				}
			}
			if(!b_temp && linkConsider(_deci_tree, _decision, _data.getValue().get(i))) {
				temp.add(new AE(_data.getValue().get(i).split(",")[_index], _data.getValue().get(i).split(",")[_data.getAttribute().size() - 1]));
			}
		}
		return temp;
	}
	
	public static List<Decision> createTreeID3(Data _data,DecisionTree _deci_tree, Decision _current, String _str) {
		Double min = AE.calculatorAE(initCountValue(_data, _deci_tree, 0, _current)), _d_temp;
		Integer minValue = 0;
		for (int i = 1; i < (_data.getAttribute().size() - 1); i++) {
			_d_temp = AE.calculatorAE(initCountValue(_data, _deci_tree, i, _current));
			//System.out.println(_d_temp);
			if(min > _d_temp){
				min = _d_temp;
				minValue = i;
			}
		}
		
		List<AE> list_xet = initCountValue(_data, _deci_tree, minValue, _current);
		List<Decision> field = new ArrayList<>();
		if(_deci_tree == null) _deci_tree = new DecisionTree(field);
		for (AE ae : list_xet) {
			if(ae.getListClass().size()==1) {
				// System.out.println(_str+minValue+" = "+ae.getValue()+" : "+ae.getListClass().get(0).getValue());
				field.add(new Decision(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				// System.out.println(_str + minValue + " = " + ae.getValue());
				Decision _tree = new Decision(minValue, ae.getValue(),null,null);
				field.add(_tree);
				_tree.setNext(createTreeID3(_data, _deci_tree, _tree, _str + "| "));
			}
		}

		return field;
	}
	
	public static DecisionTree createTreeID3(Data _data) {
		return new DecisionTree(createTreeID3(_data, null, null, ""));
	}
}
