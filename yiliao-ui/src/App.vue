<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import { getNotice } from "@/api/menu";
export default {
  name: "App",
  components: { ThemePicker },
  created() {
    // setInterval(() => {
    //   this.getNotice();
    // },10000)
  },
  methods:{
    getNotice(){
      getNotice().then(response => {
        // this.$modal.msgSuccess("修改成功");
      });
    }
  },
    metaInfo() {
        return {
            title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
            titleTemplate: title => {
                return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
            }
        }
    }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
