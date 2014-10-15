package nyc.babilonia.neuralnet;

import nyc.babilonia.Interfaces.ActivationFunction;

public class ThresholdFunction implements ActivationFunction
{
	private double threshold;
	public ThresholdFunction()
	{
		threshold =.5;
	}
	public ThresholdFunction(double th)
	{
		threshold = th;
	}
	@Override
	public double calculate(double input)
	{
		if(input > threshold)
			return 1;
		else
			return 0;
	}

	@Override
	public double calculateDerivative(double output)
	{
		throw new NeuronException("Linear activation function not suitable for cases where derivatives are needed.");
	}
	@Override
	public boolean isLinear()
	{
		return true;
	}

}
