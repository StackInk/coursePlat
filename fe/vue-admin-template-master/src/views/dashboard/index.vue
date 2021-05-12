<template>
  <div class="dashboard-container">
    <div class="dashboard-text">用户名:{{ name }}</div>
    <div class="dashboard-text">用户角色:<span v-for="(role,index) in roles" :key="index">{{ role.name }}</span></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {

    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  methods: {
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
