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

    static String cookie = "cna=TAfYGd2NAn8CAXFYYK0xRIWJ; aliyun_lang=zh; _bl_uid=sbkjRuwp2bO0U37yy30U20Cc7UR2; session-lead-visited/609de4e2aa6381038ed2f993=false; session-cloud-search-visited/609de4e2aa6381038ed2f993=false; aliyun_choice=CN; currentRegionId=cn-hangzhou; referral=%7B%22domain%22%3A%22devops.aliyun.com%22%2C%22path%22%3A%22%2F%22%2C%22query%22%3A%22%22%2C%22hash%22%3A%22%22%7D; changelog_date=1624579200000; channel=McC%2B6mWkPZ9LJtmejlaFwtRllcPAkBWJnWStG5Qa5Kk%3D; _ga=GA1.2.637785531.1642152517; _samesite_flag_=true; _hvn_login=6; XSRF-TOKEN=9a125593-9e08-4e07-a8dc-9591d7acc6cf; ak_user_locale=zh_CN; login_aliyunid=\"182****3488\"; login_aliyunid_ticket=ufyvpeV*0*Cm58slMT1tJw3_k$$LGC4a9VSnfUeRUxnztaCOe_21LQZnBB9Ybzvgxpwd0of_BNpwU_TOTNChZBoeM1KJexdfb9zhYnsN5Zos6qISCrRt7mGxbigG2Cd4fWaCmBZHIzsgdZq64XXWQgFK0; login_aliyunid_csrf=_csrf_tk_1303343247636266; login_aliyunid_pk=1023395707489464; hssid=10KaBItWJtP7iqty7qR2UXw1; hsite=6; aliyun_country=CN; aliyun_site=CN; LOGIN_ALIYUN_PK_FOR_TB=1023395707489464; TEAMBITION_SESSIONID=eyJ1aWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJhdXRoVXBkYXRlZCI6MTY0MzI0NzYzMzUxOSwidXNlciI6eyJfaWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJuYW1lIjoi6Ze15L+K5paHIiwiZW1haWwiOiJhY2NvdW50c182MDlkZTRlMjRhNDI1NDAwMmQ3YWRmZDlAbWFpbC50ZWFtYml0aW9uLmNvbSIsImF2YXRhclVybCI6Imh0dHBzOi8vdGNzLWRldm9wcy5hbGl5dW5jcy5jb20vdGh1bWJuYWlsLzExMmFmMjI1NTU1MjRhZTE5OGRhNjg0NDg1MzcxOGEzMjM1Yy93LzEwMC9oLzEwMCIsInJlZ2lvbiI6InVzIiwibGFuZyI6IiIsImlzUm9ib3QiOmZhbHNlLCJvcGVuSWQiOiIiLCJwaG9uZUZvckxvZ2luIjoiIiwiY3JlYXRlZCI6IjIwMjEtMDUtMTRUMDI6NDg6MDIuNTE0WiJ9LCJsb2dpbkZyb20iOiIifQ==; TEAMBITION_SESSIONID.sig=ZHZsnjXMRQ90rRC95bGi0z5wT-o; isg=BCwse3q4YQ56b3WSLxKIi7b4_Qpe5dCPMzMcrYZtOFd6kcybrvWgHyIgtVkpGQjn; l=eBMnuGblgsJIZc1pBOfwourza77OSIRAguPzaNbMiOCPO_1B54tRW6Lj1OY6C3GVh6yeR3WK4ADXBeYBqQd-nxv96aiE7Vkmn; tfstk=cpedBw6G_NbHGpoT0WCgNTmoGmQcZ47-5HgkeGRjdAIcYqpRiKy0HEElO0gqJGC..; mp_eSpCz4lYpMYgtuhdH0F6Wgtt_mixpanel=%7B%22distinct_id%22%3A%20%2217c2af8453e7-09eea45d39dd9d-b7a1a38-1fa400-17c2af8453fe5c%22%2C%22userKey%22%3A%20%22609de4e2aa6381038ed2f993%22%2C%22%24os_version%22%3A%20%22Windows%20NT%2010.0%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fdevops.aliyun.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22devops.aliyun.com%22%7D; _gid=GA1.2.1582496685.1643250408; mp_tbpanel__c=1; teambition_lang=zh; TB_ACCESS_TOKEN=eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhcHAiOiI1ZTczMGNmMjIwMWQyMGQ3MTc5ODhlNDYiLCJhdWQiOiIiLCJleHAiOjE2NDM1MjI4OTQsImlhdCI6MTY0MzI2MzY5NCwiaXNzIjoidHdzIiwianRpIjoibktzUDVwd1RmSWVtdHI4OXdfZHlpcjZxeWV6R1FsMGNQY3lnMTUxYk96cz0iLCJyZW5ld2VkIjoxNjQzMjQ3NjMzNTE5LCJzY3AiOlsiYXBwX3VzZXIiXSwic3ViIjoiNjA5ZGU0ZTJhYTYzODEwMzhlZDJmOTkzIiwidHlwIjoiYWNjZXNzX3Rva2VuIn0.QT9Co31aE1YJ1mW_Y5t2idgALSFvhoMF62XPxBSv-YUSOKaq-ZBAi3VkMwnW-SBez3pT968QZUZGkGqCWr3wCQ";

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
        for (int i = 0; i < graphData.size(); i++) {
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
        return "not detail";
    }

    public void getDateData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

        Date end = new Date();
        for (Date date = format.parse("2022-01-24"); date.compareTo(end) <= 0; date = addDate(date,1)) {
            Date yesterday = addDate(date, -1);
            String rangeBegin = format.format(yesterday).concat("T16:00:00.000Z");
            String rangeEnd = format.format(date).concat("T15:59:59.999Z");
            String data = getData(rangeBegin,rangeEnd);
            System.out.println(format.format(date) + "\t" + getWeekOfDate(date) + "\t" + data);
        }
    }

}
