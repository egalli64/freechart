package chart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pie {
    static Logger log = LoggerFactory.getLogger(Pie.class);

    static boolean createSimple() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Merlin", 2.50);
        dataset.setValue("Tiger", 18.27);
        dataset.setValue("Mustang", 41.33);
        dataset.setValue("Dolphin", 72.02);

        // legend off, but disable tooltips not working!
        JFreeChart chart = ChartFactory.createPieChart("Versions", dataset, false, false, false);

        PiePlot pp = (PiePlot) chart.getPlot();
        pp.setSectionPaint("Mustang", Color.MAGENTA);
        // force tooltips off
        pp.setLabelGenerator(null);

        try {
            ChartUtilities.saveChartAsPNG(new File("dump/pie.png"), chart, 500, 500);
            return true;
        } catch(IOException ioe) {
            log.error(ioe.getMessage());
            return false;
        }
    }
}
