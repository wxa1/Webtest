package main.java.tools;

import java.awt.Font;
import java.io.File;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class gen_chart {

	public static void main(String[] args) {
		try {
			// 绉嶇被鏁版嵁闆�
			DefaultCategoryDataset ds = new DefaultCategoryDataset();
			ds.setValue(100, "ibm", "绗竴瀛ｅ害");
			ds.setValue(200, "ibm", "绗簩瀛ｅ害");
			ds.setValue(600, "ibm", "绗笁瀛ｅ害");
			ds.setValue(500, "google", "绗竴瀛ｅ害");
			ds.setValue(333, "google", "绗簩瀛ｅ害");
			ds.setValue(780, "google", "绗笁瀛ｅ害");
			ds.setValue(600, "鐢ㄥ弸", "绗竴瀛ｅ害");
			ds.setValue(1500, "鐢ㄥ弸", "绗簩瀛ｅ害");
			ds.setValue(300, "鐢ㄥ弸", "绗笁瀛ｅ害");

			Font font = new Font("瀹嬩綋", Font.BOLD, 20);

			// 鍒涘缓鏌辩姸鍥�,鏌辩姸鍥惧垎姘村钩鏄剧ず鍜屽瀭鐩存樉绀轰袱绉�

			JFreeChart chart = ChartFactory.createBarChart("鍓嶄笁瀛ｅ害鍚勫ぇ鍏徃JEEAS甯傚満閿�鍞儏鍐�", "瀛ｅ害", "閿�閲�(涓囧彴)", ds,
					PlotOrientation.VERTICAL, true, true, true);

			// 璁剧疆鏁翠釜鍥剧墖鐨勬爣棰樺瓧浣�

			chart.getTitle().setFont(font);

			// 璁剧疆鎻愮ず鏉″瓧浣�

			font = new Font("瀹嬩綋", Font.BOLD, 15);
			chart.getLegend().setItemFont(font);

			// 寰楀埌缁樺浘鍖�

			CategoryPlot plot = (CategoryPlot) chart.getPlot();

			// 寰楀埌缁樺浘鍖虹殑鍩熻酱(妯酱),璁剧疆鏍囩鐨勫瓧浣�

			plot.getDomainAxis().setLabelFont(font);

			// 璁剧疆妯酱鏍囩椤瑰瓧浣�

			plot.getDomainAxis().setTickLabelFont(font);

			// 璁剧疆鑼冨洿杞�(绾佃酱)瀛椾綋

			plot.getRangeAxis().setLabelFont(font);

			// 瀛樺偍鎴愬浘鐗�
			// 璁剧疆chart鐨勮儗鏅浘鐗�

//			chart.setBackgroundImage(ImageIO.read(new File("d:/sunset.bmp")));
//			plot.setBackgroundImage(ImageIO.read(new File("d:/Water.jpg")));
			plot.setForegroundAlpha(1.0f);
			ChartUtils.saveChartAsJPEG(new File("d:/bar.jpg"), chart, 600, 400);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}