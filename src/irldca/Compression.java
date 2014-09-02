package irldca;

import java.util.LinkedList;
import java.util.List;

public class Compression {

	
	private static int[] buffer;
	private static int arcoun;

	@SuppressWarnings("unchecked")
	public static List<Integer> compress(List<Float> input, float factor) {

		float max = Float.MIN_VALUE, min = Float.MAX_VALUE;
		for (float input_ : input) {
			if (max < input_)
				max = input_;

			if (min > input_)
				min = input_;
		}
		float range = max - min;
		int numOfDigits = 0, xcount = 0;
		numOfDigits = getNumOfDigits(range);
		int b;
		if (range > 1) {
			b = numOfDigits;
		} else {
			b = 1 - numOfDigits;
		}
		float[] inputArray = new float[input.size()];
		int j = 0;
		for (float input_ : input) {
			inputArray[j++] = input_;
		}
		float largestCommonNumber = (float) ((int) (min / Math.pow(10, b)) * Math.pow(10,
				b));
		for (int i = 0; i < inputArray.length; i++)
			inputArray[i] = (float) ((inputArray[i] - largestCommonNumber) / Math.pow(10,
					b));

		max = (float) ((max - largestCommonNumber) / Math.pow(10, b));
		min = (float) ((min - largestCommonNumber) / Math.pow(10, b));
		float delta = b > 0 ? (max - min) / max / (float) (Math.pow(10, b)) * 2
				* factor : (max - min) / max / (float) (Math.pow(10, -b+1)) * 2
				* factor;
		for (int i = 0; i < inputArray.length; i++) {
			inputArray[i] = (int) ((inputArray[i] - min) / delta);

		}

		@SuppressWarnings("rawtypes")
		List result = new LinkedList();
		
		
		result.add((float) largestCommonNumber);
		result.add(delta);
		result.add(max);
		result.add(b);
		result.add(factor);
		
		max = (int) ((max - min) / delta);

		int numOfBinDigit = 2;
		while ((int) (max / 2) != 0) {
			max = max / 2;
			numOfBinDigit++;
		}
		numOfBinDigit++;
		result.add(numOfBinDigit);
		result.add(inputArray.length);

		buffer = new int[32];
		int x = 0;
		arcoun = 0;
		for (int i = 0; i < inputArray.length; i++) {
			int repeatedNumber = 1;
			while ((i < inputArray.length - 1)
					&& inputArray[i] == inputArray[i + 1]) {
				repeatedNumber++;
				i++;
			}

			for (j = 0; j < repeatedNumber / 4; j++) {
				if (xcount == 32) {
					result.add(x);
					x = 0;
					xcount = 0;
				}
				x = x * 2 + 1;
				xcount++;
			}

			inary(inputArray[i], numOfBinDigit);
			for (int index = 0; index < repeatedNumber % 4 + 1; index++) {
				for (int bufferLength = 1; bufferLength <= arcoun; bufferLength++) {
					if (xcount == 32) {
						result.add(x);
						x = 0;
						xcount = 0;
					}
					x = (x * 2) + buffer[31 - arcoun + bufferLength];// System.out.print(array[31-arcoun+i]);
					xcount++;
				}
			}
		}
		arcoun = 0;
		/*
		 * while (xcount-- >= 0) { if (x % 2 == 0) { buffer[31 - (arcoun++)] =
		 * 0; } else buffer[31 - (arcoun++)] = 1; x = x / 2;
		 * 
		 * } x = 0; for (int i = 0; i < arcoun; i++) { if (buffer[31 - arcoun +
		 * i] == 0) { x *= 2; } else { x *= 2 + 1; } }
		 * 
		 * while (arcoun < 32) x = x * 2;
		 */

		for (; xcount < 32; xcount++) {
			x *= 2;

		}

		result.add(x);
		return result;

	}

	private static void inary(float f, int numOfBinDigit) {
		int x = (int) f;
		// TODO Auto-generated method stub
		arcoun = 0;
		buffer[31 - arcoun] = x % 2;
		++arcoun;// System.out.print(x%2+" ");

		while (x / 2 != 0) {
			x = x / 2;

			buffer[31 - arcoun] = x % 2;
			// System.out.print(x%2+" ");//System.out.print(arcoun + " ");
			++arcoun;
		}
		while (arcoun < numOfBinDigit) {
			buffer[31 - arcoun++] = 0;// System.out.print(arcoun + " ");
		}

	}

	private static int getNumOfDigits(float range) {
		int b;

		if (range > 1) {
			b = 1;
			range = range / 10;
			while ((int) range > 0) {
				range = range / 10;
				b++;
			}
		} else {
			b = 0;
			while (range < 1) {
				range = range * 10;
				b++;
			}
		}
		return b;
	}
}
