package Detector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Detector {
	private String[] classValues = {"1", "2", "3", "4", "5"};
	private File dataFile;
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private int totalTest = 0;
	private int totalRightAns = 0;

	private ArrayList<Data> dataListOfClass1;
	private ArrayList<Data> dataListOfClass2;
	private ArrayList<Data> dataListOfClass3;
	private ArrayList<Data> dataListOfClass4;
	private ArrayList<Data> dataListOfClass5;

	private Map<String, Integer> wordCountOfClass1;
	private Map<String, Integer> wordCountOfClass2;
	private Map<String, Integer> wordCountOfClass3;
	private Map<String, Integer> wordCountOfClass4;
	private Map<String, Integer> wordCountOfClass5;
	private Map<String, Integer> totalCount;


	private ArrayList<Integer> takenClass1;
	private ArrayList<Integer> takenClass2;
	private ArrayList<Integer> takenClass3;
	private ArrayList<Integer> takenClass4;
	private ArrayList<Integer> takenClass5;
	private Map<String, ArrayList<Integer>> takenClassMaping;


	private ArrayList<Data> trainDataList;
	private ArrayList<Data> testDataList;


	private Map<String, ArrayList<Data>> dataListMaping;
	private Map<String, Map<String, Integer>> wordListMaping;


	private Map<String, Integer> totalGoldLevel;
	private Map<String, Integer> totalPredectedAs;
	private Map<String, Integer> truePositive;



	public void input(String path) {
		init(path);
		String input = null;
		try {
			while ((input = bufferedReader.readLine()) != null) {
				String data[] = input.split(" ");
				dataListMaping.get(data[0]).add(new Data(data));
			}
		} catch (IOException e) {
			System.out.println("got error in reading data using bufferReader");
		}
	}

	private void init(String filePath) {
		dataFile = new File(filePath);
		try {
			fileReader = new FileReader(dataFile);
		} catch (FileNotFoundException e) {
			System.out.println("file not Found");
		}
		bufferedReader = new BufferedReader(fileReader);

		dataListOfClass1 = new ArrayList<>();
		dataListOfClass2 = new ArrayList<>();
		dataListOfClass3 = new ArrayList<>();
		dataListOfClass4 = new ArrayList<>();
		dataListOfClass5 = new ArrayList<>();



		dataListMaping = new HashMap<>();
		dataListMaping.put("1", dataListOfClass1);
		dataListMaping.put("2", dataListOfClass2);
		dataListMaping.put("3", dataListOfClass3);
		dataListMaping.put("4", dataListOfClass4);
		dataListMaping.put("5", dataListOfClass5);

		wordListMaping = new HashMap<>();
		wordListMaping.put("1", wordCountOfClass1);
		wordListMaping.put("2", wordCountOfClass2);
		wordListMaping.put("3", wordCountOfClass3);
		wordListMaping.put("4", wordCountOfClass4);
		wordListMaping.put("5", wordCountOfClass5);

		takenClass1 = new ArrayList<>();
		takenClass2 = new ArrayList<>();
		takenClass3 = new ArrayList<>();
		takenClass4 = new ArrayList<>();
		takenClass5 = new ArrayList<>();

		takenClassMaping = new HashMap<String, ArrayList<Integer>>();
		takenClassMaping.put("1", takenClass1);
		takenClassMaping.put("2", takenClass2);
		takenClassMaping.put("3", takenClass3);
		takenClassMaping.put("4", takenClass4);
		takenClassMaping.put("5", takenClass5);

		totalGoldLevel = new HashMap<>();
		totalGoldLevel.put("1", 0);
		totalGoldLevel.put("2", 0);
		totalGoldLevel.put("3", 0);
		totalGoldLevel.put("4", 0);
		totalGoldLevel.put("5", 0);

		totalPredectedAs =  new HashMap<String, Integer>();
		totalPredectedAs.put("1", 0);
		totalPredectedAs.put("2", 0);
		totalPredectedAs.put("3", 0);
		totalPredectedAs.put("4", 0);
		totalPredectedAs.put("5", 0);

		truePositive = new HashMap<String, Integer>();
		truePositive.put("1", 0);
		truePositive.put("2", 0);
		truePositive.put("3", 0);
		truePositive.put("4", 0);
		truePositive.put("5", 0);
	}

	public void process() {

		for(int j = 0; j < 10; j++){
			trainDataList = new ArrayList<>();
			testDataList = new ArrayList<>();

			for(int i = 0; i < classValues.length; i++) {
				randomize2(classValues[i]);
			}

			train();
			test2();
		}
		double accuracy = (double) totalRightAns / totalTest;
		System.out.println("accuracy = " + accuracy);

		for(int i = 0; i < classValues.length; i++) {
			double precision = (double) truePositive.get(classValues[i])/ totalPredectedAs.get(classValues[i]);
			double recall = (double) truePositive.get(classValues[i]) / totalGoldLevel.get(classValues[i]);
			double fMeasure = (2 * precision * recall) / (precision + recall);
			System.out.println("class value as > " + classValues[i]);
			System.out.println("precision = " + precision);
			System.out.println("recall = " + recall);
			System.out.println("f-Measure = " + fMeasure);
		}

	}

	private void test() {
		int count = 0;
		for(int i = 0; i < testDataList.size(); i++)
		{
			System.out.println(i+ " --------------------------------------");
			String result = getResult(testDataList.get(i));
			if(result.equals(testDataList.get(i).getValueInIndex(0))) {
				count++;
				totalRightAns++;
			}
			System.out.println(result +", actual " +testDataList.get(i).getValueInIndex(0));
		}

		totalTest += testDataList.size();

		double acc = (double) count/testDataList.size();
		System.out.println(acc);
	}

	private void test2() {
		int count = 0;
		for(int i = 0; i < testDataList.size(); i++)
		{
			//System.out.println(i+ " --------------------------------------");
			String original = testDataList.get(i).getValueInIndex(0);
			String result = getResult2(testDataList.get(i));
			if(result.equals(testDataList.get(i).getValueInIndex(0))) {
				count++;
				totalRightAns++;
				int x = truePositive.get(result);
				truePositive.put(result, x+1);
			}

			totalPredectedAs.put(result, totalPredectedAs.get(result)+1);
			totalGoldLevel.put(original, totalGoldLevel.get(original)+1);
			//System.out.println(result +", actual " +testDataList.get(i).getValueInIndex(0));
		}

		totalTest += testDataList.size();

		//double acc = (double) count/testDataList.size();
		//System.out.println(acc);
	}

	private String getResult2(Data data) {
		double []probability = {1.0, 1.0, 1.0, 1.0, 1.0};

		for(int i = 0; i < probability.length; i++)
		{
			String words[] = data.getAllDataValue();
			for( int j = 1; j < words.length; j++) {
				String word = words[j];
				//System.out.print(word +" > ");
				if(wordListMaping.get(classValues[i]).containsKey(word) && totalCount.containsKey(word))
				{
					int totalOccarance = totalCount.get(word);
					int occaranceInThisClass = wordListMaping.get(classValues[i]).get(word);
					probability[i] *= (double)occaranceInThisClass / totalOccarance;

					//System.out.print("class value > "+classValues[i] +" " + wordListMaping.get(classValues[i]).get(word) +" / " + totalCount.get(word) +" || ");
					//probability[i] *= wordListMaping.get(classValues[i]).get(word) / totalCount.get(word);
				}
				//System.out.println();
			}
		}

		/*for(int i = 0; i < probability.length; i++)
			System.out.println(probability[i]);
		*/
		int index = 0;
		for(int i=0; i < probability.length; i++) {
			if(probability[i] > probability[index])
				index = i;
		}

		//System.out.println("highest " + index);

		return Integer.toString(index+1);
	}

	private String getResult(Data data) {
		int []probability = {0, 0, 0, 0, 0};

		String words[] = data.getAllDataValue();
		//System.out.println("nu,ber of Word " + words.length);
		for(int i = 1; i < words.length; i++) {
			int index = selectClassForThisWord(words[i]);
			//System.out.println("got Index > " + index);
			probability[index]++;
		}

		int index = 0;
		for(int i = 0; i < probability.length; i++) {
			if(probability[i] > probability[index]) index = i;
			//System.out.print(probability[i] + " ");
		}
		//System.out.println(" result " + index);

		/*
		for(int i = 0; i < probability.length; i++)
		{


			String words[] = data.getAllDataValue();
			for( int j = 1; j < words.length; j++) {
				String word = words[j];
				//System.out.print(word +" > ");
				if(wordListMaping.get(classValues[i]).containsKey(word) && totalCount.containsKey(word))
				{
					int totalOccarance = totalCount.get(word);
					int occaranceInThisClass = wordListMaping.get(classValues[i]).get(word);
					probability[i] *= (double)occaranceInThisClass / totalOccarance;

					//System.out.print("class value > "+classValues[i] +" " + wordListMaping.get(classValues[i]).get(word) +" / " + totalCount.get(word) +" || ");
					//probability[i] *= wordListMaping.get(classValues[i]).get(word) / totalCount.get(word);
				}
				//System.out.println();
			}
		}*/

		/*for(int i = 0; i < probability.length; i++)
			System.out.println(probability[i]);
		*/

		/*
		int index = 0;
		for(int i=0; i < probability.length; i++)
		{
			if(probability[i] > probability[index])
				index = i;
		}

		System.out.println("highest " + index);
*/
		return Integer.toString(index+1);
	}

	private int selectClassForThisWord(String word) {

		int index = 0;
		int highest = 0;


		//System.out.println(word);
		for(int i = 0; i < classValues.length; i++)
		{
			if(wordListMaping.get(classValues[i]).containsKey(word)){
				int count  = wordListMaping.get(classValues[i]).get(word);
				//System.out.println("class value " + classValues[i] +",   count " + count);
				if(count > highest) {
					highest = count;
					index = i;
				}
			}
			else
			{
			//	System.out.println("class value " + classValues[i] +",   count not found");
			}


		}

		//if(totalCount.containsKey(word))
			//System.out.println("total count > " +totalCount.get(word));

		//System.out.println("hie " + highest);
		//System.out.println("final index " + index);


		return index;
	}

	private void train() {

		wordCountOfClass1 = new HashMap<>();
		wordCountOfClass2 = new HashMap<>();
		wordCountOfClass3 = new HashMap<>();
		wordCountOfClass4 = new HashMap<>();
		wordCountOfClass5 = new HashMap<>();
		totalCount = new HashMap<>();

		wordListMaping = new HashMap<>();
		wordListMaping.put("1", wordCountOfClass1);
		wordListMaping.put("2", wordCountOfClass2);
		wordListMaping.put("3", wordCountOfClass3);
		wordListMaping.put("4", wordCountOfClass4);
		wordListMaping.put("5", wordCountOfClass5);

		for(int i = 0; i < trainDataList.size(); i++) {
			String key  = trainDataList.get(i).getValueInIndex(0);
			int numberOfWord  = trainDataList.get(i).getColumnSize();
			String data[] = trainDataList.get(i).getAllDataValue();
			//System.out.println(data.length);
			for(int j = 1; j < numberOfWord; j++)
			{
				String word = data[j];

				if(wordListMaping.get(key).containsKey(word)) {
					int x = wordListMaping.get(key).get(word);
					wordListMaping.get(key).put(word, x+1);
				} else {
					wordListMaping.get(key).put(word, 1);
				}

				if(totalCount.containsKey(word)){
					int x = totalCount.get(word);
					totalCount.put(word, x+1);
				}
				else totalCount.put(word, 1);
			}
		}


	}

	private void randomize(String classValue) {

		int totalSize = dataListMaping.get(classValue).size();
		int testSize = totalSize / 10;
		int  traningSize = totalSize - testSize;
		ArrayList<Integer> choosenTestDataIndex = new ArrayList<>();

		while(choosenTestDataIndex.size() < testSize) {
			int x = new Random().nextInt(totalSize);
			if(!choosenTestDataIndex.contains(x))
				choosenTestDataIndex.add(x);
		}

		for(int i = 0; i < totalSize; i++) {
			if(choosenTestDataIndex.contains(i)) testDataList.add(dataListMaping.get(classValue).get(i));
			else trainDataList.add(dataListMaping.get(classValue).get(i));
		}
	}

	private void randomize2(String classValue) {

		int totalSize = dataListMaping.get(classValue).size();
		int testSize = totalSize / 10;
		int  traningSize = totalSize - testSize;
		ArrayList<Integer> choosenTestDataIndex = new ArrayList<>();

		while(choosenTestDataIndex.size() < testSize) {
			int x = new Random().nextInt(totalSize);

			if(!takenClassMaping.get(classValue).contains(x)){
				choosenTestDataIndex.add(x);
				takenClassMaping.get(classValue).add(x);

			}
			/*if(!choosenTestDataIndex.contains(x))
				choosenTestDataIndex.add(x);*/
		}

		for(int i = 0; i < totalSize; i++) {
			if(choosenTestDataIndex.contains(i)) testDataList.add(dataListMaping.get(classValue).get(i));
			else trainDataList.add(dataListMaping.get(classValue).get(i));
		}
	}

}
