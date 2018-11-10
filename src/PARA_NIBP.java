import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

public class PARA_NIBP {

    private byte NIBP_FrameHeader = ConstantValue.Frame_Header;
    private byte SBP_D7;//收缩压
    private byte SBP_G2;
    private byte DBP_D7;//舒张压
    private byte DBP_G2;
    private byte MAP_D7;//平均动脉压=（收缩+2*舒张）/3
    private byte MAP_G2;
    private int SBP;
    private int DBP;
    private int MAP;

    private byte PRE_G8;//压力数据高8
    private byte PRE_D8;//压力数据低8
    private int PRE;
    private byte MSU_time_G8;//自动测量 测量时间高8位
    private byte MSU_time_D8;
    private int MSU_time;
    private byte NextMSU_G7;//下一次测量时间高7位
    private byte NextMSU_D7;
    private int NextMSU;
    private int MSU_mode;
    private byte G2;


    public float NIBP_Wave;

    public void setNIBP_FrameHeader(byte NIBP_FrameHeader)
    {
        this.NIBP_FrameHeader = NIBP_FrameHeader;
    }
    public byte getNIBP_FrameHeader()
    {
        return NIBP_FrameHeader;
    }

    public void setPRE(int PRE)
    {
        this.PRE = PRE;
    }
    public int getPRE()
    {
        return PRE;
    }

    public void setSBP(int SBP)
    {
        this.SBP = SBP;
    }
    public int getSBP()
    {
        return SBP;
    }

    public void setMAP(int MAP)
    {
        this.MAP = MAP;
    }
    public int getMAP()
    {
        return MAP;
    }

    public void setDBP(int DBP)
    {
        this.DBP = DBP;
    }
    public int getDBP()
    {
        return DBP;
    }


    public PARA_NIBP(ArrayList list)
    {
      /*  ConstantValue.flag = 1;
       // System.out.println("--开始NIBP");
       */

        switch ((byte)(list.get(3)))
        {
            case 0x31:
                this.MSU_mode = ((byte)list.get(5)&0x03);
                if (MSU_mode == 0) System.out.println("手动测量模式");
                else if (MSU_mode == 1) System.out.println("自动测量模式");
                ConstantValue.nibp_flag = 1;
                break;
            case 0x32:
                this.SBP_D7 = (byte)list.get(5);
                this.G2 = (byte)list.get(8);
                this.SBP = ((G2&0x03) << 7) | SBP_D7;
                this.MAP_D7 = (byte)list.get(6);
                this.MAP = ((G2&0x0C) << 5) | MAP_D7;
                this.DBP_D7 = (byte)list.get(7);
                this.DBP = ((G2&0x30) << 3) | DBP_D7;
                //  this.SBP = (((byte)list.get(8)&0x03) << 7)|((byte)list.get(5)&0x7F);
                //  this.MAP = (((byte)list.get(8)&0x0C) << 5)|((byte)list.get(6)&0x7F);
                //   this.DBP = (((byte)list.get(8)&0x30) << 3)|((byte)list.get(7)&0x7F);
                System.out.println(" 收缩压 = "+SBP+"   平均压 = "+MAP+"   舒张压 = "+DBP);
                ConstantValue.nibp_flag = 2;
                break;
            case 0x33:
                this.PRE_G8 = (byte)list.get(4);
                this.PRE_D8 = (byte)list.get(5);
                //    this.PRE = ((((short)list.get(5)&0xFF) << 8))|((byte)(list.get(4)));
                this.PRE = ((PRE_G8&0xFF) << 8) | PRE_D8;
                System.out.println("PRE = "+PRE);
                ConstantValue.nibp_flag = 3;
                break;
            default:
                ConstantValue.nibp_flag = 4;
                break;
        }
    }
}
