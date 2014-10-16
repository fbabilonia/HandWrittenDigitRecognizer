package nyc.babilonia.neuralnet;

public class AndClassifier
{
	Neuron perceptron;
	//just initializes the Neuron.
	public AndClassifier()
	{
		perceptron = new Neuron(2,.1,0,null,new ThresholdFunction());
	}
	public static void train(double [][] data)
	{
		
	}
	
	public static void main(String args[])
	{
		double [][] data ={ {0,0,0},
				{0,1,0} , {1,0,0} , {1,1,1}};
		train(data);
	}
}
