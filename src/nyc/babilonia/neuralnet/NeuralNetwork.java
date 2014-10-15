package nyc.babilonia.neuralnet;

import java.util.ArrayList;
import java.util.Stack;

import nyc.babilonia.Interfaces.ActivationFunction;
import nyc.babilonia.Interfaces.DigitClassifier;

public class NeuralNetwork implements DigitClassifier
{
	ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
	double learningRate;
	ActivationFunction function;
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
	/**
	 * 
	 * @param inputSize - Number of inputs in the data set.
	 * @param hiddenLayerSizes - Array consisting of the sizes of each hidden layer.
	 * @param outputSize - Number of distinct outputs we expect.
	 * @param lRate - Learning rate to use when creating individual neurons.
	 * @param func - Activation function used for neurons in the network.
	 */
	public NeuralNetwork( int inputSize , int[] hiddenLayerSizes, int outputSize, double lRate, ActivationFunction func)
	{
		Stack<ArrayList<Neuron>> networkStack = new Stack<ArrayList<Neuron>>();
		ArrayList<Neuron> tmp = new ArrayList<Neuron>();
		//Adds output layer.
		for(int outputNode = 0 ; outputNode < outputSize ; ++outputNode)
		{
			tmp.add(new Neuron(hiddenLayerSizes[hiddenLayerSizes.length-1], lRate, outputNode, null , func));
		}
		networkStack.push(tmp);
		//Takes care of the hidden layer.
		for(int hiddenLayer = hiddenLayerSizes.length -1 ; hiddenLayer >=0 ; ++hiddenLayer)
		{
			tmp = new ArrayList<Neuron>();
			for(int hiddenNode = 0 ; hiddenNode < hiddenLayerSizes[hiddenLayer] ; ++hiddenNode)
			{
				/*iSize will be used to figure out input size of current layer.  By either
				 * using the information of hiddenLayerSizes, of if this is the first hidden layer
				 * using inputSize.
				 */
				int iSize = hiddenLayer == 0 ? inputSize : hiddenLayerSizes[hiddenLayer-1];
				tmp.add(new Neuron(iSize, lRate, hiddenNode, networkStack.peek(), func));
			}
			networkStack.push(tmp);
		}
		//adds lists to the network in correct order.
		while(!networkStack.isEmpty())
		{
			network.add(networkStack.pop());
		}
	}
	@Override
	public int Classify()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
