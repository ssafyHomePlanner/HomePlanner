<template>
  <v-container fill-height fluid class="ma-8">
    <v-row>
      <v-col>
        <v-btn-toggle
          v-model="pathResultType"
          tile
          color="deep-purple accent-3"
          group
        >
          <v-btn value="recommend"> 추천순 </v-btn>

          <v-btn value="time"> 시간순 </v-btn>

          <v-btn value="distance"> 거리순 </v-btn>
        </v-btn-toggle>
        <div id="map"></div>
      </v-col>
      <v-col>
        <v-container class="mt-12">
          <v-row>
            <v-col cols="auto" class="mr-5">
              <v-row justify="start">
                <v-col cols="auto">
                  <v-row class="item-middle-box-text mt-4" justify="start">
                    <div class="path-item-middle-text">
                      아파트 경유지를 추가하세요
                    </div>
                    <v-btn @click="makeLine">라인 생성</v-btn>
                  </v-row>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row> </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-container class="mt-7 mb-12 ml-1" style="width: 100%; height: 280px">
      <v-row class="path-item-middle-text mb-7" justify="start">
        설정된 경유지 목록
      </v-row>
      <v-row justify="start"> </v-row>
    </v-container>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      clickLine: {},
      distanceOverlay: null,
      pathResultType: "recommend",
      sampleLikeLocationList: [
        {
          lon: "126.766986471789",
          lat: "37.4918092437981",
        },
        {
          lon: "126.769215401626",
          lat: "37.4981077694787",
        },
        {
          lon: "126.779310556166",
          lat: "37.4953683630967",
        },
      ],
    };
  },
  methods: {
    showDistance(content, position) {
      console.log("showDistance");
      if (this.distanceOverlay) {
        console.log("distanceOverlay 있음");
        // 커스텀오버레이가 생성된 상태이면

        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        this.distanceOverlay.setPosition(position);
        this.distanceOverlay.setContent(content);
      } else {
        console.log("distanceOverlay 없음");
        // 커스텀 오버레이가 생성되지 않은 상태이면

        // 커스텀 오버레이를 생성하고 지도에 표시합니다
        this.distanceOverlay = new kakao.maps.CustomOverlay({
          content: content, // 커스텀오버레이에 표시할 내용입니다
          position: position, // 커스텀오버레이를 표시할 위치입니다.
          xAnchor: 0,
          yAnchor: 0,
          zIndex: 3,
        });

        // 지도에 표시합니다
        this.distanceOverlay.setMap(this.map);
      }
    },
    getTimeHTML(distance) {
      console.log("getTimeHTML");
      // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
      var walkkTime = (distance / 67) | 0;
      var walkHour = "",
        walkMin = "";

      // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
      if (walkkTime > 60) {
        walkHour =
          '<span class="number">' + Math.floor(walkkTime / 60) + "</span>시간 ";
      }
      walkMin = '<span class="number">' + (walkkTime % 60) + "</span>분";

      // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
      var bycicleTime = (distance / 227) | 0;
      var bycicleHour = "",
        bycicleMin = "";

      // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
      if (bycicleTime > 60) {
        bycicleHour =
          '<span class="number">' +
          Math.floor(bycicleTime / 60) +
          "</span>시간 ";
      }
      bycicleMin = '<span class="number">' + (bycicleTime % 60) + "</span>분";

      // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
      var content = '<ul class="dotOverlay distanceInfo">';
      content += "    <li>";
      content +=
        '        <span class="label">총거리</span><span class="number">' +
        distance +
        "</span>m";
      content += "    </li>";
      content += "    <li>";
      content += '        <span class="label">도보</span>' + walkHour + walkMin;
      content += "    </li>";
      content += "    <li>";
      content +=
        '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
      content += "    </li>";
      content += "</ul>";

      return content;
    },
    makeLine() {
      let linePath = [];

      // 만들어질 경로의 위도/경도를 넣는다.
      this.sampleLikeLocationList.forEach((element) => {
        linePath.push(new kakao.maps.LatLng(element.lat, element.lon));
      });

      let polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표 배열입니다 클릭한 위치를 넣어줍니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: "#db4040", // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });

      // 선 화면에 그리기
      polyline.setMap(this.map);

      var distance = Math.round(polyline.getLength()), // 선의 총 거리를 계산합니다
        content = this.getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다

      console.log("content", content);
      console.log("purposePosition", linePath[linePath.length - 1]);

      // 그려진 선의 거리정보를 지도에 표시합니다
      this.showDistance(content, linePath[linePath.length - 1]);

      //맵에서 이동할 좌표
      let iwPosition = new kakao.maps.LatLng(
        this.sampleLikeLocationList[0].lat,
        this.sampleLikeLocationList[0].lon
      );

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      this.map.setCenter(iwPosition);
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(36.35515, 127.298415),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=e22041108e144667e2284ec7ed6bc357&libraries=services,clusterer,drawing";
      // script.src =
      //   "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" +
      //   process.env.VUE_APP_APT_DEAL_API_KEY;
      document.head.appendChild(script);
    }
  },
};
</script>

<style>
.dot {
  overflow: hidden;
  float: left;
  width: 12px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/mini_circle.png");
}
.dotOverlay {
  position: relative;
  bottom: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
  font-size: 12px;
  padding: 5px;
  background: #fff;
}
.dotOverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.number {
  font-weight: bold;
  color: #ee6152;
}
.dotOverlay:after {
  content: "";
  position: absolute;
  margin-left: -6px;
  left: 50%;
  bottom: -8px;
  width: 11px;
  height: 8px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white_small.png");
}
.distanceInfo {
  position: relative;
  top: 5px;
  left: 5px;
  list-style: none;
  margin: 0;
}
.distanceInfo .label {
  display: inline-block;
  width: 50px;
}
.distanceInfo:after {
  content: none;
}
</style>
