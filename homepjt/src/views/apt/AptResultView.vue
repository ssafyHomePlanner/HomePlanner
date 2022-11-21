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
      <v-col>아이콘 영역 </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-col class="ml-8 mr-8 pl-8 pr-8">
          <v-data-table
            :headers="headers"
            :items="desserts"
            :items-per-page="5"
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
              <v-btn color="primary" elevation="3" large>등록</v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import LineChart from "@/components/chart/LineChart.vue";
import { mapState, mapActions } from "vuex";
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
        {
          text: "Dessert (100g serving)",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Calories", value: "calories" },
        { text: "Fat (g)", value: "fat" },
        { text: "Carbs (g)", value: "carbs" },
        { text: "Protein (g)", value: "protein" },
        { text: "Iron (%)", value: "iron" },
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%",
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%",
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%",
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%",
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%",
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%",
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%",
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%",
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
      ],
    };
  },
  methods: {
    ...mapActions(houseInfoStore, ["getHouseInfoDeal"]),
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
