package com.horse.util;

import java.text.DecimalFormat;
import java.util.Vector;

import com.horse.bean.Chapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.format.Time;

/**
 * ������Ŀ����Ϊ�ڿ��鷭ҳʱ����Ҫ���еĶ����ṩ�ӿڡ�
 * ����������һҳ��������һҳ���ڷ���ÿ�����һҳʱ��������滹���½ھͼ���������һ�½ڣ�û�о����û���ʾ�Ѷ��ꡣ
 * �ڷ�����һ�½�ʱ�����ǰ�滹���½ڣ��ͷ�����һ�½ڣ�û�о����û���ʾ�����Ѿ��ǵ�һ�½ڡ�
 * 
 * ��ֱ������Ϊ���Ӧ��ֻ���ó�һ���ӿڣ���Ϊֻ������ͼ���ṩ�����ӿڣ�Ҳ���Ǳ���Ӧ����ģ�Ͳ㡣��������Ϊһ����ڿ���Ҳ���ʡ�
 * ����������ó�һ���ӿڣ���ô�ӿڵ�ʵ���࣬�ж����Ҫ��������ݡ���ôΪ�˴������ã���������ܱȽӿڸ��Ӻ��ʡ� �����Ǹ��˷��������ܲ��Ǻܺ��ʡ�
 * 
 * @author MJZ
 * 
 */
public class BookPage {
	// configuration information
	private int screenWidth; // ��Ļ���
	private int screenHeight; // ��Ļ�߶�
	private int fontSize; // �����С
	private int lineHgight;	//ÿ�еĸ߶�
	private int marginWidth = 15; // �������Ե�ľ���
	private int marginHeight = 15; // �������Ե�ľ���
	private int textColor; // ������ɫ
	private Bitmap bgBitmap; // ����ͼƬ
	private int bgColor; // ������ɫ

	private Paint paint;
	private Paint paintBottom;
	private int visibleWidth; // ��Ļ�п���ʾ�ı��Ŀ��
	private int visibleHeight;
	private Chapter chapter; // ��Ҫ������½ڶ���
	private Vector<String> linesVe; // ���½ڃ��ݷֳ��У�����ÿҳ���д洢��vector������
	private int lineCount; // һ���½��ڵ�ǰ������һ���ж�����

	private String content;
	private int chapterLen; // �½ڵĳ���
	// private int curCharPos; // ��ǰ�ַ����½�������λ��
	private int charBegin; // ÿһҳ��һ���ַ����½��е�λ��
	private int charEnd; // ÿһҳ���һ���ַ����½��е�λ��
	private boolean isfirstPage;
	private boolean islastPage;

	private Vector<Vector<String>> pagesVe;
	int pageNum;

