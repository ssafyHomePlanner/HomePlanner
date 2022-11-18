<template>
  <v-container fill-height fluid class="ma-8">
    <v-row>
      <v-col>
        <div id="map"></div>
      </v-col>
      <v-col>
        <v-container>
          <v-row>
            <v-col cols="auto" class="mr-5">
              <v-row justify="start">
                <v-col cols="auto">
                  <v-row class="item-middle-box-text mt-4" justify="start">
                    <div class="path-item-middle-text">출발지를 입력하세요</div>
                  </v-row>
                </v-col>
                <v-col cols="auto" class="pa-0 mt-3">
                  <v-icon align-self="start">mdi-help-circle-outline</v-icon>
                </v-col>
              </v-row>
              <v-row class="mt-6">
                <v-sheet
                  color="white"
                  elevation="1"
                  height="45"
                  width="270"
                  @click="searchStartLocationAddress"
                  >{{ startLocation.name ? startLocation.name : startLocation.address }}</v-sheet
                >
              </v-row>
            </v-col>
            <v-col cols="auto" class="mr-5 mt-8">
              <v-icon size="90" color="darken-2"> mdi-arrow-right </v-icon>
            </v-col>
            <v-col cols="auto" class="mr-5">
              <v-row justify="start">
                <v-col cols="auto">
                  <v-row class="item-middle-box-text mt-4" justify="start">
                    <div class="path-item-middle-text">도착지를 입력하세요</div>
                  </v-row>
                </v-col>
                <v-col cols="auto" class="pa-0 mt-3">
                  <v-icon align-self="start">mdi-help-circle-outline</v-icon>
                </v-col>
              </v-row>
              <v-row class="mt-6">
                <v-sheet color="white" elevation="1" height="45" width="270"></v-sheet>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
        <v-container class="mt-12">
          <v-row>
            <v-col cols="auto" class="mr-5">
              <v-row justify="start">
                <v-col cols="auto">
                  <v-row class="item-middle-box-text mt-4" justify="start">
                    <div class="path-item-middle-text">아파트 경유지를 추가하세요</div>
                  </v-row>
                </v-col>
                <v-col cols="auto" class="pa-0 mt-3">
                  <v-icon align-self="start">mdi-help-circle-outline</v-icon>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row>
            <v-btn @click="displayMarker(markerPositions1)">테스트 마커 표시</v-btn>
            <v-btn @click="displayInfoWindow">테스트 메시지 표시</v-btn>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      startLocation: {
        address: "",
        name: "",
        lat: "",
        lon: "",
      },
      address: "asdsads",
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441],
      ],
      markers: [],
      infowindow: null,
    };
  },
  methods: {
     searchStartLocationAddress() {
      let _this = this;
      new window.daum.Postcode({
        oncomplete:  function (data) {
          //아파트 이름이 있을 경우 아파트 이름을 넣어줌
          if (data.buildingName) {
            _this.startLocation.name = data.buildingName;
          }

          _this.startLocation.address = data.address;
          // _this.searchAddressToGPS(data.address).then((data) => {
          //   console.log("gps", data);
          // });

        },
      }).open();
    },
    searchAddressToGPS(address) {
      // 주소-좌표 변환 객체를 생성합니다
      let geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(address, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          console.log(coords);
          return coords;
        }
      });
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map((position) => new kakao.maps.LatLng(...position));

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
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

.path-item-middle-text {
  font-weight: bold;
  text-align: center;
  font-size: 22px;
}
</style>
