import world.sound.tunes.Chord;
import java.util.*;

/**Represents a Chord with information about its mode and inversion*/
public class OurChord extends Chord{
	
	/**Represents the pitch of the root of this chord*/
	int base;
	
	/**Represents the chord type, 0 for major, 1 for minor, 2 for augmented, 3 for diminished*/
	int MODE;
	
	/**Represents the inversion type*/
	int INV;
	
	/**Contains the one algorithm of use*/
	Algorithms algo = new Algorithms();
	
	ArrayList<Integer> major = new ArrayList<Integer>(Arrays.asList(0, 4, 7, 12, 16));
	ArrayList<Integer> minor = new ArrayList<Integer>(Arrays.asList(0, 3, 7, 12, 15));
	ArrayList<Integer> aug = new ArrayList<Integer>(Arrays.asList(0, 4, 8, 12, 16));
	ArrayList<Integer> dim = new ArrayList<Integer>(Arrays.asList(0, 3, 6, 12, 15));
	ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>(Arrays.asList(this.major, this.minor, this.aug, this.dim));
	ArrayList<String> modeStrings = new ArrayList<String>(Arrays.asList("major", "minor", "augmented", "diminished"));
	ArrayList<String> invStrings = new ArrayList<String>(Arrays.asList("", "first inversion", "second inversion"));
	
	OurChord(int base, int mode, int inv){
		super();
		this.base = base;
		this.MODE = mode;
		this.INV = inv;
	}
	
	public int getBase(){
		return this.base;
	}
	
	public int getMode(){
		return this.MODE;
	}
	
	public int getInv(){
		return this.INV;
	}
	
	public ArrayList<Integer> getOffsets(){
		ArrayList<Integer> offsets = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++){
			offsets.add(this.table.get(MODE).get(i+INV));
		}
		return offsets;
	}
	
	public ArrayList<Integer> getPitches(){
		ArrayList<Integer> pitches = new ArrayList<Integer>();
		for(int i:this.getOffsets()){
			pitches.add(this.base + i);
		}
		return pitches;
	}
	
	public Chord makeChord(){
		Chord someChord = new Chord();
		for(int i:this.getPitches()){
			someChord.addNote(i, 8);
		}
		return someChord;
	}

	public String toString(){
        return algo.noteName(base) + " " + this.modeStrings.get(MODE) + " " + this.invStrings.get(INV);
    }

	public void setMode(int mode){
		if(mode < 0 || mode > 3){
			throw new RuntimeException("Invalid mode setting.");
		}
		this.MODE = mode;
	}
	
	public void setMode(String mode){
		if(mode.equals("maj")){
			this.MODE = 0;
		}
		else if(mode.equals("min")){
			this.MODE = 1;
		}
		else if(mode.equals("aug")){
			this.MODE = 2;
		}
		else if(mode.equals("dim")){
			this.MODE = 3;
		}
		else throw new RuntimeException("Invalid mode setting.");
		
	}

	public void increaseInv(){
		if(this.INV < 2)
			this.INV++;
	}
	
	public void decreaseInv(){
		if(this.INV > 0)
			this.INV--;
	}
	
	public void setBase(int base){
		if(base < 0 || base > 128){
			throw new RuntimeException("Invalid base note value.");
		}
		this.base = base;
	}


}