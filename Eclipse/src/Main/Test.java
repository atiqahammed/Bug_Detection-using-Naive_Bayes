package Main;

import Detector.Detector;

public class Test {

	public static void main(String[] args) {
		Detector detector = new Detector();
		detector.input("outputGcc.txt");
		detector.process();

	}

}
