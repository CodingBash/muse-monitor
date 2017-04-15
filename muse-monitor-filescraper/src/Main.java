import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static List<String> lines = new ArrayList<String>();
	static String line = null;

	public static void main(String... args) {
		try {
			File f1 = new File(args[0]);
			FileReader fr = new FileReader(f1);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				line = line + ", ";
				lines.add(line);
			}
			fr.close();
			br.close();

			FileWriter fw = new FileWriter(f1);
			BufferedWriter out = new BufferedWriter(fw);
			for (String s : lines)
				out.write(s);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
