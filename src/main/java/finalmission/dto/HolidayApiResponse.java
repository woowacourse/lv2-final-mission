package finalmission.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class HolidayApiResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {
        private Header header;
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    public static class Header {
        private String resultCode;
        private String resultMsg;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }
    }

    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }

        public int getNumOfRows() {
            return numOfRows;
        }

        public void setNumOfRows(int numOfRows) {
            this.numOfRows = numOfRows;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }

    public static class Items {
        @JsonProperty("item")
        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        private List<HolidayItem> item;

        public List<HolidayItem> getItem() {
            return item;
        }

        public void setItem(List<HolidayItem> item) {
            this.item = item;
        }
    }

    public static class HolidayItem {
        @JsonProperty("dateKind")
        private String dateKind;

        @JsonProperty("dateName")
        private String dateName;

        @JsonProperty("isHoliday")
        private String isHoliday;

        @JsonProperty("locdate")
        private int locdate;

        @JsonProperty("seq")
        private int seq;

        public String getDateKind() {
            return dateKind;
        }

        public void setDateKind(String dateKind) {
            this.dateKind = dateKind;
        }

        public String getDateName() {
            return dateName;
        }

        public void setDateName(String dateName) {
            this.dateName = dateName;
        }

        public String getIsHoliday() {
            return isHoliday;
        }

        public void setIsHoliday(String isHoliday) {
            this.isHoliday = isHoliday;
        }

        public int getLocdate() {
            return locdate;
        }

        public void setLocdate(int locdate) {
            this.locdate = locdate;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }
    }
}
