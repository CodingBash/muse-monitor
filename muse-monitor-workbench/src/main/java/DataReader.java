import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class DataReader {
	public double[] readData(String fileName) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			final File tempFile = File.createTempFile("stream2file", ".txt");
			tempFile.deleteOnExit();
			try (FileOutputStream out = new FileOutputStream(tempFile)) {
				IOUtils.copy(classLoader.getResourceAsStream(fileName), out);
			}
			String s = FileUtils.readFileToString(tempFile);
			String[] sList = StringUtils.split(s, ",");

			double[] dList = new double[sList.length];
			for (int i = 0; i < sList.length; i++) {
				dList[i] = Double.parseDouble(sList[i]);
			}
			return dList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new double[0];
	}
}
