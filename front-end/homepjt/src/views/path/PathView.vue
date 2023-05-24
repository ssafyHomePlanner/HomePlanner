<template>
  <v-container fill-height fluid class="ma-8">
    <v-row>
      <v-col cols="auto">
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
        </v-container>
        <AptSearchTab v-on:clickLikeApartment="clickLikeApartment" />
      </v-col>
      <v-col cols="auto">
        <v-container id="map" style="width: 925px; height: 625px">
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
                  <v-icon @click="deleteAparment(apartment.aptCode)"
                    >mdi-close</v-icon
                  >
                </v-btn>
              </v-row>
              <v-row justify="center" class="item-middle-box-text mt-4"
                >{{ apartment.apartmentName }}
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-3">
                {{ apartment.dong }} ({{ apartment.roadName }})
              </v-row>
            </v-col>
          </v-row>
        </v-sheet>
      </v-row>
    </v-container>
    <v-container style="width: 100%; height: 200px">
      <v-btn
        style="height: 60px"
        block
        color="primary"
        @click="searchOptimalPath"
        :disabled="
          startLocation.address.length < 1 || endLocation.address.length < 1
        "
      >
        최적 경로 탐색하기
      </v-btn>
    </v-container>
  </v-container>
</template>

<script>
import AptSearchTab from "@/components/apt/AptSearchTab.vue";

import { mapActions } from "vuex";

const bookmarkStore = "bookmarkStore";

export default {
  components: {
    AptSearchTab,
  },
  data() {
    return {
      tab: null,
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
      startMarker: null,
      endMarker: null,
    };
  },
  methods: {
    ...mapActions(bookmarkStore, ["getPathInfoList"]),
    async searchOptimalPath() {
      let requestList = [];

      let startLocationName = this.startLocation.name
        ? this.startLocation.name
        : this.startLocation.address;

      let endLocationName = this.endLocation.name
        ? this.endLocation.name
        : this.endLocation.address;

      let start = {
        aptCode: 0,
        aptName: startLocationName,
        lat: this.startLocation.lat,
        lon: this.startLocation.lon,
      };

      let end = {
        aptCode: 0,
        aptName: endLocationName,
        lat: this.endLocation.lat,
        lon: this.endLocation.lon,
      };

      // console.log("start", start);
      // console.log("end", end);

      requestList.push(start);

      this.pathList.forEach((element) => {
        let temp = {
          aptCode: element.aptCode,
          aptName: element.apartmentName,
          lat: element.lat,
          lon: element.lon,
        };
        requestList.push(temp);
      });

      requestList.push(end);

      console.log("requestList", requestList);
      await this.getPathInfoList(requestList);

      this.movePathResultView();
    },
    movePathResultView() {
      this.$router.push({ name: "pathResult" }).catch(() => {});
    },
    clickLikeApartment(location) {
      let cnt = 0;
      console.log(location);

      if (this.pathList.length == 0) {
        this.pathList.push(location);
      } else {
        this.pathList.forEach((item) => {
          console.log("item : " + item);
          if (item.aptCode === location.aptCode) {
            console.log("같음");
            cnt++;
          }
        });

        if (cnt === 0) {
          this.pathList.push(location);
        }
      }

      //마커 추가
      let gps = [location.lat, location.lon];
      this.markerPositions.push(gps);
      this.displayMarker(this.markerPositions);

      // x 표시 누르면, 삭제된 리스트 되돌리기
    },
    deleteAparment(aptCode) {
      this.pathList.forEach((item, index) => {
        if (item.aptCode === aptCode) {
          this.pathList.splice(index, 1);
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
                _this.startLocation.lat = result[0].y;
                _this.startLocation.lon = result[0].x;

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

                if (_this.startMarker) {
                  _this.startMarker.setMap(null);
                  _this.startMarker = null;
                }

                // 출발 마커를 생성합니다
                // 결과값으로 받은 위치를 마커로 표시합니다
                _this.startMarker = new kakao.maps.Marker({
                  map: _this.map, // 출발 마커가 지도 위에 표시되도록 설정합니다
                  position: coords,
                  image: startImage, // 출발 마커이미지를 설정합니다
                });

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
                _this.endLocation.lat = result[0].y;
                _this.endLocation.lon = result[0].x;

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

                if (_this.endMarker) {
                  _this.endMarker.setMap(null);
                  _this.endMarker = null;
                }

                // 도착 마커를 생성합니다
                // 결과값으로 받은 위치를 마커로 표시합니다
                _this.endMarker = new kakao.maps.Marker({
                  map: _this.map, // 도착 마커가 지도 위에 표시되도록 설정합니다
                  position: coords,
                  image: arriveImage, // 도착 마커이미지를 설정합니다
                });

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
