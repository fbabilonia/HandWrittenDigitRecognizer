package nyc.babilonia.neuralnet;

public class BinaryLogicClassifier
{
	static Neuron perceptron = new Neuron(2,.1,0,null,new ThresholdFunction());
	//just initializes the Neuron.
	public BinaryLogicClassifier()
	{
	}
	
	public static void train(double [][] data)
	{
		double error;
		int itteration =0;
		do
		{
			error = 0;
			for(double [] input : data)
			{
				send(input);
				error += Math.abs((input[2] - perceptron.getOutput()));
				perceptron.updateWeights(input[2]);
			}
			System.out.println(error);
			++itteration;
		}
		while (error >0 && itteration < 10000);
	}

	private static void send(double[] input)
	{
		perceptron.setInput(0, input[0]);
		perceptron.setInput(1, input[1]);
		perceptron.activate();
	}
	public static boolean test(double [] test)
	{
		send(test);
		return perceptron.getOutput() == test[2];
	}
	public static void main(String args[])
	{
		double [][] xor ={ {0,0,0},
				{0,1,1} , {1,0,1} , {1,1,0}};
		double [][] or ={ {0,0,0},
				{0,1,1} , {1,0,1} , {1,1,1}};
		double [][] and ={ {0,0,0},
				{0,1,0} , {1,0,0} , {1,1,1}};
		testSet(xor);
		testSet(and);
		testSet(or);
	}

	private static void testSet(double[][] data)
	{
		train(data);
		for(double [] test : data)
		{
			System.out.println(test(test));
		}
		perceptron.reset();
	}
}
