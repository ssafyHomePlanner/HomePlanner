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
                <v-sheet
                  color="white"
                  elevation="1"
                  height="45"
                  width="270"
                  @click="searchEndLocationAddress"
                  >{{ endLocation.name ? endLocation.name : endLocation.address }}</v-sheet
                >
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
            <v-tabs v-model="tab">
              <v-tab>찜한 아파트</v-tab>
              <v-tab>아파트 검색</v-tab>
            </v-tabs>
            <v-tabs-items v-model="tab">
              <v-tab-item>
                <v-container style="width: 500px; height: 500px">
                  <v-virtual-scroll bench="5" :items="items" height="300" item-height="64">
                    <template v-slot:default="{ item }">
                      <v-list-item :key="item">
                        <v-list-item-action>
                          <v-btn fab small depressed color="primary">
                            {{ item }}
                          </v-btn>
                        </v-list-item-action>

                        <v-list-item-content>
                          <v-list-item-title>
                            User Database Record <strong>ID {{ item }}</strong>
                          </v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>

                      <v-divider></v-divider>
                    </template>
                  </v-virtual-scroll>
                </v-container>
              </v-tab-item>
              <v-tab-item>2</v-tab-item>
            </v-tabs-items>
            <!-- <v-btn @click="displayMarker(markerPositions1)">테스트 마커 표시</v-btn>
            <v-btn @click="displayInfoWindow">테스트 메시지 표시</v-btn> -->
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
      tab: null,
      startLocation: {
        address: "",
        name: "",
        lat: "",
        lon: "",
      },
      endLocation: {
        address: "",
        name: "",
        lat: "",
        lon: "",
      },
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441],
      ],
      markers: [],
      infowindow: null,
    };
  },
  computed: {
    items() {
      return Array.from({ length: this.length }, (k, v) => v + 1);
    },
    length() {
      return 7000;
    },
  },
  methods: {
    searchStartLocationAddress() {
      let _this = this;
      new window.daum.Postcode({
        oncomplete: function (data) {
          //아파트 이름이 있을 경우 아파트 이름을 넣어줌
          if (data.buildingName) {
            _this.startLocation.name = data.buildingName;
          }
          _this.startLocation.address = data.address;
          // 주소-좌표 변환 객체를 생성합니다
          let geocoder = new kakao.maps.services.Geocoder();

          // 주소로 좌표를 검색합니다
          geocoder.addressSearch(_this.startLocation.address, function (result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {
              let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

              // 인포윈도우로 장소에 대한 설명을 표시합니다
              let infowindow = new kakao.maps.InfoWindow({
                content: `<div style="width:150px;text-align:center;padding:6px 0;">${_this.startLocation.address}</div>`,
              });

              let startSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png", // 출발 마커이미지의 주소입니다
                startSize = new kakao.maps.Size(50, 45), // 출발 마커이미지의 크기입니다
                startOption = {
                  offset: new kakao.maps.Point(15, 43), // 출발 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
                };

              // 출발 마커 이미지를 생성합니다
              let startImage = new kakao.maps.MarkerImage(startSrc, startSize, startOption);

              // 출발 마커를 생성합니다
              // 결과값으로 받은 위치를 마커로 표시합니다
              let startMarker = new kakao.maps.Marker({
                map: _this.map, // 출발 마커가 지도 위에 표시되도록 설정합니다
                position: coords,
                image: startImage, // 출발 마커이미지를 설정합니다
              });

              infowindow.open(_this.map, startMarker);

              // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
              _this.map.setCenter(coords);
            }
          });
        },
      }).open();
    },
    searchEndLocationAddress() {
      let _this = this;
      new window.daum.Postcode({
        oncomplete: function (data) {
          //아파트 이름이 있을 경우 아파트 이름을 넣어줌
          if (data.buildingName) {
            _this.endLocation.name = data.buildingName;
          }

          _this.endLocation.address = data.address;
          // 주소-좌표 변환 객체를 생성합니다
          let geocoder = new kakao.maps.services.Geocoder();

          // 주소로 좌표를 검색합니다
          geocoder.addressSearch(_this.endLocation.address, function (result, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {
              let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

              // 인포윈도우로 장소에 대한 설명을 표시합니다
              let infowindow = new kakao.maps.InfoWindow({
                content: `<div style="width:150px;text-align:center;padding:6px 0;">${_this.endLocation.address}</div>`,
              });

              let arriveSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png", // 도착 마커이미지 주소입니다
                arriveSize = new kakao.maps.Size(50, 45), // 도착 마커이미지의 크기입니다
                arriveOption = {
                  offset: new kakao.maps.Point(15, 43), // 도착 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
                };

              // 도착 마커 이미지를 생성합니다
              let arriveImage = new kakao.maps.MarkerImage(arriveSrc, arriveSize, arriveOption);

              // 도착 마커를 생성합니다
              // 결과값으로 받은 위치를 마커로 표시합니다
              let arriveMarker = new kakao.maps.Marker({
                map: _this.map, // 도착 마커가 지도 위에 표시되도록 설정합니다
                position: coords,
                image: arriveImage, // 도착 마커이미지를 설정합니다
              });

              infowindow.open(_this.map, arriveMarker);

              // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
              _this.map.setCenter(coords);
            }
          });
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

      let iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
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
