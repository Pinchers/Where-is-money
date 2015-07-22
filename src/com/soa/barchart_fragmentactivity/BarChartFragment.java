package com.soa.barchart_fragmentactivity;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.soa.note.R;

public class BarChartFragment extends Fragment {

	private int Xlength;
	private double[] payOfMoney, incomeOfMoney;
	private int[] xLable;
	private LinearLayout barChart_layout;

	public static BarChartFragment newInstance(int Xlength, double[] payOfMoney, double[] incomeOfMoney) {

		BarChartFragment barChartFragment = new BarChartFragment();
		Bundle bundle = new Bundle();
		// x轴的长度 显示多少组数据
		bundle.putInt("Xlength", Xlength);
		// 填充X轴数据的数据组
		bundle.putDoubleArray("payOfMoney", payOfMoney);
		bundle.putDoubleArray("incomeOfMoney", incomeOfMoney);

		barChartFragment.setArguments(bundle);
		return barChartFragment;

	}

	/**
	 * 在初始化布局之前 先初始化数据
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 拿到bundle对象 判断不为空开始初始化数据
		if (null != getArguments()) {

			Xlength = getArguments().getInt("Xlength");
			payOfMoney = getArguments().getDoubleArray("payOfMoney");
			incomeOfMoney = getArguments().getDoubleArray("incomeOfMoney");

		}

		xLable = new int[Xlength];
		int count = 2;
		for (int i = 0; i < Xlength; i++) {
			xLable[i] = count++;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// 装载布局
		View view = inflater.inflate(R.layout.barchart_activity, container, false);
		barChart_layout = (LinearLayout) view.findViewById(R.id.barChart_layout);
		// 设置填充大小
		barChart_layout
				.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		init();

		return view;

	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) { // TODO
	 * Auto-generated method stub super.onCreate(savedInstanceState);
	 * setContentView(R.layout.barchart_activity); // 获得资源 初始化柱状图
	 * barChart_layout = (LinearLayout) findViewById(R.id.barChart_layout);
	 * init(); //设置填充大小 barChart_layout.setLayoutParams(new
	 * LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	 * LayoutParams.MATCH_PARENT));
	 * 
	 * Button button=(Button) findViewById(R.id.zhuzhuangtu);
	 * button.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub Tools.skip(BarChartActivity.this, LineChartActivity.class); } }); }
	 */

	/**
	 *  
	 */
	private void init() {// 柱状图的两个序列的名字
		String[] titles = new String[] { getResources().getString(R.string.add_rd_pay),
				getResources().getString(R.string.add_rd_income) };
		// 存放柱状图两个序列的值
		ArrayList<double[]> value = new ArrayList<double[]>();
		value.add(payOfMoney);
		value.add(incomeOfMoney);
		// 两个状的颜色
		int[] colors = { Color.BLUE, Color.GREEN };

		// 为li1添加柱状图
		barChart_layout.addView(
				xyChar(titles, value, colors, Xlength, 5, new double[] { 1, 6.5, 0, 1000 }, xLable,
						getResources().getString(R.string.barChartFragment_string_title), true),
				new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	private GraphicalView xyChar(String[] title, ArrayList<double[]> arrayList, int[] colors, int x, int y,
			double[] range, int[] xLable, String xtitle, boolean f) {
		// 多个渲染
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		// 多个序列的数据集
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// 构建数据集以及渲染
		for (int i = 0; i < title.length; i++) {

			XYSeries series = new XYSeries(title[i]);
			double[] yLable = arrayList.get(i);
			for (int j = 0; j < yLable.length; j++) {
				series.add(xLable[j], yLable[j]);
			}
			dataset.addSeries(series);
			XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
			// 设置颜色
			xyRenderer.setColor(colors[i]);
			// 设置点的样式 //
			xyRenderer.setPointStyle(PointStyle.SQUARE);
			// 将要绘制的点添加到坐标绘制中

			renderer.addSeriesRenderer(xyRenderer);
		}
		// 设置x轴标签数
		renderer.setXLabels(x);
		// 设置Y轴标签数
		renderer.setYLabels(y);
		// 设置x轴的最大值
		renderer.setXAxisMax(x - 0.5);
		// 设置轴的颜色
		renderer.setAxesColor(Color.BLACK);
		// 设置x轴和y轴的标签对齐方式
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		// 设置现实网格
		renderer.setShowGrid(true);

		renderer.setShowAxes(true);
		// 设置条形图之间的距离
		renderer.setBarSpacing(0.5);
		renderer.setInScroll(false);
		// 设置 X轴 Y轴 可滑动
		renderer.setPanEnabled(true, false);
		renderer.setClickEnabled(false);
		// 设置x轴和y轴标签的颜色
		renderer.setXLabelsColor(Color.RED);
		renderer.setYLabelsColor(0, Color.RED);

		int length = renderer.getSeriesRendererCount();
		// 设置图标的标题
		renderer.setChartTitle(xtitle);
		renderer.setLabelsColor(Color.RED);

		// 设置图例的字体大小
		renderer.setLegendTextSize(20);
		// 设置x轴和y轴的最大最小值
		renderer.setRange(range);
		renderer.setMarginsColor(0x00888888);
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer ssr = renderer.getSeriesRendererAt(i);
			ssr.setChartValuesTextAlign(Align.RIGHT);
			ssr.setChartValuesTextSize(12);
			ssr.setDisplayChartValues(f);
		}
		GraphicalView mChartView = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset,
				renderer, Type.DEFAULT);

		return mChartView;

	}

}
