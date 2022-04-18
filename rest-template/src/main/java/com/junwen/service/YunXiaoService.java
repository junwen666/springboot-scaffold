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

    static String cookie = "cna=TAfYGd2NAn8CAXFYYK0xRIWJ; aliyun_lang=zh; session-lead-visited/609de4e2aa6381038ed2f993=false; session-cloud-search-visited/609de4e2aa6381038ed2f993=false; currentRegionId=cn-hangzhou; t=16240c2e774a195a40fae57c96156b98; referral=%7B%22domain%22%3A%22devops.aliyun.com%22%2C%22path%22%3A%22%2F%22%2C%22query%22%3A%22%22%2C%22hash%22%3A%22%22%7D; changelog_date=1624579200000; login_aliyunid_pk=1023395707489464; TEAMBITION_SESSIONID.sig=9iNia8lF13GBXoJCGtcg-Z-o1rQ; aliyun_choice=CN; teambition_lang=zh; _samesite_flag_=true; cookie2=197049e11d54bdd1af3f0206a6cebf53; _tb_token_=f3e453bb07d73; _hvn_login=6; csg=babdd43d; login_aliyunid_ticket=$w3Ck$9LGn4aeVSxfUtRUOnzsSCS5jrNY6*Pq96zszvEa_snpof_BNTwUhTOoNC1ZBeeMfKJzxdnb95hYssNIZor6q7SCxRtgmGCbifG2Cd4ZWazmBdHI6sgXZqg4XFWQfyKpeu*0vCmV8s*MT5tJl1_0; login_aliyunid_csrf=_csrf_tk_1853850263966970; hssid=1HLaZ-SsFX1HOiIDHyosLkA1; hsite=6; aliyun_country=CN; aliyun_site=CN; login_aliyunid=182****3488; LOGIN_ALIYUN_PK_FOR_TB=1023395707489464; TEAMBITION_SESSIONID=eyJ1aWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJhdXRoVXBkYXRlZCI6MTY0OTk5MDQxMjU5OSwidXNlciI6eyJfaWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJuYW1lIjoi6Ze15L+K5paHIiwiZW1haWwiOiJhY2NvdW50c182MDlkZTRlMjRhNDI1NDAwMmQ3YWRmZDlAbWFpbC50ZWFtYml0aW9uLmNvbSIsImF2YXRhclVybCI6Imh0dHBzOi8vdGNzLWRldm9wcy5hbGl5dW5jcy5jb20vdGh1bWJuYWlsLzExMmFmMjI1NTU1MjRhZTE5OGRhNjg0NDg1MzcxOGEzMjM1Yy93LzEwMC9oLzEwMCIsInJlZ2lvbiI6InVzIiwibGFuZyI6IiIsImlzUm9ib3QiOmZhbHNlLCJvcGVuSWQiOiIiLCJwaG9uZUZvckxvZ2luIjoiIiwiY3JlYXRlZCI6IjIwMjEtMDUtMTRUMDI6NDg6MDIuNTE0WiJ9LCJsb2dpbkZyb20iOiIifQ==; isg=BMnJJPyHzyWQALBdwqHVrMMb2PUjFr1Io87_HWs-RbDvsunEs2bNGLfj8BYE6lWA; l=eBMnuGblgsJIZPiOBOfwourza77OSIRAguPzaNbMiOCPOH1B5w5cW62QQ4Y6C3GVh682R38sRq92BeYBqQd-nxv9JQZjEYDmn; tfstk=c5vVB3DtLxH4qf6s9t6Z5xm9S9-AZMKDj8SCi2v9HzvzvgfcinzOqaHd4GZzSsf..; TB_ACCESS_TOKEN=eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhcHAiOiI1ZTczMGNmMjIwMWQyMGQ3MTc5ODhlNDYiLCJhdWQiOiIiLCJleHAiOjE2NTA1MjMxNjgsImlhdCI6MTY1MDI2Mzk2OCwiaXNzIjoidHdzIiwianRpIjoiakluN3JXNG5PNXk5QVFEMUw3SFVGbDN5c0lJaGNiLURDMFJUZW1YVlB5cz0iLCJyZW5ld2VkIjoxNjQ5OTkwNDEyNTk5LCJzY3AiOlsiYXBwX3VzZXIiXSwic3ViIjoiNjA5ZGU0ZTJhYTYzODEwMzhlZDJmOTkzIiwidHlwIjoiYWNjZXNzX3Rva2VuIn0.htEKA_fBbrxJCWei6GYEsOfmI3SmyjKoRq9Jj_MshIUKvnCaDr4Ln6VFcGPO_5gja5TMpApigvedlDsCcr4GDg; XSRF-TOKEN=bb9b0fb8-9ed1-4b9b-9215-04e067bc30ed";

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
        for (Date date = format.parse("2022-03-01"); date.compareTo(end) <= 0; date = addDate(date,1)) {
            Date yesterday = addDate(date, -1);
            String rangeBegin = format.format(yesterday).concat("T16:00:00.000Z");
            String rangeEnd = format.format(date).concat("T15:59:59.999Z");
            String data = getData(rangeBegin,rangeEnd);
            System.out.println(format.format(date) + "\t" + getWeekOfDate(date) + "\t" + data);
        }
    }

}
