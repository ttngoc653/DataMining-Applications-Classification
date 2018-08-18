import java.util.ArrayList;
import java.util.List;

class DecisionTree{
	private List<DecisionOfTree> field;
	public List<DecisionOfTree> getField() {
		return field;
	}
	public void setField(List<DecisionOfTree> field) {
		this.field = field;
	}
	public DecisionTree(List<DecisionOfTree> _field) {
		this.field = _field;
	}
	public DecisionTree() {
		this.field = new ArrayList<>();
	}
	public static void outputID3(DecisionOfTree _deci_current, String str, Data _data) {
		for (DecisionOfTree _deci : _deci_current.getNext()) {
			System.out.println(str + _data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci.getResult() == null ? "" : (": " + _deci.getResult())));
			if(_deci.getNext()!=null) outputID3(_deci, str + "| ", _data);
		}
	}
	public void outputID3(Data _data) {
		for (DecisionOfTree _deci : field) {
			System.out.println(_data.convertAttributeName(_deci.getIndexAttribute()) + " = " + _deci.getValue() + (_deci.getResult() == null ? "" : (": " + _deci.getResult())));
			if(_deci.getNext()!=null) outputID3(_deci, "| ", _data);
		}
	}
	private String result(String _str, DecisionOfTree _current) {
		for (DecisionOfTree decision : _current.getNext()) {
			if (decision.getValue().equals(_str.split(",")[decision.getIndexAttribute()])) {
				return decision.getResult() == null ? result(_str, decision) : decision.getResult();
			}
		}
		return "";
	}
	public String result(String _str) {
		for (DecisionOfTree decision : field) {
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
