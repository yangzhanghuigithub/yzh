package com.learn.yzh.common.message.voucher;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:GiveVoucheMessageWarper
 *封装红包消息
 * @author licho
 * @create 2018-11-22 12:49
 */
public class GiveVoucherMessageWrapper {
    private String transactionID;//各类发券业务的主键ID
    private int giveVoucherType;//送券类型
    private boolean isSuccess=false;//送券成功标志
    private List<String> successList=new ArrayList<>();//发送成功的会员列表集合
    private List<String> applyIDs;//发送的批次ID
    private Map<String,Object> message=new HashMap<>();//消息内容
    private JPushMessage jPushMessage;//推送消息
    private SmsMessage smsMessage;//推送短信

    public GiveVoucherMessageWrapper(){

    }



    public JPushMessage createJPushMessage(String title, String content){
        this.jPushMessage=new JPushMessage(title,content);
        return this.jPushMessage;
    }

    public SmsMessage createSmsMessage(String title,String content){
        this.smsMessage=new SmsMessage(title,content);
        return this.smsMessage;
    }
    public List<String> getApplyIDs() {
        return applyIDs;
    }

    public void setApplyIDs(List<String> applyIDs) {
        this.applyIDs = applyIDs;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public GiveVoucherMessageWrapper setSuccessCyclic(boolean success) {
        isSuccess = success;
        return  this;
    }

    public List<String> getSuccessList() {
        return successList;
    }

    public GiveVoucherMessageWrapper setSuccessListCyclic(List<String> successList) {
        this.successList = successList;
        return this;
    }

    public GiveVoucherMessageWrapper(String transactionID, int giveVoucherType){
        this.transactionID=transactionID;
        this.giveVoucherType=giveVoucherType;
    }

    public static GiveVoucherMessageWrapper getMessage(String transactionID,int giveVoucherType){
        GiveVoucherMessageWrapper wrapper=new GiveVoucherMessageWrapper(transactionID,giveVoucherType);
        return wrapper;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getGiveVoucherType() {
        return giveVoucherType;
    }

    public void setGiveVoucherType(int giveVoucherType) {
        this.giveVoucherType = giveVoucherType;
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    public void setMessage(Map<String, Object> message) {
        this.message = message;
    }

    public GiveVoucherMessageWrapper putParamCyclic(String key,Object val){
        this.message.put(key,val);
        return this;
    }

    public GiveVoucherMessageWrapper putParamsCyclic(Map<String,Object> values){
        this.message.putAll(values);
        return this;
    }

    public Object getParam(String key){
        return this.message.get(key);
    }

    public JPushMessage getjPushMessage() {
        return jPushMessage;
    }

    public GiveVoucherMessageWrapper setjPushMessageCyclic(JPushMessage jPushMessage) {
        this.jPushMessage = jPushMessage;
        return this;
    }

    public SmsMessage getSmsMessage() {
        return smsMessage;
    }

    public GiveVoucherMessageWrapper setSmsMessageCyclic(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
        return this;
    }

    public void setjPushMessage(JPushMessage jPushMessage) {
        this.jPushMessage = jPushMessage;
    }

    public void setSmsMessage(SmsMessage smsMessage) {
        this.smsMessage = smsMessage;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public void setSuccessList(List<String> successList) {
        this.successList = successList;
    }

    public class JPushMessage{
        private String companyCode;
        private String cinemaCode;
        private String groupId;
        private String cinemaName;
        private String noticeType;
        private String title;
        private String content;
        public JPushMessage(){

        }
        public JPushMessage(String title,String content){
            this.title=title;
            this.content=content;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public void setCinemaCode(String cinemaCode) {
            this.cinemaCode = cinemaCode;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public void setNoticeType(String noticeType) {
            this.noticeType = noticeType;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public JPushMessage setCompanyCodeCyclic(String companyCode) {
            this.companyCode = companyCode;
            return this;
        }

        public String getCinemaCode() {
            return cinemaCode;
        }

        public JPushMessage setCinemaCodeCyclic(String cinemaCode) {
            this.cinemaCode = cinemaCode;
            return this;
        }

        public String getGroupId() {
            return groupId==null ?(transactionID+noticeType==null ? "":noticeType) :groupId;
        }

        public JPushMessage setGroupIdCyclic(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public JPushMessage setCinemaNameCyclic(String cinemaName) {
            this.cinemaName = cinemaName;
            return this;
        }

        public String getNoticeType() {
            return noticeType;
        }

        public JPushMessage setNoticeTypeCyclic(String noticeType) {
            this.noticeType = noticeType;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public JPushMessage setTitleCyclic(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public JPushMessage setContentCyclic(String content) {
            this.content = content;
            return this;
        }

        public List<String> getMemberList(){

            return successList;
        }
        public String getTransactionID() {
            return transactionID;
        }
    }

    public class SmsMessage{
        private String cinemaCode;
        private int mesType=-1;//短信类型
        private String title;
        private String content;
        private boolean isMarketing;
        private String countNumber;
        private Map<String,String> changedParam=new HashMap<>();
        public SmsMessage(){

        }
        public SmsMessage(String title,String content){
            this.title=title;
            this.content=content;
            changedParam.put("content",content);
        }

        public void setCinemaCode(String cinemaCode) {
            this.cinemaCode = cinemaCode;
        }

        public void setMesType(int mesType) {
            this.mesType = mesType;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setMarketing(boolean marketing) {
            isMarketing = marketing;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
        }

        public Map<String, String> getChangedParam() {
            return changedParam;
        }

        public void setChangedParam(Map<String, String> changedParam) {
            this.changedParam = changedParam;
        }

        public SmsMessage putChangedParam(String key,String value){
            changedParam.put(key,value);
            return this;
        }

        public String getCinemaCode() {
            return cinemaCode;
        }

        public SmsMessage setCinemaCodeCyclic(String cinemaCode) {
            this.cinemaCode = cinemaCode;
            return this;
        }

        public int getMesType() {
            return mesType;
        }

        public SmsMessage setMesTypeCyclic(int mesType) {
            this.mesType = mesType;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public SmsMessage setTitleCyclic(String title) {
            this.title = title;
            return this;
        }

        public String getContent() {
            return content;
        }

        public SmsMessage setContentCyclic(String content) {
            this.content = content;
            return this;
        }

        public boolean isMarketing() {
            return isMarketing;
        }

        public SmsMessage setMarketingCyclic(boolean marketing) {
            isMarketing = marketing;
            return this;
        }

        public String getCountNumber() {
            return countNumber==null ? (successList!=null? String.valueOf(successList.size()) : null) : countNumber;
        }

        public SmsMessage setCountNumberCyclic(String countNumber) {
            this.countNumber = countNumber;
            return this;
        }
        public List<String> getMemberList(){
            return successList;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public boolean assertParamValid(){
            return cinemaCode!=null&&mesType!=-1&&title!=null&&title!=null&&changedParam!=null;
        }
    }


}
