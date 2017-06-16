package guru.bonacci.oogway.util;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * This class does not belong here. 
 * In fact, it does not belong anywhere. 
 * It should not even exist.
 * That why it is here.
 */
public class MyFileUtils {

	public static String readToString(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		return readToString(resource.getInputStream());
	}	
		
	public static List<String> readToList(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);
		return readToList(resource.getInputStream());
	}	

	private static String readToString(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
        	return buffer.lines().collect(joining("\n"));
        }
    }

	private static List<String> readToList(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
        	return buffer.lines().collect(toList());
        }
    }
}
