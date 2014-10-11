package nyc.babilonia.neuralnet;

import nyc.babilonia.Interfaces.ActivationFunction;

public class SigmoidFunction implements ActivationFunction
{
	/**
	 * Implements the sigmoid function (1/(1+e^(-input))
	 */
	@Override
	public double calculate(double input)
	{
		return (1 / (1 + Math.pow(Math.E, -1 * (input))));
	}

	/**
	 * Implements derivative of the sigmoid function output*(1-output)
	 */
	@Override
	public double calculateDerivative(double output)
	{
		return (output*(1-output));
	}
}
