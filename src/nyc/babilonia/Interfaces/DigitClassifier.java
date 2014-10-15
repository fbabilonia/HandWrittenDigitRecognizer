package nyc.babilonia.Interfaces;

public interface DigitClassifier
{
	/**
	 * Predicts a number (Classify) based on the data given.  
	 * <br>
	 * For best results have data normalized.
	 * @param data - the inputs for the model.
	 * @return the digit the model is predicting.
	 */
	int Classify(double [] data);
}
