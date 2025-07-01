package finalmission.reservation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubwayLineOverlapChecker {

    // 2호선의 역 목록
    // 순환선이므로 마지막 역과 첫 번째 역이 연결되어 있음
    private static final List<String> line2 = Arrays.asList(
            "시청", "을지로입구", "을지로3가", "을지로4가", "동대문역사문화공원", "신당", "상왕십리", "왕십리", "한양대",
            "뚝섬", "성수", "건대입구", "구의", "강변", "잠실나루", "잠실", "잠실새내", "종합운동장", "삼성", "선릉",
            "역삼", "강남", "교대", "서초", "방배", "사당", "낙성대", "서울대입구", "봉천", "신림", "신대방",
            "구로디지털단지", "대림", "신도림", "문래", "영등포구청", "당산", "합정", "홍대입구", "신촌", "이대",
            "아현", "충정로"
    );

    public static boolean isOverlap(
            String departStation1, String arriveStation1,
            String departStation2, String arriveStation2
    ) {
        Set<String> path1 = getShortestPathSet(departStation1, arriveStation1);
        Set<String> path2 = getShortestPathSet(departStation2, arriveStation2);

        for (String station : path1) {
            if (path2.contains(station)) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> getShortestPathSet(String start, String end) {
        int startIdx = line2.indexOf(start);
        int endIdx = line2.indexOf(end);
        int size = line2.size();

        if (startIdx == -1 || endIdx == -1) {
            throw new IllegalArgumentException("존재하지 않는 역입니다.");
        }

        List<String> clockwise = new ArrayList<>();
        for (int i = startIdx; i != endIdx; i = (i + 1) % size) {
            clockwise.add(line2.get(i));
        }
        clockwise.add(line2.get(endIdx));

        List<String> counterClockwise = new ArrayList<>();
        for (int i = startIdx; i != endIdx; i = (i - 1 + size) % size) {
            counterClockwise.add(line2.get(i));
        }
        counterClockwise.add(line2.get(endIdx));

        return new HashSet<>(
                clockwise.size() <= counterClockwise.size() ? clockwise : counterClockwise
        );
    }
}
