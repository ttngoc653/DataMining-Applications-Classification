import java.util.ArrayList;
import java.util.List;

public class Process {
	static Boolean linkConsiderField(DecisionOfTree _deci, DecisionOfTree _current, String _value_consider) {
		if(_current.getNext() == null) return null;
		for(int i=0;i<_current.getNext().size();i++) { 
			if(_current.getNext().get(i).getIndexAttribute() == _deci.getIndexAttribute() && _current.getNext().get(i).getValue() == _deci.getValue()) 
				return _value_consider.split(",")[_current.getNext().get(i).getIndexAttribute()].equals(_current.getNext().get(i).getValue());
			Boolean temp = linkConsiderField(_deci, _current.getNext().get(i),_value_consider);
			if(temp != null) return !temp ? false : _value_consider.split(",")[_current.getNext().get(i).getIndexAttribute()].equals(_current.getNext().get(i).getValue());
		}
		return null;
	}
	
	static Boolean linkConsider(DecisionOfTree _head, DecisionOfTree _deci, String _value_consider) {
		if(_head == null) return true;
		for(int i=0;i<_head.getNext().size();i++) { 
			if(_head.getNext().get(i).getIndexAttribute() == _deci.getIndexAttribute() && _head.getNext().get(i).getValue() == _deci.getValue()) {
				return _value_consider.split(",")[_head.getNext().get(i).getIndexAttribute()].equals(_head.getNext().get(i).getValue());
			}
			Boolean _b_temp = linkConsiderField(_deci, _head.getNext().get(i), _value_consider);
			if(_b_temp != null)	return _b_temp;	
		}
		return true;
	}
	
	static Boolean equalDecisionBefore(DecisionOfTree _deci,DecisionOfTree _current, Integer _index) {
		if(_current.getNext() == null) return null;
		for(int i=0;i<_current.getNext().size();i++) { 
			if(_current.getNext().get(i).getIndexAttribute() == _deci.getIndexAttribute() && _current.getNext().get(i).getValue() == _deci.getValue()) 
				return _index == _current.getNext().get(i).getIndexAttribute();
			Boolean _b_temp = equalDecisionBefore(_deci, _current.getNext().get(i), _index);
			if(_b_temp != null)	return _b_temp ? _b_temp : _current.getNext().get(i).getIndexAttribute() == _index;
		}
		return null;
	}
	
