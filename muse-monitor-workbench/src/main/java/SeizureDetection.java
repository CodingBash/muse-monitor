import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import jwave.Transform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.daubechies.Daubechies4;

public class SeizureDetection {

	public static final String[] FILENAMES = { "F001.txt", "N001.TXT", "O001.txt", "S001.txt", "Z001.txt" };

	public static void main(String[] args) {
		for (int j = 0; j < FILENAMES.length; j++) {
			System.out.println(FILENAMES[j]);
			double[] EEG_DATA = new DataReader().readData(FILENAMES[j]);

			for (int i = 0; i < 20; i++) {
				if (EEG_DATA.length < Math.pow(2, i)) {
					EEG_DATA = Arrays.copyOfRange(EEG_DATA, 0, (int) Math.pow(2, i - 1));
					break;
				}
			}
			indicate(EEG_DATA);
		}
	}

	public static void indicate(double[] eegData){
		Transform t = new Transform(new WaveletPacketTransform(new Daubechies4()));

		double[][] decomposition = t.decompose(eegData);

		int coefficients = decomposition[0].length;
		double sSum = 0.;
		for (int k = 0; k < coefficients; k++) {
			sSum += Math.log(Math.pow(decomposition[3][k], 2));

		}
		if (sSum > 20000) {
			System.out.println("SEIZURE");
		}
	}
	public static void analyze(double[] data) {
		Transform t = new Transform(new WaveletPacketTransform(new Daubechies4()));

		double[] de = t.forward(data);
		/*
		 * 
		 */
		double[][] decomposition = t.decompose(data);

		int levels = decomposition.length;
		int coefficients = decomposition[0].length;
		double[] featureVector = new double[coefficients];

		/*
		 * For each coefficient (M = 2^p)
		 */
		for (int i = 0; i < coefficients; i++) {
			double sum = 0;
			/*
			 * For each level (p)
			 */
			for (int k = 0; k < levels; k++) {
				sum += Math.abs(decomposition[k][i]);
			}
			featureVector[i] = sum / levels;

		}

		System.out.println();
		double sSum = 0.;
		for (int k = 0; k < coefficients; k++) {
			sSum += Math.log(Math.pow(decomposition[3][k], 2));

		}

		double eSum = 0.;
		for (int k = 0; k < coefficients; k++) {
			eSum += Math.pow(Math.abs((decomposition[3][k])), 2);
		}

		double eTot = 0.;
		for (int level = 0; level <= 3; level++) {
			for (int k = 0; k < coefficients; k++) {
				eTot += Math.pow(Math.abs((decomposition[level][k])), 2);
			}
		}
		eTot += eSum;

		double RWE = eSum / eTot;

		if(sSum > 20000){
			System.out.println("SEIZURE");
		}
		System.out.println("RWE: " + RWE);
		System.out.println("ENTROPY: " + sSum);
		/*
		 * TODO: Feature vector?
		 * 
		 * TODO: Need to be careful about array length (2^p) | p in N
		 */
		// TODO: Step 2: Utilize pca_transform for dimensionality reduction
		/*
		 * We are to solve Z = transpose(A) * Y where Y is the feature vector
		 * and Z is the reduced feature vector
		 * 
		 * Y (and hence Z) is an element in a class C, with size c(=3 for
		 * normal, interictal, and ictal EEG)
		 */

		// TODO: Step 3" Utilize JAMA for custom implementation of classifier
		// assignment

	}
}
