package nyc.babilonia.Interfaces;

public interface ActivationFunction
{
	double calculate(double input);
	double calculateDerivative(double output);
	boolean isLinear();
}
