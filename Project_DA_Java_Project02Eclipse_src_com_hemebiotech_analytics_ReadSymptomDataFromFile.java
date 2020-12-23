package com.hemebiotech.analytics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyticsCounter {
	private final static List<String> symptomsList = new ArrayList<>();
	public static final String SYMPTOMS_TXT = "symptoms.txt";
	public static final String RESULT_OUT = "result.out";

	public static void main(String args[]) throws Exception {
		final BufferedReader reader = new BufferedReader(new FileReader(SYMPTOMS_TXT));
		fillSymptomsList(reader);
		writeResultsToFile();
		reader.close();

	}

	private static void writeResultsToFile() throws IOException {
		final FileWriter writer = new FileWriter(RESULT_OUT);
		Map<String, Long> collect = symptomsList.stream()
				.collect(
						Collectors.groupingBy(
								Function.identity(), Collectors.counting()
						));
		collect.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEach(value -> {
					try {
						writer.write(value.getKey() + " " + value.getValue() + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
		writer.close();
	}


	private static void fillSymptomsList(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null) {
			symptomsList.add(line);
			line = reader.readLine();
		}
	}
}
