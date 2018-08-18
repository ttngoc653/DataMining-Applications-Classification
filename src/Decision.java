import java.util.ArrayList;
import java.util.List;

class DecisionOfTree {
	private Integer index_attribute;
	private String value;
	private String result;
	private List<DecisionOfTree> next;
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
	public List<DecisionOfTree> getNext() {
		return next;
	}
	public void setNext(List<DecisionOfTree> next) {
		this.next = next;
	}
	public DecisionOfTree() {
		this.index_attribute = -1;
		this.value = null;
		this.result = null;
		this.next = null;
	}
	public DecisionOfTree(Integer _index_attribute, String _value, String _result, List<DecisionOfTree> _next){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.next = _next;
	}
	public DecisionOfTree(Integer _index_attribute, String _value, String _result){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = _result;
		this.next = new ArrayList<>();
	}
	public DecisionOfTree(Integer _index_attribute, String _value, List<DecisionOfTree> _next){
		this.index_attribute = _index_attribute;
		this.value = _value;
		this.result = null;
		this.next = _next;
	}
}