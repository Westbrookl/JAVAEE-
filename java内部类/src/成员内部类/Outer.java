//package 成员内部类;

public class Outer {
	private Inner inner = null;
	public Outer(){
		
	}
    public Inner getInnerInstance() {
        if(inner == null)
            inner = new Inner();
        return inner;
    }
	protected class Inner{
		public Inner(){
			
		}
	}

}