	/**
	 * ���½�һ��BookPage����ʱ����Ҫ�����ṩ���ݣ���֧����Ļ��ҳ���ܡ�
	 * 
	 * @param screenWidth
	 *            ��Ļ��ȣ���������ÿ����ʾ������
	 * @param screenHeight
	 *            ��Ļ�߶ȣ���������ÿҳ��ʾ������
	 * @param chapter
	 *            �½ڶ���
	 */
	public BookPage(int screenWidth, int screenHeight, Chapter chapter) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.chapter = chapter;
		init();
	}

	/**
	 * ��ʼ��ð��ն��������˳������ʼ����ͳһ���ڽ�����Ҫ�޸�ĳ��������ʱ�������ҵ��� �Դ���ά��Ӧ��Ҳ�����ðɡ�
	 */
	protected void init() {
		bgBitmap = null;
		bgColor = 0xffff9e85;
		textColor = Color.BLACK;
		content = chapter.getContent();
		chapterLen = content.length();
		// curCharPos = 0;
		charBegin = 0;
		charEnd = 0;
		fontSize = 30;
		lineHgight = fontSize + 8;
		linesVe = new Vector<String>();

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Align.LEFT);
		paint.setTextSize(fontSize);
		paint.setColor(textColor);
		
		paintBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
		paintBottom.setTextAlign(Align.LEFT);
		paintBottom.setTextSize(fontSize / 2);
		paintBottom.setColor(textColor);

		visibleWidth = screenWidth - marginWidth * 2;
		visibleHeight = screenHeight - marginHeight * 2;
		lineCount = visibleHeight / lineHgight - 2;
		isfirstPage = true;
		islastPage = false;
		pagesVe = new Vector<Vector<String>>();
		pageNum = -1;
		slicePage();
	}

	public Vector<String> getCurPage() {
		return linesVe;
	}

	protected void slicePage() {
		pagesVe.clear();
		int curPos = 0;
		while (curPos < chapterLen) {
			Vector<String> lines = new Vector<String>();
			charBegin = curPos;
			while (lines.size() < lineCount && curPos < chapterLen) {
				int i = content.indexOf("\n", curPos);
				String paragraphStr = content.substring(curPos, i);
				// curCharPos += i;
				if (curPos == i)
					lines.add("");

				while (paragraphStr.length() > 0) {
					int horSize = paint.breakText(paragraphStr, true,
							visibleWidth, null);
					lines.add(paragraphStr.substring(0, horSize));
					paragraphStr = paragraphStr.substring(horSize);
					curPos += horSize;
					if (lines.size() > lineCount)
						break;
				}
				// ����ǰ�һ���ζ�ȡ��Ļ�����Ҫ����ǰλ�ü�1
				if (paragraphStr.length() == 0)
					curPos += "\n".length();
			}
			pagesVe.add(lines);
		}

	}

	/**
	 * ������һҳ
	 */
	public boolean nextPage() {
		if (isLastPage()) {
			if (!nextChapter()) // ����Ѿ�������ĩβ����ô���ܼ���ִ�з�ҳ����
				return false;
		}
		/*
		 * Vector<String> lines = new Vector<String>(); charBegin = charEnd;
		 * while (lines.size() < lineCount && charEnd < chapterLen) { int i =
		 * content.indexOf("\n", charEnd); String paragraphStr =
		 * content.substring(charEnd, i); // curCharPos += i; if (charEnd == i)
		 * lines.add("");
		 * 
		 * while (paragraphStr.length() > 0) { int horSize =
		 * paint.breakText(paragraphStr, true, visibleWidth, null);
		 * lines.add(paragraphStr.substring(0, horSize)); paragraphStr =
		 * paragraphStr.substring(horSize); charEnd += horSize; if (lines.size()
		 * > lineCount) break; } // ����ǰ�һ���ζ�ȡ��Ļ�����Ҫ����ǰλ�ü�1 if
		 * (paragraphStr.length() == 0) charEnd += "\n".length(); } linesVe =
		 * lines;
		 */
		linesVe = pagesVe.get(++pageNum);
		return true;
	}

	/**
	 * ������һҳ
	 */
	public boolean prePage() {
		if (isFirstPage()) {
			if (!preChapter()) // ����Ѿ��������һ�£��Ͳ��ܼ���ִ�з�ҳ����
				return false;
		}
		/*
		 * Vector<String> lines = new Vector<String>(); String backStr =
		 * content.substring(0, charBegin); charEnd = charBegin;
		 * 
		 * while (lines.size() < lineCount && charBegin > 0) { int i =
		 * backStr.lastIndexOf("\n"); if(i == -1) i = 0; String paragraphStr =
		 * backStr.substring(i, charBegin); Vector<String> vet = new
		 * Vector<String>(lines);
		 * 
		 * // if(charBegin == i)lines.add("");
		 * 
		 * while (paragraphStr.length() > 0) { int horSize =
		 * paint.breakText(paragraphStr, true, visibleWidth, null);
		 * lines.add(paragraphStr.substring(0, horSize)); paragraphStr =
		 * paragraphStr.substring(horSize); charBegin -= horSize; if
		 * (lines.size() > lineCount) break; }
		 * 
		 * backStr = content.substring(0, charBegin); int j = -1; for (String
		 * line : vet) lines.insertElementAt(line, ++j); } linesVe = lines;
		 */
		linesVe = pagesVe.get(--pageNum);
		return true;
	}

	/**
	 * ������һ�£�������ֵΪfalse����ǰ�½��Ѿ�Ϊ���һ��
	 */
	public boolean nextChapter() {
		int order = chapter.getOrder();
		Chapter tempChapter = IOHelper.getChapter(order + 1);
		if (tempChapter == null)
			return false;
		chapter = tempChapter;
		content = chapter.getContent();
		chapterLen = content.length();
		// curCharPos = 0;
		charBegin = 0;
		charEnd = 0;
		slicePage();
		pageNum = -1;
		return true;
	}

	/**
	 * ������һ��,������ֵΪfalse����ǰ�½��Ѿ�Ϊ��һ��
	 */
	public boolean preChapter() {
		int order = chapter.getOrder();
		Chapter tempChapter = IOHelper.getChapter(order - 1);
		if (tempChapter == null)
			return false;
		chapter = tempChapter;
		content = chapter.getContent();
		chapterLen = content.length();
		// curCharPos = chapterLen;
		charBegin = chapterLen;
		charEnd = chapterLen;
		slicePage();
		pageNum = pagesVe.size();
		return true;
	}

	public boolean isFirstPage() {
		if (pageNum <= 0)
			return true;
		return false;
	}

	public boolean isLastPage() {
		if (pageNum >= pagesVe.size() - 1)
			return true;
		return false;
	}

	public void draw(Canvas c) {
		if (linesVe.size() == 0)
			nextPage();
		if (linesVe.size() > 0) {
			if (bgBitmap == null)
				c.drawColor(bgColor);
			else
				c.drawBitmap(bgBitmap, 0, 0, null);

			int y = marginHeight;
			for (String line : linesVe) {
				y += lineHgight;
				c.drawText(line, marginWidth, y, paint);
			}
		}

		// float percent = (float) (charBegin * 1.0 / chapterLen);
		float percent = (float) ((pageNum + 1) * 1.0 / pagesVe.size());
		DecimalFormat df = new DecimalFormat("#0.0");
		String percetStr = df.format(percent * 100) + "%";

		Time time = new Time();
		time.setToNow();
		String timeStr;
		if (time.minute < 10)
			timeStr = "" + time.hour + " : 0" + time.minute;
		else
			timeStr = "" + time.hour + " : " + time.minute;

		int pSWidth = (int) paintBottom.measureText("99.9%") + 2;
		int titWidth = (int) paintBottom.measureText(chapter.getTitle());

		
		c.drawText(timeStr, marginWidth / 2, screenHeight - 5, paintBottom);
		c.drawText(chapter.getTitle(), screenWidth / 2 - titWidth / 2,
				screenHeight - 5, paintBottom);
		c.drawText(percetStr, screenWidth - pSWidth, screenHeight - 5, paintBottom);
	}

	public void setBgBitmap(Bitmap bMap) {
		bgBitmap = Bitmap.createScaledBitmap(bMap, screenWidth, screenHeight,
				true);
	}

}
