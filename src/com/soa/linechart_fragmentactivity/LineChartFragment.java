package com.soa.linechart_fragmentactivity;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.soa.note.R;

public class LineChartFragment extends Fragment {

	private LinearLayout linearLayout;
	// 折线说明文字
	private String[] titles = new String[] { "2014", "2015" };
	// 数据
	private List<double[]> values;
	// 每个数据点的颜色
	private List<int[]> colors;
	// 每个数据点的简要 说明
	private List<String[]> explains;
	// 折线的线条颜色
	private int[] mSeriescolors;
	// 渲染器
	private XYMultipleSeriesRenderer renderer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 装载布局
		View view = inflater.inflate(R.layout.linechart_activity, container,
				false);

		linearLayout = (LinearLayout) view.findViewById(R.id.lineChart_layout);
		// 设置填充大小
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		initValues();
		// 开始绘制折线
		drawChart();
		return view;
	}

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// getActivity().setContentView(R.layout.linechart_activity);
	// linearLayout = (LinearLayout) getActivity().findViewById(R.id.zhexian);
	// //设置填充大小
	// linearLayout.setLayoutParams(new
	// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	// LayoutParams.MATCH_PARENT));
	// initValues();
	// drawChart();
	//
	//
	//
	// Button button=(Button) getActivity().findViewById(R.id.zhexian111);
	// button.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// Tools.skip(getActivity(), PieChartActivity.class);
	// }
	// });
	//
	//
	// }

	/**
	 * 初始化数据
	 */
	private void initValues() {
		values = new ArrayList<double[]>();
		colors = new ArrayList<int[]>();
		explains = new ArrayList<String[]>();

		values.add(new double[] { 5100, 5101, 5270, 5310, 5420, 5430, 5400,
				5490, 5590, 6200, 6240, 6500 });
		values.add(new double[] { 4500, 4800, 5000, 5370, 5480, 5570, 5620,
				5750, 6200, 6450, 6700, 7100 });

		colors.add(new int[] { Color.RED, Color.RED, Color.RED, Color.RED,
				Color.RED, Color.RED, Color.RED, Color.RED, Color.RED,
				Color.RED, Color.RED, Color.RED });
		colors.add(new int[] { Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE,
				Color.GREEN, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE,
				Color.BLUE, Color.BLUE, Color.BLUE });

		explains.add(new String[] { "1月", "2月", "3月", "4月", "5月", "6月", "7月",
				"8月", "9月", "10月", "11月", "12月" });
		explains.add(new String[] { "1月", "2月", "3月", "4月", "5月", "6月", "7月",
				"8月", "9月", "10月", "11月", "12月" });

		mSeriescolors = new int[] { Color.rgb(153, 204, 0),
				Color.rgb(247, 185, 64) };
	}

	/**
	 * 开始绘折线图
	 */
	private void drawChart() {
		renderer = new XYMultipleSeriesRenderer();
		int length = mSeriescolors.length;
		for (int i = 0; i < length; i++) {
			// 创建SimpleSeriesRenderer单一渲染器
			XYSeriesRenderer r = new XYSeriesRenderer();
			// 设置渲染器颜色
			r.setColor(mSeriescolors[i]);
			r.setFillPoints(true);
			r.setPointStyle(PointStyle.SQUARE);
			r.setLineWidth(1);
			r.setChartValuesTextSize(16);
			renderer.addSeriesRenderer(r);
		}
		// 坐标轴标题文字大小
		renderer.setAxisTitleTextSize(16);
		// 图形标题文字大小
		renderer.setChartTitleTextSize(15);
		// 轴线上标签文字大小
		renderer.setLabelsTextSize(15);
		// 说明文字大小
		renderer.setLegendTextSize(15);
		// 图表标题
		renderer.setChartTitle("收入对比");
		// X轴标题
		renderer.setXTitle("时间/月");
		// Y轴标题
		renderer.setYTitle("收入金额/元");
		// X轴最小坐标点
		renderer.setXAxisMin(0);
		// X轴最大坐标点
		renderer.setXAxisMax(12);
		// Y轴最小坐标点
		renderer.setYAxisMin(0);
		// Y轴最大坐标点
		renderer.setYAxisMax(8000);
		// 坐标轴颜色
		renderer.setAxesColor(Color.rgb(51, 181, 229));
		renderer.setXLabelsColor(Color.rgb(51, 181, 229));
		renderer.setYLabelsColor(0, Color.rgb(51, 181, 229));
		// 设置图表上标题与X轴与Y轴的说明文字颜色
		renderer.setLabelsColor(Color.GRAY);
		// 设置字体加粗
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		// 设置在图表上是否显示值标签
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
		renderer.getSeriesRendererAt(1).setDisplayChartValues(true);
		// 显示屏幕可见取区的XY分割数
		renderer.setXLabels(12);
		renderer.setYLabels(8);
		// X刻度标签相对X轴位置
		renderer.setXLabelsAlign(Align.CENTER);
		// Y刻度标签相对Y轴位置
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setPanEnabled(true, true);
		renderer.setZoomEnabled(true);
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomRate(1.1f);
		renderer.setBarSpacing(0.5f);

		// 标尺开启
		// renderer.setScaleLineEnabled(true);
		// // 设置标尺提示框高
		// renderer.setScaleRectHeight(10);
		// // 设置标尺提示框宽
		// renderer.setScaleRectWidth(150);
		// // 设置标尺提示框背景色
		// renderer.setScaleRectColor(Color.argb(150, 52, 182, 232));
		// renderer.setScaleLineColor(Color.argb(175, 150, 150, 150));
		// renderer.setScaleCircleRadius(35);
		// // 第一行文字的大小
		// renderer.setExplainTextSize1(20);
		// // 第二行文字的大小
		// renderer.setExplainTextSize2(20);

		// 临界线
		// double[] limit = new double[] { 15000, 12000, 4000, 9000 };
		// renderer.setmYLimitsLine(limit);
		// int[] colorsLimit = new int[] { Color.rgb(100, 255, 255),
		// Color.rgb(100, 255, 255), Color.rgb(0, 255, 255),
		// Color.rgb(0, 255, 255) };
		// renderer.setmYLimitsLineColor(colorsLimit);

		// 显示表格线
		renderer.setShowGrid(true);
		// 如果值是0是否要显示
		// renderer.setDisplayValue0(true);
		// 创建渲染器数据填充器
		XYMultipleSeriesDataset mXYMultipleSeriesDataset = new XYMultipleSeriesDataset();
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int[] c = colors.get(i);
			String[] e = explains.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				// 设置每个点的颜色
				series.add(v[k]);
			}
			mXYMultipleSeriesDataset.addSeries(series.toXYSeries());
		}
		// 背景
		renderer.setApplyBackgroundColor(false);
		renderer.setBackgroundColor(Color.rgb(222, 222, 200));
		renderer.setMarginsColor(Color.rgb(222, 222, 200));

		// 线图
		View chart = ChartFactory.getLineChartView(getActivity(),
				mXYMultipleSeriesDataset, renderer);
		linearLayout.addView(chart);
	}

}
