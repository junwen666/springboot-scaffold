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

    @Autowired
    private RestTemplate restTemplate;

    public static Date addDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
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

    public String getData(String date) {
        HttpHeaders headers = new HttpHeaders();
        //cookie
        headers.add(HttpHeaders.COOKIE, "cna=TAfYGd2NAn8CAXFYYK0xRIWJ; aliyun_lang=zh; _bl_uid=sbkjRuwp2bO0U37yy30U20Cc7UR2; changelog_date=1624579200000; TB_NAVIGATION_DOCK_GUIDE_VISIBLE%2F609de4e2aa6381038ed2f993=false; session-lead-visited/609de4e2aa6381038ed2f993=false; _ga=GA1.2.1020035479.1632808486; UM_distinctid=17c2f0ef5aa386-0677f9d791f9fb-b7a1a38-1fa400-17c2f0ef5abcad; announcement_date=1624579200000; _alicloud_ab_trace_id=54b72270-319c-11ec-bd1a-fbe88f38a242; referral=%7B%22domain%22%3A%22devops.aliyun.com%22%2C%22path%22%3A%22%2F%22%2C%22query%22%3A%22%22%2C%22hash%22%3A%22%22%7D; session-cloud-search-visited/609de4e2aa6381038ed2f993=false; mp_eSpCz4lYpMYgtuhdH0F6Wgtt_mixpanel=%7B%22distinct_id%22%3A%20%2217c2af8453e7-09eea45d39dd9d-b7a1a38-1fa400-17c2af8453fe5c%22%2C%22userKey%22%3A%20%22609de4e2aa6381038ed2f993%22%2C%22%24os_version%22%3A%20%22Windows%20NT%2010.0%22%2C%22%24initial_referrer%22%3A%20%22https%3A%2F%2Fdevops.aliyun.com%2F%22%2C%22%24initial_referring_domain%22%3A%20%22devops.aliyun.com%22%7D; mp_tbpanel__c=2; teambition_lang=zh; _samesite_flag_=true; _hvn_login=6; XSRF-TOKEN=beff60f4-f555-40e0-81ac-1eb774f61988; TB_ACCESS_TOKEN=eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhcHAiOiI1ZTczMGNmMjIwMWQyMGQ3MTc5ODhlNDYiLCJhdWQiOiIiLCJleHAiOjE2NDAzMTA1MjYsImlhdCI6MTY0MDA1MTMyNiwiaXNzIjoidHdzIiwianRpIjoibHNVOEU0VUV2amdmaXNodmlyOTBhVk15ZmtNVUZubWdNU05LNXl4NWp1QT0iLCJyZW5ld2VkIjoxNjM5NzAyMTM5Mzg5LCJzY3AiOlsiYXBwX3VzZXIiXSwic3ViIjoiNjA5ZGU0ZTJhYTYzODEwMzhlZDJmOTkzIiwidHlwIjoiYWNjZXNzX3Rva2VuIn0.k-LMd272_fcfBYRsereWPMlCweOSGK3Br1t2opMBfDeD2HUEvkTon0j39dtg6lAk8RmLsHivM7hIVt8v-ESDCg; login_aliyunid=\"182****3488\"; login_aliyunid_ticket=zmBdHI6sgXZqg4XFWQfyKpeu*0vCmV8s*MT5tJl3_1$$wGCka9LSn4UeVUxfztRCOniLfe4wNqxur_ktsatDn19f_INpoU_BOTwChTBoNM1ZJeedfK9zxYnbN5hossqIZCr6t7SGxRigm2Cb4fGaCdWZ0; login_aliyunid_csrf=_csrf_tk_1302840051361006; login_aliyunid_pk=1023395707489464; hssid=1RuyiAcKPBce5OqQX3Pr4-Q1; hsite=6; aliyun_country=CN; aliyun_site=CN; LOGIN_ALIYUN_PK_FOR_TB=1023395707489464; TEAMBITION_SESSIONID=eyJ1aWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJhdXRoVXBkYXRlZCI6MTY0MDA1MTM1NjI2MSwidXNlciI6eyJfaWQiOiI2MDlkZTRlMmFhNjM4MTAzOGVkMmY5OTMiLCJuYW1lIjoi6Ze15L+K5paHIiwiZW1haWwiOiJhY2NvdW50c182MDlkZTRlMjRhNDI1NDAwMmQ3YWRmZDlAbWFpbC50ZWFtYml0aW9uLmNvbSIsImF2YXRhclVybCI6Imh0dHBzOi8vdGNzLWRldm9wcy5hbGl5dW5jcy5jb20vdGh1bWJuYWlsLzExMmFmMjI1NTU1MjRhZTE5OGRhNjg0NDg1MzcxOGEzMjM1Yy93LzEwMC9oLzEwMCIsInJlZ2lvbiI6InVzIiwibGFuZyI6IiIsImlzUm9ib3QiOmZhbHNlLCJvcGVuSWQiOiIiLCJwaG9uZUZvckxvZ2luIjoiIiwiY3JlYXRlZCI6IjIwMjEtMDUtMTRUMDI6NDg6MDIuNTE0WiJ9LCJsb2dpbkZyb20iOiIifQ==; TEAMBITION_SESSIONID.sig=0aE5rX6T3bqbctTTCE6KXlTkjU0; isg=BF9fYlwKQteP60Zb-JOLctnJ7rPpxLNmOO50uvGs4I5VgH8C-ZB8twdRRhD-GIve; l=eBMnuGblgsJIZhVZBOfZourza77TbIRAguPzaNbMiOCPOyCB5-ecW6dXoD86CnGVh6qJR35T8LLDBeYBqQd-nxv96aiE7mMmn; tfstk=c38hBdOFxHSQKjjMc2_B6kIw1p0AZ64PnU867EzS-bXAdTTNivra0wnts9DjLu1..");
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        String content = "{\"filter\":{\"rangeBegin\":\"" + date + "\",\"rangeEnd\":\"" + date + "\",\"executorId\":[\"616f71b560ebaf99ec6842fb\"],\"isArchived\":false,\"isSubtask\":true},\"name\":\"工时统计\",\"ignoreSaved\":true}";
        HttpEntity requestEntity = new HttpEntity(content, headers);
        JSONObject jsonObject = restTemplate.postForObject("https://devops.aliyun.com/api/boards/tproject/tdr/report/60fe85d1b5bb6000015fbec6", requestEntity, JSONObject.class);
        //System.out.println(jsonObject);
        if (!jsonObject.containsKey("graphData")) {
            return "not graphData";
        }
        JSONArray graphData = jsonObject.getJSONArray("graphData");
        boolean hasDetail = false;
        for (int i = 0; i < graphData.size(); i++) {
            if (graphData.getJSONObject(i).getString("name").equals("detail")) {
                hasDetail = true;
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
        if (!hasDetail) {
            return "not detail";
        }
        return "null";
    }

    public void getDateData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");

        Date end = format.parse("2021-12-20");
        for (Date date = format.parse("2021-09-26"); date.compareTo(end) <= 0; date = addDate(date)) {
            String data = getData(format.format(date));
            System.out.println(format.format(date) + "\t" + getWeekOfDate(date) + "\t" + data);
        }
    }

}
