import java.util.ArrayList;
import java.util.List;

public class Process {

	static Boolean linkConsider(DecisionOfTree _deci,DecisionOfTree _current, String _value_consider) {
		if(_current.getNext() == null) return null;
		for(DecisionOfTree _i_deci : _current.getNext()) {
			if(_i_deci == _deci) 
				return _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
			Boolean temp = linkConsider(_deci, _i_deci,_value_consider);
			if(temp != null) return !temp ? false : _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
		}
		return null;
	}
	
	static Boolean linkConsider(DecisionTree _head, DecisionOfTree _deci, String _value_consider) {
		if(_head == null) return true;
		for (DecisionOfTree _i_deci : _head.getField()) {
			if(_i_deci == _deci) {
				return _value_consider.split(",")[_i_deci.getIndexAttribute()].equals(_i_deci.getValue());
			}
			Boolean _b_temp = linkConsider(_deci, _i_deci, _value_consider);
			if(_b_temp != null)	return _b_temp;	
		}
		return true;
	}
	
	static Boolean equalDecisionBefore(DecisionOfTree _deci,DecisionOfTree _current, Integer _index) {
		if(_current.getNext() == null) return null;
		for(DecisionOfTree _i_deci : _current.getNext()) {
			if(_i_deci == _deci) 
				return _index == _i_deci.getIndexAttribute();
			Boolean _b_temp = equalDecisionBefore(_deci, _i_deci, _index);
			if(_b_temp != null)	return _b_temp ? _b_temp : _i_deci.getIndexAttribute() == _index;
		}
		return null;
	}
	
	private static Boolean equalDecisionBefore(DecisionTree _deci_tree, DecisionOfTree _deci, Integer _index) {
		if(_deci_tree == null) return false; 
		for (DecisionOfTree _i_deci : _deci_tree.getField()) {
			if(_i_deci == _deci) 
				return _index == _i_deci.getIndexAttribute();
			Boolean _b_temp = equalDecisionBefore(_deci, _i_deci, _index);
			if(_b_temp != null)	return _b_temp ? _b_temp : _i_deci.getIndexAttribute() == _index;	
		}
		return false;
	}
	
	static List<AE> initCountValue(Data _data, DecisionTree _deci_tree, Integer _index, DecisionOfTree _decision){
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
	
	public static List<DecisionOfTree> createTreeID3(Data _data,DecisionTree _deci_tree, DecisionOfTree _current, String _str) {
		List<AE> _list_xet_min = initCountValue(_data, _deci_tree, 0, _current), _list_xet;
		Double min = AE.calculatorAE(_list_xet_min);
		Integer minValue = 0;
		for (int i = 1; i < (_data.getAttribute().size() - 1); i++) {
			_list_xet = initCountValue(_data, _deci_tree, i, _current);
			//System.out.println(_d_temp);
			if(min > AE.calculatorAE(_list_xet)){
				_list_xet_min = _list_xet;
				min = AE.calculatorAE(_list_xet_min);
				minValue = i;
			}
		}
		
		List<DecisionOfTree> field = new ArrayList<>();
		if(_deci_tree == null) _deci_tree = new DecisionTree(field);
		for (AE ae : _list_xet_min) {
			if(ae.getListClass().size()==1) {
				// System.out.println(_str+minValue+" = "+ae.getValue()+" : "+ae.getListClass().get(0).getValue());
				field.add(new DecisionOfTree(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				// System.out.println(_str + minValue + " = " + ae.getValue());
				DecisionOfTree _tree = new DecisionOfTree(minValue, ae.getValue(),null,null);
				field.add(_tree);
				_tree.setNext(createTreeID3(_data, _deci_tree, _tree, _str + "| "));
			}
		}

		return field;
	}
	
	public static DecisionTree createTreeID3(Data _data) {
		return new DecisionTree(createTreeID3(_data, null, null, ""));
	}
	
	// k-folds
	static List<AE> initCountValue(Data _data, Integer _i_folds, DecisionTree _deci_tree, Integer _index, DecisionOfTree _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getValue().size(); i++) {
			if(Integer.valueOf(_i_folds * _data.getValue().size() / 10) <= i && i <= Integer.valueOf((_i_folds + 1) * _data.getValue().size() / 10 - 1)) ;
			else {
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
		}
		return temp;
	}
	
	public static List<DecisionOfTree> createTreeID3(Data _data, Integer _i_folds, DecisionTree _deci_tree, DecisionOfTree _current, String _str) {
		List<AE> _list_xet_min = initCountValue(_data, _i_folds, _deci_tree, 0, _current), _list_xet;
		Double min = Double.MAX_VALUE;
		Integer minValue = -1;
		for (int i = 0; i < (_data.getAttribute().size() - 1); i++) {
			_list_xet = initCountValue(_data, _i_folds, _deci_tree, i, _current);
			//System.out.println(_d_temp);
			if(min > AE.calculatorAE(_list_xet) && !equalDecisionBefore(_deci_tree,_current, i)){
				_list_xet_min = _list_xet;
				min = AE.calculatorAE(_list_xet_min);
				minValue = i;
			}
		}
		
		List<DecisionOfTree> field = new ArrayList<>();
		if(_deci_tree == null) _deci_tree = new DecisionTree(field);
		for (AE ae : _list_xet_min) {
			if(ae.getListClass().size()==1) {
				System.out.println(_str + minValue + " = " + ae.getValue() + " : " + ae.getListClass().get(0).getValue());
				field.add(new DecisionOfTree(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				System.out.println(_str + minValue + " = " + ae.getValue());
				DecisionOfTree _tree = new DecisionOfTree(minValue, ae.getValue(),null,null);
				field.add(_tree);
				_tree.setNext(createTreeID3(_data, _i_folds, _deci_tree, _tree, _str + "| "));
			}
		}

		return field;
	}

	public static DecisionTree createTreeID3(Data _data, Integer _i_folds) {
		return new DecisionTree(createTreeID3(_data, _i_folds, null, null, ""));
	}
}
