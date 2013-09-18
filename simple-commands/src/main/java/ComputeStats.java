/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */

import imagej.command.Command;
import imagej.data.Dataset;
import imagej.data.measure.StatisticsService;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * A command that computes some statistics of a dataset.
 * <p>
 * For an even simpler command, see {@link HelloWorld} in this same package!
 * </p>
 */
@Plugin(type = Command.class, menuPath = "Analyze>Compute Statistics")
public class ComputeStats implements Command {

	@Parameter
	private StatisticsService statisticsService;

	@Parameter
	private Dataset dataset;

	@Parameter(type = ItemIO.OUTPUT)
	private double arithmeticMean;

	@Parameter(type = ItemIO.OUTPUT)
	private double geometricMean;

	@Parameter(type = ItemIO.OUTPUT)
	private double harmonicMean;

	@Parameter(type = ItemIO.OUTPUT)
	private double maximum;

	@Parameter(type = ItemIO.OUTPUT)
	private double median;

	@Parameter(type = ItemIO.OUTPUT)
	private double midpoint;

	@Parameter(type = ItemIO.OUTPUT)
	private double minimum;

	@Parameter(type = ItemIO.OUTPUT)
	private double populationKurtosis;

	@Parameter(type = ItemIO.OUTPUT)
	private double populationKurtosisExcess;

	@Parameter(type = ItemIO.OUTPUT)
	private double populationSkew;

	@Parameter(type = ItemIO.OUTPUT)
	private double populationStdDev;

	@Parameter(type = ItemIO.OUTPUT)
	private double populationVariance;

	@Parameter(type = ItemIO.OUTPUT)
	private double product;

	@Parameter(type = ItemIO.OUTPUT)
	private double sampleKurtosis;

	@Parameter(type = ItemIO.OUTPUT)
	private double sampleKurtosisExcess;

	@Parameter(type = ItemIO.OUTPUT)
	private double sampleSkew;

	@Parameter(type = ItemIO.OUTPUT)
	private double sampleStdDev;

	@Parameter(type = ItemIO.OUTPUT)
	private double sampleVariance;

	@Parameter(type = ItemIO.OUTPUT)
	private double sum;

	@Parameter(type = ItemIO.OUTPUT)
	private double sumOfSquaredDeviations;

	/**
	 * Computes some statistics on the input dataset, using ImageJ's built-in
	 * statistics service.
	 */
	@Override
	public void run() {
		arithmeticMean = statisticsService.arithmeticMean(dataset);
		geometricMean = statisticsService.geometricMean(dataset);
		harmonicMean = statisticsService.harmonicMean(dataset);
		maximum = statisticsService.maximum(dataset);
		median = statisticsService.median(dataset);
		midpoint = statisticsService.midpoint(dataset);
		minimum = statisticsService.minimum(dataset);
		populationKurtosis = statisticsService.populationKurtosis(dataset);
		populationKurtosisExcess =
			statisticsService.populationKurtosisExcess(dataset);
		populationSkew = statisticsService.populationSkew(dataset);
		populationStdDev = statisticsService.populationStdDev(dataset);
		populationVariance = statisticsService.populationVariance(dataset);
		product = statisticsService.product(dataset);
		sampleKurtosis = statisticsService.sampleKurtosis(dataset);
		sampleKurtosisExcess = statisticsService.sampleKurtosisExcess(dataset);
		sampleSkew = statisticsService.sampleSkew(dataset);
		sampleStdDev = statisticsService.sampleStdDev(dataset);
		sampleVariance = statisticsService.sampleVariance(dataset);
		sum = statisticsService.sum(dataset);
		sumOfSquaredDeviations = statisticsService.sumOfSquaredDeviations(dataset);
	}

}
