package nyc.babilonia.neuralnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		learningRate = lRate;
		function = func;
		makeNetwork(inputSize , hiddenLayerSizes , outputSize);
	}
	private void makeNetwork(int inputSize, int[] hiddenLayerSizes,int outputSize)
	{
		Stack<ArrayList<Neuron>> networkStack = new Stack<ArrayList<Neuron>>();
		ArrayList<Neuron> tmp = new ArrayList<Neuron>();
		//Adds output layer.
		for(int outputNode = 0 ; outputNode < outputSize ; ++outputNode)
		{
			tmp.add(new Neuron(hiddenLayerSizes[hiddenLayerSizes.length-1], learningRate, outputNode, null , function));
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
				tmp.add(new Neuron(iSize, learningRate, hiddenNode, networkStack.peek(), function));
			}
			networkStack.push(tmp);
		}
		//adds lists to the network in correct order.
		while(!networkStack.isEmpty())
		{
			network.add(networkStack.pop());
		}
		
	}
	/**
	 * Will read the saved file and build the network from it.
	 * Expected format will be the first line consists of the number of outputs,
	 * the number of hidden layers, the number of inputs, the learning rate, and the activation function.
	 * The following line will consists of the size of each hidden layer.
	 * After that each line will consist of weights for each neuron starting from the top down.
	 * @param networkFile - The file where the network has been saved.
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public NeuralNetwork(File networkFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		BufferedReader br = new BufferedReader(new FileReader(networkFile));
		String line = br.readLine();
		String [] data = line.split(" ");
		int outputs = Integer.parseInt(data[0]);
		int hiddenLayers = Integer.parseInt(data[1]);
		int inputs = Integer.parseInt(data[2]);
		learningRate = Integer.parseInt(data[3]);
		Class func = Class.forName(data[3]);
		function = (ActivationFunction) func.newInstance();
		int [] sizes = new int [hiddenLayers];
		line = br.readLine();
		data = line.split(" ");
		for(int layer = 0 ; layer < hiddenLayers ; ++layer)
		{
			sizes[layer] = Integer.parseInt(data[layer]);
		}
		makeNetwork(inputs, sizes, outputs);
		for(int layer = network.size() - 1 ; layer >= 0 ; --layer)
		{
			for(int neuron = 0 ; neuron < network.get(layer).size() ; ++neuron)
			{
				line = br.readLine();
				data = line.split(" ");
				for(int weight = 0 ; weight < data.length ; ++ weight)
				{
					network.get(layer).get(neuron).weights[weight] = Integer.parseInt(data[weight]);
				}
			}
		}
		br.close();
	}//end constructor from file.
	/**
	 * Saves the network to a file, format will be the first line consists of the number of outputs,
	 * the number of hidden layers, the number of inputs, the learning rate, and the activation function.
	 * The following line will consists of the size of each hidden layer.
	 * After that each line will consist of weights for each neuron starting from the top down.
	 * @param networkFile - The file where the network will be saved.
	 * @throws IOException 
	 */
	public void saveNetwork(File networkFile) throws IOException
	{
		PrintWriter pw = new PrintWriter(new FileWriter(networkFile));
		pw.println(network.get(network.size()-1).size() + " " + (network.size()-1) + " " 
				  + (network.get(0).get(0).weights.length-1) + " " + learningRate + " " + function.getClass().toString());
		for(int layer = 0 ; layer < (network.size() -1) ; ++layer)
		{
			pw.print(network.get(layer).size() + " ");
		}
		pw.println();
		for(int layer = (network.size()-1) ; layer >= 0 ; --layer)
		{
			for(int neuron = 0 ; neuron < network.get(layer).size() ; ++layer)
			{
				for(int weight = 0 ; weight < network.get(layer).get(neuron).weights.length ; ++weight)
				{
					pw.print(network.get(layer).get(neuron).weights[weight] + " ");
				}
				pw.println();
			}
		}
		pw.flush();
		pw.close();
	}
	@Override
	public int Classify()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
