package com.junwen.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class YunXiaoService {

    static String cookie = "cna=SAXYGYANx0ECAXFYYK0dTIn7; _samesite_flag_=true; cookie2=1e3fad8ee221f50e3ba7a05b7fc4397b; t=ee82c2b19738cce11ba26fa1ede5907f; _tb_token_=e3b67aeee90ee; _hvn_login=6; csg=e8552cf0; login_aliyunid_ticket=sgdZq64XXWQgyKFeuf0vpmV*s*CT58JlM_1t$w3W1$8Xk6jDy9Nhtdi8HJAZKbfbOaLBdOkLKpOISwHHi_Qcpof_BNTwUhTOoNC1ZBeeMfKJzxdnb95hYssNIZor6q7SCxRtgmGCbifG2Cd4ZWazmBIH0; login_aliyunid_csrf=_csrf_tk_1626155447595114; login_aliyunid_pk=1540855447594154; hssid=1PkscXDilFo5FkCrtwK8tVQ1; hsite=6; aliyun_country=CN; aliyun_site=CN; aliyun_lang=zh; login_aliyunid=aliyun246585****; currentRegionId=cn-hangzhou; aliyun_choice=CN; teambition_lang=zh; LOGIN_ALIYUN_PK_FOR_TB=1540855447594154; TEAMBITION_SESSIONID=eyJ1aWQiOiI2MmFjMjA1Y2FhOTg3NmMyZTA4OTg4ZmIiLCJhdXRoVXBkYXRlZCI6MTY1NTQ0NzY0NDc0NiwidXNlciI6eyJfaWQiOiI2MmFjMjA1Y2FhOTg3NmMyZTA4OTg4ZmIiLCJuYW1lIjoiYWxpeXVuMjQ2NTg1MjMxMSIsImVtYWlsIjoiYWNjb3VudHNfNjJhYzIwNWNhYTk4NzZjMmUwODk4OGYzQG1haWwudGVhbWJpdGlvbi5jb20iLCJhdmF0YXJVcmwiOiJodHRwczovL3Rjcy1kZXZvcHMuYWxpeXVuY3MuY29tL3RodW1ibmFpbC8xMTJpOTE2MTUzMGJkMWRmYmE5NmMwYzc0OTM0M2EyMTZlN2Ivdy8xMDAvaC8xMDAiLCJyZWdpb24iOiIiLCJsYW5nIjoiIiwiaXNSb2JvdCI6ZmFsc2UsIm9wZW5JZCI6IiIsInBob25lRm9yTG9naW4iOiIiLCJjcmVhdGVkIjoiMjAyMi0wNi0xN1QwNjozNDowNC43NDZaIn0sImxvZ2luRnJvbSI6IiJ9; TEAMBITION_SESSIONID.sig=n3HAQ-LLF5Hc2PXwbaqHfwng5p4; isg=BN_f4ox0wTodVsYGH9aheAUrbjNpRDPmDKKjfHEsew7VAP-CeRTDNl3RwpB-mAte; l=eBg7I33ggmQBkHypBOfwourza77OSIRAguPzaNbMiOCPskCB5g6FW6jd9xY6C3GVh6WyR3uKagA6BeYBqQd-nxvtGwBLE8Dmn; tfstk=cMNFBb1Rg6CUQSc5HXGPR01dahadaWU3rCujKil3P3bASgkn0sqy2VmOMVuW73Dh.";

    @Autowired
    private RestTemplate restTemplate;

    public static Date addDate(Date date,int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public String getWeekOfDate(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public String getData(String rangeBegin,String rangeEnd) {
        HttpHeaders headers = new HttpHeaders();
        //cookie
        headers.add(HttpHeaders.COOKIE, cookie);
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        String content = "{\"filter\":{\"rangeBegin\":\"" + rangeBegin + "\",\"rangeEnd\":\"" + rangeEnd + "\",\"executorId\":[\"609de4e2aa6381038ed2f993\"],\"isArchived\":false,\"isSubtask\":true},\"name\":\"工时统计\",\"ignoreSaved\":true}";
        HttpEntity requestEntity = new HttpEntity(content, headers);
        JSONObject jsonObject = restTemplate.postForObject("https://devops.aliyun.com/api/boards/tproject/tdr/report/60fe85d1b5bb6000015fbec6", requestEntity, JSONObject.class);
        //System.out.println(jsonObject);
        if (!jsonObject.containsKey("graphData")) {
            return "not graphData";
        }
        JSONArray graphData = jsonObject.getJSONArray("graphData");
        JSONArray rows = graphData.getJSONObject(0).getJSONArray("rows");
        if(null != rows && rows.size() > 0){
            return rows.getJSONArray(0).getString(1);
        }else {
            return "0";
        }
        /*for (int i = 0; i < graphData.size(); i++) {
            if (graphData.getJSONObject(i).getString("name").equals("detail")) {
                JSONArray rows = graphData.getJSONObject(i).getJSONArray("rows");
                if (rows.size() <= 0) {
                    return "0";
                }
                String result = "";
                for (int j = 0; j < rows.size(); j++) {
                    result += rows.getJSONArray(j).getString(6);
                    if (j !=rows.size() - 1 ){
                        result+="\t,";
                    }
                }
                return result;
            }
        }
        return "not detail";*/
    }

    public void getDateData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

        Date end = new Date();
        for (Date date = format.parse("2022-05-16"); date.compareTo(end) <= 0; date = addDate(date,1)) {
            Date yesterday = addDate(date, -1);
            String rangeBegin = format.format(yesterday).concat("T16:00:00.000Z");
            String rangeEnd = format.format(date).concat("T15:59:59.999Z");
            String data = getData(rangeBegin,rangeEnd);
            System.out.println(format.format(date) + "\t" + getWeekOfDate(date) + "\t" + data);
        }
    }

}
