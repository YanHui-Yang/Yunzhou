<template>
  <div class="amap-page-container" style="height: 430px; background: #ccc">
    <el-amap-search-box
      class="search-box"
      :search-option="searchOption"
      :on-search-result="onSearchResult"
    ></el-amap-search-box>
    <el-amap
      vid="amapDemo"
      :center="center"
      :zoom="zoom"
      :plugin="plugin"
      :events="events"
      style="margin-top: -10px; margin-bottom: -3px"
    >
      <el-amap-marker
        v-for="(marker, index) in markers"
        :position="marker"
        :key="'marker' + index"
      ></el-amap-marker>
    </el-amap>
    <div class="toolbar" v-if="loaded">
      <!-- position: [{{ lng }}, {{ lat }}]  -->
      <!-- <strong>地址：</strong> -->
      <el-col :span="20">
        <el-input
          style="color: #f34d37; margin-top: 44px"
          size="mini"
          v-model="address"
        ></el-input>
      </el-col>
    </div>
    <div v-else>正在定位</div>
  </div>
</template>

<style scoped>
.search-box {
  position: absolute;
  top: 15px;
  left: 15px;
}

.amap-page-container {
  position: relative;
}

.toolbar {
  padding: 10px;
}
</style>

<script>
export default {
  name: 'Amap',
  data: function () {
    let self = this

    return {
      markers: [
        // [121.59996, 31.197646],
        // [121.40018, 31.197622],
        // [121.69991, 31.207649],
      ],
      searchOption: {
        city: '广州',
        citylimit: false,
      },
      zoom: 18,
      loaded: false,
      center: [113.144395, 23.279154],
      plugin: [
        {
          pName: 'Geolocation',
          events: {
            init(o) {
              // o 是高德地图定位插件实例
              o.getCurrentPosition((status, result) => {
                if (result && result.position) {
                  self.lng = result.position.lng
                  self.lat = result.position.lat
                  self.center = [self.lng, self.lat]
                  self.address = result.formattedAddress
                  self.loaded = true
                  self.$nextTick()
                }
              })
            },
          },
        },
      ],
      address: '',
      events: {
        click(e) {
          let { lng, lat } = e.lnglat
          self.lng = lng
          self.lat = lat

          // 这里通过高德 SDK 完成。
          var geocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: 'all',
          })
          geocoder.getAddress([lng, lat], function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
              if (result && result.regeocode) {
                self.address = result.regeocode.formattedAddress
                self.$nextTick()
              }
            }
          })
        },
      },
      lng: 0,
      lat: 0,
    }
  },
  methods: {
    addMarker: function () {
      let lng = 121.5 + Math.round(Math.random() * 1000) / 10000
      let lat = 31.197646 + Math.round(Math.random() * 500) / 10000
      this.markers.push([lng, lat])
    },
    onSearchResult(pois) {
      let latSum = 0
      let lngSum = 0
      if (pois.length > 0) {
        pois.forEach((poi) => {
          let { lng, lat } = poi
          lngSum += lng
          latSum += lat
          this.markers.push([poi.lng, poi.lat])
        })
        let mapcenter = {
          lng: lngSum / pois.length,
          lat: latSum / pois.length,
        }
        this.center = [mapcenter.lng, mapcenter.lat]
      }
    },
  },
  watch: {
    address: {
      handler: function (val, oldVal) {
        this.address = val
        this.$emit('sendAddress', this.address)
      },
      // 深度观察监听
      deep: true,
    },
  },
}
</script>