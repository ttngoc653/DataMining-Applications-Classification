
class HOfAE {
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
	public HOfAE(String _value) {
		this.value = _value;
		this.count_class = 1;
	}
	public void upCountClass() {
		this.count_class++;
	}
}