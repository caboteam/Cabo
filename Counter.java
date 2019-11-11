package CP317;

public class Counter {
	private int value = 0;

	private void _countup() {
		this.value += 1;
	}
	
	public int count() {
		this._countup();
		return this.value;
	}
	
	public void reset() {
		this.value = 0;
	}
	
	public int getCount() {
		return this.value;
	}
	
	public void print() {
		System.out.println(this.value);
	}
	
}
