import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MedicalWaveDisplayPanel extends JPanel{
    public Surface surf;
    JPanel infoPanel; //information on this control...
    private String lock;
    private Lock m_lock = new ReentrantLock();
    private PaintLockService m_pls = new PaintLockService();
//    private LinkedList<Object> list = new LinkedList<>(); //获取的数据存储的载体

    private int i = 0;
    private int[] SPO2wave = new int[60];
    private int[] ECGwave = new int[60];
    private int[] RESPwave = new int[60];

    public MedicalWaveDisplayPanel()
    {
        lock = new String();
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(new EtchedBorder(),"Medical Info Panel"));
        add(surf = new Surface(this.m_pls));//new Surface(this.m_pls)

        return;
    }
    public void paint(Graphics g)
    {
        //paint方法不需要编写代码调用，只需要重写。
        super.paint(g);//paint重写时,先调用此语句
        return;
    }
    public int DispStart()//绘制方格
    {
        this.surf.start();
        return 0;
    }
    public int DispStop()
    {
        this.surf.stop();
        return 0;
    }



    public synchronized void putSPO2data(int spo2)
    {
        while (i == 60)
        {
            i = 0;
            this.SetSpo2WaveData(SPO2wave);
        }
        SPO2wave[i++] = spo2;

    }
    public synchronized void putECGdata(int ecg)
    {
        while (i == 60)
        {
            i = 0;
            this.SetECG_WaveData(ECGwave);
        }
        ECGwave[i++] = ecg;
    }
    public synchronized void putRESP(int resp)
    {
        while (i == 60)
        {
            i = 0;
            this.SetRESPWaveData(RESPwave);
        }
        RESPwave[i++] = resp;
    }

    public synchronized int SetECG_WaveData(int[] data)//MedicalWaveFrame调用
    {
        float _fMax = 200.0f;
        float _fMin = -190.0f;
        float _fNormalize = _fMax-_fMin;

        float _f = 0.0f;
        //    System.out.println("wait ecg_data push");
        //m_lock.lock();
        while(m_pls.hasValue == true)
        {
            System.out.println("Panel await();");
            m_pls.await();
        }
        for (int _i0 = 0 ; _i0 < data.length; _i0++)
        {
            _f = (data[_i0]-_fMin)/_fNormalize;
            this.surf.data[_i0] = _f;//32行

        }
        m_pls.SetHasValue(true);
        m_pls.signal();//唤醒一个等待线程。

        //    System.out.println("data push finish");

        return 0;
    }

    public synchronized int SetSpo2WaveData(int[] data0)
    {

      //  float Min = Arrays.stream(data0).min().getAsInt();
     //   float Max = Arrays.stream(data0).max().getAsInt();

        float Max = 227.0f;
        float Min = 10.0f;
        float Mid = Max - Min;
        float f = 0.0f;

        while (m_pls.hasValue == true)
        {
            m_pls.await();
        }

        for(int i = 0;i < data0.length; i++)
        {
         //   f = (data0[i] - Min)/Mid;
             f = (Max - data0[i])/Mid;
            this.surf.data[i] = f;
        }

    //    System.out.println("wait spo2 data push");
      /*  while (m_pls.hasValue = true)
        {
            m_pls.await();
        } */
/*
        for(int i = 0;i < data0.length; i++)
        {
            f = (data0[i] - Min)/Mid;
            this.surf.data[i] = f;
        }
*/
        m_pls.SetHasValue(true);
        m_pls.signal();

    //    System.out.println("data push finish");
        return 0;
    }

    public synchronized int SetRESPWaveData(int[] data0)
    {

      //  float Min = Arrays.stream(data0).min().getAsInt();
      //  float Max = Arrays.stream(data0).max().getAsInt();

        float Max = 250.0f;
        float Min = 10.0f;
        float Mid = Max - Min;
        float f = 0.0f;
    //    System.out.println("resp data push");
        while(m_pls.hasValue == true)
        {
            System.out.println("Panel await();");
            m_pls.await();
        }
      /*  while (m_pls.hasValue = true)
        {
            m_pls.await();
        } */
        for(int i = 0;i < data0.length; i++)
        {
            f = (data0[i] - Min)/Mid;
            this.surf.data[i] = f;
        }
        m_pls.SetHasValue(true);
        m_pls.signal();
        return 0;
    }
}
