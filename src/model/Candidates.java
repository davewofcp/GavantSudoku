package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Candidates {
	private ArrayList<char[]> values;
	
	public Candidates() {
		values = new ArrayList<char[]>();
	}
	
	public boolean hasValue(char c) {
		for (char[] value : values) {
			if (value[0] == c) return true;
		}
		return false;
	}

	public boolean hasValues() {
		return values.size() > 0;
	}
	
	public char[] getValues() {
		if (!hasValues()) return null;
		char[] returnVal = new char[values.size()];
		for (int i = 0; i < values.size(); i++) {
			returnVal[i] = values.get(i)[0];
		}
		return returnVal;
	}
	
	public void add(char c) {
		if (hasValue(c)) return;
		char[] ca = new char[1];
		ca[0] = c;
		values.add(ca);
	}
	
	public void add(char[] c) {
		for (char ch : c) {
			if (hasValue(ch)) continue;
			char[] ca = new char[1];
			ca[0] = ch;
			values.add(ca);
		}
	}
	
	public void remove(char c) {
		Iterator<char[]> itr = values.iterator();
		while (itr.hasNext()) {
			char[] value = itr.next();
			if (value[0] == c) itr.remove();
		}
	}
	
	public void removeAll() {
		values.clear();
	}
}
