<template>
  <v-container fill-height fluid class="ma-5">
    <v-row justify="start">
      <v-col cols="auto">
        <v-container id="map" style="width: 925px; height: 625px">
        </v-container>
      </v-col>
      <v-col>
        <v-container class="heart-shape">
          <v-row justify="center" class="align-center">
            <v-btn icon color="pink">
              <v-icon large color="red darken-2"> mdi-heart </v-icon>
            </v-btn>
            <span class="ml-2 mr-3"> {{ houseInfo.likeCnt }} 회 </span>
            <v-icon large color="#999999"> mdi-eye </v-icon>
            <span class="ml-2 mr-3"> {{ houseInfo.viewCnt }} 회 </span>
          </v-row>
        </v-container>
        <div class="apt-item-middle-text">
          아파트 명: {{ houseInfo.apartmentName }}
        </div>
        <div class="apt-item-middle-text">
          건축년도: {{ houseInfo.buildYear }}년
        </div>
        <div class="apt-item-middle-text">동이름: {{ houseInfo.dong }}</div>
        <div class="apt-item-middle-text">
          도로명주소: {{ houseInfo.roadName }}
        </div>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-col class="ml-8 mr-8 pl-8 pr-8">
          <v-data-table
            :headers="headers"
            :items="houseDealList"
            :items-per-page="10"
            class="elevation-1"
          ></v-data-table>
        </v-col>
      </v-col>
      <v-col> <LineChart /> </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row>
      <v-col cols="auto">
        <v-container>
          <v-row>
            <v-col cols="col-8">
              <v-text-field
                counter="25"
                hint="댓글을 작성하세요"
                label="댓글"
                solo
                style="min-width: 550px"
              ></v-text-field>
            </v-col>
            <v-col cols="col-4">
              <v-btn
                color="primary"
                elevation="3"
                large
                @click="printHouseDealList"
                >등록</v-btn
              >
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LineChart from "@/components/chart/LineChart.vue";
import { mapState } from "vuex";
const houseInfoStore = "houseInfoStore";

export default {
  components: {
    LineChart,
  },
  computed: {
    ...mapState(houseInfoStore, ["houseInfo", "houseDealList"]),
  },
  data() {
    return {
      headers: [
        { text: "거래년도", value: "dealYear" },
        { text: "전용미터 (m2)", value: "area" },
        { text: "층", value: "floor" },
        { text: "거래 금액", value: "dealAmount" },
      ],
    };
  },
  methods: {
    printHouseDealList() {
      console.log(this.houseDealList);
    },
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(
          this.$store.state.houseInfoStore.houseInfo.lat,
          this.$store.state.houseInfoStore.houseInfo.lon
        ),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);

      // 마커가 표시될 위치입니다
      let markerPosition = new kakao.maps.LatLng(
        this.$store.state.houseInfoStore.houseInfo.lat,
        this.$store.state.houseInfoStore.houseInfo.lon
      );

      // 마커를 생성합니다
      let marker = new kakao.maps.Marker({
        position: markerPosition,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      marker.setMap(this.map);

      // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
      let zoomControl = new kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
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
.apt-item-middle-text {
  margin-top: 25px;
  margin-bottom: 25px;
  font-weight: bold;
  text-align: left;
  font-size: 22px;
}

.heart-shape {
  position: relative;
  top: 8px;
}
</style>
