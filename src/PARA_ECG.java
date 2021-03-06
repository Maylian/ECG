import com.sun.org.apache.xpath.internal.SourceTree;
import org.omg.CORBA.INTERNAL;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class PARA_ECG {


    int i,j = 0;

    private byte HrD8;
    private byte HrG8;
    private short HR;
    private byte Patient_Type;
    private byte job_mode; //工作模式
    private byte ECG_Msg; //ECG过载信息
    private byte ECG_DLMsg;//ECG导联信息
    private byte LA_FallMsg;//LA导联脱落信息
    private byte RA_FallMsg;
    private byte LL_FallMsg;
    private byte RL_FallMsg;

    private int ECG_Oder; //ECG波形数据顺序

    private byte thr_I_D8; //3导联低8位
    private byte thr_I_G8;

    private short  thr_I;
    private short  thr_II;
    private short  thr_III;

    private byte thr_II_D8;
    private byte thr_II_G8;
    private byte thr_III_D8;
    private byte thr_III_G8;

    private short  fiv_I;
    private short  fiv_II;
    private short  fiv_III;
    private short  fiv_AVR;
    private short  fiv_AVL_;
    private short  fiv_AVF;
    private short  fiv_V_;
    private byte fiv_I_D8;
    private byte fiv_I_G8;
    private byte fiv_II_D8;
    private byte fiv_II_G8;
    private byte fiv_III_D8;
    private byte fiv_III_G8;
    private byte fiv_AVR_D8;
    private byte fiv_AVR_G8;
    private byte fiv_AVL_D8;
    private byte fiv_AVL_G8;
    private byte fiv_AVF_D8;
    private byte fiv_AVF_G8;
    private byte fiv_V_D8;
    private byte fiv_V_G8;


    public short getHR()
    {
        return HR;
    }

    public short getThr_I() {
        return thr_I;
    }
    public short getThr_II()
    {
        return thr_II;
    }
    public short getThr_III()
    {
        return thr_III;
    }
    public short getfiv_I()
    {
        return fiv_I;
    }
    public short getfiv_II()
    {
        return fiv_II;
    }
    public short getfiv_III()
    {
        return fiv_III;
    }


    //写入文件
    public void inputfile(int data)
    {
        FileWriter fw = null;
        try
        {
            File f = new File("C:\\Users\\814-2\\Desktop\\ECG_5_I.txt");
            fw = new FileWriter(f,true);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.print(data+" ");
        pw.flush();
        try
        {
            fw.flush();
            pw.close();
            fw.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    public PARA_ECG(ArrayList list)
    { 
       // System.out.print("--------ECG");

        switch ((byte)list.get(3))
        {
            case 0x31:
                this.Patient_Type = (byte)((byte)list.get(4)&0x07);
                this.job_mode = (byte) ((byte)list.get(4)&0x70);
                ConstantValue.ecg_flag = 1;
                break;
            case 0x33:
                this.HR = (short) ((((byte)list.get(5)&0xFF) << 8) | ((byte)list.get(4)&0xFF));
            //    System.out.println("  心率 = "+HR);
                ConstantValue.ecg_flag = 2;
                break;
            case 0x3E:
                this.thr_II_D8 = (byte)list.get(6);
                this.thr_II_G8 = (byte)list.get(7);
                thr_II = (short) (((thr_II_G8&0xFF) << 8) | thr_II_D8&0xFF);
                System.out.println("------------thr_II "+thr_II);
                ConstantValue.flag = 3;
                break;
            case 0x3F:
                this.fiv_I_D8 = (byte)list.get(6);
                this.fiv_I_G8 = (byte)list.get(7);
                this.fiv_I = (short)(((fiv_I_G8&0xFF) << 8) | fiv_I_D8&0xFF);
                this.fiv_II_D8 = (byte)list.get(8);
                this.fiv_II_G8 = (byte)list.get(9);
                this.fiv_II = (short) (((fiv_II_G8&0xFF) << 8) | fiv_II_D8&0xFF);
                this.fiv_III = (short) ((((byte)list.get(11)&0XFF) << 8) | ((byte)list.get(10)&0XFF));
                //    System.out.println(" --------------fiv_I "+fiv_I);
             //   System.out.println(" --------------fiv_II "+fiv_II);
                //    System.out.println(" --------------fiv_III "+fiv_III);
                ConstantValue.ecg_flag = 4;
                break;
            default:
                ConstantValue.ecg_flag = 5;
                break;
        }
    }
}
