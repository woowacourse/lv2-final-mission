package finalmission.infrastructure;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import java.util.List;

public class HolidayResponse {

    private Response response;

    public HolidayResponse() {
    }

    public HolidayResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {

        private Header header;
        private Body body;

        public Response() {
        }

        public Response(Header header, Body body) {
            this.header = header;
            this.body = body;
        }

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

        public static class Header {
            private String resultCode;
            private String resultMsg;

            public Header() {
            }

            public Header(String resultCode, String resultMsg) {
                this.resultCode = resultCode;
                this.resultMsg = resultMsg;
            }

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

            public Body() {
            }

            public Body(Items items, int numOfRows, int pageNo, int totalCount) {
                this.items = items;
                this.numOfRows = numOfRows;
                this.pageNo = pageNo;
                this.totalCount = totalCount;
            }

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

            public static class Items {
                private List<Item> item;

                public Items() {
                }

                public Items(List<Item> item) {
                    this.item = item;
                }

                public List<Item> getItem() {
                    return item;
                }

                public void setItem(List<Item> item) {
                    this.item = item;
                }

                public static class Item {
                    private String dateKind;
                    private String dateName;
                    private String isHoliday;

                    @JsonDeserialize(using = LocdateDeserializer.class)
                    private LocalDate locdate;

                    private int seq;

                    public Item() {
                    }

                    public Item(String dateKind, String dateName, String isHoliday, LocalDate locdate, int seq) {
                        this.dateKind = dateKind;
                        this.dateName = dateName;
                        this.isHoliday = isHoliday;
                        this.locdate = locdate;
                        this.seq = seq;
                    }

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

                    public LocalDate getLocdate() {
                        return locdate;
                    }

                    public void setLocdate(LocalDate locdate) {
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
        }
    }
}
