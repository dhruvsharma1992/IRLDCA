package irldca;

import java.util.LinkedList;
import java.util.List;

public class Decompression {
	private static int[] buffer = new int[32];


	public static List<Float> decompress(List input) {

		float largestCommonNumber = (float) input.get(0);
		float delta = (float) input.get(1);
		float max = (float) input.get(2);
		int b = (int) input.get(3);
		float factor = (float) input.get(4);
		int numOfBinDigit = (int) input.get(5);
		int inputLength = (int) input.get(6);

		List<Integer> result = new LinkedList<Integer>();
		int xcount = 0, x = 0, repeated = 0;

		for (int index = 7; index < input.size(); index++) {
			inary((int) input.get(index));
			for (int i = 0; i < 32; i++) {
				if (xcount == numOfBinDigit) {
					if (repeated == 0)
						result.add(x);
					else {
						for (int j = 0; j < repeated * 4; j++)
							result.add(x);
					}

					if (result.size() == inputLength)
						break;
					xcount = 0;
					repeated = 0;
					x = 0;
				}
				if (xcount == 0) {

					while (buffer[i] == 1) {
						repeated++;
						i++;
						if (i == 32)
							break;
					}
				}

				if (i == 32)
					break;
				x = x * 2 + buffer[i];
				xcount++;
			}
		}

		float min = b > 0 ? (1 - delta / 2 / factor * (float) Math.pow(10, b))
				* max : (1 - delta / 2 / factor * (float) Math.pow(10, -b+1)) * max;
		float[] resultAr = new float[result.size()];

		for (int i = 0; i < resultAr.length; i++)
			resultAr[i] = result.get(i);

		for (int i = 0; i < resultAr.length; i++) {
			resultAr[i] = (resultAr[i]) * delta + min;

		}
		for (int i = 0; i < resultAr.length; i++)
			resultAr[i] = (float) ((resultAr[i] * Math.pow(10, b)) + largestCommonNumber);

		System.out.println();

		List<Float> resultList = new LinkedList<Float>();
		for (int i = 0; i < resultAr.length; i++)
			resultList.add(resultAr[i]);

		return resultList;
	}

	private static void inary(int x) {
		// TODO Auto-generated method stub
		boolean isNegative = false;
		if (x < 0) {

			x = -x;
			isNegative = true;
		}

		int arcoun = 0;
		buffer[31 - arcoun] = x % 2;
		++arcoun;// System.out.print(x%2+" ");

		while (x / 2 != 0) {
			x = x / 2;

			buffer[31 - arcoun] = x % 2;
			// System.out.print(x%2+" ");//System.out.print(arcoun + " ");
			++arcoun;
		}
		while (arcoun < 32) {
			buffer[31 - arcoun++] = 0;// System.out.print(arcoun + " ");
		}

		if (isNegative) {

			for (int i = 0; i < 32; i++) {
				buffer[i] += 1;
				buffer[i] = buffer[i] % 2;
			}

			int carry = 0;
			buffer[31] += 1;
			carry = buffer[31] / 2;
			buffer[31] = buffer[31] % 2;
			for (int i = 30; i >= 0; i--) {
				buffer[i] += carry;
				carry = buffer[i] / 2;
				buffer[i] = buffer[i] % 2;
			}
		}
	}

}
