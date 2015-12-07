package com.zeng.reader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class BookPageFactory {

	
	private File book_file = null;
	private MappedByteBuffer m_mbBuf = null;
	/**  �ֽ��ܳ���  */
	private int m_mbBufLen = 0;
	/** �ֽڿ�ʼλ�� */
	private int m_mbBufBegin = 0;
	/** �ֽڽ���λ�� */
	private int m_mbBufEnd = 0;
	private String m_strCharsetName = "gbk";
	private Bitmap m_book_bg = null;
	private int mWidth;
	private int mHeight;
	/**Vector<String> m_lines String������*/
	private Vector<String> m_lines = new Vector<String>();

	private int m_fontSize = 24;
	private int m_textColor = Color.BLACK;
	private int m_backColor = 0xffff9e85; // ������ɫ
	private int marginWidth = 15; // �������Ե�ľ���
	private int marginHeight = 20; // �������Ե�ľ���
	/**ÿҳ������ʾ������*/
	private int mLineCount; // ÿҳ������ʾ������
	private float mVisibleHeight; // �������ݵĿ�
	private float mVisibleWidth; // �������ݵĿ�
	private boolean m_isfirstPage,m_islastPage;

	// private int m_nLineSpaceing = 5;

	private Paint mPaint;
	private Paint percentPaint;
	private String strPercent;
	public BookPageFactory(int w, int h) {
		// TODO Auto-generated constructor stub
		mWidth = w;
		mHeight = h;
		// percentPaint.���ò�����ʽ  ��д�ٷֱ�ר��
		percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		percentPaint.setTextAlign(Align.LEFT);
		percentPaint.setTextSize(24);//�����С 24
		percentPaint.setColor(Color.BLACK);//����
		// �����Ǽ�������paint�ġ�
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);//�����С 24
		mPaint.setColor(m_textColor);//����
		mVisibleWidth = mWidth - marginWidth * 2;// �������ݵĿ�   �������ҵı�Ե����һ���ľ��� ����*2
		mVisibleHeight = mHeight - marginHeight * 2;// �������ݵĸ�
		mLineCount = (int) (mVisibleHeight / m_fontSize); // ����ʾ������ / ����ʾ�ĸ߶ȳ���ÿ������ĸ߶�
	}

	public void openbook(String strFilePath) throws IOException {
		book_file = new File(strFilePath);
		long lLen = book_file.length();//�����ֽڳ��ȣ��ж��ٸ��ֽ�
		m_mbBufLen = (int) lLen;//һ����
		//MappedByteBuffer ����
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map( FileChannel.MapMode.READ_ONLY, 0, lLen);
	}
	
	//��ȡǰһ����
	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (m_strCharsetName.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetName.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;// ֮ǰ�Ľ����㣬 ���ػ�ȡ
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}


	// ��ȡ��һ����
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// ���ݱ����ʽ�жϻ���
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {  // \r\n?
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	protected Vector<String> pageDown() {
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // ��ȡһ������
			m_mbBufEnd += paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0) { 
				float str_pixel = mPaint.measureText(strParagraph);
				System.out.println(str_pixel);
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
				lines.add(strParagraph.substring(0, nSize));// 
				strParagraph = strParagraph.substring(nSize);// 
				if (lines.size() >= mLineCount) {
					System.out.println(lines.size());
					break;
				}
			}
			if (strParagraph.length() != 0) {
				try {
					m_mbBufEnd -= (strParagraph + strReturn).getBytes(m_strCharsetName).length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return lines;//������һҳ������
	}
	//��ȡǰһҳ������, �ٰѿ�ʼ��=>������
	protected void pageUp() {
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");

			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}
		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m_mbBufEnd = m_mbBufBegin;
		return;
	}

	protected void prePage() throws IOException {
		if (m_mbBufBegin <= 0) {
			m_mbBufBegin = 0;//��һҳ
			m_isfirstPage=true;
			return;
		}else m_isfirstPage=false;
		m_lines.clear();//���
		pageUp();// m_mbBufEnd = m_mbBufBegin;
		m_lines = pageDown();
	}

	public void nextPage() throws IOException {
		if (m_mbBufEnd >= m_mbBufLen) {
			m_islastPage=true;
			return;
		}else m_islastPage=false;
		m_lines.clear();
		m_mbBufBegin = m_mbBufEnd;//��֮ǰ�Ľ�β�ط� Ϊ��һ����ʼλ��
		m_lines = pageDown();
	}

	public void onDraw(Canvas c) {
		if (m_lines.size() == 0)
			m_lines = pageDown();//��ʼ��ʱΪ0
		if (m_lines.size() > 0) {
			if (m_book_bg == null)
				c.drawColor(m_backColor);
			else
				c.drawBitmap(m_book_bg, 0, 0, null);
			int y = marginHeight;
			for (String strLine : m_lines) {//ÿ��ȡһ�оͻ�һ�е����������marginwidth,x,marginHeight+�����С
				y += m_fontSize;
				c.drawText(strLine, marginWidth, y, mPaint);
			}
		}
		float fPercent = (float) (m_mbBufBegin * 1.0 / m_mbBufLen);
		DecimalFormat df = new DecimalFormat("#0.0");
		 strPercent = df.format(fPercent * 100) + "%";
		int nPercentWidth = (int) percentPaint.measureText("999.9%") + 1;
		c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5, percentPaint);
	}

	public void setBgBitmap(Bitmap BG) {
		m_book_bg = BG;
	}
	
	public boolean isfirstPage() {
		return m_isfirstPage;
	}
	public boolean islastPage() {
		return m_islastPage;
	}
	
	
	// ��������ǩ ��Ҫʹ�õ�����������
	public int getM_mbBufBegin() {
		return m_mbBufBegin;
	}

	public void setM_mbBufBegin(int m_mbBufBegin) {
		this.m_mbBufBegin = m_mbBufBegin;
	}

	public Vector<String> getM_lines() {
		return m_lines;
	}

	public void setM_lines(Vector<String> m_lines) {
		this.m_lines = m_lines;
	}

	public int getM_mbBufEnd() {
		return m_mbBufEnd;
	}

	public void setM_mbBufEnd(int m_mbBufEnd) {
		this.m_mbBufEnd = m_mbBufEnd;
	}
	public void clearM_lines(){
		this.m_lines.clear();
	}

	public int getM_fontSize() {
		return m_fontSize;
	}

	public void setM_fontSize(int m_fontSize) {
		this.m_fontSize = m_fontSize;
		mPaint.setTextSize(m_fontSize);
		mLineCount = (int) (mVisibleHeight / m_fontSize); // ����ʾ������ / ����ʾ�ĸ߶ȳ���ÿ������ĸ߶�
	}

	public String getStrPercent() {
		return strPercent;
	}

	public void setStrPercent(String strPercent) {
		this.strPercent = strPercent;
	}
	
}
