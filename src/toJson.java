import org.json.JSONObject;

import java.util.ArrayList;

public class toJson {

    public void tojson(ArrayList msg) //throws Exception  //String IP 主机IP
    {
        JSONObject jsonObject = new JSONObject();

     //   PARA_ECG para_ecg = new PARA_ECG(msg);
        PARA_NIBP para_nibp = new PARA_NIBP(msg);

        if (ConstantValue.flag == 1)
        {
            //床号
            jsonObject.put("NIBP_FrameHeader",para_nibp.getNIBP_FrameHeader());
            jsonObject.put("PRE",para_nibp.getPRE());
            jsonObject.put("SBP",para_nibp.getSBP());
            jsonObject.put("MAP",para_nibp.getMAP());
            jsonObject.put("DBP",para_nibp.getDBP());




        }
     //   System.out.println("JSON:" + jsonObject);
    }

}
