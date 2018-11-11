
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Surface extends JPanel implements Runnable{
    public Thread thread;
    public long sleepAmount = 1000;//sleep 1 second.
    private int m_sfWidth,m_sfHeight;
    private BufferedImage m_bimg; //BufferedImage的成员变量 m_bimg;
    private Graphics2D m_big; //Java Graipcs2D Context;//提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
    private Font m_font = new Font("Times New Roman", Font.PLAIN, 11); //set Font
    private Runtime m_runtime = Runtime.getRuntime(); // Get system Runtime;通过 getRuntime 方法获取当前运行时
    private int columnInc;
    private int m_ascent, m_descent;
    private Line2D graphLine = new Line2D.Float(); //Line2D 表示 (x,y) 坐标空间中的线段;使用 float 坐标指定的线段。

    private Color graphColor = new Color(46, 139, 87); //所绘的图（方格）的颜色
    private Color mfColor = new Color(0, 100, 0);
    // 创建具有指定红色、绿色和蓝色值的不透明的 sRGB 颜色，这些值都在 (0 - 255) 的范围内。
    private int pts[];
    private int ptNum;
    private int m_ptArrayIndex = 0;
    private int m_sptArrayIndex = 0;

    private Rectangle graphOutlineRect = new Rectangle();//构造一个新的 Rectangle，其左上角的坐标为 (0,0)，宽度和高度均为 0。
    public List<Float> m_wavePointArray = new ArrayList<Float>(30);//构造一个具有指定初始容量的空列表//存点
    public float data[] = new float[30]; //接收的数据
    public float spo2_data[] = new float[10];
    private boolean m_drawstutas = false;

    private boolean m_havedata = false;
    private int m_graphH;
    private String lock;
    private Lock m_lock;
    private PaintLockService m_pls;
    public int GetwaveLength()
    {
        return this.m_wavePointArray.size();
    }

    public boolean getHaveData()
    {
        return m_havedata;
    }

    public Surface(PaintLockService _m_pls)
    {
        m_pls = _m_pls;
        setBackground(Color.BLACK); //set background Colour is Black...
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//边画边显示?//实现鼠标单击波形冻结
                if (thread == null) {
                    start();
                } else {
                    stop();
                }
                //super.mouseClicked(e);// response Mouse Clicked Event...
            }
        });
    }
    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(135, 80);
    }//Dimension是尺寸
    public int SetECGWaveData(int _ecgdata[],float _fNormalize)
    {
        return 0;
    }

    @Override
    public void paint(Graphics g)//绘制组件的外观。
    {
        if (m_big == null) {
            return;
        }

        m_big.setBackground(this.getBackground());
        m_big.clearRect(0, 0, m_sfWidth, m_sfHeight);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。

        // .. Draw History Graph(图形) ..
        m_big.setColor(graphColor);//set Graph Color//24行
        int graphX = 5;
        int graphY = 5;
        int graphW = m_sfWidth - 2 * graphX;
        int graphH = m_sfHeight - 2 * graphY;

        graphOutlineRect.setRect(graphX, graphY, graphW, graphH);//将此 Rectangle 的边界设置为整数边界，它包含指定的 x、y、width 和 height。
        m_big.draw(graphOutlineRect);

        int graphRow = graphH / 10;//行数
        for (int j = graphY; j <= graphH + graphY; j += graphRow) {
            graphLine.setLine(graphX, j, graphX + graphW, j);
            m_big.draw(graphLine);
        }
        // .. Draw animated column movement ..
        int graphColumn = graphW / 15;//列数

        if (columnInc >= graphColumn) {
            columnInc = 0;
        }

        for (int j = graphX + columnInc; j < graphW + graphX; j += graphColumn) {
            graphLine.setLine(j, graphY, j, graphY + graphH);
            m_big.draw(graphLine);
        }

        ++columnInc;

        m_drawstutas = true;

        float _fPtsV = this.data[m_ptArrayIndex];  //初值为0
      //  float _sfPtsV = this.spo2_data[m_sptArrayIndex];

        _fPtsV = 1 - _fPtsV;
   //     _sfPtsV = 1 - _sfPtsV;
        if (pts == null) {
            pts = new int[graphW];
            ptNum = 0;
        } else if (pts.length != graphW) {
            int tmp[] = null;
            if (ptNum < graphW) {
                tmp = new int[ptNum];
                System.arraycopy(pts, 0, tmp, 0, tmp.length);
            } else {
                tmp = new int[graphW];
                System.arraycopy(pts, pts.length - tmp.length, tmp, 0, tmp.length);
                ptNum = tmp.length - 2;
            }
            pts = new int[graphW];
            System.arraycopy(tmp, 0, pts, 0, tmp.length);
        } else {
//            System.out.println("pts length"+pts.length);
//            System.out.println("graphw"+graphW);

            if (m_graphH != graphH) {
                System.out.println("Change graph");
                for (int i0 = 0; i0 < ptNum - 1; i0++)
                {
                    //float _ft0 = this.m_wavePointArray.get(i0);
                    float _ft0 = pts[i0];
                    pts[i0] = (int) (graphY + graphH * _ft0);
                }
            }

            m_big.setColor(Color.YELLOW);//波形曲线的颜色
            pts[ptNum] = (int) (graphY + graphH * _fPtsV);
        //    pts[ptNum] = (int) (graphY + graphH * _sfPtsV);

            this.m_graphH = graphH;

            for (int j = graphX + ptNum, k = 0; k < ptNum; k++, j--) {
                if (k != 0) {
                    if (pts[k] != pts[k + 1]) {
                        m_big.drawLine(j, pts[k], j - 1, pts[k + 1]);//画线段 x1:(j,pts[k]) x2:(j-1,pts[k+1])
                    } else {
                        m_big.fillRect(j, pts[k], 2, 2); //画的线的粗细并用前景色填充
                    }
                }
            }
            if (ptNum >= pts.length - 1) {
                //throw out oldest point
                for (int j = 1; j <= ptNum; j++) {
                    pts[j - 1] = pts[j];
                }
                //--ptNum;
                //System.out.println("throw out oldest point");
            } else {
                ptNum++;
                //System.out.println("ptNum");
            }
        }

        if (m_ptArrayIndex >= 29) { //127
            m_ptArrayIndex = 0;
            //m_drawstutas = false;
            //m_havedata = false;
            for (int _inx1 = 0; _inx1 < 30; _inx1++) {  //128
                this.data[_inx1] = 0;
            }
            // m_drawstutas1 = true;
            m_pls.SetHasValue(false);
            m_pls.signal();
        } else {
            m_ptArrayIndex++;
        }


        g.drawImage(m_bimg, 0, 0, this);//direct curve to background, do not erase the background...
        System.out.println();
    }

        public void start() {//pass

        thread = new Thread(this);
        //thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName("MedicalInfoDrawing");
        thread.start();
        }

    public synchronized void stop() {
        thread = null;
        notify();// what to do of notify function...??
    }

    @Override
    public void run() {

        //System.out.println(this.m_wavePointArray.size());
        Thread me = Thread.currentThread(); //当前线程
        while (this.thread == me && !isShowing() || getSize().width == 0) {
            try {
                Thread.sleep(500);//every millisecond to check the windows is showing,
            } catch (InterruptedException e) {
                return;
            }
        }
        while (thread == me && isShowing()) {
            Dimension _d = getSize();
            if (_d.width != this.m_sfWidth || _d.height != this.m_sfHeight) {
                this.m_sfWidth = _d.width;
                this.m_sfHeight = _d.height;
                m_bimg = (BufferedImage) createImage(m_sfWidth, m_sfHeight);
                m_big = m_bimg.createGraphics(); // Create a Graphic Context from Buffered Image that point to the Buffer
                m_big.setFont(this.m_font);
                FontMetrics fm = m_big.getFontMetrics(m_font);
                m_ascent = fm.getAscent();  //about the font...
                m_descent = fm.getDescent();//about the font...

            }
            while (m_pls.hasValue == false) {
                System.out.println("Surface");
                m_pls.await();
            }

            repaint();  ////

            try {
                Thread.sleep(7);//every 40 millisecond to repiant the panel...v a total 25 frame drawn pre-second.
            } catch (InterruptedException e) {
                break;
            }
        }
        this.thread = null; //release the thread...
    }

 }

