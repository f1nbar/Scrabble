import java.util.LinkedList;

public class Frame {
	
	private int FRAME_SIZE = 7;
	
	LinkedList<Tile> frame = new LinkedList<Tile>();	//linked list to store tile objects
	
	
	public void remove_letter(Tile ...c) {				//This function removes the tiles from the frame
		
		for(int i =0;i< c.length;i++) {
			frame.remove(c[i]);	
			System.out.println(toString());
		}
	}
	
	public boolean check_letters(Tile ...c) {			//This checks if the letters are avalaible or not returning a suitable boolean
		
		for(int i=0;i<c.length;i++) {
			if(frame.indexOf(c[i]) == -1) {
				return false;
			}
		}
		return true;
	}
	
	public boolean isempty() {							//returns true if frame is empty
		
		if(frame.isEmpty()) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public String toString() {							//returns the string 
		
		String result = null;
		
		for(int i =0;i<frame.size();i++) {
			if(result == null) {
				result = frame.get(i).getLetter();
			} else {
			result = result + frame.get(i).getLetter();
			}
		}
		
		return result;
	}
	
	
	public boolean fill_frame(Tile ...t) {
		
		if(t.length <= FRAME_SIZE-frame.size()) {
			for(int i = 0;i<t.length;i++) {
				frame.add(t[i]);
			}
			
			return true;
		} else {
			return false;
		}
		
	}
	
	public Tile getlindexletter(int i) {
		return frame.get(i);
	}
	
	
	

}
