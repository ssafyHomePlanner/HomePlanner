<template>
  <v-container fill-height fluid class="ma-8">
    <v-row>
      <v-col>
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
                  </v-row>
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
    <v-container class="mt-5 mb-8">
      <v-btn class="mx-2" fab dark large color="purple">
        <v-icon dark> mdi-android </v-icon>
      </v-btn>
    </v-container>
  </v-container>
</template>

<script>
export default {
  methods: {
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

<style></style>
