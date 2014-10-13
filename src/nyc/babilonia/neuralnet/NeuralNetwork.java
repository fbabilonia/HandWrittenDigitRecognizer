package nyc.babilonia.neuralnet;

import java.util.ArrayList;

import nyc.babilonia.Interfaces.ActivationFunction;
import nyc.babilonia.Interfaces.DigitClassifier;

public class NeuralNetwork implements DigitClassifier
{
	ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
	double learningRate;
	ActivationFunction function;
	@SuppressWarnings("unused")
	private NeuralNetwork()
	{}
	/**
	 * Creates a Neural Network consisting of an input layer, and a hidden layer of size hiddenSize, and 
	 * an output layer of outputSize.  Neurons will be made with using a learningRate of lRate.
	 * @param inputSize - Number of inputs in the data set.
	 * @param hiddenSize - Number of hidden Neurons in the hidden Layer.
	 * @outputSize - Number of distinct outputs we expect.
	 * @param lRate - Learning rate to use when creating individual neurons.
	 * @param func - Activation function used for neurons in the network.
	 */
	public NeuralNetwork(int inputSize ,int hiddenSize,int outputSize, double lRate, ActivationFunction func)
	{
		this(inputSize, (new int [] { hiddenSize}) , outputSize, lRate , func);
	}
	public NeuralNetwork( int inputSize , int[] hiddenLayerSizes, int outputSize, double lRate, ActivationFunction func)
	{
		for(int i = 0 ; i < hiddenLayerSizes.length +1 ; ++i)
		{
			network.add(new ArrayList<Neuron>());
		}
	}
	@Override
	public int Classify()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
