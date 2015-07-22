package com.soa.piechart_fragmentactivity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.soa.note.R;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class PieChartFragment extends Fragment {

	GraphicalView graphicalView;// 动态图表
	private CategorySeries series = new CategorySeries("");
	private double[] values;
	private String[] sort;
	LinearLayout charrt;// 显示图表
	int[] colors = { Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, Color.GREEN, Color.LTGRAY, Color.DKGRAY,
			Color.MAGENTA, Color.CYAN, Color.WHITE, Color.MAGENTA, Color.RED };
	DefaultRenderer renderer = getrenderer(colors);
	CategorySeries dataset;

	/**
	 * 创建并且传值的方法
	 * 
	 * @param values
	 *            饼状图对应的数值
	 * @param sort
	 *            分类的文本信息
	 * @return 一个饼状图实例
	 */
	public static PieChartFragment newInstance(double[] values, String[] sort) {

		PieChartFragment pieChartFragment = new PieChartFragment();
		// 新建一个bundle对象
		Bundle bundle = new Bundle();
		// 添加数值与分类信息的数据
		bundle.putDoubleArray("values", values);
		bundle.putStringArray("sort", sort);
		pieChartFragment.setArguments(bundle);
		// 返回一个饼状图实例
		return pieChartFragment;

	}

	/**
	 * 初始化全局变量 在初始化布局之前 先初始化数据
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 拿到bundle对象
		// 判断不为空 开始取数据
		if (null != getArguments()) {

			values = getArguments().getDoubleArray("values");
			sort = getArguments().getStringArray("sort");
			dataset = buildCategoryDataset("饼状图", values, sort);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// 装载布局
		View view = inflater.inflate(R.layout.piechart_activity, container, false);
		charrt = (LinearLayout) view.findViewById(R.id.pieChart_layout);
		charrt.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		graphicalView = ChartFactory.getPieChartView(getActivity().getBaseContext(), dataset, renderer);
		charrt.removeAllViews();
		charrt.setBackgroundColor(getResources().getColor(R.color.steelblue));
		// 设置填充大小
		charrt.addView(graphicalView,
				new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));// 显示饼状图

		return view;
	}

	/*
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.piechart_activity);
	 * 
	 * charrt = (LinearLayout) findViewById(R.id.pieChart_layout);
	 * charrt.setLayoutParams(new LinearLayout.LayoutParams(500, 700));
	 * graphicalView = ChartFactory.getPieChartView(getBaseContext(), dataset,
	 * renderer); charrt.removeAllViews();
	 * charrt.setBackgroundColor(Color.TRANSPARENT); //设置填充大小
	 * charrt.addView(graphicalView, new LayoutParams(LayoutParams.MATCH_PARENT,
	 * LayoutParams.MATCH_PARENT));// 显示饼状图 }
	 */

	// 设置渲染器
	private DefaultRenderer getrenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLegendTextSize(20);
		// 左下角标注的字体大小
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		// 是否放大缩小
		renderer.setChartTitleTextSize(20);
		// 标题文字大小
		renderer.setChartTitle("比例分配图");
		// 标题 默认居中显示
		renderer.setLabelsTextSize(10);
		// 标记文字的大小
		renderer.setPanEnabled(true);
		// 是否可以平移
		renderer.setClickEnabled(false);
		// 是否可以点击
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	private CategorySeries buildCategoryDataset(String title, double[] values, String[] sort) {
		CategorySeries series = new CategorySeries(title);

		for (int i = 0; i < values.length; i++) {
			series.add(sort[i], values[i]);
		}

		return series;
	}

	// @Override
	// protected void onResume() {
	// super.onResume();
	// if (graphicalView == null) {
	// LinearLayout layout = (LinearLayout) findViewById(R.id.linp);
	// graphicalView = ChartFactory.getPieChartView(this, series, renderer);
	// renderer.setClickEnabled(true);
	// graphicalView.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// SeriesSelection seriesSelection =
	// graphicalView.getCurrentSeriesAndPoint();
	// if (seriesSelection == null) {
	// Toast.makeText(PieChartr.this, "No chart element selected",
	// Toast.LENGTH_SHORT)
	// .show();
	// } else {
	// for (int i = 0; i < series.getItemCount(); i++) {
	// renderer.getSeriesRendererAt(i).setHighlighted(i ==
	// seriesSelection.getPointIndex());
	// }
	// graphicalView.repaint();
	// Toast.makeText(
	// PieChartr.this,
	// "Chart data point index " + seriesSelection.getPointIndex() + " selected"
	// + " point value=" + seriesSelection.getValue(),
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// });
	// }
	// }

}
