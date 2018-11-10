import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import gnu.io.*;

public class Serial_Port extends Thread implements SerialPortEventListener{ //监听

    private MainMedicalWaveFrame m_mwdp;
    private PaintLockService pls;

    static CommPortIdentifier portID;  // 串口通信管理类
    static Enumeration<CommPortIdentifier> portList; // 有效连接上的端口的枚举
    static InputStream inputStream;          // 从串口来的输入流
    static OutputStream outputStream; // 向串口输出的流
    static SerialPort serialPort;     // 串口的引用
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();  // 堵塞队列用来存放读到的数据

  //  public static boolean hasvalue = false;
    int i = 0;
    ArrayList list = new ArrayList();


    /**
     * 获取本机所有端口，逐个打开，成功并设置参数
     * @param  //波特率 ，数据位 ，停止位 ，校验位
     * @throws PortInUseException 端口被占用时
     * @throws IOException
     * @throws TooManyListenersException
     * @throws UnsupportedCommOperationException
     * @return
     */
    public int StartComport()
    {
        /*//用于标识端口的变量
        CommPortIdentifier portIdentifier = null;*/
        portList = CommPortIdentifier.getPortIdentifiers(); //取得端口列表
        String portName = ""; //当前串口名称
        while (portList.hasMoreElements())
        {
            portID = portList.nextElement();
            portName = portID.getName();
            if (portID.getPortType() == CommPortIdentifier.PORT_SERIAL) { //判断端口类型是否为串口
                if (portID.getName().equals(portName)) // serialPort instanceof SerialPort 判断是不是串口
                {
                    try //尝试打开端口
                    {
                     //   hasvalue = true;
                        serialPort = (SerialPort) portID.open(portID.getName(), 1000); //1000表示如果串口被占用的时候本程序的最长等待时间
                        System.out.println(portID.getName() + "---串口打开成功");
                        //    if(serialPort instanceof SerialPort) 判断是不是串口
                    } catch (PortInUseException e) {
                     //   e.printStackTrace(); //要打开的端口被占用时抛出该异常
                     //   this.close();
                    }
                    try {
                        inputStream = serialPort.getInputStream(); //将从串口来的输入流对象实例化
                        outputStream = serialPort.getOutputStream();
                        if(inputStream.available() <= 0) //没有数据
                        {
                            System.out.println(portID.getName()+"---串口关闭");
                            continue;
                        }
                    } catch (IOException e) {
                        e.printStackTrace(); //抛出异常堆栈信息
                    }
                    try //给当前串口添加一个监听器
                    {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置监听器生效，即：当有数据时通知
                    serialPort.notifyOnDataAvailable(true);

                    try // 设置串口的一些读写参数
                    {
                        // 依次是比特率、数据位、停止位、校验位
                        serialPort.setSerialPortParams(230400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    return 1;
                }

            }
        }
        return 0;
    }

    public void close(){
        serialPort.close();
        serialPort = null;
        portID = null;
        System.out.println(serialPort.getName()+"---串口关闭成功");
    }

    /**
     * 从串口读取数据  当端口有可用数据,读到缓冲数组，输出到终端
      * @param event
     */
    public void serialEvent(SerialPortEvent event)
    {
        switch (event.getEventType())
        {
            case SerialPortEvent.BI: //Break Interrupt 通讯中断
            case SerialPortEvent.OE: //Overrun Error 溢位错误
            case SerialPortEvent.FE: //Framing Error 传帧错误
            case SerialPortEvent.PE: //Parity Error 校验错误
            case SerialPortEvent.CD: //Carrier Detect 载波检测
            case SerialPortEvent.CTS: //Clear to send 清除发送
            case SerialPortEvent.DSR: //Data set ready 数据设备就绪
            case SerialPortEvent.RI:  //Ring indicator 响铃指示
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: //Output buffer is empty 输出缓冲区清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: //Data available at the serial port 当端口有可用数据,读到缓冲数组，输出到终端
                byte[] readBuffer = new byte[1024];//缓冲区大小 ： 1024
                try
                {
                   // InputStream input=sk.getInputStream();
                 //   String ip=serialPort.c
                    int numBytes = -1;//等于-1表示文件读取完毕,返回读取字节的长度
                    while (inputStream.available() > 0)
                    {
                        numBytes = inputStream.read(readBuffer);
                        for(int k = 0; k < numBytes; k++)
                            list.add(readBuffer[k]);

                        int SPO2_data;
                        //   int[] ECG_data = new int[64];
                        int RESP_data;
                        // int[] RESP_data = new int[128];
                        int fiv_I,fiv_II,fiv_III;



                        if(numBytes > 0)
                        {
                            //  msgQueue.add("数据长度---"+numBytes);

                        //    toJson json = new toJson();
                        //    json.tojson(readBuffer);
                            while (list.size() != 0) {
                                //判断数据的完整性
                                while ((byte)list.get(0) != -1)
                                {
                                    list.remove(0);
                                    if (list.size() == 0) break;  //debug后增加完善
                                }
                                if (list.size() == 0 | list.size() == 1) break;  //debug后增加完善
                                switch ((byte) (list.get(2)))
                                {

                                    case 0x02:
                                        PARA_ECG ecg = new PARA_ECG(list);
                                        Delete(1);
                                        m_mwdp.hr = ecg.getHR();
                                        fiv_I = ecg.getfiv_I();
                                        fiv_II = ecg.getfiv_II();
                                        m_mwdp.setECGwavedata(fiv_I,fiv_II);
                                        break;

                                    case 0x03:
                                        PARA_RESP resp = new PARA_RESP(list);
                                        Delete(2);
                                        m_mwdp.rr = resp.getRR();
                                        RESP_data = resp.getRR_Wave();
                                        m_mwdp.setRESPwavedata(RESP_data);
                                        break;
                                    case 0x04:
                                        PARA_TEMP temp = new PARA_TEMP(list);
                                        Delete(3);
                                        m_mwdp.bt1 = temp.getBT1();
                                        m_mwdp.bt2 = temp.getBT2();
                                        break;

                                    case 0x05:
                                        PARA_SPO2 spo2 = new PARA_SPO2(list);
                                        Delete(4);
                                        m_mwdp.pr = spo2.getPR();
                                        m_mwdp.spo2 = spo2.getSPO2();
                                        SPO2_data = spo2.getSpo2wavedata();
                                        m_mwdp.setSPO2wavedata(SPO2_data);
                                        break;
                                    case 0x06:
                                        PARA_NIBP nibp = new PARA_NIBP(list);
                                        Delete(5);
                                        m_mwdp.sbp = nibp.getSBP();
                                        m_mwdp.map = nibp.getMAP();
                                        m_mwdp.dbp = nibp.getDBP();
                                        m_mwdp.pre = nibp.getPRE();
                                        break;
                                    default:
                                        Delete(6);
                                        break;
                                }
                            }


                            //msgQueue.add(new Date()+"真实收到的数据为：---"+new String(readBuffer));
                            readBuffer = new byte[1024];// 重新构造缓冲对象，否则有可能会影响接下来接收的数据
                        }else
                        {
                            msgQueue.add("没有读到数据");
                        }
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void Delete(int sign)
    {
        switch (sign)
        {
            //ECG
            case 1:
                switch (ConstantValue.ecg_flag)
                {
                    case 1:
                        try {
                            list.subList(0, 13).clear();
                        } catch (Exception e) {
                            System.out.println("--------ECG case1 错误");
                        }
                        break;
                    case 2:
                        try {
                            list.subList(0, 7).clear();
                        } catch (Exception e) {
                            System.out.println("--------ECG case2 错误");
                        }
                        break;
                    case 4:
                        try {
                            list.subList(0, 21).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------ECG case4 错误");
                            //e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            list.subList(0,((byte)list.get(1)+2)).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------ECG case5 错误");
                            //  e.printStackTrace();
                        }
                        break;
                }
                break;
            //RESP
            case 2:
                switch (ConstantValue.resp_flag)
                {
                    case 1:
                        try {
                            list.subList(0, 7).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------RESP case1 错误");
                        }
                        break;
                    case 2:
                        try {
                            list.subList(0, 7).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------RESP case2 错误");
                        }
                        break;
                    case 3:
                        try {
                            list.subList(0,((byte)list.get(1)+2)).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------RESP case3 错误");
                        }
                        break;
                }
                break;
            //TEMP
            case 3:
                switch (ConstantValue.temp_flag)
                {
                    case 1:
                        list.subList(0,9).clear();
                        break;
                    case 2:
                        list.subList(0,(byte)list.get(1)+2).clear();
                        break;
                }
                break;
            //SPO2
            case 4:
                switch (ConstantValue.spo2_flag)
                {
                    case 1:
                        //    list.removeRange(0,11);
                        try {
                            list.subList(0, 11).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------SPO2 case1 错误");
                        }
                        break;
                    case 2:
                        //    list.removeRange(0,7);
                        try {
                            list.subList(0, 7).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------SPO2 case2 错误");
                        }
                        break;
                    case 3:
                        try {
                            list.subList(0,((byte)list.get(1)+2)).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------SPO2 case3 错误");
                        }
                        break;
                }
                break;
            //NIBP
            case 5:
                switch (ConstantValue.nibp_flag)
                {
                    case 1:
                        list.subList(0, 9).clear();
                        break;
                    case 2:
                        list.subList(0, 14).clear();
                        break;
                    case 3:
                        list.subList(0, 7).clear();
                        break;
                    case 4:
                        try {
                            //debug后增加完善
                            if (list.size() < ((byte)list.get(1)+2))
                            {
                                list.clear();
                                break;
                            }
                            list.subList(0,((byte)list.get(1)+2)).clear();
                        } catch (Exception e) {
                            list.clear();
                            System.out.println("--------NIBP错误");
                            // e.printStackTrace();
                        }
                        break;
                }
                break;
            //不完善的数据
            case 6:

               /* if (list.size() < ((byte)list.get(1)+2))
                {
                    list.clear();
                    break;
                }
                list.subList(0,((byte)list.get(1)+2)).clear();*/
                //debug后增加完善
                try {
                    if (list.size() < ((byte)list.get(1)+2))
                    {
                        list.clear();
                        break;
                    }
                    list.subList(0,((byte)list.get(1)+2)).clear();
                } catch (Exception e) {
                    list.clear();
                    System.out.println("不完善数据错误");
                    //   e.printStackTrace();
                }
                break;
        }
    }

    /**
     *  往串口发送上行应答数据
     *  data[2] 模块号 data[3] 命令 ，两者是接收到的帧中的帧数据
     * if判断所需应答帧的命令是否对应，则发送相应的上行应答帧
     */
    public void Return_Upframe(byte[] data)
    {
            switch (data[2]) //模块号
            {
//NIBP 模块的上行应答
                case 0x06:
                    if(data[3] == 0x32) //命令
                    {
                        try //血压测量参数帧的应答
                        {
                            String str = " FF 04 06 38 32 73"; //
                            byte[] bytes = this.hexStrToBinaryStr(str);
                            outputStream.write(bytes);
                            System.out.println("血压测量上行已应答");
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (data[3] == 0x35)
                    {
                        try //压力校准结果帧的应答
                        {
                            String str = " FF 04 06 38 35 76"; //
                            byte[] bytes = this.hexStrToBinaryStr(str);
                            outputStream.write(bytes);
                            System.out.println("压力校准结果上行已应答");
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                /**
                * ECG模块的上行应答
                */
                case 0x42:
                    if (data[3] == 0x37)
                    {
                        try //3导联ST段模板帧的应答
                        {
                            String str = " FF 04 05 44 37 83"; //
                            byte[] bytes = this.hexStrToBinaryStr(str);
                            outputStream.write(bytes);
                            System.out.println("3导联ST段模板帧上行已应答");
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (data[3] == 0x38)
                    {
                        try //5导联ST段模板帧的应答
                        {
                            String str = " FF 04 05 44 38 84"; //
                            byte[] bytes = this.hexStrToBinaryStr(str);
                            outputStream.write(bytes);
                            System.out.println("5导联ST段模板帧上行已应答");
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if (data[3] == 0x39)
                    {
                        try //12导联ST段模板帧的应答
                        {
                            String str = " FF 04 05 44 39 85"; //
                            byte[] bytes = this.hexStrToBinaryStr(str);
                            outputStream.write(bytes);
                            System.out.println("12导联ST段模板帧上行已应答");
                        }catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }

                default:break;
            }
    }


    /**
     * 将十六进制的字符串转换出字节数组
     * @param hexString
     * @return
     */
    public static byte[] hexStrToBinaryStr(String hexString)
    {
        hexString = hexString.replaceAll(" ","");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len/2];
        while (index < len)
        {
            String sub = hexString.substring(index,index + 2);
            bytes[index/2] = (byte)Integer.parseInt(sub,16);
            index += 2;
        }
        return bytes;
    }


    public void run() {
        try {
            while (true) {// 如果堵塞队列中存在数据就将其输出
                if (msgQueue.size() > 0) {
                    System.out.println(msgQueue.take());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向串口发送下行帧数据
     */

    public void Send_DownFrame()
    {
        int i = this.StartComport();
        if (i == 1) {
        //        this.start(); // 启动线程来处理收到的数据   //目前可行可不行
            try //向发送命令
            {
                String str = "FF 04 02 01 01 07 FF 04 02 04 02 0B FF 04 02 12 01 18";//  //FF 04 46 01 00 4A FF 04 46 02 01 4C  // //
                byte[] bytes = this.hexStrToBinaryStr(str);
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Serial_Port(MainMedicalWaveFrame _mwdp)
    {
        this.Send_DownFrame();
        this.m_mwdp = _mwdp;
    }
}
