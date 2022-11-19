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
                  >{{
                    startLocation.name
                      ? startLocation.name
                      : startLocation.address
                  }}</v-sheet
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
                  >{{
                    endLocation.name ? endLocation.name : endLocation.address
                  }}</v-sheet
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
                    <div class="path-item-middle-text">
                      아파트 경유지를 추가하세요
                    </div>
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
                <v-container style="width: 500px; height: 280px">
                  <v-virtual-scroll
                    bench="5"
                    :items="sampleLikeLocationList"
                    height="270"
                    item-height="64"
                  >
                    <template v-slot:default="{ item }">
                      <v-list-item
                        :key="item.name"
                        @click="clickLikeApartment(item)"
                      >
                        <v-list-item-content>
                          <v-list-item-title>
                            <strong>{{ item.name }}</strong>
                          </v-list-item-title>
                          <v-list-item-subtitle>
                            {{ item.address }}
                          </v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider></v-divider>
                    </template>
                  </v-virtual-scroll>
                </v-container>
              </v-tab-item>
              <v-tab-item>
                <v-container style="width: 500px; height: 280px">
                  아파트 검색 화면
                </v-container>
              </v-tab-item>
            </v-tabs-items>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-container class="mt-7 mb-12 ml-1" style="width: 100%; height: 280px">
      <v-row class="path-item-middle-text mb-7" justify="start">
        설정된 경유지 목록
      </v-row>
      <v-row justify="start">
        <v-sheet
          v-for="(apartment, index) in pathList"
          :key="index"
          :apartment="apartment"
          height="120"
          width="200"
          elevation="4"
          class="pl-4 pt-4 mr-5"
          rounded="xl"
        >
          <v-row class="mr-0 ml-2">
            <v-col cols="auto">
              <v-row justify="end">
                <v-btn icon>
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </v-row>
              <v-row justify="center" class="item-middle-box-text mt-4"
                >{{ apartment.name }}
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-3">
                {{ apartment.address }}
              </v-row>
            </v-col>
          </v-row>
        </v-sheet>
      </v-row>
    </v-container>
    <v-container style="width: 100%; height: 200px">
      <v-btn block color="primary" @click="movePathResultView">
        최적 경로 탐색하기
      </v-btn>
    </v-container>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      tab: null,
      sampleLikeLocationList: [
        {
          address: "경기도 부천시 중동로 02",
          name: "팰리스카운티",
          lon: "126.766986471789",
          lat: "37.4918092437981",
        },
        {
          address: "경기도 부천시 중동로 02",
          name: "그린타운(삼성)",
          lon: "126.769215401626",
          lat: "37.4981077694787",
        },
        {
          address: "경기도 부천시 중동로 03",
          name: "리첸시아중동",
          lon: "126.779310556166",
          lat: "37.4953683630967",
        },
      ],
      pathList: [],
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
      markerPositions: [],
      markers: [],
      infowindow: null,
    };
  },
  methods: {
    movePathResultView() {
      this.$router.push({ name: "pathResult" });
    },
    clickLikeApartment(location) {
      this.pathList.push(location);

      //마커 추가
      let gps = [location.lat, location.lon];
      this.markerPositions.push(gps);
      this.displayMarker(this.markerPositions);

      this.sampleLikeLocationList.forEach((item, index) => {
        if (item.name === location.name) {
          this.sampleLikeLocationList.splice(index, 1);
        }
      });
    },
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
          geocoder.addressSearch(
            _this.startLocation.address,
            function (result, status) {
              // 정상적으로 검색이 완료됐으면
              if (status === kakao.maps.services.Status.OK) {
                let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                let infowindow = new kakao.maps.InfoWindow({
                  content: `<div style="width:150px;text-align:center;padding:6px 0;">${_this.startLocation.address}</div>`,
                  removable: true,
                });

                let startSrc =
                    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png", // 출발 마커이미지의 주소입니다
                  startSize = new kakao.maps.Size(50, 45), // 출발 마커이미지의 크기입니다
                  startOption = {
                    offset: new kakao.maps.Point(15, 43), // 출발 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
                  };

                // 출발 마커 이미지를 생성합니다
                let startImage = new kakao.maps.MarkerImage(
                  startSrc,
                  startSize,
                  startOption
                );

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
            }
          );
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
          geocoder.addressSearch(
            _this.endLocation.address,
            function (result, status) {
              // 정상적으로 검색이 완료됐으면
              if (status === kakao.maps.services.Status.OK) {
                let coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                let infowindow = new kakao.maps.InfoWindow({
                  content: `<div style="width:150px;text-align:center;padding:6px 0;">${_this.endLocation.address}</div>`,
                  removable: true,
                });

                let arriveSrc =
                    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png", // 도착 마커이미지 주소입니다
                  arriveSize = new kakao.maps.Size(50, 45), // 도착 마커이미지의 크기입니다
                  arriveOption = {
                    offset: new kakao.maps.Point(15, 43), // 도착 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
                  };

                // 도착 마커 이미지를 생성합니다
                let arriveImage = new kakao.maps.MarkerImage(
                  arriveSrc,
                  arriveSize,
                  arriveOption
                );

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
            }
          );
        },
      }).open();
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

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      let zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );

      // 마커 이미지의 이미지 주소입니다
      let imageSrc =
        "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      if (positions.length > 0) {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position: position,
              image: markerImage, // 마커 이미지
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
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
