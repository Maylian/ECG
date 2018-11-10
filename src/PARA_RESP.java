import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PARA_RESP {

    private byte RR; //呼吸率
    private int RR_Wave;
    private byte Patient;//病人类型
    private byte StifleId;//窒息标识
    private byte Resp_Warning;//报警信息
    private int[] RESP_WAVE = new int[128];

    public byte getRR()
    {
        return RR;
    }

    public void setRESP_WAVE(int[] RESP_WAVE)
    {
        this.RESP_WAVE = RESP_WAVE;
    }
    public int[] getRESP_WAVE()
    {
        return RESP_WAVE;
    }
    public byte getPatient(){return Patient;}
    public byte getStifleId(){return StifleId;}
    public byte getResp_Warning(){return  Resp_Warning;}
    public int getRR_Wave()
    {
        return RR_Wave;
    }

    //写入文件
    public void inputfile(int data)
    {
        FileWriter fw = null;
        try
        {
            File f = new File("C:\\Users\\814-2\\Desktop\\SPO2.txt");
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

    public PARA_RESP(ArrayList list)
    {
        // System.out.println("--开始RESP");
        switch ((byte)list.get(3))
        {
            case 0x34:
                this.RR = (byte)(list.get(5));
                System.out.println(" RR = "+RR);
                ConstantValue.resp_flag = 1;
                break;
            case 0x35:
                this.RR_Wave = (byte)list.get(4)&0xFF;
            //    this.inputfile(RR_Wave);
             //   System.out.println("--------RR_wace   "+RR_Wave);
                ConstantValue.resp_flag = 2;
                break;
            default:
                ConstantValue.resp_flag = 3;
                break;

        }
    }
}











