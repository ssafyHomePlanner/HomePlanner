<template>
  <v-container fill-height fluid class="ma-8">
    <v-row>
      <v-col cols="auto">
        <v-container>
          <v-row justify="space-between">
            <v-col>
              <v-btn-toggle
                v-model="pathResultType"
                tile
                color="deep-purple accent-3"
                group
              >
                <v-btn value="time"> 시간순 </v-btn>
                <v-btn value="distance"> 거리순 </v-btn>
              </v-btn-toggle>
            </v-col>
            <v-col cols="auto">
              <v-btn class="mt-4" color="primary"
                ><v-icon>mdi-content-save</v-icon>경로 저장</v-btn
              >
              <v-btn @click="makeLine">테스트 버튼</v-btn>
              <v-btn @click="printData">데이터 출력</v-btn>
            </v-col>
          </v-row>
        </v-container>
        <v-container id="map" style="width: 925px; height: 625px">
        </v-container>
      </v-col>
      <v-col cols="auto" class="mr-5 mt-12">
        <v-container class="ml-0 mr-5 pr-1" style="width: 350px; height: 500px">
          <v-virtual-scroll
            bench="5"
            :items="sampleLikeLocationList"
            height="270"
            item-height="80"
            class="pa-1 mt-3"
          >
            <template v-slot:default="{ item }">
              <v-list-item :key="item.name">
                <v-list-item-content>
                  <v-breadcrumbs :items="items" class="pa-2">
                    <template v-slot:divider>
                      <v-container style="width: 100px; height: 50px">
                        <v-row justify="center">
                          <v-icon large>mdi-forward</v-icon>
                          예상 시간: 30분
                        </v-row>
                      </v-container>
                    </template>
                  </v-breadcrumbs>
                </v-list-item-content>
              </v-list-item>
              <v-divider></v-divider>
            </template>
          </v-virtual-scroll>
        </v-container>
        <v-container style="width: 350px; height: 100px">
          <h2>총 거리: {{ timePathList[0].dist }}</h2>
          <br />
          <h3>총 예상 시간: {{ timePathList[0].time }}</h3>
        </v-container>
      </v-col>
    </v-row>
    <v-container class="mt-7 mb-12 ml-1" style="width: 100%; height: 280px">
      <v-row>
        <v-col class="ma-5">
          <v-stepper
            alt-labels
            v-for="(data, index) in timePathList"
            :key="index"
          >
            <v-stepper-header>
              <div
                v-for="(element, index) in data.pathList"
                :key="index"
                style="display: inline"
              >
                <v-stepper-step :step="index + 1">
                  {{ element.aptName }}
                </v-stepper-step>
                <v-divider></v-divider>
              </div>
            </v-stepper-header>
          </v-stepper>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

const bookmarkStore = "bookmarkStore";

export default {
  computed: {
    ...mapState(bookmarkStore, [
      "timePathList",
      "distPathList",
      "timeArr",
      "distArr",
    ]),
  },
  data() {
    return {
      items: [
        {
          text: "출발지",
        },
        {
          text: "도착지",
        },
      ],
      clickLine: {},
      distanceOverlay: null,
      pathResultType: "time",
      dots: [],
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
    printData() {
      console.log("timePathList", this.timePathList);
      console.log("distPathList", this.distPathList);
      console.log("timeArr", this.timeArr);
      console.log("distArr", this.distArr);
    },
    showDistance(content, position) {
      if (this.distanceOverlay) {
        // 커스텀오버레이가 생성된 상태이면

        // 커스텀 오버레이의 위치와 표시할 내용을 설정합니다
        this.distanceOverlay.setPosition(position);
        this.distanceOverlay.setContent(content);
      } else {
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
      // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
      let walkkTime = (distance / 67) | 0;
      let walkHour = "",
        walkMin = "";

      // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
      if (walkkTime > 60) {
        walkHour =
          '<span class="number">' + Math.floor(walkkTime / 60) + "</span>시간 ";
      }
      walkMin = '<span class="number">' + (walkkTime % 60) + "</span>분";

      // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
      let bycicleTime = (distance / 227) | 0;
      let bycicleHour = "",
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
      let content = '<ul class="dotOverlay distanceInfo">';
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
    // 선이 그려지고 있는 상태일 때 지도를 클릭하면 호출하여
    // 클릭 지점에 대한 정보 (동그라미와 클릭 지점까지의 총거리)를 표출하는 함수입니다
    displayCircleDot(position, distance) {
      // 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
      let circleOverlay = new kakao.maps.CustomOverlay({
        content: '<span class="dot"></span>',
        position: position,
        zIndex: 1,
      });

      // 지도에 표시합니다
      circleOverlay.setMap(this.map);

      if (distance > 0) {
        // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
        let distanceOverlay = new kakao.maps.CustomOverlay({
          content:
            '<div class="dotOverlay">거리 <span class="number">' +
            distance +
            "</span>m</div>",
          position: position,
          yAnchor: 1,
          zIndex: 2,
        });

        // 지도에 표시합니다
        distanceOverlay.setMap(this.map);
      }

      // 배열에 추가합니다
      this.dots.push({ circle: circleOverlay, distance: this.distanceOverlay });
    },
    makeLine() {
      let linePath = [];

      // 만들어질 경로의 위도/경도를 넣는다.
      this.sampleLikeLocationList.forEach((element) => {
        linePath.push(new kakao.maps.LatLng(element.lat, element.lon));
      });

      linePath.forEach((element) => {
        // 클릭한 지점에 대한 정보를 지도에 표시합니다
        this.displayCircleDot(element, 0);
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

      let distance = Math.round(polyline.getLength()), // 선의 총 거리를 계산합니다
        content = this.getTimeHTML(distance); // 커스텀오버레이에 추가될 내용입니다

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
#map {
  width: 100%;
  height: 600px;
}
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
