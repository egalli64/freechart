/**
 * @author manny egalli64@gmail.com
 */
package chart;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pie {
    static Logger log = LoggerFactory.getLogger(Pie.class);

    static private JFreeChart createPiePlotSimple() {
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
        return chart;
    }

    /**
     * Push a chart to a file in PNG format
     * 
     * Info: http://thisthread.blogspot.com/2017/02/first-pie-with-jfreechart.html
     * 
     * @return true if succeded
     */
    static public boolean createSimple() {
        JFreeChart chart = createPiePlotSimple();
        try {
            ChartUtilities.saveChartAsPNG(new File("dump/pie.png"), chart, 500, 500);
            return true;
        } catch(IOException ioe) {
            log.error(ioe.getMessage());
            return false;
        }
    }

    /**
     * Push a chart in a PDF document
     *  
     * Info: http://thisthread.blogspot.com/2017/02/jfreechart-and-pdfbox.html
     * 
     * @return true if succeeded
     */
    static public boolean createSimpleOnPdf() {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        JFreeChart chart = createPiePlotSimple();
        BufferedImage bi = chart.createBufferedImage(500, 500);
        try {
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            PDImageXObject ximage = LosslessFactory.createFromImage(doc, bi);
            cs.drawImage(ximage, 100, 100);
            cs.close();
            doc.save(new File("dump/pie.pdf"));
        } catch(IOException ioe) {
            log.error(ioe.getMessage());
            return false;
        }
        return true;
    }
}
