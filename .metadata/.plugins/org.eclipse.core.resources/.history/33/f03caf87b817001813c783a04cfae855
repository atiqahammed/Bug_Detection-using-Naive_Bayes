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
	
	private ArrayList<Data> trainDataList;
	private ArrayList<Data> testDataList;
	
	
	private Map<String, ArrayList<Data>> dataListMaping;
	private Map<String, Map<String, Integer>> wordListMaping;
	
	
	public void input(String path) {
		init(path);
		
		String input = null;
		try {
			while ((input = bufferedReader.readLine()) != null) {
				String data[] = input.split(" ");
				//System.out.println(data[0]);
				dataListMaping.get(data[0]).add(new Data(data));
				//System.out.println(dataListMaping.get(data[0]).size());
			}
			
			//for(int i = 0; i < classValues.length; i++)
			//	System.out.println(dataListMaping.get(classValues[i]).size());
			
			/*
			System.out.println(wordCountOfClass1.size());
			System.out.println(wordCountOfClass2.size());
			System.out.println(wordCountOfClass3.size());
			System.out.println(wordCountOfClass4.size());
			System.out.println(wordCountOfClass5.size());
			*/

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
		
		//dataListOfClass1.add(new Data());
		//System.out.println(dataListOfClass1.size());
		//System.out.println(dataListMaping.get("1").size());
		
		
	}

	public void process() {
		
		trainDataList = new ArrayList<>();
		testDataList = new ArrayList<>();
		
		for(int i = 0; i < classValues.length; i++) {
			randomize(classValues[i]);
		}
		
		train();
		test();
		
		//System.out.println(trainDataList.size());
		//System.out.println(testDataList.size());
		
	}

	private void test() {
		int count = 0;
		for(int i = 0; i < testDataList.size(); i++)
		{
			String result = getResult(testDataList.get(i));
			if(result.equals(testDataList.get(0).getValueInIndex(0))) count++;
			//System.out.println(result +", actual - " +testDataList.get(0).getValueInIndex(0));
		}
		
		double acc = (double) count/testDataList.size();
		System.out.println(acc);
		
	}

	private String getResult(Data data) {
		double []probability = {1.0, 1.0, 1.0, 1.0, 1.0};
		
		//System.out.println("i am here");
		//for(int j = 0; j < data)
		
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
		for(int i=0; i < probability.length; i++)
		{
			if(probability[i] > probability[index])
				index = i;
		}
		
		System.out.println("highest " + index);
		
		return Integer.toString(index+1);
		
		
		
		//return null;
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
		
		
		/*
		wordListMaping.get("1").put("hello", 2);
		
		if(wordListMaping.get(classValues[0]).containsKey("helo")) {
			System.out.println("its ok");
		} else {
			System.out.println("not ok");
		}*/
		
		
		
		
		
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
			if(!choosenTestDataIndex.contains(x)) choosenTestDataIndex.add(x);	
		}
		
		for(int i = 0; i < totalSize; i++) {
			if(choosenTestDataIndex.contains(i)) testDataList.add(dataListMaping.get(classValue).get(i));
			else trainDataList.add(dataListMaping.get(classValue).get(i));
		}
	}
}
