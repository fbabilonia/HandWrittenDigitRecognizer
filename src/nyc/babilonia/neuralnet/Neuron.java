package nyc.babilonia.neuralnet;

import java.util.ArrayList;

import nyc.babilonia.Interfaces.ActivationFunction;

/**
 * 
 * @author Fernando Babilonia
 * Neuron class that models a perceptron.  
 *
 */
public class Neuron
{
	double[] weights;
	double[] input;
	double[] oldDelta; // for use with momentum
	private double output , momentum =.7 , learningNumber , error;
	int neuronNumber; //neuron number in current level
	ActivationFunction activate; 
	ArrayList<Neuron> nextLevel = new ArrayList<Neuron>();
	public Neuron(int inputSize, double learningRate, int num, ArrayList<Neuron> nl, ActivationFunction function) 
	{
		weights = new double[inputSize + 1];
		input = new double[inputSize + 1];
		oldDelta = new double[inputSize + 1];
		nextLevel = nl;
		learningNumber = learningRate;
		neuronNumber = num;
		reset();
		input[input.length - 1] = 1;
		activate = function;
	}// end constructor
	//sends output to next level
	private void sendResultsToNextLevel()
	{
		for (int i = 0; i < this.nextLevel.size(); ++i)
			this.nextLevel.get(i).setInput(neuronNumber, output);
	}
	/**
	 * Access for the lower level to send its output to this neurons level
	 * @param index - index of input.
	 * @param data - input from lower level
	 */
	public void setInput(int index, double data)
	{
		this.input[index] = data;
	}
	/**
	 * Randomly resets all weights in this perceptron.
	 */
	public void reset()
	{
		for (int i = 0; i < weights.length - 1; ++i)
			weights[i] = Math.random()/1000;
		weights[weights.length - 1] = Math.random() / weights.length;
	}
	/**
	 * Activates the perceptron using the supplied Activation Function.
	 * Sends results to the next level.
	 */
	public void activate()
	{
		double sum = 0;
		for (int i = 0; i < input.length; ++i)
			sum += (input[i] * weights[i]);
		output = activate.calculate(sum);
		if(nextLevel != null)
			sendResultsToNextLevel();
	}// end activate

	/**
	 * Calculates error for non linear functions for use with a backpropagation algorithms.
	 * Error is computed as derivativeOfActivationFunction * (expectedValue - result).
	 * @param expected - Expected outcome 
	 */
	private void calculateErrorBackPropagation(double expected)
	{
		double derivative = activate.calculateDerivative(output); 
		if (this.nextLevel == null)
		{
			error = derivative * (expected - output);
		} else
		{
			error = 0;
			for (int i = 0; i < nextLevel.size(); ++i)
			{
				error += ((nextLevel.get(i).getError()) * (nextLevel.get(i)
						.getWeight(neuronNumber)));
			}
			error = error * derivative;
		}
		updateWeights();
	}// end calculate error
	/**
	 * Calculates Error for the Neuron when using a linear activation function.
	 * Error is calculated by the expectedValue - result.
	 * @param expected
	 */
	private void calculateErrorLinear(double expected)
	{
		error = expected - output;
		updateWeights();
	}//end calculateErrorLinear
	/**
	 * Calls correct errorCalculation scheme, and updates weights.
	 * @param expected - expected outcome.
	 */
	public void updateWeights(double expected)
	{
		if(activate.isLinear())
			calculateErrorLinear(expected);
		else
			calculateErrorBackPropagation(expected);
	}
	/*
	 * Updates weights by first calculating the delta by learningNumber*input*error.
	 * The delta is then added to the current weight, plus a fraction of the old delta
	 * to smooth momentum.
	 */
	private void updateWeights()
	{
		double delta;
		for (int i = 0; i < weights.length; ++i)
		{
			delta = (learningNumber * input[i] * error);
			weights[i] = weights[i] + delta + (oldDelta[i] * momentum);
			oldDelta[i] = delta;
		}
	}

	/**
	 * Returns current error for the Neuron
	 * @return
	 */
	public double getError()
	{
		return error;
	}

	/**
	 * @param position - Position in weight array.
	 * @return - Returns current weight of at position.
	 */
	public double getWeight(int position)
	{
		return weights[position];
	}
	/**
	 * 
	 * @return - Output of Neuron
	 */
	public double getOutput()
	{
		return output;
	}
}