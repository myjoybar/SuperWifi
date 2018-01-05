package me.joybar.superwifi.data;

import java.util.Random;

import me.joybar.superwifi.R;

/**
 * Created by joybar on 2018/1/4.
 */

public enum ColorSelector {

	BLUE("blue", R.color.theme_blue_background),
	PURPLE("purple", R.color.theme_purple_background),
	YELLOW("yellow", R.color.theme_yellow_background),
	GREEN("green", R.color.theme_green_background),
	RED("red", R.color.theme_red_background);

	static int indexBg = new Random().nextInt(ColorSelector.values().length);
	static int indexTv = indexBg+1;
	static int getIndexTvBG = indexBg+3;

	private String name;
	private int colorId;

	public static int getRandomColorID(){
		int size = ColorSelector.values().length;
		Random random = new Random();
		int index = random.nextInt(size);
		return ColorSelector.values()[index].getColorId();

	}

	public static int getRandomBgSortedColorID(){
		int size = ColorSelector.values().length;
		return ColorSelector.values()[indexBg++%size].getColorId();
	}

	public static int getRandomTvBgSortedColorID(){
		int size = ColorSelector.values().length;
		return ColorSelector.values()[getIndexTvBG++%size].getColorId();
	}

	public static int getRandomTvSortedColorID(){
		int size = ColorSelector.values().length;
		return ColorSelector.values()[indexTv++%size].getColorId();
	}

	ColorSelector(String name, int colorId) {
		this.name = name;
		this.colorId = colorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
}