	static List<AE> initCountValue(DataFile _data, DecisionOfTree _deci_tree, Integer _index, DecisionOfTree _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getData().size(); i++) {
			b_temp = false;
			for (int j = 0; j < temp.size(); j++) {
				if(temp.get(j).getValue().equals(_data.getData().get(i).split(",")[_index]) && linkConsider(_deci_tree, _decision, _data.getData().get(i))) {
					if(!temp.get(j).getListClass().contains(temp.get(j).searchH(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1])))
						temp.get(j).addListClass(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1]);
					else temp.get(j).upCountClass(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1]);
					b_temp = true;
				}
			}
			if(!b_temp && linkConsider(_deci_tree, _decision, _data.getData().get(i))) {
				temp.add(new AE(_data.getData().get(i).split(",")[_index], _data.getData().get(i).split(",")[_data.getAttribute().size() - 1]));
			}
		}
		return temp;
	}
	
	public static void createTreeID3(DataFile _data,DecisionOfTree _deci_tree, DecisionOfTree _current) {
		List<AE> _list_xet_min = initCountValue(_data, _deci_tree, 0, _current), _list_xet;
		Double min = AE.calculatorAE(_list_xet_min);
		Integer minValue = 0;
		_current.setNext(new ArrayList<>());
		for (int i = 1; i < (_data.getAttribute().size() - 1); i++) {
			_list_xet = initCountValue(_data, _deci_tree, i, _current);
			//System.out.println(_d_temp);
			if(min > AE.calculatorAE(_list_xet)){
				_list_xet_min = _list_xet;
				min = AE.calculatorAE(_list_xet_min);
				minValue = i;
			}
		}
		for (AE ae : _list_xet_min) {
			if(ae.getListClass().size() == 1) {
				_current.getNext().add(new DecisionOfTree(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				DecisionOfTree _item = new DecisionOfTree(minValue, ae.getValue(),null, null);
				_current.getNext().add(_item);
				createTreeID3(_data, _deci_tree, _item);
			}
		}
	}
	
	public static DecisionTree createTreeID3(DataFile _data) {
		DecisionOfTree _deci = new DecisionOfTree(null, null, null, new ArrayList<>());
		createTreeID3(_data, _deci, _deci);
		return new DecisionTree(_deci.getNext());
	}
	
	// k-folds
	static List<AE> initCountValue(DataFile _data, Integer _i_folds, DecisionOfTree _deci_tree, Integer _index, DecisionOfTree _decision){
		List<AE> temp = new ArrayList<>();
		Boolean b_temp = false;
		for (int i = 0; i < _data.getData().size(); i++) {
			if(Integer.valueOf(_i_folds * _data.getData().size() / 10) <= i && i <= Integer.valueOf((_i_folds + 1) * _data.getData().size() / 10 - 1)) ;
			else {
				b_temp = false;
				for (int j = 0; j < temp.size(); j++) {
					if(temp.get(j).getValue().equals(_data.getData().get(i).split(",")[_index]) && linkConsider(_deci_tree, _decision, _data.getData().get(i))) {
						if(!temp.get(j).getListClass().contains(temp.get(j).searchH(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1])))
							temp.get(j).addListClass(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1]);
						else temp.get(j).upCountClass(_data.getData().get(i).split(",")[_data.getAttribute().size() - 1]);
						b_temp = true;
					}
				}
				if(!b_temp && linkConsider(_deci_tree, _decision, _data.getData().get(i))) {
					temp.add(new AE(_data.getData().get(i).split(",")[_index], _data.getData().get(i).split(",")[_data.getAttribute().size() - 1]));
				}
			}
		}
		return temp;
	}
	
	public static void createTreeID3(DataFile _data, Integer _i_folds, DecisionOfTree _deci_tree, DecisionOfTree _current, String _str) {
		List<AE> _list_xet_min = initCountValue(_data, _i_folds, _deci_tree, 0, _current), _list_xet;
		Double min = AE.calculatorAE(_list_xet_min);
		Integer minValue = 0;
		_current.setNext(new ArrayList<>());
		for (int i = 1; i < (_data.getAttribute().size() - 1); i++) {
			_list_xet = initCountValue(_data,_i_folds, _deci_tree, i, _current);
			//System.out.println(_d_temp);
			if(min > AE.calculatorAE(_list_xet)){
				_list_xet_min = _list_xet;
				min = AE.calculatorAE(_list_xet_min);
				minValue = i;
			}
		}
		for (AE ae : _list_xet_min) {
			if(ae.getListClass().size() == 1) {
				//System.out.println(_str+minValue+" = "+ae.getValue()+" : "+ae.getListClass().get(0).getValue());
				_current.getNext().add(new DecisionOfTree(minValue, ae.getValue(), ae.getListClass().get(0).getValue(), null));
			}else {
				//System.out.println(_str + minValue + " = " + ae.getValue());
				DecisionOfTree _item = new DecisionOfTree(minValue, ae.getValue(),null, null);
				_current.getNext().add(_item);
				createTreeID3(_data, _i_folds, _deci_tree, _item, _str + "| ");
			}
		}
	}

	public static DecisionTree createTreeID3(DataFile _data, Integer _i_folds) {
		DecisionOfTree _deci = new DecisionOfTree(null, null, null, new ArrayList<>());
		createTreeID3(_data, _i_folds, _deci, _deci, "");
		return new DecisionTree(_deci.getNext());
	}
}