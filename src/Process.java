import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class DecisionTree{
	private Integer index_attribute;
	private String value;
	private String result;
	private List<DecisionTree> next;
	public Integer getIndexAtribute() {
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
	public DecisionTree(Integer _index_attribute,String _value,String _result) {
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.setNext(new ArrayList<>());
	}
	public List<DecisionTree> getNext() {
		return next;
	}
	public void setNext(List<DecisionTree> next) {
		this.next = next;
	}
}

public class Process {
	private static DecisionTree head;
	
	@SuppressWarnings("unused")
	private static DecisionTree searchResultBefore(DecisionTree _item) {
		return searchResultBefore(_item, head);
	}
	private static DecisionTree searchResultBefore(DecisionTree _item, DecisionTree _current) {
		for(int i=0;i<_current.getNext().size();i++) {
			if(_current.getNext().get(i).getIndexAtribute()==_item.getIndexAtribute() && _current.getNext().get(i).getValue() == _item.getValue())
				return _current;
			DecisionTree temp = searchResultBefore(_item, _current.getNext().get(i));
			if(temp!=null) return temp;
		}
		if(_item == head)	return null;
		return null;
	}
	
	public static DecisionTree createTreeID3(Data _data, DecisionTree _current) {
		List<String> field_class = new ArrayList<>();
		List<Integer> field_class_value = new ArrayList<>();
		
		for (int i = 0; i < _data.getValue().size(); i++) {
			if(field_class.indexOf(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length -1]) == -1) {
				field_class.add(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length -1]);
				field_class_value.add(1);
			}else {
				field_class_value.set(field_class.indexOf(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length -1]), field_class_value.get(field_class.indexOf(_data.getValue().get(i).split(",")[_data.getValue().get(i).split(",").length -1]))+1);
			}
		}
		System.out.println(field_class.size()+" - "+field_class_value.size());
		for (int i = 0; i < field_class.size(); i++) {
			System.out.println(field_class.get(i)+" ::::: "+field_class_value.get(i));
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
