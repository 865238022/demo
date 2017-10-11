package top.huzhurong.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by 竹 on 2017/10/9.
 */
public class ImageGenarator {
    private static DefaultPieDataset getDataSet(Map<String,Float> data){
        DefaultPieDataset dataset = new DefaultPieDataset();
        //数据来源于数据库 如果对应的数据填充到对应的地方也是个问题
        Set<Map.Entry<String, Float>> d =  data.entrySet();
        d.iterator().forEachRemaining(action->{
           String key = action.getKey();
           Float value = action.getValue();
           dataset.setValue(key,value);
        });
        return dataset;
    }

    private static StandardChartTheme standardChartTheme(){
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        return standardChartTheme;
    }

    /**
     * 根据 map 数据生成对应的饼状图
     * @param map 包含的数据
     */
    public static void getPieChart( Map<String ,Float> map ){
        // 数据来与数据库
        DefaultPieDataset data = getDataSet(map);
        StandardChartTheme standardChartTheme = standardChartTheme();
        ChartFactory.setChartTheme(standardChartTheme);
        String title =   MySimpleDateFormat.tarsfer(new Date())+ " XX图";
        JFreeChart chart = ChartFactory.createPieChart(title, // 图表标题
                data,
                true, // 是否显示图例
                true,
                false
        );
        PiePlot pieplot = (PiePlot) chart.getPlot(); //通过JFreeChart 对象获得
        pieplot.setNoDataMessage("无数据可供显示！"); // 没有数据的时候显示的内容
        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                ("{0}: ({2})"), NumberFormat.getNumberInstance(),
                new DecimalFormat("0.00%")));
        File pieChart = new File( "名字(可修改).jpeg" );
        try {
            ChartUtilities.saveChartAsJPEG(pieChart,0.4f,chart,430,350,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
