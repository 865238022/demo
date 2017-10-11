package top.huzhurong.demo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by 竹 on 2017/10/11.
 */
public class BarChart {
    public static void main(String[] args) throws Exception {

        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
        ChartFactory.setChartTheme(standardChartTheme);

        final String sancan = "三餐";
        final String snacks = "零食";
        final String surf_the_internet = "上网";
        final String shoppings = "购物";
        final String buy_books = "买书";
        final String other = "其他";

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(18, sancan, sancan);
        dataset.addValue(8.0, snacks, snacks);
        dataset.addValue(21.0, surf_the_internet, surf_the_internet);
        dataset.addValue(42.0, shoppings, shoppings);
        dataset.addValue(16.0, other, other);
        JFreeChart barChart = ChartFactory.createBarChart(
                "消费使用统计",
                "消费分布", "消费多少",
                dataset, PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        // 设置横虚线可见
        plot.setRangeGridlinesVisible(true);
        // 虚线色彩
        plot.setRangeGridlinePaint(Color.gray);

        // 数据轴精度
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0.00");
        vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式
        // x轴设置

        CategoryAxis domainAxis = plot.getDomainAxis();

        // Lable（Math.PI/3.0）度倾斜
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions
        // .createUpRotationLabelPositions(Math.PI / 3.0));

        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// 横轴上的 Lable 是否完整显示

        // 设置距离图片左端距离
        domainAxis.setLowerMargin(0.1);
        // 设置距离图片右端距离
        domainAxis.setUpperMargin(0.1);
        // 设置 columnKey 是否间隔显示
        // domainAxis.setSkipCategoryLabelsToFit(true);

        plot.setDomainAxis(domainAxis);
        // 设置柱图背景色（注意，系统取色的时候要使用16位的模式来查看颜色编码，这样比较准确）
        plot.setBackgroundPaint(new Color(255, 255, 204));

        // y轴设置
        ValueAxis rangeAxis = plot.getRangeAxis();
        // 设置最高的一个 Item 与图片顶端的距离
        rangeAxis.setUpperMargin(0.15);
        // 设置最低的一个 Item 与图片底端的距离
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);

        BarRenderer renderer = new BarRenderer();
        // 设置柱子宽度
        renderer.setMaximumBarWidth(0.05);
        // 设置柱子高度
        renderer.setMinimumBarLength(0.2);
        // 设置柱子边框颜色
        renderer.setBaseOutlinePaint(Color.BLACK);
        // 设置柱子边框可见
        renderer.setDrawBarOutline(true);

        // // 设置柱的颜色
        renderer.setSeriesPaint(0, new Color(204, 255, 255));
        renderer.setSeriesPaint(1, new Color(153, 204, 255));
        renderer.setSeriesPaint(2, new Color(51, 204, 204));

        // 设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.0);

        // 显示每个柱的数值，并修改该数值的字体属性
        renderer.setIncludeBaseInRange(true);
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // 设置柱的透明度
        plot.setForegroundAlpha(1.0f);

        int width = 450; /* Width of the image */
        int height = 350; /* Height of the image */

        File BarChart = new File("BarChart.jpeg");
        ChartUtilities.saveChartAsJPEG(BarChart, barChart, width, height);
    }
}
